package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.HouseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HouseHeader(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onFeedback: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var wifiCopied by remember { mutableStateOf(false) }
    var addressCopied by remember { mutableStateOf(false) }

    fun copyToClipboard(label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        onFeedback("$label copiado com sucesso!")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Welcome Hero Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("hero_banner_card"),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Airbnb",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Preferido dos Hóspedes • Airbnb",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Nota",
                                    tint = Color(0xFFE67700),
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "5.0 ★ São Lourenço",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Text(
                        text = "Solarium",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-0.5).sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Casa ampla com vista panorâmica • Anfitriã: ${HouseRepository.HOST_NAME}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "4 quartos • 2 andares • 8 hóspedes • Bairro Ramon",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Quick Chips Row (Wi-Fi and Address)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Wi-Fi Quick Copy
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (wifiCopied) MaterialTheme.colorScheme.secondaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    copyToClipboard("Senha do Wi-Fi", HouseRepository.WIFI_PASSWORD)
                                    wifiCopied = true
                                    coroutineScope.launch {
                                        delay(2500)
                                        wifiCopied = false
                                    }
                                }
                                .testTag("wifi_copy_chip")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (wifiCopied) Icons.Default.Check else Icons.Default.Wifi,
                                    contentDescription = "Wi-Fi",
                                    tint = if (wifiCopied) MaterialTheme.colorScheme.secondary
                                    else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = if (wifiCopied) "Senha Copiada!" else "Wi-Fi: ${HouseRepository.WIFI_SSID}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = if (wifiCopied) "Pronto para colar" else "Toque para copiar",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        // Address Quick Copy
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (addressCopied) MaterialTheme.colorScheme.secondaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    copyToClipboard("Endereço", HouseRepository.HOUSE_ADDRESS)
                                    addressCopied = true
                                    coroutineScope.launch {
                                        delay(2500)
                                        addressCopied = false
                                    }
                                }
                                .testTag("address_copy_chip")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (addressCopied) Icons.Default.Check else Icons.Default.Place,
                                    contentDescription = "Endereço",
                                    tint = if (addressCopied) MaterialTheme.colorScheme.secondary
                                    else MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = if (addressCopied) "Endereço Copiado!" else "Rua Pres. C. Branco",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Atrás do Le Sapé",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search in Manual Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("manual_search_field"),
            placeholder = {
                Text(
                    text = "Buscar por chaves, TV, fogão, lixo, regras...",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            )
        )
    }
}
