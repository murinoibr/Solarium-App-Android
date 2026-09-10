package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LocalRecommendation

@Composable
fun RecommendationsScreen(
    recommendations: List<LocalRecommendation>,
    selectedCategory: String,
    favoriteIds: Set<String>,
    onSelectCategory: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onFeedback: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var query by remember { mutableStateOf("") }

    val categories = remember {
        listOf(
            "Todos",
            "Favoritos",
            "Passeios Clássicos",
            "Gastronomia Mineira",
            "Cafés & Degustação",
            "Aventura & Vista",
            "Lazer & Relaxamento"
        )
    }

    val filteredList = remember(recommendations, selectedCategory, favoriteIds, query) {
        recommendations.filter { item ->
            val matchesCategory = when (selectedCategory) {
                "Todos" -> true
                "Favoritos" -> favoriteIds.contains(item.id)
                else -> item.category == selectedCategory
            }
            val matchesQuery = query.isBlank() ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.description.contains(query, ignoreCase = true) ||
                    item.hostTip.contains(query, ignoreCase = true) ||
                    item.tags.any { it.contains(query, ignoreCase = true) }

            matchesCategory && matchesQuery
        }
    }

    fun openInMaps(rec: LocalRecommendation) {
        try {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode("${rec.title}, São Lourenço - MG")}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode("${rec.title}, São Lourenço - MG")}")
            )
            context.startActivity(webIntent)
        }
    }

    fun copyAddress(address: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Endereço", address)
        clipboard.setPrimaryClip(clip)
        onFeedback("Endereço copiado!")
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("recommendations_screen_list"),
        contentPadding = PaddingValues(bottom = 36.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Title and Description Banner
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Dicas & Recomendações",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "O melhor de São Lourenço e região com curadoria da anfitriã",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Search Bar
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Buscar restaurante, balão, café, passeio...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("rec_search_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { onSelectCategory(cat) },
                            label = {
                                if (cat == "Favoritos") {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Default.Favorite,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Salvos (${favoriteIds.size})")
                                    }
                                } else {
                                    Text(cat)
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Empty state
        if (filteredList.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (selectedCategory == "Favoritos") "Nenhum favorito salvo ainda!" else "Nenhuma recomendação encontrada.",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (selectedCategory == "Favoritos") "Toque no ícone de coração nos cartões para guardar seus passeios preferidos." else "Tente selecionar outra categoria ou limpar a busca.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(
                items = filteredList,
                key = { it.id }
            ) { item ->
                RecommendationCard(
                    recommendation = item,
                    isFavorite = favoriteIds.contains(item.id),
                    onToggleFavorite = { onToggleFavorite(item.id) },
                    onNavigate = { openInMaps(item) },
                    onCopyAddress = { copyAddress(item.address) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun RecommendationCard(
    recommendation: LocalRecommendation,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onNavigate: () -> Unit,
    onCopyAddress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("rec_card_${recommendation.id}"),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = recommendation.category,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        if (recommendation.isMustVisit) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "Imperdível",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = recommendation.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.testTag("fav_btn_${recommendation.id}")
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favoritar",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = recommendation.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Insider Host Tip
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "Dica da Valéria",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Dica da Anfitriã Valéria:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = recommendation.hostTip,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Info tags: Price & Location
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = recommendation.address,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = recommendation.priceLevel,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onCopyAddress,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Endereço", style = MaterialTheme.typography.labelMedium)
                }

                Button(
                    onClick = onNavigate,
                    modifier = Modifier.weight(1.3f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Ver no Maps", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
