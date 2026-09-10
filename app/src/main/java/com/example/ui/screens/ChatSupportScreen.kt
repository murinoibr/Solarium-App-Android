package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.data.model.ChatMessage
import com.example.data.repository.HouseRepository
import java.net.URLEncoder

@Composable
fun ChatSupportScreen(
    messages: List<ChatMessage>,
    isLoading: Boolean,
    quickPrompts: List<String>,
    onSendMessage: (String) -> Unit,
    onClearChat: () -> Unit,
    onFeedback: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size, isLoading) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    fun copyMessage(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Mensagem do Suporte", text)
        clipboard.setPrimaryClip(clip)
        onFeedback("Mensagem copiada!")
    }

    fun openHostWhatsApp(customText: String? = null) {
        try {
            val phone = HouseRepository.HOST_PHONE
            val defaultMsg = "Olá, ${HouseRepository.HOST_NAME}! Sou hóspede da sua casa em São Lourenço (Rua Pres. Castelo Branco, 95)."
            val fullText = if (customText.isNullOrBlank()) {
                defaultMsg
            } else {
                "$defaultMsg\n\nGostaria de tirar uma dúvida:\n\"${customText.trim()}\""
            }
            val encodedMsg = URLEncoder.encode(fullText, "UTF-8")
            val whatsappUri = Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=$encodedMsg")
            val intent = Intent(Intent.ACTION_VIEW, whatsappUri)
            context.startActivity(intent)
        } catch (e: Exception) {
            onFeedback("Não foi possível abrir o WhatsApp. Tentando ligação...")
            try {
                val telIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${HouseRepository.HOST_PHONE}"))
                context.startActivity(telIntent)
            } catch (ex: Exception) {
                onFeedback("WhatsApp da proprietária: ${HouseRepository.HOST_PHONE_DISPLAY}")
            }
        }
    }

    fun openHostContact() {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${HouseRepository.HOST_PHONE}")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            onFeedback("Telefone da proprietária: ${HouseRepository.HOST_PHONE_DISPLAY}")
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .testTag("chat_support_screen")
    ) {
        // Chat Header with status & clear button
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.SupportAgent,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Concierge Digital da Casa",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFF2B8A3E),
                                modifier = Modifier.size(8.dp)
                            ) {}
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Online • Respostas Imediatas",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Botão rápido de WhatsApp no Topo
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF25D366).copy(alpha = 0.15f),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { openHostWhatsApp() }
                            .testTag("chat_header_whatsapp_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "WhatsApp da Valéria",
                                tint = Color(0xFF25D366),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "WhatsApp",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF1B7A3C)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    IconButton(
                        onClick = { openHostContact() },
                        modifier = Modifier.testTag("call_host_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Ligar para Valéria",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = onClearChat,
                        modifier = Modifier.testTag("clear_chat_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Limpar conversa",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Dedicated Section Card for Direct WhatsApp Query to the Host
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE7F7ED)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("whatsapp_direct_section_card")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF25D366),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Falar com a Anfitriã Valéria",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF0F5132)
                        )
                        Text(
                            text = "WhatsApp: ${HouseRepository.HOST_PHONE_DISPLAY}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = Color(0xFF155724)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF25D366),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                if (inputText.isNotBlank()) {
                                    openHostWhatsApp(inputText)
                                } else {
                                    openHostWhatsApp()
                                }
                            }
                            .testTag("open_host_whatsapp_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (inputText.isNotBlank()) "Enviar Pergunta" else "Abrir Zap",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                ),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatBubble(
                    message = msg,
                    onCopy = { copyMessage(msg.text) },
                    onSendToWhatsApp = { openHostWhatsApp(msg.text) }
                )
            }

            if (isLoading) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Consultando o manual da casa...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Quick Suggestions Horizontal Chips
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)) {
                Text(
                    text = "Dúvidas Frequentes Rápidas:",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(quickPrompts) { prompt ->
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    onSendMessage(prompt)
                                }
                        ) {
                            Text(
                                text = prompt,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Input Field and Send Button
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Digite sua pergunta sobre a casa...") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_field"),
                    shape = RoundedCornerShape(24.dp),
                    singleLine = false,
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.width(6.dp))

                // Direct Ask on Host WhatsApp button
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF25D366),
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickable {
                            openHostWhatsApp(inputText)
                            inputText = ""
                        }
                        .testTag("chat_input_whatsapp_button")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "Perguntar no WhatsApp da Proprietária",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(6.dp))

                Surface(
                    shape = CircleShape,
                    color = if (inputText.isNotBlank() && !isLoading) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickable(enabled = inputText.isNotBlank() && !isLoading) {
                            val textToSend = inputText
                            inputText = ""
                            onSendMessage(textToSend)
                        }
                        .testTag("chat_send_button")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Enviar mensagem",
                            tint = if (inputText.isNotBlank() && !isLoading) Color.White
                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(
    message: ChatMessage,
    onCopy: () -> Unit,
    onSendToWhatsApp: () -> Unit
) {
    val isUser = message.isFromUser
    val bubbleColor = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val textColor = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!isUser) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .size(30.dp)
                    .padding(top = 2.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = "Concierge",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Surface(
            shape = RoundedCornerShape(
                topStart = 18.dp,
                topEnd = 18.dp,
                bottomStart = if (isUser) 18.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 18.dp
            ),
            color = bubbleColor,
            shadowElevation = if (isUser) 1.dp else 2.dp,
            modifier = Modifier.widthIn(max = 295.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isUser) {
                        // Forward this guest question directly to WhatsApp
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White.copy(alpha = 0.25f),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(onClick = onSendToWhatsApp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Chat,
                                    contentDescription = "Enviar esta pergunta ao WhatsApp da dona",
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Mandar no Zap da Anfitriã",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = Color.White
                                )
                            }
                        }
                    } else {
                        // Assistente: opções de copiar e de abrir com a dona
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF25D366).copy(alpha = 0.15f),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(onClick = onSendToWhatsApp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Chat,
                                    contentDescription = "Tirar dúvida com a dona",
                                    tint = Color(0xFF1B7A3C),
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Confirmar com Valéria",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = Color(0xFF1B7A3C)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(onClick = onCopy)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.ContentCopy,
                                    contentDescription = "Copiar resposta",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Copiar",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                modifier = Modifier
                    .size(30.dp)
                    .padding(top = 2.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Você",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
