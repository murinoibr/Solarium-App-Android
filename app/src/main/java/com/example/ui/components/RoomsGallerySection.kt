package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Deck
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material.icons.filled.Yard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.model.RoomItem
import com.example.data.model.RoomPhoto

enum class GalleryDisplayMode {
    CAROUSEL,
    EXPANDED_BY_ROOM
}

@Composable
fun RoomsGallerySection(
    rooms: List<RoomItem>,
    onRoomClick: (RoomItem) -> Unit,
    onNavigateToMapPoint: ((String) -> Unit)? = null,
    onNavigateToManual: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("Todos") }
    var selectedRoomForDetail by remember { mutableStateOf<RoomItem?>(null) }
    var initialPhotoIndexForDetail by remember { mutableIntStateOf(0) }
    var fullScreenViewerData by remember { mutableStateOf<Pair<RoomItem, Int>?>(null) }
    var displayMode by remember { mutableStateOf(GalleryDisplayMode.CAROUSEL) }

    val totalPhotosCount = remember(rooms) {
        rooms.sumOf { it.allPhotos.size }
    }

    val filters = listOf(
        "Todos",
        "Quartos",
        "Salas",
        "Cozinha & Copa",
        "Varandas & Solarium",
        "Banheiros",
        "Fachada & Garagem"
    )

    val filteredRooms = when (selectedFilter) {
        "Quartos" -> rooms.filter { it.categoryIcon == "Bed" || it.title.contains("Quarto", ignoreCase = true) }
        "Salas" -> rooms.filter { it.categoryIcon == "Tv" || it.categoryIcon == "MusicNote" || it.title.contains("Sala", ignoreCase = true) }
        "Cozinha & Copa" -> rooms.filter { it.categoryIcon == "Kitchen" || it.title.contains("Cozinha", ignoreCase = true) }
        "Varandas & Solarium" -> rooms.filter { it.categoryIcon == "Deck" || it.title.contains("Solarium", ignoreCase = true) || it.title.contains("Varanda", ignoreCase = true) }
        "Banheiros" -> rooms.filter { it.categoryIcon == "Bathtub" || it.title.contains("Banheiro", ignoreCase = true) }
        "Fachada & Garagem" -> rooms.filter { it.categoryIcon == "DirectionsCar" || it.categoryIcon == "Yard" || it.title.contains("Fachada", ignoreCase = true) || it.title.contains("Garagem", ignoreCase = true) }
        else -> rooms
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("rooms_gallery_section")
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(26.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Fotos dos Cômodos",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "$totalPhotosCount fotos oficiais organizadas por cômodo",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 34.dp, top = 2.dp)
                )
            }

            // Display Mode Toggle (Carrossel vs Grade)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        displayMode = if (displayMode == GalleryDisplayMode.CAROUSEL) {
                            GalleryDisplayMode.EXPANDED_BY_ROOM
                        } else {
                            GalleryDisplayMode.CAROUSEL
                        }
                    }
                    .testTag("toggle_gallery_display_mode")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (displayMode == GalleryDisplayMode.CAROUSEL) Icons.Default.GridView else Icons.Default.ViewCarousel,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (displayMode == GalleryDisplayMode.CAROUSEL) "Ver Todas" else "Carrossel",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Filter Pills by Room Type
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filters) { filter ->
                val isSelected = selectedFilter == filter
                val countForFilter = when (filter) {
                    "Todos" -> rooms.size
                    "Quartos" -> rooms.count { it.categoryIcon == "Bed" || it.title.contains("Quarto", ignoreCase = true) }
                    "Salas" -> rooms.count { it.categoryIcon == "Tv" || it.categoryIcon == "MusicNote" || it.title.contains("Sala", ignoreCase = true) }
                    "Cozinha & Copa" -> rooms.count { it.categoryIcon == "Kitchen" || it.title.contains("Cozinha", ignoreCase = true) }
                    "Varandas & Solarium" -> rooms.count { it.categoryIcon == "Deck" || it.title.contains("Solarium", ignoreCase = true) || it.title.contains("Varanda", ignoreCase = true) }
                    "Banheiros" -> rooms.count { it.categoryIcon == "Bathtub" || it.title.contains("Banheiro", ignoreCase = true) }
                    "Fachada & Garagem" -> rooms.count { it.categoryIcon == "DirectionsCar" || it.categoryIcon == "Yard" || it.title.contains("Fachada", ignoreCase = true) || it.title.contains("Garagem", ignoreCase = true) }
                    else -> rooms.size
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { selectedFilter = filter }
                        .testTag("room_filter_$filter")
                ) {
                    Text(
                        text = "$filter ($countForFilter)",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        AnimatedContent(
            targetState = displayMode,
            transitionSpec = {
                fadeIn(animationSpec = tween(240)) togetherWith fadeOut(animationSpec = tween(180))
            },
            label = "gallery_display_mode_transition"
        ) { mode ->
            if (mode == GalleryDisplayMode.CAROUSEL) {
                // Horizontal Carousel of Rooms
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("rooms_carousel")
                ) {
                    items(filteredRooms) { room ->
                        RoomVisualCard(
                            room = room,
                            onClick = {
                                initialPhotoIndexForDetail = 0
                                selectedRoomForDetail = room
                                onRoomClick(room)
                            }
                        )
                    }
                }
            } else {
                // Expanded View Organized by Room
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    filteredRooms.forEach { room ->
                        RoomExpandedSectionItem(
                            room = room,
                            onPhotoClick = { photoIndex ->
                                fullScreenViewerData = Pair(room, photoIndex)
                            },
                            onRoomClick = {
                                initialPhotoIndexForDetail = 0
                                selectedRoomForDetail = room
                                onRoomClick(room)
                            }
                        )
                    }
                }
            }
        }
    }

    // Modal Sheet / Dialog with Room details and photo viewer
    if (selectedRoomForDetail != null) {
        RoomDetailDialog(
            room = selectedRoomForDetail!!,
            initialPhotoIndex = initialPhotoIndexForDetail,
            onDismiss = { selectedRoomForDetail = null },
            onOpenFullScreen = { photoIndex ->
                fullScreenViewerData = Pair(selectedRoomForDetail!!, photoIndex)
            },
            onNavigateToMapPoint = { pointId ->
                selectedRoomForDetail = null
                onNavigateToMapPoint?.invoke(pointId)
            },
            onNavigateToManual = { sectionId ->
                selectedRoomForDetail = null
                onNavigateToManual?.invoke(sectionId)
            }
        )
    }

    // Full-Screen Photo Viewer Dialog
    if (fullScreenViewerData != null) {
        val (room, initialIndex) = fullScreenViewerData!!
        FullScreenPhotoViewerDialog(
            room = room,
            initialIndex = initialIndex,
            onDismiss = { fullScreenViewerData = null }
        )
    }
}

@Composable
private fun RoomVisualCard(
    room: RoomItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(245.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .testTag("room_card_${room.id}"),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            // Visual Banner with Local/CDN Photo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(room.gradientColors.first),
                                Color(room.gradientColors.second)
                            )
                        )
                    )
            ) {
                val primaryPhotoUrl = room.allPhotos.firstOrNull()?.displayUrl ?: room.imageUrl
                if (primaryPhotoUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(primaryPhotoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = room.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Dark gradient scrim for legibility
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
                }

                // Room Floor Badge
                Surface(
                    shape = RoundedCornerShape(bottomEnd = 10.dp),
                    color = Color.Black.copy(alpha = 0.6f),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = room.floor,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.SemiBold),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                // Photo Count Badge (e.g. 📷 5 fotos)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.65f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoLibrary,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${room.allPhotos.size} fotos",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }
                }

                // Bed Config / Highlights Pill at bottom
                Surface(
                    shape = RoundedCornerShape(topStart = 8.dp),
                    color = Color.Black.copy(alpha = 0.65f),
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = room.bedConfig.take(26),
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Medium),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Text Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = room.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = iconForRoomCategory(room.categoryIcon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = room.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 15.sp
                )

                // Thumbnail mini-strip if multiple photos exist
                if (room.allPhotos.size > 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        room.allPhotos.take(4).forEach { photo ->
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .size(width = 38.dp, height = 26.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(photo.displayUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                        if (room.allPhotos.size > 4) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.size(width = 28.dp, height = 26.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "+${room.allPhotos.size - 4}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Highlight pill
                if (room.highlights.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "★ ${room.highlights.first()}",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

/**
 * Expanded layout showing each room with a horizontal strip of all its photos with captions
 */
@Composable
private fun RoomExpandedSectionItem(
    room: RoomItem,
    onPhotoClick: (Int) -> Unit,
    onRoomClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .testTag("room_expanded_${room.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Room Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onRoomClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(30.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = iconForRoomCategory(room.categoryIcon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = room.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${room.floor} • ${room.allPhotos.size} foto(s)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f),
                    modifier = Modifier.clickable { onRoomClick() }
                ) {
                    Text(
                        text = "Ver detalhes",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Photos strip for this specific room
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(room.allPhotos) { index, photo ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .width(160.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onPhotoClick(index) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(photo.displayUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = photo.caption,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Surface(
                                    shape = CircleShape,
                                    color = Color.Black.copy(alpha = 0.5f),
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(4.dp)
                                        .size(24.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Fullscreen,
                                            contentDescription = "Expandir foto",
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = photo.caption,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(8.dp),
                                lineHeight = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RoomDetailDialog(
    room: RoomItem,
    initialPhotoIndex: Int = 0,
    onDismiss: () -> Unit,
    onOpenFullScreen: (Int) -> Unit,
    onNavigateToMapPoint: ((String) -> Unit)? = null,
    onNavigateToManual: ((Int) -> Unit)? = null
) {
    val scrollState = rememberScrollState()
    val photos = room.allPhotos
    var currentPhotoIndex by remember { mutableIntStateOf(initialPhotoIndex.coerceIn(0, (photos.size - 1).coerceAtLeast(0))) }

    val currentPhoto = photos.getOrNull(currentPhotoIndex)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(24.dp))
                .testTag("room_detail_dialog"),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                // Header Visual with current photograph
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(room.gradientColors.first),
                                    Color(room.gradientColors.second)
                                )
                            )
                        )
                ) {
                    if (currentPhoto != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(currentPhoto.displayUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = currentPhoto.caption,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { onOpenFullScreen(currentPhotoIndex) }
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.4f),
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.75f)
                                        )
                                    )
                                )
                        )
                    }

                    // Top Bar controls: Fullscreen & Close
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.5f),
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { onOpenFullScreen(currentPhotoIndex) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Fullscreen,
                                    contentDescription = "Expandir tela cheia",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Tela Cheia",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                            }
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.Black.copy(alpha = 0.5f),
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Close, contentDescription = "Fechar", tint = Color.White, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }

                    // Bottom info: Floor and Room Title
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color.Black.copy(alpha = 0.6f)
                        ) {
                            Text(
                                text = room.floor,
                                style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = room.title,
                            style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontWeight = FontWeight.ExtraBold)
                        )
                    }
                }

                // Active Photo Caption
                if (currentPhoto != null) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = currentPhoto.caption,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${currentPhotoIndex + 1}/${photos.size}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                // Thumbnail gallery if multiple photos exist
                if (photos.size > 1) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(photos) { index, photo ->
                            val isSelected = currentPhotoIndex == index
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
                                modifier = Modifier
                                    .size(width = 72.dp, height = 52.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { currentPhotoIndex = index }
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(photo.displayUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = photo.caption,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }

                // Body content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Bed Config / Setup
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = iconForRoomCategory(room.categoryIcon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Configuração do Cômodo",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = room.bedConfig,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Descrição:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = room.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 21.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Amenities Chips
                    Text(
                        text = "Comodidades no cômodo:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    FlowRowSimple(items = room.amenities) { amenity ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                            modifier = Modifier.padding(bottom = 6.dp, end = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = amenity,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }

                    // Highlights
                    if (room.highlights.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        room.highlights.forEach { hl ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 2.dp)
                            ) {
                                Text("★", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = hl,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            if (room.manualSectionId != null && onNavigateToManual != null) {
                                Button(
                                    onClick = { onNavigateToManual(room.manualSectionId) },
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Ver Manual", color = MaterialTheme.colorScheme.onSecondaryContainer)
                                }
                            }

                            if (room.floorPointId != null && onNavigateToMapPoint != null) {
                                Button(
                                    onClick = { onNavigateToMapPoint(room.floorPointId) },
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Na Planta")
                                }
                            }
                        }

                        Button(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Text("Fechar", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Full screen photo viewer with navigation arrows, caption, and counter
 */
@Composable
private fun FullScreenPhotoViewerDialog(
    room: RoomItem,
    initialIndex: Int,
    onDismiss: () -> Unit
) {
    val photos = room.allPhotos
    var currentIndex by remember { mutableIntStateOf(initialIndex.coerceIn(0, (photos.size - 1).coerceAtLeast(0))) }
    val photo = photos.getOrNull(currentIndex)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.96f))
                .testTag("fullscreen_photo_viewer")
        ) {
            // Main Image
            if (photo != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo.displayUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = photo.caption,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 80.dp)
                )
            }

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = room.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = "Foto ${currentIndex + 1} de ${photos.size}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(44.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.2f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Close, contentDescription = "Fechar", tint = Color.White)
                        }
                    }
                }
            }

            // Left navigation arrow
            if (currentIndex > 0) {
                IconButton(
                    onClick = { currentIndex-- },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 12.dp)
                        .size(48.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.6f),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Anterior", tint = Color.White)
                        }
                    }
                }
            }

            // Right navigation arrow
            if (currentIndex < photos.size - 1) {
                IconButton(
                    onClick = { currentIndex++ },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp)
                        .size(48.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.6f),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Próxima", tint = Color.White)
                        }
                    }
                }
            }

            // Bottom Caption Bar
            if (photo != null) {
                Surface(
                    color = Color.Black.copy(alpha = 0.75f),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = photo.caption,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FlowRowSimple(
    items: List<String>,
    content: @Composable (String) -> Unit
) {
    Column {
        val chunked = items.chunked(3)
        chunked.forEach { rowItems ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowItems.forEach { item ->
                    content(item)
                }
            }
        }
    }
}

private fun iconForRoomCategory(iconName: String): ImageVector {
    return when (iconName) {
        "Bed" -> Icons.Default.Bed
        "Tv" -> Icons.Default.Tv
        "Kitchen" -> Icons.Default.Kitchen
        "Deck" -> Icons.Default.Deck
        "Bathtub" -> Icons.Default.Bathtub
        "Yard" -> Icons.Default.Yard
        "MusicNote" -> Icons.Default.MusicNote
        "DirectionsCar" -> Icons.Default.DirectionsCar
        "LocalLaundryService" -> Icons.Default.LocalLaundryService
        else -> Icons.Default.Bed
    }
}
