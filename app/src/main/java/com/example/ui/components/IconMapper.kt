package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector

fun getSectionIcon(iconName: String): ImageVector {
    return when (iconName) {
        "Favorite" -> Icons.Default.Favorite
        "Place" -> Icons.Default.Place
        "Key" -> Icons.Default.Key
        "Tv" -> Icons.Default.Tv
        "Bed" -> Icons.Default.Bed
        "Restaurant" -> Icons.Default.Restaurant
        "Shield" -> Icons.Default.Shield
        "Delete" -> Icons.Default.Delete
        "DirectionsCar" -> Icons.Default.DirectionsCar
        "Explore" -> Icons.Default.Explore
        else -> Icons.AutoMirrored.Filled.Help
    }
}
