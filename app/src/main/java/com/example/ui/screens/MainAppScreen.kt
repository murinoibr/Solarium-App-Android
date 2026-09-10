package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.SolariumTheme
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
                SolariumFloatingNavigationBar(
                    currentTab = uiState.currentTab,
                    onSelectTab = { viewModel.selectTab(it) }
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
                            viewModel.selectTab(3)
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
                                onNavigateToRecommendations = { viewModel.selectTab(2) },
                                onNavigateToChat = { viewModel.selectTab(3) },
                                onFeedback = { viewModel.showFeedback(it) }
                            )
                            1 -> ManualScreen(
                                sections = viewModel.allSections,
                                searchQuery = uiState.searchQuery,
                                onSearchChange = { viewModel.updateSearchQuery(it) },
                                onSectionClick = { viewModel.openSection(it) },
                                onFeedback = { viewModel.showFeedback(it) },
                                onNavigateToMapPoint = null,
                                onNavigateToManual = { sectionId ->
                                    viewModel.openSectionById(sectionId)
                                }
                            )
                            2 -> RecommendationsScreen(
                                recommendations = viewModel.allRecommendations,
                                selectedCategory = uiState.recommendationCategory,
                                favoriteIds = uiState.favoriteRecIds,
                                onSelectCategory = { viewModel.filterRecommendationCategory(it) },
                                onToggleFavorite = { viewModel.toggleFavorite(it) },
                                onFeedback = { viewModel.showFeedback(it) }
                            )
                            3 -> ChatSupportScreen(
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

private data class NavItemData(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@Composable
private fun SolariumFloatingNavigationBar(
    currentTab: Int,
    onSelectTab: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = SolariumTheme.colors
    val navItems = remember {
        listOf(
            NavItemData("Início", Icons.Default.Home, Icons.Outlined.Home, "nav_tab_home"),
            NavItemData("Manual", Icons.AutoMirrored.Filled.MenuBook, Icons.AutoMirrored.Outlined.MenuBook, "nav_tab_manual"),
            NavItemData("Dicas", Icons.Default.Explore, Icons.Outlined.Explore, "nav_tab_recommendations"),
            NavItemData("Concierge", Icons.Default.SupportAgent, Icons.Outlined.SupportAgent, "nav_tab_chat")
        )
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .shadow(
                elevation = 10.dp,
                shape = CircleShape,
                clip = false,
                ambientColor = colors.textPrimary.copy(alpha = 0.12f),
                spotColor = colors.sunOrange.copy(alpha = 0.20f)
            )
            .testTag("main_navigation_bar"),
        shape = CircleShape,
        color = colors.creamSurface.copy(alpha = 0.94f),
        tonalElevation = 6.dp,
        border = BorderStroke(1.dp, colors.linenBorder.copy(alpha = 0.85f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEachIndexed { index, item ->
                val selected = currentTab == index

                val iconScale by animateFloatAsState(
                    targetValue = if (selected) 1.15f else 1.0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    ),
                    label = "nav_icon_scale"
                )

                val activeBgColor by animateColorAsState(
                    targetValue = if (selected) colors.sunOrangeContainer else Color.Transparent,
                    animationSpec = tween(durationMillis = 240),
                    label = "nav_bg_color"
                )

                val activeContentColor by animateColorAsState(
                    targetValue = if (selected) colors.sunOrange else colors.textSecondary.copy(alpha = 0.75f),
                    animationSpec = tween(durationMillis = 200),
                    label = "nav_content_color"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(CircleShape)
                        .background(activeBgColor)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(bounded = true, radius = 32.dp),
                            onClick = { onSelectTab(index) }
                        )
                        .padding(vertical = 8.dp)
                        .testTag(item.testTag),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label,
                            tint = activeContentColor,
                            modifier = Modifier
                                .size(22.dp)
                                .scale(iconScale)
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.5.sp,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                            ),
                            color = activeContentColor,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
