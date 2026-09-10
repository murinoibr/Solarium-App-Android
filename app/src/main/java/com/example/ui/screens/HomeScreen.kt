package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ManualSection
import com.example.data.repository.HouseRepository
import com.example.ui.theme.SolariumTheme
import kotlin.math.cos
import kotlin.math.sin

/**
 * Tela Inicial Independente (Início) com visual acolhedor,
 * logomarca minimalista e clean do Solarium e acesso direto aos 10 itens do manual.
 */
@Composable
fun HomeScreen(
    sections: List<ManualSection>,
    onSectionClick: (ManualSection) -> Unit,
    onNavigateToManual: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToRecommendations: () -> Unit,
    onNavigateToChat: () -> Unit,
    onFeedback: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val colors = SolariumTheme.colors

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.creamBackground)
            .testTag("home_screen_root"),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // 1. Logomarca Solarium Clean e Minimalista
        item(key = "solarium_brand_header") {
            SolariumMinimalistBrandHeader(
                onCopyAddress = {
                    copyToClipboard(context, HouseRepository.HOUSE_ADDRESS, "Endereço da casa copiado!")
                    onFeedback("Endereço copiado para a área de transferência")
                }
            )
        }

        // 2. Cartões Rápidos de Acesso e Wi-Fi (Clean & Funcional)
        item(key = "quick_info_strip") {
            QuickInfoStrip(
                onCopyWifi = {
                    copyToClipboard(context, HouseRepository.WIFI_PASSWORD, "Senha do Wi-Fi copiada!")
                    onFeedback("Senha do Wi-Fi copiada: ${HouseRepository.WIFI_PASSWORD}")
                },
                onContactHost = {
                    openWhatsApp(context, HouseRepository.HOST_PHONE, "Olá Valéria! Sou hóspede da casa Solarium.")
                },
                onFeedback = onFeedback
            )
        }

        // 3. Cabeçalho dos 10 Itens do Manual
        item(key = "ten_items_header") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Manual da Casa",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                letterSpacing = (-0.2).sp
                            ),
                            color = colors.textPrimary
                        )
                        Text(
                            text = "10 guias essenciais para sua estadia",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = colors.softYellowContainer,
                        modifier = Modifier.clickable { onNavigateToManual() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Ver Tudo",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                ),
                                color = colors.onSoftYellowContainer
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = colors.onSoftYellowContainer,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }

        // 4. Grade/Lista Minimalista com os 10 Itens
        items(
            count = sections.size,
            key = { index -> "manual_item_${sections[index].id}" }
        ) { index ->
            val section = sections[index]
            MinimalistManualItemRow(
                index = index + 1,
                section = section,
                onClick = { onSectionClick(section) }
            )
        }

        // 5. Atalhos Rápidos para Outras Seções (Clean & Modern)
        item(key = "quick_shortcuts_footer") {
            QuickNavigationShortcuts(
                onNavigateToMap = onNavigateToMap,
                onNavigateToRecommendations = onNavigateToRecommendations,
                onNavigateToChat = onNavigateToChat
            )
        }
    }
}

/**
 * Logomarca minimalista e clean do Solarium com estética ensolarada e acolhedora.
 */
@Composable
private fun SolariumMinimalistBrandHeader(
    onCopyAddress: () -> Unit
) {
    val colors = SolariumTheme.colors
    val infiniteTransition = rememberInfiniteTransition(label = "sun_pulse")
    val sunAuraScale by infiniteTransition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sun_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Ícone Minimalista de Sol com Raios Geométricos
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                colors.softYellowContainer.copy(alpha = 0.9f),
                                colors.sunOrangeContainer.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                MinimalistSunLogoCanvas(
                    sunColor = colors.sunOrange,
                    rayColor = colors.softYellow,
                    pulse = sunAuraScale,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Nome da Marca com Tipografia Elegante e Amplo Espaçamento
            Text(
                text = "S O L A R I U M",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 4.5.sp,
                    fontFamily = FontFamily.Serif
                ),
                color = colors.textPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "CASA DE CAMPO • SÃO LOURENÇO, MG",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp
                ),
                color = colors.warmTerracotta,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Frase de Boas-Vindas Minimalista
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = colors.creamCard,
                border = androidx.compose.foundation.BorderStroke(1.dp, colors.linenBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(role = Role.Button, onClick = onCopyAddress)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = colors.sunOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = HouseRepository.HOUSE_ADDRESS,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = colors.textSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copiar endereço",
                        tint = colors.warmTerracotta,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
    }
}

/**
 * Desenho vetorial minimalista do sol em Canvas (Círculo solar + 8 raios finos e elegantes).
 */
@Composable
private fun MinimalistSunLogoCanvas(
    sunColor: Color,
    rayColor: Color,
    pulse: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val centerRadius = (size.width * 0.24f) * pulse
        val innerRay = size.width * 0.34f
        val outerRay = size.width * 0.46f

        // Círculo Central Solar
        drawCircle(
            color = sunColor,
            radius = centerRadius,
            center = Offset(centerX, centerY)
        )

        // 8 Raios Solares Finos e Elegantes
        for (i in 0 until 8) {
            val angle = Math.toRadians((i * 45.0)).toFloat()
            val startX = centerX + innerRay * cos(angle)
            val startY = centerY + innerRay * sin(angle)
            val endX = centerX + outerRay * cos(angle)
            val endY = centerY + outerRay * sin(angle)

            drawLine(
                color = rayColor,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

/**
 * Linha de atalhos rápidos essenciais (Wi-Fi 1-toque, Check-in/out, Contato Valéria).
 */
@Composable
private fun QuickInfoStrip(
    onCopyWifi: () -> Unit,
    onContactHost: () -> Unit,
    onFeedback: (String) -> Unit
) {
    val colors = SolariumTheme.colors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Cartão Wi-Fi Rápido
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = colors.creamCard,
            border = androidx.compose.foundation.BorderStroke(1.dp, colors.linenBorder),
            modifier = Modifier
                .weight(1f)
                .clickable(role = Role.Button, onClick = onCopyWifi)
                .testTag("quick_wifi_chip")
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(colors.sunOrangeContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Wifi,
                        contentDescription = "Wi-Fi",
                        tint = colors.sunOrange,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Wi-Fi: ${HouseRepository.WIFI_SSID}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        ),
                        color = colors.textPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Toque p/ senha",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp
                        ),
                        color = colors.warmTerracotta
                    )
                }
            }
        }

        // Cartão Contato Valéria
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = colors.creamCard,
            border = androidx.compose.foundation.BorderStroke(1.dp, colors.linenBorder),
            modifier = Modifier
                .weight(1f)
                .clickable(role = Role.Button, onClick = onContactHost)
                .testTag("quick_host_chip")
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(colors.softYellowContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "WhatsApp Valéria",
                        tint = colors.softYellow,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Anfitriã Valéria",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        ),
                        color = colors.textPrimary,
                        maxLines = 1
                    )
                    Text(
                        text = "WhatsApp direto",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp
                        ),
                        color = colors.sunOrange
                    )
                }
            }
        }
    }
}

/**
 * Item Minimalista de cada uma das 10 seções do manual.
 */
@Composable
private fun MinimalistManualItemRow(
    index: Int,
    section: ManualSection,
    onClick: () -> Unit
) {
    val colors = SolariumTheme.colors
    val icon = iconForManualSection(section.id, section.iconName)
    val formattedIndex = if (index < 10) "0$index" else "$index"

    // Variação harmônica e acolhedora dos tons da paleta Solarium para os 10 itens
    val (itemTint, containerTint) = when (index) {
        1 -> Pair(colors.sunOrange, colors.sunOrangeContainer)
        2 -> Pair(colors.warmTerracotta, colors.warmTerracottaContainer)
        3 -> Pair(colors.softYellow, colors.softYellowContainer)
        4 -> Pair(colors.sunOrange, colors.sunbeam)
        5 -> Pair(colors.warmTerracotta, colors.warmTerracottaContainer)
        6 -> Pair(colors.sunOrange, colors.sunOrangeContainer)
        7 -> Pair(Color(0xFFBA3D25), Color(0xFFFFDDD4))
        8 -> Pair(Color(0xFF43763D), Color(0xFFD6ECCF))
        9 -> Pair(Color(0xFF26798C), Color(0xFFD0F0F7))
        else -> Pair(colors.softYellow, colors.softYellowContainer)
    }

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.creamCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, colors.linenBorder),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .testTag("manual_item_card_${section.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Número do Item (01 a 10) em estilo minimalista
            Text(
                text = formattedIndex,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                ),
                color = colors.textSecondary.copy(alpha = 0.6f),
                modifier = Modifier.width(26.dp)
            )

            // Ícone do Item em Contêiner Circular Suave
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(containerTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = section.title,
                    tint = itemTint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Informações do Item
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = section.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    ),
                    color = colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = section.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.5.sp
                    ),
                    color = colors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Seta sutil minimalista
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Abrir guia",
                tint = colors.textSecondary.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

/**
 * Mapeamento dos 10 itens para ícones limpos e expressivos do Material.
 */
private fun iconForManualSection(id: Int, iconName: String): ImageVector {
    return when (id) {
        1 -> Icons.Default.Favorite
        2 -> Icons.Default.Place
        3 -> Icons.Default.Key
        4 -> Icons.Default.Tv
        5 -> Icons.Default.Bed
        6 -> Icons.Default.Restaurant
        7 -> Icons.Default.Shield
        8 -> Icons.Default.Delete
        9 -> Icons.Default.DirectionsCar
        10 -> Icons.Default.Explore
        else -> when (iconName) {
            "Favorite" -> Icons.Default.Favorite
            "Place" -> Icons.Default.Place
            "Key" -> Icons.Default.Key
            "Tv" -> Icons.Default.Tv
            "Bed" -> Icons.Default.Bed
            "Restaurant" -> Icons.Default.Restaurant
            "Shield" -> Icons.Default.Shield
            "Delete" -> Icons.Default.Delete
            "DirectionsCar" -> Icons.Default.DirectionsCar
            else -> Icons.Default.Explore
        }
    }
}

/**
 * Atalhos Rápidos no Rodapé da Página Inicial.
 */
@Composable
private fun QuickNavigationShortcuts(
    onNavigateToMap: () -> Unit,
    onNavigateToRecommendations: () -> Unit,
    onNavigateToChat: () -> Unit
) {
    val colors = SolariumTheme.colors

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = colors.warmLinen,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Explorar a Casa e Arredores",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = colors.textPrimary,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShortcutPill(
                title = "Planta Baixa",
                icon = Icons.Default.Map,
                tint = colors.sunOrange,
                onClick = onNavigateToMap,
                modifier = Modifier.weight(1f)
            )

            ShortcutPill(
                title = "Dicas Locais",
                icon = Icons.Default.Explore,
                tint = colors.softYellow,
                onClick = onNavigateToRecommendations,
                modifier = Modifier.weight(1f)
            )

            ShortcutPill(
                title = "Concierge IA",
                icon = Icons.AutoMirrored.Filled.Chat,
                tint = colors.warmTerracotta,
                onClick = onNavigateToChat,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ShortcutPill(
    title: String,
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = SolariumTheme.colors

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = colors.creamCard,
        border = androidx.compose.foundation.BorderStroke(1.dp, colors.linenBorder),
        modifier = modifier.clickable(role = Role.Button, onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = tint,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp
                ),
                color = colors.textPrimary,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

/**
 * Utilitários para área de transferência e WhatsApp.
 */
private fun copyToClipboard(context: Context, text: String, label: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
}

private fun openWhatsApp(context: Context, phoneNumber: String, message: String) {
    try {
        val cleanNumber = phoneNumber.replace(Regex("[^0-9]"), "")
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$cleanNumber&text=${Uri.encode(message)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (_: Exception) {
        // Fallback para discador se WhatsApp não estiver instalado
        try {
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            context.startActivity(dialIntent)
        } catch (_: Exception) {
            // Silencioso
        }
    }
}
