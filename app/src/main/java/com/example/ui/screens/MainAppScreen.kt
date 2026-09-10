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
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
                )
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
                                onFeedback = { viewModel.showFeedback(it) }
                            )
                            1 -> ManualScreen(
                                sections = viewModel.allSections,
                                searchQuery = uiState.searchQuery,
                                onSearchChange = { viewModel.updateSearchQuery(it) },
                                onSectionClick = { viewModel.openSection(it) },
                                onFeedback = { viewModel.showFeedback(it) },
                                onNavigateToManual = { sectionId ->
                                    viewModel.openSectionById(sectionId)
                                }
                            )
                                recommendations = viewModel.allRecommendations,
                                selectedCategory = uiState.recommendationCategory,
                                favoriteIds = uiState.favoriteRecIds,
                                onSelectCategory = { viewModel.filterRecommendationCategory(it) },
                                onToggleFavorite = { viewModel.toggleFavorite(it) },
                                onFeedback = { viewModel.showFeedback(it) }
                            )
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
