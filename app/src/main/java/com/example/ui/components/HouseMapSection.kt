package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Deck
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Yard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * Representação de cada ponto de atenção do Mapa da Casa
 */
data class HouseKeyPoint(
    val id: String,
    val title: String,
    val roomZone: String,
    val shortSummary: String,
    val badge: String,
    val isWarning: Boolean = false,
    val icon: ImageVector,
    val imageUrl: String,
    val instructions: List<String>,
    val precautions: List<String>,
    val manualChapterId: Int,
    val manualChapterTitle: String,
    val floorPointId: String,
    val normX: Float,
    val normY: Float,
    val accentColorHex: Long
)

/**
 * Seção de 'Mapa da Casa' com visualização interativa em Imagem/Planta e Grade de Ambientes.
 * Inclui pontos clicáveis para Porta de Entrada, Garagem, Pátio e Cozinha com pop-up detalhado
 * de instruções e cuidados descritos no manual.
 */
@Composable
fun HouseMapSection(
    onNavigateToMapPoint: ((String) -> Unit)? = null,
    onNavigateToManual: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var viewMode by remember { mutableStateOf(HouseMapViewMode.INTERACTIVE_IMAGE) }
    var selectedPointForDialog by remember { mutableStateOf<HouseKeyPoint?>(null) }

    val keyPoints = remember { getKeyPointsList() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("house_map_section")
            .padding(vertical = 10.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Mapa da Casa",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = "Interativo",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "Toque nos pontos para ver instruções de uso e cuidados",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // View Mode Toggle (Imagem Interativa vs. Grade de Ambientes)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (viewMode == HouseMapViewMode.INTERACTIVE_IMAGE)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { viewMode = HouseMapViewMode.INTERACTIVE_IMAGE }
                    .testTag("house_map_tab_interactive")
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Layers,
                        contentDescription = null,
                        tint = if (viewMode == HouseMapViewMode.INTERACTIVE_IMAGE)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Planta Interativa",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (viewMode == HouseMapViewMode.INTERACTIVE_IMAGE) FontWeight.Bold else FontWeight.Medium
                        ),
                        color = if (viewMode == HouseMapViewMode.INTERACTIVE_IMAGE)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (viewMode == HouseMapViewMode.GRID)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { viewMode = HouseMapViewMode.GRID }
                    .testTag("house_map_tab_grid")
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.GridView,
                        contentDescription = null,
                        tint = if (viewMode == HouseMapViewMode.GRID)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Grade de Ambientes",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (viewMode == HouseMapViewMode.GRID) FontWeight.Bold else FontWeight.Medium
                        ),
                        color = if (viewMode == HouseMapViewMode.GRID)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Content Area (Switch between Interactive Image and Grid)
        AnimatedContent(
            targetState = viewMode,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "house_map_view_mode"
        ) { mode ->
            when (mode) {
                HouseMapViewMode.INTERACTIVE_IMAGE -> {
                    InteractiveHouseCanvasView(
                        points = keyPoints,
                        onPointClick = { selectedPointForDialog = it }
                    )
                }
                HouseMapViewMode.GRID -> {
                    HouseGridCardsView(
                        points = keyPoints,
                        onPointClick = { selectedPointForDialog = it }
                    )
                }
            }
        }

        // Quick Clickable Chips below for fast 1-tap access
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(keyPoints) { pt ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(pt.accentColorHex).copy(alpha = 0.12f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(pt.accentColorHex).copy(alpha = 0.35f)),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { selectedPointForDialog = pt }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = pt.icon,
                            contentDescription = null,
                            tint = Color(pt.accentColorHex),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = pt.title,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = Color(pt.accentColorHex)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.TouchApp,
                            contentDescription = null,
                            tint = Color(pt.accentColorHex).copy(alpha = 0.7f),
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }

    // Modal Pop-up Informativo com Instruções de Uso e Cuidados
    if (selectedPointForDialog != null) {
        HousePointInfoDialog(
            point = selectedPointForDialog!!,
            onDismiss = { selectedPointForDialog = null },
            onNavigateToManual = { chapterId ->
                selectedPointForDialog = null
                onNavigateToManual?.invoke(chapterId)
            },
            onNavigateToMapPoint = { pointId ->
                selectedPointForDialog = null
                onNavigateToMapPoint?.invoke(pointId)
            }
        )
    }
}

enum class HouseMapViewMode {
    INTERACTIVE_IMAGE,
    GRID
}

/**
 * Visualização da Imagem/Planta Interativa da Casa com desenho arquitetônico estilizado,
 * zonas de cômodos delimitadas e pins pulsantes com toque interativo.
 */
@Composable
private fun InteractiveHouseCanvasView(
    points: List<HouseKeyPoint>,
    onPointClick: (HouseKeyPoint) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pin_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(22.dp)),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column {
            // Blueprint Canvas with Tap Gestures
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .pointerInput(points) {
                        detectTapGestures { tapOffset ->
                            val w = size.width
                            val h = size.height
                            // Find closest point within touch radius of 48dp
                            val touchRadiusPx = 48 * density
                            val hit = points.find { pt ->
                                val px = pt.normX * w
                                val py = pt.normY * h
                                val distSq = (tapOffset.x - px) * (tapOffset.x - px) + (tapOffset.y - py) * (tapOffset.y - py)
                                distSq <= touchRadiusPx * touchRadiusPx
                            }
                            if (hit != null) {
                                onPointClick(hit)
                            }
                        }
                    }
            ) {
                val boxWidth = maxWidth
                val boxHeight = maxHeight

                // Architectural Floor Blueprint Drawing
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawHouseArchitecturalBlueprint(size.width, size.height)
                }

                // Interactive Clickable Pins with Animated Glow & Tag
                points.forEach { pt ->
                    val pinColor = Color(pt.accentColorHex)
                    val xOffset = boxWidth * pt.normX - 22.dp
                    val yOffset = boxHeight * pt.normY - 22.dp

                    Box(
                        modifier = Modifier
                            .offset(x = xOffset, y = yOffset)
                            .size(48.dp)
                            .clickable { onPointClick(pt) }
                            .testTag("house_map_pin_${pt.id}"),
                        contentAlignment = Alignment.Center
                    ) {
                        // Soft pulsing halo
                        Box(
                            modifier = Modifier
                                .size(34.dp * pulseScale)
                                .clip(CircleShape)
                                .background(pinColor.copy(alpha = 0.22f))
                        )

                        // Outer circular badge
                        Surface(
                            shape = CircleShape,
                            color = pinColor,
                            shadowElevation = 4.dp,
                            modifier = Modifier.size(30.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = pt.icon,
                                    contentDescription = pt.title,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    // Floating label next to the pin for quick reading
                    val labelXOffset = (boxWidth * pt.normX + 16.dp).coerceAtMost(boxWidth - 110.dp)
                    val labelYOffset = (boxHeight * pt.normY - 14.dp).coerceAtLeast(8.dp)

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color.Black.copy(alpha = 0.78f),
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .offset(x = labelXOffset, y = labelYOffset)
                            .clickable { onPointClick(pt) }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = pt.title,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Bottom prompt strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF1F5F9))
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Toque em qualquer ponto numerado para abrir as regras",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = Color(0xFF334155)
                    )
                }

                Text(
                    text = "4 Pontos-Chave",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

/**
 * Visualização em Grade Interativa com Cartões dos Ambientes (Porta, Garagem, Pátio, Cozinha).
 * Cada cartão possui foto real do Airbnb, tags de atenção e botão direto para abrir as instruções.
 */
@Composable
private fun HouseGridCardsView(
    points: List<HouseKeyPoint>,
    onPointClick: (HouseKeyPoint) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Render 2 rows of 2 cards
        val pairs = points.chunked(2)
        pairs.forEach { rowPoints ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowPoints.forEach { pt ->
                    HouseGridCard(
                        point = pt,
                        onClick = { onPointClick(pt) },
                        modifier = Modifier.weight(1f)
                    )
                }
                // If odd number of points, fill space
                if (rowPoints.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun HouseGridCard(
    point: HouseKeyPoint,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accentColor = Color(point.accentColorHex)

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .testTag("house_map_grid_card_${point.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Photographic banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .background(Color(0xFFE2E8F0))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(point.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = point.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark gradient scrim for badge legibility
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.35f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.65f)
                                )
                            )
                        )
                )

                // Badge top-start
                Surface(
                    shape = RoundedCornerShape(bottomEnd = 8.dp),
                    color = if (point.isWarning) Color(0xFFD9480F) else accentColor,
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = point.badge,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                }

                // Icon badge top-end
                Surface(
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.55f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(5.dp)
                        .size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = point.icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            // Card Body Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = point.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = point.shortSummary,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tap affordance
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ver Cuidados",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(11.dp)
                    )
                }
            }
        }
    }
}

/**
 * Pop-up Informativo com Instruções de Uso e Cuidados descritos no Manual.
 */
@Composable
private fun HousePointInfoDialog(
    point: HouseKeyPoint,
    onDismiss: () -> Unit,
    onNavigateToManual: ((Int) -> Unit)? = null,
    onNavigateToMapPoint: ((String) -> Unit)? = null
) {
    val scrollState = rememberScrollState()
    val accentColor = Color(point.accentColorHex)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .testTag("house_map_popup_dialog"),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                // Photographic Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(accentColor)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(point.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = point.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Scrim
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.35f),
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.8f)
                                    )
                                )
                            )
                    )

                    // Close button
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .testTag("house_map_popup_close_btn")
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.55f),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Fechar",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    // Title & Category in Header
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (point.isWarning) Color(0xFFD9480F) else accentColor
                        ) {
                            Text(
                                text = point.badge,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 10.sp
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = point.title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        )

                        Text(
                            text = point.roomZone,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                // Body content with Instructions and Precautions
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // SECTION 1: Instruções de Uso
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Instruções de Uso:",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    point.instructions.forEach { instr ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "•",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = instr,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // SECTION 2: Cuidados do Manual (Highlighted Warning/Care Card)
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (point.isWarning) Color(0xFFFFF9DB) else Color(0xFFFEF3C7),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (point.isWarning) Color(0xFFF59F00) else Color(0xFFFBBF24)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (point.isWarning) Icons.Default.Warning else Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = if (point.isWarning) Color(0xFFD9480F) else Color(0xFFB45309),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Cuidados & Recomendações do Manual:",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (point.isWarning) Color(0xFFD9480F) else Color(0xFF92400E)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            point.precautions.forEach { prec ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "★",
                                        color = if (point.isWarning) Color(0xFFD9480F) else Color(0xFFB45309),
                                        fontSize = 11.sp,
                                        modifier = Modifier.padding(end = 6.dp, top = 2.dp)
                                    )
                                    Text(
                                        text = prec,
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, fontWeight = FontWeight.Medium),
                                        color = if (point.isWarning) Color(0xFF782500) else Color(0xFF78350F),
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // SECTION 3: Action Buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (onNavigateToManual != null) {
                                Button(
                                    onClick = { onNavigateToManual(point.manualChapterId) },
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("house_map_popup_manual_btn"),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Ver Cap. ${point.manualChapterId}", fontSize = 12.sp)
                                }
                            }

                            if (onNavigateToMapPoint != null) {
                                OutlinedButton(
                                    onClick = { onNavigateToMapPoint(point.floorPointId) },
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("house_map_popup_map_btn")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Map,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Ver na Planta", fontSize = 12.sp)
                                }
                            }
                        }

                        Button(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Text("Entendido / Fechar", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Desenha a planta arquitetônica esquemática estilizada da casa com divisões de cômodos,
 * texturas e indicações claras de cada área.
 */
private fun DrawScope.drawHouseArchitecturalBlueprint(w: Float, h: Float) {
    val wallColor = Color(0xFF334155)
    val dividerColor = Color(0xFF94A3B8)
    val roomFillMain = Color(0xFFF1F5F9)
    val outdoorFill = Color(0xFFE2E8F0)
    val garageFill = Color(0xFFE2E8F0)

    // Outer boundaries
    val left = w * 0.08f
    val right = w * 0.92f
    val top = h * 0.08f
    val bottom = h * 0.92f

    // Background terrain grid
    drawRect(
        color = Color(0xFFF8FAFC),
        topLeft = Offset(0f, 0f),
        size = Size(w, h)
    )

    // Zone 1: Pátio Aberto & Solarium (Top Right)
    drawRoundRect(
        color = outdoorFill,
        topLeft = Offset(w * 0.55f, top),
        size = Size(w * 0.37f, h * 0.28f),
        cornerRadius = CornerRadius(14f, 14f)
    )

    // Zone 2: Cozinha & Área de Serviço (Mid Right)
    drawRoundRect(
        color = roomFillMain,
        topLeft = Offset(w * 0.55f, top + h * 0.30f),
        size = Size(w * 0.37f, h * 0.26f),
        cornerRadius = CornerRadius(10f, 10f)
    )

    // Zone 3: Sala de Estar & TV / Piano (Center)
    drawRoundRect(
        color = roomFillMain,
        topLeft = Offset(w * 0.30f, top + h * 0.30f),
        size = Size(w * 0.23f, h * 0.32f),
        cornerRadius = CornerRadius(10f, 10f)
    )

    // Zone 4: Varandas & Quartos (Left)
    drawRoundRect(
        color = roomFillMain,
        topLeft = Offset(left, top),
        size = Size(w * 0.45f, h * 0.28f),
        cornerRadius = CornerRadius(12f, 12f)
    )

    // Zone 5: Garagem Privativa (3 Vagas - Bottom Left)
    drawRoundRect(
        color = garageFill,
        topLeft = Offset(left, top + h * 0.60f),
        size = Size(w * 0.34f, h * 0.24f),
        cornerRadius = CornerRadius(12f, 12f)
    )

    // Zone 6: Entrada Principal & Calçada (Bottom Center)
    drawRoundRect(
        color = roomFillMain,
        topLeft = Offset(w * 0.44f, top + h * 0.64f),
        size = Size(w * 0.48f, h * 0.20f),
        cornerRadius = CornerRadius(12f, 12f)
    )

    // Structural Walls (Thick Lines)
    val wallStroke = Stroke(width = 3.5f)
    val dashedStroke = Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f))

    // Perimeter boundary outline
    drawRoundRect(
        color = wallColor,
        topLeft = Offset(left, top),
        size = Size(right - left, bottom - top),
        cornerRadius = CornerRadius(16f, 16f),
        style = Stroke(width = 4f)
    )

    // Internal partition walls
    drawLine(wallColor, Offset(w * 0.55f, top), Offset(w * 0.55f, bottom), wallStroke.width)
    drawLine(wallColor, Offset(left, top + h * 0.28f), Offset(w * 0.55f, top + h * 0.28f), wallStroke.width)
    drawLine(wallColor, Offset(w * 0.55f, top + h * 0.28f), Offset(right, top + h * 0.28f), dashedStroke.width)
    drawLine(wallColor, Offset(w * 0.30f, top + h * 0.28f), Offset(w * 0.30f, top + h * 0.62f), wallStroke.width)
    drawLine(wallColor, Offset(left, top + h * 0.60f), Offset(right, top + h * 0.60f), wallStroke.width)
    drawLine(wallColor, Offset(w * 0.42f, top + h * 0.60f), Offset(w * 0.42f, bottom), wallStroke.width)

    // Decorative door swing arcs
    // Main entrance door arc
    val doorPath = Path().apply {
        moveTo(w * 0.46f, top + h * 0.60f)
        cubicTo(
            w * 0.49f, top + h * 0.60f,
            w * 0.52f, top + h * 0.63f,
            w * 0.52f, top + h * 0.66f
        )
    }
    drawPath(doorPath, color = Color(0xFF4F46E5), style = Stroke(width = 2.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 4f), 0f)))

    // Garage gate dashed indication
    drawLine(
        color = Color(0xFF0D9488),
        start = Offset(left + 8f, bottom),
        end = Offset(w * 0.40f, bottom),
        strokeWidth = 4.5f
    )
}

/**
 * Lista dos 4 pontos de atenção requeridos com todas as instruções e cuidados do manual.
 */
private fun getKeyPointsList(): List<HouseKeyPoint> {
    return listOf(
        HouseKeyPoint(
            id = "porta_entrada",
            title = "Porta de Entrada",
            roomZone = "Fachada Frontal / Sala de Estar",
            shortSummary = "Segredo da chave da porta antiga colonial de madeira",
            badge = "Dica Essencial da Chave",
            isWarning = false,
            icon = Icons.Default.Key,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/6fb3a5d8-c70f-488f-9a74-d45a9ca2ee41.jpeg",
            instructions = listOf(
                "Utilize a chave da porta principal (identificada no chaveiro da casa).",
                "Segredo de Ouro: Para abrir com facilidade, segure a chave com uma mão e com a outra puxe a folha da porta suavemente para fora enquanto gira a chave.",
                "O movimento de puxar alivia a pressão do trinco antigo e a lingueta destranca com extrema leveza.",
                "Ao sair para passeios, dê duas voltas completas na chave para manter a casa segura."
            ),
            precautions = listOf(
                "Não force a chave na fechadura caso ela pareça dura: basta puxar a folha para fora.",
                "A chave preta do chaveiro NÃO é da porta de entrada: ela é exclusivamente a chave reserva de emergência do portão eletrônico da garagem.",
                "Feche bem a porta e confirme que trancou antes de se ausentar da residência."
            ),
            manualChapterId = 3,
            manualChapterTitle = "Acesso, Chaves e Garagem",
            floorPointId = "entrada_principal",
            normX = 0.46f,
            normY = 0.68f,
            accentColorHex = 0xFF4F46E5 // Indigo
        ),
        HouseKeyPoint(
            id = "garagem",
            title = "Garagem Privativa",
            roomZone = "Pátio Frontal de Estacionamento",
            shortSummary = "3 Vagas (1 coberta) e abertura do portão eletrônico",
            badge = "Controle do Portão",
            isWarning = false,
            icon = Icons.Default.DirectionsCar,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/4be16a9e-8761-4564-94fe-b3fc9a9ca3e0.jpeg",
            instructions = listOf(
                "Capacidade total privativa para 3 veículos da sua família ou grupo.",
                "1 Vaga Coberta sob a laje/alpendre (em frente à entrada) e 2 Vagas Descobertas no pátio pavimentado.",
                "Para abrir e fechar o portão eletrônico deslizante, aperte o BOTÃO SUPERIOR DIREITO do controle remoto.",
                "O portão se abre suavemente para a lateral direita."
            ),
            precautions = listOf(
                "Sempre aguarde e confirme visualmente o fechamento completo do portão antes de sair do carro ou da casa.",
                "A chave preta é apenas para RESERVA DE EMERGÊNCIA (caso falte luz ou o controle falhe). Guarde-a com atenção no chaveiro.",
                "Atenção ao manobrar perto do muro lateral e do portãozinho de pedestres."
            ),
            manualChapterId = 3,
            manualChapterTitle = "Acesso, Chaves e Garagem",
            floorPointId = "garagem",
            normX = 0.22f,
            normY = 0.78f,
            accentColorHex = 0xFF0D9488 // Teal
        ),
        HouseKeyPoint(
            id = "patio",
            title = "Pátio & Varandas",
            roomZone = "Área Externa dos Fundos / Solarium",
            shortSummary = "Espreguiçadeiras, refeições ao ar livre e área de fumantes",
            badge = "Solarium & Área Aberta",
            isWarning = false,
            icon = Icons.Default.Yard,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/7c490215-dcfa-4395-ba38-b7aeaa0c0e7d.jpeg",
            instructions = listOf(
                "Espaço amplo ao ar livre e privativo, ensolarado e ideal para banho de sol e relaxamento.",
                "Espreguiçadeiras confortáveis disponíveis para descanso ao sol da Mantiqueira.",
                "As mesas e cadeiras plásticas que ficam na cozinha podem ser levadas livremente para o pátio para refeições ao ar livre.",
                "Acesso direto pela porta dos fundos que conecta a cozinha e a área de serviço ao pátio."
            ),
            precautions = listOf(
                "ÚNICA ÁREA DE FUMANTES: É estritamente proibido fumar dentro de qualquer cômodo da casa. O pátio e varandas abertas são os únicos locais onde o fumo é permitido.",
                "Degrau Alto: Atenção redobrada ao degrau de desnível na transição entre a varanda e a cozinha/sala, especialmente à noite!",
                "Chuva: Em caso de chuva iminente, favor recolher almofadas ou toalhas para o interior.",
                "Horário de Silêncio obrigatório das 22h às 07h da manhã."
            ),
            manualChapterId = 4,
            manualChapterTitle = "Entretenimento na Casa",
            floorPointId = "patio",
            normX = 0.74f,
            normY = 0.20f,
            accentColorHex = 0xFFD97706 // Warm Amber
        ),
        HouseKeyPoint(
            id = "cozinha",
            title = "Cozinha Equipada",
            roomZone = "Cozinha & Espaço Gourmet",
            shortSummary = "Uso da Air Fryer, fogão 4 bocas, kit café e alerta do forno",
            badge = "Atenção: Forno a Gás",
            isWarning = true,
            icon = Icons.Default.Kitchen,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/a0e88383-7fa3-4cb5-ae1c-29e2e6754020.jpeg",
            instructions = listOf(
                "Equipada com fogão 4 bocas, geladeira duplex com freezer e formas de gelo.",
                "Kit café completo com coador, suporte e garrafa térmica — café mineiro e açúcar de cortesia na bancada.",
                "Air Fryer elétrica prática na bancada para assar pães de queijo, batatas e lanches sem óleo e sem sujeira.",
                "Torradeira elétrica rápida para preparar seus pães e torradas no café da manhã."
            ),
            precautions = listOf(
                "ALERTA DE SEGURANÇA NO FORNO A GÁS: A chama do forno a gás é baixa e exige extrema cautela e vigilância manual para acender. Recomendamos enfaticamente o uso da Air Fryer para assados, pois é muito mais rápida, segura e prática!",
                "Mantenha sempre os registros das bocas do fogão bem fechados após cozinhar.",
                "Lixeira de Recicláveis: Cesto específico para secos (plásticos, latas, papéis) fica ao lado da geladeira."
            ),
            manualChapterId = 6,
            manualChapterTitle = "Cozinha e Equipamentos",
            floorPointId = "fogao",
            normX = 0.72f,
            normY = 0.46f,
            accentColorHex = 0xFFEA580C // Terracotta
        )
    )
}
