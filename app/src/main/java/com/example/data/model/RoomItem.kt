package com.example.data.model

data class RoomPhoto(
    val filename: String,
    val caption: String,
    val isPrimary: Boolean = false
) {
    val assetUrl: String get() = "file:///android_asset/house_photos/$filename"
    val cdnUrl: String get() = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/$filename"
    val displayUrl: String get() = assetUrl
}

data class RoomItem(
    val id: String,
    val title: String,
    val floor: String, // "1º Andar (Térreo)" or "2º Andar (Superior)" or "Área Externa"
    val subtitle: String,
    val bedConfig: String,
    val description: String,
    val amenities: List<String>,
    val highlights: List<String>,
    val categoryIcon: String, // "Bed", "Tv", "Kitchen", "Deck", "Bathtub", "Yard", "DirectionsCar"
    val gradientColors: Pair<Long, Long>,
    val floorPointId: String? = null, // Link to pin on interactive map
    val imageUrl: String? = null, // Main Airbnb photograph URL
    val additionalImages: List<String> = emptyList(), // Extra Airbnb photograph URLs
    val photos: List<RoomPhoto> = emptyList(), // Structured photos by room
    val manualSectionId: Int? = null // Link to corresponding Manual Section (e.g. 4, 5, 6, 7)
) {
    val allPhotos: List<RoomPhoto>
        get() {
            if (photos.isNotEmpty()) return photos
            val list = mutableListOf<RoomPhoto>()
            imageUrl?.let {
                val fname = it.substringAfterLast("/")
                list.add(RoomPhoto(fname, title, isPrimary = true))
            }
            additionalImages.forEach {
                val fname = it.substringAfterLast("/")
                list.add(RoomPhoto(fname, title, isPrimary = false))
            }
            return list
        }

    val photoCount: Int
        get() = if (photos.isNotEmpty()) photos.size else (if (imageUrl != null) 1 else 0) + additionalImages.size
}
