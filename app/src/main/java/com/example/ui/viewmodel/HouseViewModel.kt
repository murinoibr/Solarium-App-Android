package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.gemini.GeminiChatService
import com.example.data.model.ChatMessage
import com.example.data.model.FloorPlanCategory
import com.example.data.model.FloorPlanPoint
import com.example.data.model.LocalRecommendation
import com.example.data.model.LocationCategory
import com.example.data.model.ManualSection
import com.example.data.model.MapLocation
import com.example.data.model.MapViewMode
import com.example.data.repository.HouseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class HouseUiState(
    val currentTab: Int = 0, // 0: Manual, 1: Mapa, 2: Dicas, 3: Chat
    val selectedSection: ManualSection? = null,
    val searchQuery: String = "",
    val activeMapCategory: LocationCategory? = null,
    val selectedLocation: MapLocation? = null,
    val mapViewMode: MapViewMode = MapViewMode.FLOOR_PLAN,
    val activeFloorCategory: FloorPlanCategory? = null,
    val selectedFloorPoint: FloorPlanPoint? = null,
    val floorSearchQuery: String = "",
    val recommendationCategory: String = "Todos",
    val favoriteRecIds: Set<String> = emptySet(),
    val chatMessages: List<ChatMessage> = listOf(
        ChatMessage(
            id = "msg_welcome",
            text = "Olá! Seja muito bem-vindo à nossa casa em São Lourenço! Sou a Concierge Digital da anfitriã Valéria. Como posso te ajudar hoje?",
            isFromUser = false,
        ),
    ),
    val isChatLoading: Boolean = false,
    val copyFeedbackMessage: String? = null,
)

class HouseViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HouseUiState())
    val uiState: StateFlow<HouseUiState> = _uiState.asStateFlow()

    val allSections: List<ManualSection> = HouseRepository.sections
    val allLocations: List<MapLocation> = HouseRepository.mapLocations
    val allFloorPoints: List<FloorPlanPoint> = HouseRepository.floorPlanPoints
    val allRecommendations: List<LocalRecommendation> = HouseRepository.localRecommendations
    val quickPrompts: List<String> = HouseRepository.quickQuestions

    fun selectTab(tabIndex: Int) {
        _uiState.update { it.copy(currentTab = tabIndex) }
    }

    fun openSection(section: ManualSection) {
        _uiState.update { it.copy(selectedSection = section) }
    }

    fun openSectionById(sectionId: Int) {
        val found = allSections.find { it.id == sectionId }
        _uiState.update { it.copy(selectedSection = found) }
    }

    fun closeSection() {
        _uiState.update { it.copy(selectedSection = null) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun filterMapCategory(category: LocationCategory?) {
        _uiState.update {
            it.copy(
                activeMapCategory = if (it.activeMapCategory == category) null else category,
                selectedLocation = null,
            )
        }
    }

    fun selectMapLocation(location: MapLocation?) {
        _uiState.update { it.copy(selectedLocation = location) }
    }

    fun setMapViewMode(mode: MapViewMode) {
        _uiState.update { it.copy(mapViewMode = mode) }
    }

    fun filterFloorCategory(category: FloorPlanCategory?) {
        _uiState.update {
            it.copy(
                activeFloorCategory = if (it.activeFloorCategory == category) null else category,
                selectedFloorPoint = null,
            )
        }
    }

    fun selectFloorPoint(point: FloorPlanPoint?) {
        _uiState.update { it.copy(selectedFloorPoint = point) }
    }

    fun selectFloorPointById(pointId: String) {
        val found = allFloorPoints.find { it.id == pointId }
        _uiState.update { it.copy(selectedFloorPoint = found, mapViewMode = MapViewMode.FLOOR_PLAN) }
    }

    fun updateFloorSearch(query: String) {
        _uiState.update { it.copy(floorSearchQuery = query) }
    }

    fun filterRecommendationCategory(category: String) {
        _uiState.update { it.copy(recommendationCategory = category) }
    }

    fun toggleFavorite(recId: String) {
        _uiState.update { state ->
            val updated = if (state.favoriteRecIds.contains(recId)) {
                state.favoriteRecIds - recId
            } else {
                state.favoriteRecIds + recId
            }
            state.copy(favoriteRecIds = updated)
        }
    }

    fun showFeedback(message: String) {
        _uiState.update { it.copy(copyFeedbackMessage = message) }
    }

    fun clearFeedback() {
        _uiState.update { it.copy(copyFeedbackMessage = null) }
    }

    fun sendChatMessage(query: String) {
        if (query.isBlank()) return
        val userMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            text = query.trim(),
            isFromUser = true,
        )

        _uiState.update { state ->
            state.copy(
                chatMessages = state.chatMessages + userMsg,
                isChatLoading = true,
            )
        }

        viewModelScope.launch {
            val answer = GeminiChatService.askAssistant(query)
            val assistantMsg = ChatMessage(
                id = UUID.randomUUID().toString(),
                text = answer,
                isFromUser = false,
            )
            _uiState.update { state ->
                state.copy(
                    chatMessages = state.chatMessages + assistantMsg,
                    isChatLoading = false,
                )
            }
        }
    }

    fun clearChat() {
        _uiState.update {
            it.copy(
                chatMessages = listOf(
                    ChatMessage(
                        id = "msg_reset",
                        text = "Conversa reiniciada! Como posso te auxiliar com a estadia na casa ou passeios em São Lourenço?",
                        isFromUser = false,
                    ),
                ),
            )
        }
    }
}
