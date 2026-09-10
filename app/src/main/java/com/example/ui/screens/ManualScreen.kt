package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ManualSection
import com.example.data.model.RoomItem
import com.example.data.repository.HouseRepository
import com.example.ui.components.HouseHeader
import com.example.ui.components.HouseMapSection
import com.example.ui.components.RoomsGallerySection
import com.example.ui.components.SectionCard

@Composable
fun ManualScreen(
    sections: List<ManualSection>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onSectionClick: (ManualSection) -> Unit,
    onFeedback: (String) -> Unit,
    rooms: List<RoomItem> = HouseRepository.houseRooms,
    onNavigateToMapPoint: ((String) -> Unit)? = null,
    onNavigateToManual: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val filteredSections = if (searchQuery.isBlank()) {
        sections
    } else {
        val query = searchQuery.lowercase().trim()
        sections.filter { section ->
            section.title.lowercase().contains(query) ||
                    section.subtitle.lowercase().contains(query) ||
                    section.shortSummary.lowercase().contains(query) ||
                    section.items.any {
                        it.title.lowercase().contains(query) ||
                                it.description.lowercase().contains(query) ||
                                it.steps.any { step -> step.lowercase().contains(query) }
                    } ||
                    section.warnings.any { it.lowercase().contains(query) } ||
                    section.tips.any { it.lowercase().contains(query) }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("manual_screen_list"),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        item {
            HouseHeader(
                searchQuery = searchQuery,
                onSearchChange = onSearchChange,
                onFeedback = onFeedback
            )
        }

        // Quick House Stats Row (Only if not searching)
        if (searchQuery.isBlank()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickBadge(
                        icon = Icons.Default.NightsStay,
                        title = "Silêncio",
                        subtitle = "22h às 07h",
                        modifier = Modifier.weight(1f)
                    )
                    QuickBadge(
                        icon = Icons.Default.DirectionsCar,
                        title = "Garagem",
                        subtitle = "3 vagas (1 cob.)",
                        modifier = Modifier.weight(1f)
                    )
                    QuickBadge(
                        icon = Icons.Default.Bed,
                        title = "Check-in",
                        subtitle = "A partir das 13h",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Rooms & House Visual Gallery Section (Airbnb Layout)
            item {
                RoomsGallerySection(
                    rooms = rooms,
                    onRoomClick = { /* Handled in dialog */ },
                    onNavigateToMapPoint = onNavigateToMapPoint,
                    onNavigateToManual = onNavigateToManual
                )
            }

            // Interactive House Map & Key Points Section (Planta e Grade Interativa)
            item {
                HouseMapSection(
                    onNavigateToMapPoint = onNavigateToMapPoint,
                    onNavigateToManual = onNavigateToManual
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Capítulos do Manual (1 a 10)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "10 tópicos",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Section Cards
        if (filteredSections.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Nenhum item encontrado para \"$searchQuery\"",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Tente pesquisar por 'wifi', 'chave', 'tv', 'degrau' ou 'lixeira'.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(
                items = filteredSections,
                key = { it.id }
            ) { section ->
                SectionCard(
                    section = section,
                    onClick = { onSectionClick(section) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun QuickBadge(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
