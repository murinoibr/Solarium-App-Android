package com.example.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ManualSection(
    val id: Int, // 1 to 10
    val title: String,
    val subtitle: String,
    val shortSummary: String,
    val iconName: String,
    val accentColorHex: Long = 0xFFC85A32,
    val items: List<SectionItem> = emptyList(),
    val tips: List<String> = emptyList(),
    val warnings: List<String> = emptyList(),
    val imageUrl: String? = null, // Airbnb photograph for this section
    val roomCategory: String? = null // e.g. "Sala", "Cozinha", "Quarto", "Pátio"
)

data class SectionItem(
    val title: String,
    val description: String,
    val steps: List<String> = emptyList(),
    val badge: String? = null
)

data class MapLocation(
    val id: String,
    val title: String,
    val category: LocationCategory,
    val address: String,
    val description: String,
    val tip: String? = null,
    val latitude: Double,
    val longitude: Double,
    val distanceEstimate: String,
    val tags: List<String> = emptyList()
)

enum class LocationCategory(val label: String) {
    HOUSE("A Casa & Arredores"),
    SERVICES("Serviços & Coleta"),
    ATTRACTION("Passeios & Lazer"),
    FOOD("Gastronomia & Cafés"),
    TRANSPORT("Transporte & Ônibus")
}

data class LocalRecommendation(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val hostTip: String,
    val address: String,
    val priceLevel: String,
    val isMustVisit: Boolean = false,
    val tags: List<String> = emptyList()
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val quickActionTitle: String? = null,
    val quickActionPayload: String? = null
)

data class FloorPlanPoint(
    val id: String,
    val title: String,
    val category: FloorPlanCategory,
    val roomOrZone: String,
    val description: String,
    val tipOrWarning: String? = null,
    val isWarning: Boolean = false,
    val badge: String? = null,
    val normX: Float,
    val normY: Float,
    val iconName: String,
    val manualSectionId: Int? = null,
    val tags: List<String> = emptyList()
)

enum class FloorPlanCategory(val label: String, val colorHex: Long) {
    ALL("Todos", 0xFF495057),
    ENTRANCE_ACCESS("Entradas & Portões", 0xFF2B8A3E),
    PARKING_OUTDOORS("Garagem & Pátio", 0xFF1971C2),
    LIVING_LEISURE("Sala, TV & Lazer", 0xFFE8590C),
    KITCHEN_APPLIANCES("Cozinha & Eletros", 0xFF7048E8),
    COMFORT_SERVICE("Conforto & Serviço", 0xFF0CA678),
    SURROUNDINGS("Entorno & Bairro", 0xFFD9480F)
}

enum class MapViewMode(val title: String) {
    FLOOR_PLAN("Planta da Casa"),
    NEIGHBORHOOD("Bairro & Cidade")
}
