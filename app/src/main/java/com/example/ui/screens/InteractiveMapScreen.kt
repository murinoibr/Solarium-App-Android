package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Deck
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TableRestaurant
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.FloorPlanCategory
import com.example.data.model.FloorPlanPoint
import com.example.data.model.LocationCategory
import com.example.data.model.MapLocation
import com.example.data.model.MapViewMode

@Composable
fun InteractiveMapScreen(
    locations: List<MapLocation>,
    selectedLocation: MapLocation?,
    activeCategory: LocationCategory?,
    onSelectCategory: (LocationCategory?) -> Unit,
    onSelectLocation: (MapLocation?) -> Unit,
    onFeedback: (String) -> Unit,
    floorPoints: List<FloorPlanPoint> = emptyList(),
    selectedFloorPoint: FloorPlanPoint? = null,
    activeFloorCategory: FloorPlanCategory? = null,
    mapViewMode: MapViewMode = MapViewMode.FLOOR_PLAN,
    floorSearchQuery: String = "",
    onSelectFloorPoint: (FloorPlanPoint?) -> Unit = {},
    onSelectFloorCategory: (FloorPlanCategory?) -> Unit = {},
    onChangeMapViewMode: (MapViewMode) -> Unit = {},
    onUpdateFloorSearch: (String) -> Unit = {},
    onNavigateToManual: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showFullDetailDialog by remember { mutableStateOf(false) }

    val filteredFloorPoints = remember(floorPoints, activeFloorCategory, floorSearchQuery) {
        var list = if (activeFloorCategory == null || activeFloorCategory == FloorPlanCategory.ALL) {
            floorPoints
        } else {
            floorPoints.filter { it.category == activeFloorCategory }
        }

        if (floorSearchQuery.isNotBlank()) {
            val q = floorSearchQuery.trim().lowercase()
            list = list.filter {
                it.title.lowercase().contains(q) ||
                    it.roomOrZone.lowercase().contains(q) ||
                    it.description.lowercase().contains(q) ||
                    it.tags.any { tag -> tag.lowercase().contains(q) }
            }
        }
        list
    }

    val filteredLocations = remember(locations, activeCategory) {
        if (activeCategory == null) locations
        else locations.filter { it.category == activeCategory }
    }

    fun openInGoogleMaps(lat: Double, lng: Double, address: String) {
        try {
            val gmmIntentUri = Uri.parse("geo:$lat,$lng?q=${Uri.encode(address)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lng")
            )
            context.startActivity(webIntent)
        }
    }

    fun copyToClipboard(label: String, content: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, content)
        clipboard.setPrimaryClip(clip)
        onFeedback("$label copiado com sucesso!")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("interactive_map_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Navigation & Mode Switcher
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Mapa & Planta Interativa",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Solarium • R. Pres. Castelo Branco, 95 Ramon",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Badge showing total clickable spots
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (mapViewMode == MapViewMode.FLOOR_PLAN) Icons.Default.Home else Icons.Default.Place,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (mapViewMode == MapViewMode.FLOOR_PLAN) {
                                        "${filteredFloorPoints.size} marcações"
                                    } else {
                                        "${filteredLocations.size} pontos"
                                    },
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Mode Switcher: Planta da Casa vs. Bairro e Cidade
                    TabRow(
                        selectedTabIndex = if (mapViewMode == MapViewMode.FLOOR_PLAN) 0 else 1,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        contentColor = MaterialTheme.colorScheme.primary,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[if (mapViewMode == MapViewMode.FLOOR_PLAN) 0 else 1]),
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .height(42.dp)
                    ) {
                        Tab(
                            selected = mapViewMode == MapViewMode.FLOOR_PLAN,
                            onClick = { onChangeMapViewMode(MapViewMode.FLOOR_PLAN) },
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Layers, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Planta da Casa & Terreno", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            },
                            modifier = Modifier.testTag("tab_floor_plan")
                        )
                        Tab(
                            selected = mapViewMode == MapViewMode.NEIGHBORHOOD,
                            onClick = { onChangeMapViewMode(MapViewMode.NEIGHBORHOOD) },
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Bairro & Cidade", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            },
                            modifier = Modifier.testTag("tab_neighborhood_map")
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (mapViewMode == MapViewMode.FLOOR_PLAN) {
                        // Quick Search Bar for Floor Points
                        OutlinedTextField(
                            value = floorSearchQuery,
                            onValueChange = onUpdateFloorSearch,
                            placeholder = { Text("Buscar TV, piano, vagas, forno, rede, secador...", fontSize = 12.sp) },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = "Buscar", modifier = Modifier.size(18.dp))
                            },
                            trailingIcon = {
                                if (floorSearchQuery.isNotBlank()) {
                                    IconButton(onClick = { onUpdateFloorSearch("") }) {
                                        Icon(Icons.Default.Clear, contentDescription = "Limpar", modifier = Modifier.size(16.dp))
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("floor_plan_search_input")
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Category Filter Chips for Floor Plan
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            contentPadding = PaddingValues(horizontal = 2.dp)
                        ) {
                            items(FloorPlanCategory.entries.toTypedArray()) { cat ->
                                val isSelected = if (cat == FloorPlanCategory.ALL) {
                                    activeFloorCategory == null || activeFloorCategory == FloorPlanCategory.ALL
                                } else {
                                    activeFloorCategory == cat
                                }
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        if (cat == FloorPlanCategory.ALL) onSelectFloorCategory(null)
                                        else onSelectFloorCategory(cat)
                                    },
                                    label = { Text(cat.label, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(cat.colorHex),
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }

                        // Quick key points shortcuts (Porta de Entrada, Garagem, Pátio, Cozinha)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Pontos-Chave:",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val shortcuts = listOf(
                                    Triple("entrada_principal", "Porta Entrada", 0xFF4F46E5),
                                    Triple("garagem", "Garagem", 0xFF0D9488),
                                    Triple("patio", "Pátio", 0xFFD97706),
                                    Triple("cozinha", "Cozinha", 0xFFEA580C)
                                )
                                items(shortcuts) { (id, label, colorHex) ->
                                    val isPointSelected = selectedFloorPoint?.id == id
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (isPointSelected) Color(colorHex) else Color(colorHex).copy(alpha = 0.14f),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .clickable {
                                                val pt = floorPoints.find { it.id == id }
                                                if (pt != null) onSelectFloorPoint(pt)
                                            }
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = if (isPointSelected) Color.White else Color(colorHex),
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        // Category Filter Chips for Neighborhood Map
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 2.dp)
                        ) {
                            item {
                                FilterChip(
                                    selected = activeCategory == null,
                                    onClick = { onSelectCategory(null) },
                                    label = { Text("Todos") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                            items(LocationCategory.entries.toTypedArray()) { cat ->
                                FilterChip(
                                    selected = activeCategory == cat,
                                    onClick = { onSelectCategory(cat) },
                                    label = { Text(cat.label) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Interactive Canvas Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                AnimatedContent(
                    targetState = mapViewMode,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(260)) + scaleIn(initialScale = 0.98f)) togetherWith
                        (fadeOut(animationSpec = tween(200)) + scaleOut(targetScale = 0.98f))
                    },
                    label = "map_canvas_mode_transition",
                    modifier = Modifier.fillMaxSize()
                ) { mode ->
                    if (mode == MapViewMode.FLOOR_PLAN) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FloorPlanCanvas(
                                points = filteredFloorPoints,
                                selectedPoint = selectedFloorPoint,
                                onPointTap = { onSelectFloorPoint(it) },
                                modifier = Modifier.fillMaxSize()
                            )

                            // Quick Item Selector Carousel at top of Canvas
                            if (filteredFloorPoints.isNotEmpty()) {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .fillMaxWidth()
                                        .background(Color(0xFFF1F5F9).copy(alpha = 0.85f))
                                ) {
                                    items(filteredFloorPoints) { point ->
                                        val isSelected = selectedFloorPoint?.id == point.id
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = if (isSelected) Color(point.category.colorHex) else MaterialTheme.colorScheme.surface,
                                            shadowElevation = if (isSelected) 4.dp else 1.dp,
                                            modifier = Modifier
                                                .clickable { onSelectFloorPoint(point) }
                                                .testTag("quick_chip_${point.id}")
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = iconForFloorPoint(point.iconName),
                                                    contentDescription = null,
                                                    tint = if (isSelected) Color.White else Color(point.category.colorHex),
                                                    modifier = Modifier.size(13.dp)
                                                )
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Text(
                                                    text = point.title,
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontSize = 11.sp,
                                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                                    ),
                                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxSize()) {
                            NeighborhoodMapCanvas(
                                locations = filteredLocations,
                                selectedLocation = selectedLocation,
                                onPinTap = { onSelectLocation(it) },
                                modifier = Modifier.fillMaxSize()
                            )

                            // Legend in Neighborhood Mode
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                                shadowElevation = 3.dp,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    LegendRow(color = MaterialTheme.colorScheme.primary, label = "Sua Casa (Castelo Branco 95)")
                                    LegendRow(color = MaterialTheme.colorScheme.secondary, label = "Le Sapé (Ponto Referência)")
                                    LegendRow(color = Color(0xFF1098AD), label = "Parada de Ônibus (110m)")
                                    LegendRow(color = Color(0xFFE67700), label = "Turismo & Águas")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom Selected Point Card (Floor Plan Point)
        AnimatedVisibility(
            visible = selectedFloorPoint != null,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(14.dp)
        ) {
            if (selectedFloorPoint != null) {
                FloorPointBottomCard(
                    point = selectedFloorPoint,
                    onClose = { onSelectFloorPoint(null) },
                    onOpenFullDialog = { showFullDetailDialog = true },
                    onCopyTip = { copyToClipboard(selectedFloorPoint.title, "${selectedFloorPoint.title}\n${selectedFloorPoint.description}") },
                    onNavigateToManual = {
                        if (selectedFloorPoint.manualSectionId != null) {
                            onNavigateToManual(selectedFloorPoint.manualSectionId)
                        }
                    }
                )
            }
        }

        // Bottom Selected Location Card (Neighborhood Map)
        AnimatedVisibility(
            visible = selectedLocation != null && mapViewMode == MapViewMode.NEIGHBORHOOD,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(14.dp)
        ) {
            if (selectedLocation != null) {
                LocationBottomCard(
                    location = selectedLocation,
                    onClose = { onSelectLocation(null) },
                    onCopy = { copyToClipboard("Endereço", selectedLocation.address) },
                    onNavigate = { openInGoogleMaps(selectedLocation.latitude, selectedLocation.longitude, selectedLocation.address) }
                )
            }
        }

        // Detailed Pop-up Dialog for Clicked Point
        if (showFullDetailDialog && selectedFloorPoint != null) {
            FloorPointDetailDialog(
                point = selectedFloorPoint,
                onDismiss = { showFullDetailDialog = false },
                onCopy = { copyToClipboard(selectedFloorPoint.title, "${selectedFloorPoint.title} (${selectedFloorPoint.roomOrZone})\n${selectedFloorPoint.description}") },
                onNavigateToManual = {
                    showFullDetailDialog = false
                    if (selectedFloorPoint.manualSectionId != null) {
                        onNavigateToManual(selectedFloorPoint.manualSectionId)
                    }
                },
                onOpenMaps = {
                    openInGoogleMaps(-22.1122, -45.0560, "Rua Presidente Castelo Branco, 95, Ramon, São Lourenço - MG")
                }
            )
        }
    }
}

@Composable
private fun FloorPointBottomCard(
    point: FloorPlanPoint,
    onClose: () -> Unit,
    onOpenFullDialog: () -> Unit,
    onCopyTip: () -> Unit,
    onNavigateToManual: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("floor_point_detail_card"),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(point.category.colorHex).copy(alpha = 0.15f),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = iconForFloorPoint(point.iconName),
                                contentDescription = null,
                                tint = Color(point.category.colorHex),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = point.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Text(
                            text = point.roomOrZone,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Fechar")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Badge if present
            if (point.badge != null) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (point.isWarning) Color(0xFFFFF3BF) else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Text(
                        text = point.badge,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (point.isWarning) Color(0xFFD9480F) else MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Text(
                text = point.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            // Warning or Tip banner
            if (point.tipOrWarning != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (point.isWarning) Color(0xFFFFF4E6) else Color(0xFFEBFBEE),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (point.isWarning) Color(0xFFFFD8A8) else Color(0xFFB2F2BB)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (point.isWarning) Icons.Default.Warning else Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = if (point.isWarning) Color(0xFFE8590C) else Color(0xFF2B8A3E),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = point.tipOrWarning,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = if (point.isWarning) Color(0xFFD9480F) else Color(0xFF2B8A3E),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onOpenFullDialog,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Ver Detalhes", fontSize = 12.sp)
                }

                if (point.manualSectionId != null) {
                    Button(
                        onClick = onNavigateToManual,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ver no Manual", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun FloorPointDetailDialog(
    point: FloorPlanPoint,
    onDismiss: () -> Unit,
    onCopy: () -> Unit,
    onNavigateToManual: () -> Unit,
    onOpenMaps: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("floor_point_modal_dialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(point.category.colorHex).copy(alpha = 0.2f),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = iconForFloorPoint(point.iconName),
                                    contentDescription = null,
                                    tint = Color(point.category.colorHex),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(point.category.colorHex).copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = point.category.label,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color(point.category.colorHex),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = point.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Fechar")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Location Zone
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Place, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Localização: ${point.roomOrZone}",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Badge highlight
                if (point.badge != null) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (point.isWarning) Color(0xFFFFE3E3) else Color(0xFFD3F9D8),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "★ ${point.badge}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = if (point.isWarning) Color(0xFFC92A2A) else Color(0xFF2B8A3E),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Full description
                Text(
                    text = point.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 22.sp
                )

                // Warning / Tip Card
                if (point.tipOrWarning != null) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (point.isWarning) Color(0xFFFFF9DB) else Color(0xFFEBFBEE),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (point.isWarning) Color(0xFFFFEC99) else Color(0xFFD3F9D8)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = if (point.isWarning) Icons.Default.Warning else Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = if (point.isWarning) Color(0xFFF59F00) else Color(0xFF40C057),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = if (point.isWarning) "ATENÇÃO ESPECIAL:" else "DICA DA ANFITRIÃ:",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (point.isWarning) Color(0xFFE67700) else Color(0xFF2F9E44)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = point.tipOrWarning,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons inside Dialog
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onCopy,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Copiar", fontSize = 12.sp)
                    }

                    if (point.category == FloorPlanCategory.SURROUNDINGS) {
                        Button(
                            onClick = onOpenMaps,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Abrir GPS", fontSize = 12.sp)
                        }
                    } else if (point.manualSectionId != null) {
                        Button(
                            onClick = onNavigateToManual,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("No Manual", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationBottomCard(
    location: MapLocation,
    onClose: () -> Unit,
    onCopy: () -> Unit,
    onNavigate: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("location_detail_card"),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = location.category.label,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = location.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = location.address,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Fechar")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = location.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (location.tip != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = location.tip,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Directions,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Distância da casa: ${location.distanceEstimate}",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onCopy,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Copiar")
                }

                Button(
                    onClick = onNavigate,
                    modifier = Modifier
                        .weight(1.4f)
                        .testTag("open_maps_button"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Navegar no Maps")
                }
            }
        }
    }
}

@Composable
private fun FloorPlanCanvas(
    points: List<FloorPlanPoint>,
    selectedPoint: FloorPlanPoint?,
    onPointTap: (FloorPlanPoint) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color(0xFFF8FAFC))
            .pointerInput(points) {
                detectTapGestures { tapOffset ->
                    val w = size.width
                    val h = size.height
                    // Find closest point within touch radius of 44dp
                    val hit = points.find { pt ->
                        val px = pt.normX * w
                        val py = pt.normY * h
                        val distSq = (tapOffset.x - px) * (tapOffset.x - px) + (tapOffset.y - py) * (tapOffset.y - py)
                        distSq <= (46 * density) * (46 * density)
                    }
                    if (hit != null) {
                        onPointTap(hit)
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArchitecturalFloorPlan(size.width, size.height)
        }

        // Render interactive pins overlay
        points.forEach { pt ->
            val isSelected = selectedPoint?.id == pt.id
            FloorPlanPin(
                point = pt,
                isSelected = isSelected,
                onClick = { onPointTap(pt) }
            )
        }
    }
}

@Composable
private fun FloorPlanPin(
    point: FloorPlanPoint,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    androidx.compose.ui.layout.Layout(
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .testTag("floor_pin_${point.id}")
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) Color(point.category.colorHex) else Color(point.category.colorHex).copy(alpha = 0.92f),
                    border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, Color.White) else null,
                    shadowElevation = if (isSelected) 8.dp else 3.dp,
                    modifier = Modifier.size(if (isSelected) 36.dp else 28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = iconForFloorPoint(point.iconName),
                            contentDescription = point.title,
                            tint = Color.White,
                            modifier = Modifier.size(if (isSelected) 20.dp else 16.dp)
                        )
                    }
                }

                // Mini Label under pin
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (isSelected) Color(point.category.colorHex) else Color.White.copy(alpha = 0.95f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(point.category.colorHex).copy(alpha = 0.3f)),
                    shadowElevation = 2.dp,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Text(
                        text = point.title.take(12),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 8.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold
                        ),
                        color = if (isSelected) Color.White else Color(0xFF1E293B),
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                        maxLines = 1
                    )
                }
            }
        }
    ) { measurables, constraints ->
        val placeable = measurables.first().measure(constraints)
        val parentWidth = constraints.maxWidth
        val parentHeight = constraints.maxHeight

        val x = (point.normX * parentWidth - placeable.width / 2).toInt()
        val y = (point.normY * parentHeight - placeable.height / 2).toInt()

        layout(parentWidth, parentHeight) {
            placeable.placeRelative(x = x, y = y)
        }
    }
}

private fun DrawScope.drawArchitecturalFloorPlan(w: Float, h: Float) {
    // 1. Surrounding Grounds / Background
    drawRect(color = Color(0xFFF1F5F9), size = size)

    // 2. Property Boundary (Muro / Limites do Terreno)
    val propLeft = w * 0.06f
    val propRight = w * 0.94f
    val propTop = h * 0.08f
    val propBottom = h * 0.88f
    val propWidth = propRight - propLeft
    val propHeight = propBottom - propTop

    // Ground grass/pavement
    drawRoundRect(
        color = Color(0xFFE2E8F0),
        topLeft = Offset(propLeft, propTop),
        size = Size(propWidth, propHeight),
        cornerRadius = CornerRadius(16f, 16f)
    )

    // 3. Street Area at Bottom (Rua Pres. Castelo Branco, 95)
    val streetTop = h * 0.88f
    drawRect(
        color = Color(0xFF334155),
        topLeft = Offset(0f, streetTop),
        size = Size(w, h - streetTop)
    )

    // Sidewalk (Calçada)
    drawRect(
        color = Color(0xFFCBD5E1),
        topLeft = Offset(0f, streetTop - 36f),
        size = Size(w, 36f)
    )

    // Street Road Markings (Dashed Line)
    val roadDash = Path().apply {
        moveTo(0f, streetTop + 45f)
        lineTo(w, streetTop + 45f)
    }
    drawPath(
        roadDash,
        color = Color(0xFFF8FAFC).copy(alpha = 0.6f),
        style = Stroke(
            width = 4f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(24f, 16f), 0f)
        )
    )

    // Sidewalk Tree (Árvore à esquerda do portão)
    drawCircle(
        color = Color(0xFF2B8A3E),
        radius = 24f,
        center = Offset(w * 0.12f, streetTop - 18f)
    )
    drawCircle(
        color = Color(0xFF40C057),
        radius = 16f,
        center = Offset(w * 0.12f, streetTop - 18f)
    )

    // 4. Front Yard & Parking (Garagem: 1 Coberta + 2 Descobertas)
    // Front Yard Pavement (Pátio Frontal)
    val frontYardLeft = w * 0.08f
    val frontYardTop = h * 0.70f
    val frontYardWidth = w * 0.84f
    val frontYardHeight = (h * 0.88f) - frontYardTop

    drawRoundRect(
        color = Color(0xFFE2E8F0),
        topLeft = Offset(frontYardLeft, frontYardTop),
        size = Size(frontYardWidth, frontYardHeight),
        cornerRadius = CornerRadius(10f, 10f)
    )

    // Covered Parking Bay 1 (Sob alpendre / laje)
    drawRoundRect(
        color = Color(0xFFD0EBFF),
        topLeft = Offset(w * 0.10f, h * 0.72f),
        size = Size(w * 0.24f, h * 0.14f),
        cornerRadius = CornerRadius(8f, 8f)
    )
    // Dashed roof line for covered bay
    drawRoundRect(
        color = Color(0xFF1971C2),
        topLeft = Offset(w * 0.10f, h * 0.72f),
        size = Size(w * 0.24f, h * 0.14f),
        cornerRadius = CornerRadius(8f, 8f),
        style = Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f))
    )

    // Uncovered Parking Bays 2 & 3 (2 Vagas Descobertas)
    val bayDash = Path().apply {
        moveTo(w * 0.22f, h * 0.72f)
        lineTo(w * 0.22f, h * 0.86f)
    }
    drawPath(bayDash, color = Color(0xFF1971C2), style = Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)))

    // 5. Backyard Patio (Pátio Externo dos Fundos ensolarado)
    drawRoundRect(
        color = Color(0xFFFFECC8),
        topLeft = Offset(w * 0.60f, h * 0.12f),
        size = Size(w * 0.32f, h * 0.18f),
        cornerRadius = CornerRadius(12f, 12f)
    )

    // 6. House Structure (Paredes Externas da Casa)
    val houseLeft = w * 0.10f
    val houseRight = w * 0.88f
    val houseTop = h * 0.30f
    val houseBottom = h * 0.70f
    val houseWidth = houseRight - houseLeft
    val houseHeight = houseBottom - houseTop

    // Base House Floor (Térreo interior em tom aconchegante)
    drawRoundRect(
        color = Color(0xFFFDFBF7),
        topLeft = Offset(houseLeft, houseTop),
        size = Size(houseWidth, houseHeight),
        cornerRadius = CornerRadius(12f, 12f)
    )

    // Varandas Cobertas (Lateral e Frente)
    drawRoundRect(
        color = Color(0xFFEDE0D4),
        topLeft = Offset(houseLeft + 4f, houseTop + 4f),
        size = Size(w * 0.26f, h * 0.16f),
        cornerRadius = CornerRadius(8f, 8f)
    )

    // Perimeter Wall (Grossa, estilo arquitetônico)
    drawRoundRect(
        color = Color(0xFF475569),
        topLeft = Offset(houseLeft, houseTop),
        size = Size(houseWidth, houseHeight),
        cornerRadius = CornerRadius(12f, 12f),
        style = Stroke(width = 6f)
    )

    // 7. Internal Room Partitions (Divisões Internas)
    val wallColor = Color(0xFF64748B)

    // Divider: Bedrooms/Veranda left partition
    drawLine(
        color = wallColor,
        start = Offset(w * 0.36f, houseTop),
        end = Offset(w * 0.36f, houseBottom),
        strokeWidth = 4f
    )

    // Divider: Kitchen & Laundry right partition
    drawLine(
        color = wallColor,
        start = Offset(w * 0.62f, houseTop),
        end = Offset(w * 0.62f, h * 0.54f),
        strokeWidth = 4f
    )

    // Divider between Kitchen and Service Area
    drawLine(
        color = wallColor,
        start = Offset(w * 0.74f, houseTop),
        end = Offset(w * 0.74f, h * 0.40f),
        strokeWidth = 4f
    )

    // Divider between Living room and Dining room
    drawLine(
        color = Color(0xFF94A3B8),
        start = Offset(w * 0.36f, h * 0.54f),
        end = Offset(w * 0.62f, h * 0.54f),
        strokeWidth = 3f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
    )

    // Front Main Door Opening
    drawArc(
        color = Color(0xFFC85A32),
        startAngle = 180f,
        sweepAngle = 90f,
        useCenter = false,
        topLeft = Offset(w * 0.38f, houseBottom - 26f),
        size = Size(50f, 50f),
        style = Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f))
    )

    // High Step Warning on Veranda (Degrau Alto)
    drawLine(
        color = Color(0xFFF59F00),
        start = Offset(w * 0.24f, houseTop + 6f),
        end = Offset(w * 0.36f, houseTop + 6f),
        strokeWidth = 4f
    )
}

private fun DrawScope.NeighborhoodMapCanvas(
    locations: List<MapLocation>,
    selectedLocation: MapLocation?,
    onPinTap: (MapLocation) -> Unit,
    modifier: Modifier = Modifier
) {
    // Relative coordinates mapping for stylized neighborhood map
    val pinOffsets = mapOf(
        "house" to Pair(0.42f, 0.44f),
        "trash_bin" to Pair(0.36f, 0.46f),
        "sape" to Pair(0.55f, 0.42f),
        "bus_stop" to Pair(0.68f, 0.41f),
        "parque_aguas" to Pair(0.70f, 0.70f),
        "quinta_cedro" to Pair(0.20f, 0.22f),
        "trem_aguas" to Pair(0.78f, 0.58f),
        "balonismo" to Pair(0.85f, 0.30f),
        "morro_cruzeiro" to Pair(0.28f, 0.82f),
        "rodoviaria" to Pair(0.84f, 0.76f)
    )

    drawStylizedNeighborhood(pinOffsets)
}

@Composable
private fun NeighborhoodMapCanvas(
    locations: List<MapLocation>,
    selectedLocation: MapLocation?,
    onPinTap: (MapLocation) -> Unit,
    modifier: Modifier = Modifier
) {
    val pinOffsets = remember(locations) {
        mapOf(
            "house" to Pair(0.42f, 0.44f),
            "trash_bin" to Pair(0.36f, 0.46f),
            "sape" to Pair(0.55f, 0.42f),
            "bus_stop" to Pair(0.68f, 0.41f),
            "parque_aguas" to Pair(0.70f, 0.70f),
            "quinta_cedro" to Pair(0.20f, 0.22f),
            "trem_aguas" to Pair(0.78f, 0.58f),
            "balonismo" to Pair(0.85f, 0.30f),
            "morro_cruzeiro" to Pair(0.28f, 0.82f),
            "rodoviaria" to Pair(0.84f, 0.76f)
        )
    }

    Box(
        modifier = modifier
            .background(Color(0xFFE8ECE9))
            .pointerInput(locations) {
                detectTapGestures { tapOffset ->
                    val w = size.width
                    val h = size.height
                    val hit = locations.find { loc ->
                        val coords = pinOffsets[loc.id] ?: Pair(0.5f, 0.5f)
                        val pinX = coords.first * w
                        val pinY = coords.second * h
                        val distSq = (tapOffset.x - pinX) * (tapOffset.x - pinX) + (tapOffset.y - pinY) * (tapOffset.y - pinY)
                        distSq <= (48 * density) * (48 * density)
                    }
                    if (hit != null) {
                        onPinTap(hit)
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawStylizedNeighborhood(pinOffsets)
        }

        locations.forEach { loc ->
            val coords = pinOffsets[loc.id] ?: Pair(0.5f, 0.5f)
            val isSelected = selectedLocation?.id == loc.id
            val pinColor = when (loc.id) {
                "house" -> Color(0xFFC85A32)
                "trash_bin" -> Color(0xFF2B8A3E)
                "sape" -> Color(0xFF3B6955)
                "bus_stop" -> Color(0xFF1098AD)
                "parque_aguas" -> Color(0xFF1C7ED6)
                "quinta_cedro" -> Color(0xFFD9480F)
                "trem_aguas" -> Color(0xFF7048E8)
                "balonismo" -> Color(0xFFF59F00)
                "morro_cruzeiro" -> Color(0xFF37B24D)
                else -> Color(0xFF495057)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                MapPinView(
                    location = loc,
                    relativeX = coords.first,
                    relativeY = coords.second,
                    isSelected = isSelected,
                    pinColor = pinColor,
                    onClick = { onPinTap(loc) }
                )
            }
        }
    }
}

@Composable
private fun MapPinView(
    location: MapLocation,
    relativeX: Float,
    relativeY: Float,
    isSelected: Boolean,
    pinColor: Color,
    onClick: () -> Unit
) {
    androidx.compose.ui.layout.Layout(
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .testTag("map_pin_${location.id}")
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = pinColor,
                    shadowElevation = if (isSelected) 8.dp else 3.dp,
                    modifier = Modifier.size(if (isSelected) 42.dp else 34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = when (location.category) {
                                LocationCategory.HOUSE -> Icons.Default.Home
                                LocationCategory.SERVICES -> Icons.Default.Delete
                                LocationCategory.TRANSPORT -> Icons.Default.DirectionsBus
                                LocationCategory.FOOD -> Icons.Default.Restaurant
                                LocationCategory.ATTRACTION -> Icons.Default.Explore
                            },
                            contentDescription = location.title,
                            tint = Color.White,
                            modifier = Modifier.size(if (isSelected) 22.dp else 18.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color.White.copy(alpha = 0.95f),
                    shadowElevation = 2.dp,
                    modifier = Modifier.padding(top = 3.dp)
                ) {
                    Text(
                        text = if (location.id == "house") "★ A CASA" else location.title.take(15),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold
                        ),
                        color = if (isSelected) pinColor else Color(0xFF1E293B),
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                        maxLines = 1
                    )
                }
            }
        }
    ) { measurables, constraints ->
        val placeable = measurables.first().measure(constraints)
        val parentWidth = constraints.maxWidth
        val parentHeight = constraints.maxHeight

        val x = (relativeX * parentWidth - placeable.width / 2).toInt()
        val y = (relativeY * parentHeight - placeable.height).toInt()

        layout(parentWidth, parentHeight) {
            placeable.placeRelative(x = x, y = y)
        }
    }
}

private fun DrawScope.drawStylizedNeighborhood(pinOffsets: Map<String, Pair<Float, Float>>) {
    val w = size.width
    val h = size.height

    drawRect(color = Color(0xFFE9EFE8), size = size)

    // Green Hills / Parks
    val parkBrush = Brush.radialGradient(
        colors = listOf(Color(0xFFC7E2C9), Color(0xFFE2EDE2)),
        center = Offset(w * 0.70f, h * 0.70f),
        radius = w * 0.28f
    )
    drawCircle(
        brush = parkBrush,
        radius = w * 0.28f,
        center = Offset(w * 0.70f, h * 0.70f)
    )

    // Mineral Lake at Parque das Águas
    drawCircle(
        color = Color(0xFFA5D8F3),
        radius = w * 0.10f,
        center = Offset(w * 0.74f, h * 0.73f)
    )

    // North Green Hills (Quinta do Cedro area)
    drawRoundRect(
        color = Color(0xFFD6EAD8),
        topLeft = Offset(w * 0.05f, h * 0.08f),
        size = Size(w * 0.35f, h * 0.22f),
        cornerRadius = CornerRadius(40f, 40f)
    )

    val streetColor = Color(0xFFFFFFFF)
    val streetBorderColor = Color(0xFFD4DAD4)

    // Main Avenue
    val mainRoad = Path().apply {
        moveTo(w * 0.10f, h * 0.44f)
        lineTo(w * 0.90f, h * 0.42f)
    }
    drawPath(mainRoad, color = streetBorderColor, style = Stroke(width = 32f, cap = StrokeCap.Round))
    drawPath(mainRoad, color = streetColor, style = Stroke(width = 24f, cap = StrokeCap.Round))

    // Rua Pres. Castelo Branco
    val houseStreet = Path().apply {
        moveTo(w * 0.25f, h * 0.44f)
        lineTo(w * 0.45f, h * 0.44f)
        lineTo(w * 0.50f, h * 0.34f)
    }
    drawPath(houseStreet, color = streetBorderColor, style = Stroke(width = 24f, cap = StrokeCap.Round))
    drawPath(houseStreet, color = streetColor, style = Stroke(width = 18f, cap = StrokeCap.Round))

    // Le Sapé street
    val sapeStreet = Path().apply {
        moveTo(w * 0.45f, h * 0.44f)
        lineTo(w * 0.68f, h * 0.42f)
    }
    drawPath(sapeStreet, color = Color(0xFFFFE3D6), style = Stroke(width = 20f, cap = StrokeCap.Round))

    // Walking path to bus stop
    val walkPath = Path().apply {
        moveTo(w * 0.42f, h * 0.44f)
        lineTo(w * 0.55f, h * 0.42f)
        lineTo(w * 0.68f, h * 0.41f)
    }
    drawPath(
        walkPath,
        color = Color(0xFFC85A32),
        style = Stroke(
            width = 4f,
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f)
        )
    )
}

@Composable
private fun LegendRow(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = color,
            modifier = Modifier.size(10.dp)
        ) {}
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

private fun iconForFloorPoint(name: String): ImageVector {
    return when (name) {
        "Key" -> Icons.Default.Key
        "DoorBack" -> Icons.Default.Home
        "DirectionsWalk" -> Icons.Default.DirectionsWalk
        "Yard" -> Icons.Default.Deck
        "Deck" -> Icons.Default.Deck
        "AirlineSeatFlat" -> Icons.Default.Deck
        "Piano" -> Icons.Default.MusicNote
        "Tv" -> Icons.Default.Tv
        "ModeFan" -> Icons.Default.Air
        "Air" -> Icons.Default.Air
        "LocalLaundryService" -> Icons.Default.LocalLaundryService
        "Coffee" -> Icons.Default.Coffee
        "Kitchen" -> Icons.Default.Kitchen
        "SoupKitchen" -> Icons.Default.Kitchen
        "Warning" -> Icons.Default.Warning
        "DinnerDining" -> Icons.Default.Restaurant
        "BakeryDining" -> Icons.Default.Restaurant
        "Blender" -> Icons.Default.Coffee
        "TableRestaurant" -> Icons.Default.TableRestaurant
        "DirectionsCar" -> Icons.Default.DirectionsCar
        "Delete" -> Icons.Default.Delete
        "DirectionsBus" -> Icons.Default.DirectionsBus
        "Hotel" -> Icons.Default.Hotel
        "Place" -> Icons.Default.Place
        else -> Icons.Default.Place
    }
}
