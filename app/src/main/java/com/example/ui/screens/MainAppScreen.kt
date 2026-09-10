package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.viewmodel.HouseViewModel

@Composable
fun MainAppScreen(
    viewModel: HouseViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.copyFeedbackMessage) {
        uiState.copyFeedbackMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearFeedback()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (uiState.selectedSection == null) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 3.dp,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .testTag("main_navigation_bar")
                ) {
                    NavigationBarItem(
                        selected = uiState.currentTab == 0,
                        onClick = { viewModel.selectTab(0) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.currentTab == 0) Icons.Default.Home else Icons.Outlined.Home,
                                contentDescription = "Início"
                            )
                        },
                        label = {
                            Text(
                                "Início",
                                fontWeight = if (uiState.currentTab == 0) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_tab_home")
                    )

                    NavigationBarItem(
                        selected = uiState.currentTab == 1,
                        onClick = { viewModel.selectTab(1) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.currentTab == 1) Icons.AutoMirrored.Filled.MenuBook else Icons.AutoMirrored.Outlined.MenuBook,
                                contentDescription = "Manual da Casa"
                            )
                        },
                        label = {
                            Text(
                                "Manual",
                                fontWeight = if (uiState.currentTab == 1) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_tab_manual")
                    )

                    NavigationBarItem(
                        selected = uiState.currentTab == 2,
                        onClick = { viewModel.selectTab(2) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.currentTab == 2) Icons.Default.Map else Icons.Outlined.Map,
                                contentDescription = "Mapa Interativo"
                            )
                        },
                        label = {
                            Text(
                                "Mapa",
                                fontWeight = if (uiState.currentTab == 2) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_tab_map")
                    )

                    NavigationBarItem(
                        selected = uiState.currentTab == 3,
                        onClick = { viewModel.selectTab(3) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.currentTab == 3) Icons.Default.Explore else Icons.Outlined.Explore,
                                contentDescription = "Dicas Locais"
                            )
                        },
                        label = {
                            Text(
                                "Dicas",
                                fontWeight = if (uiState.currentTab == 3) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_tab_recommendations")
                    )

                    NavigationBarItem(
                        selected = uiState.currentTab == 4,
                        onClick = { viewModel.selectTab(4) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.currentTab == 4) Icons.Default.SupportAgent else Icons.Outlined.SupportAgent,
                                contentDescription = "Concierge IA"
                            )
                        },
                        label = {
                            Text(
                                "Concierge",
                                fontWeight = if (uiState.currentTab == 4) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_tab_chat")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = uiState.selectedSection,
                transitionSpec = {
                    if (targetState != null) {
                        (slideInHorizontally(
                            animationSpec = spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = Spring.DampingRatioNoBouncy),
                            initialOffsetX = { fullWidth -> (fullWidth * 0.18f).toInt() }
                        ) + fadeIn(animationSpec = tween(280, easing = FastOutSlowInEasing))) togetherWith
                        (slideOutHorizontally(
                            animationSpec = tween(220, easing = FastOutSlowInEasing),
                            targetOffsetX = { fullWidth -> (-fullWidth * 0.10f).toInt() }
                        ) + fadeOut(animationSpec = tween(200)))
                    } else {
                        (slideInHorizontally(
                            animationSpec = spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = Spring.DampingRatioNoBouncy),
                            initialOffsetX = { fullWidth -> (-fullWidth * 0.10f).toInt() }
                        ) + fadeIn(animationSpec = tween(260, easing = FastOutSlowInEasing))) togetherWith
                        (slideOutHorizontally(
                            animationSpec = tween(220, easing = FastOutSlowInEasing),
                            targetOffsetX = { fullWidth -> (fullWidth * 0.18f).toInt() }
                        ) + fadeOut(animationSpec = tween(200)))
                    }
                },
                label = "section_detail_transition"
            ) { section ->
                if (section != null) {
                    SectionDetailScreen(
                        section = section,
                        onBack = { viewModel.closeSection() },
                        onAskInChat = { question ->
                            viewModel.closeSection()
                            viewModel.selectTab(4)
                            viewModel.sendChatMessage(question)
                        },
                        onFeedback = { viewModel.showFeedback(it) }
                    )
                } else {
                    AnimatedContent(
                        targetState = uiState.currentTab,
                        transitionSpec = {
                            val forward = targetState > initialState
                            val initialOffset = if (forward) { width: Int -> (width * 0.14f).toInt() } else { width: Int -> (-width * 0.14f).toInt() }
                            val targetExit = if (forward) { width: Int -> (-width * 0.12f).toInt() } else { width: Int -> (width * 0.12f).toInt() }
                            (slideInHorizontally(
                                animationSpec = spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = Spring.DampingRatioNoBouncy),
                                initialOffsetX = initialOffset
                            ) + fadeIn(animationSpec = tween(280, easing = FastOutSlowInEasing))) togetherWith
                            (slideOutHorizontally(
                                animationSpec = tween(220, easing = FastOutSlowInEasing),
                                targetOffsetX = targetExit
                            ) + fadeOut(animationSpec = tween(200)))
                        },
                        label = "tab_transition"
                    ) { tab ->
                        when (tab) {
                            0 -> HomeScreen(
                                sections = viewModel.allSections,
                                onSectionClick = { viewModel.openSection(it) },
                                onNavigateToManual = { viewModel.selectTab(1) },
                                onNavigateToMap = { viewModel.selectTab(2) },
                                onNavigateToRecommendations = { viewModel.selectTab(3) },
                                onNavigateToChat = { viewModel.selectTab(4) },
                                onFeedback = { viewModel.showFeedback(it) }
                            )
                            1 -> ManualScreen(
                                sections = viewModel.allSections,
                                searchQuery = uiState.searchQuery,
                                onSearchChange = { viewModel.updateSearchQuery(it) },
                                onSectionClick = { viewModel.openSection(it) },
                                onFeedback = { viewModel.showFeedback(it) },
                                onNavigateToMapPoint = { pointId ->
                                    viewModel.selectFloorPointById(pointId)
                                    viewModel.selectTab(2)
                                },
                                onNavigateToManual = { sectionId ->
                                    viewModel.openSectionById(sectionId)
                                }
                            )
                            2 -> InteractiveMapScreen(
                                locations = viewModel.allLocations,
                                selectedLocation = uiState.selectedLocation,
                                activeCategory = uiState.activeMapCategory,
                                onSelectCategory = { viewModel.filterMapCategory(it) },
                                onSelectLocation = { viewModel.selectMapLocation(it) },
                                onFeedback = { viewModel.showFeedback(it) },
                                floorPoints = viewModel.allFloorPoints,
                                selectedFloorPoint = uiState.selectedFloorPoint,
                                activeFloorCategory = uiState.activeFloorCategory,
                                mapViewMode = uiState.mapViewMode,
                                floorSearchQuery = uiState.floorSearchQuery,
                                onSelectFloorPoint = { viewModel.selectFloorPoint(it) },
                                onSelectFloorCategory = { viewModel.filterFloorCategory(it) },
                                onChangeMapViewMode = { viewModel.setMapViewMode(it) },
                                onUpdateFloorSearch = { viewModel.updateFloorSearch(it) },
                                onNavigateToManual = { sectionId ->
                                    viewModel.selectTab(1)
                                    viewModel.openSectionById(sectionId)
                                }
                            )
                            3 -> RecommendationsScreen(
                                recommendations = viewModel.allRecommendations,
                                selectedCategory = uiState.recommendationCategory,
                                favoriteIds = uiState.favoriteRecIds,
                                onSelectCategory = { viewModel.filterRecommendationCategory(it) },
                                onToggleFavorite = { viewModel.toggleFavorite(it) },
                                onFeedback = { viewModel.showFeedback(it) }
                            )
                            4 -> ChatSupportScreen(
                                messages = uiState.chatMessages,
                                isLoading = uiState.isChatLoading,
                                quickPrompts = viewModel.quickPrompts,
                                onSendMessage = { viewModel.sendChatMessage(it) },
                                onClearChat = { viewModel.clearChat() },
                                onFeedback = { viewModel.showFeedback(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}
