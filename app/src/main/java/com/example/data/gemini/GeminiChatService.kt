package com.example.data.gemini

import android.util.Log
import com.example.BuildConfig
import com.example.data.repository.HouseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiChatService {

    private const val TAG = "GeminiChatService"
    private const val MODEL_NAME = "gemini-3.1-flash-lite-preview"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"

    private val client = OkHttpClient.Builder()
        .connectTimeout(25, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(25, TimeUnit.SECONDS)
        .build()

    private val systemInstructions = """
        Você é a Concierge Digital Inteligente da casa da anfitriã Valéria em São Lourenço - Minas Gerais (alugada via Airbnb).
        Seu papel é responder a todas as dúvidas dos hóspedes com simpatia mineira, extrema clareza, objetividade e acolhimento.
        
        INFORMAÇÕES OFICIAIS DO MANUAL DA CASA:
        - Proprietária / Anfitriã: Valéria (WhatsApp: +55 21 99691-7799 / (21) 99691-7799). O hóspede pode mandar mensagem para ela diretamente pelo botão de WhatsApp no app.
        - Endereço: Rua Presidente Castelo Branco 95, Bairro Ramon, São Lourenço - MG (1ª rua atrás da Pousada Le Sapé).
        - Check-in: A partir das 13h.
        - Wi-Fi: Nome da rede '${HouseRepository.WIFI_SSID}', Senha '${HouseRepository.WIFI_PASSWORD}'.
        - Chaves: Chave da porta de entrada, porta dos fundos e portãozinho de pedestre. A chave preta é apenas reserva de emergência (caso falte luz ou o controle falhe).
        - Porta antiga da entrada: Para abrir, puxe a porta suavemente para fora com uma mão enquanto gira a chave com a outra.
        - Portão de carros: Botão superior direito do controle remoto. A casa tem 3 vagas (1 coberta).
        - TV e Streaming: Tem 2 controles. Ligue a TV com o controle MAIOR (Power e Home) e selecione HDMI2 (Sky Net) ou HDMI4/Chromecast (para espelhar celular). Se escolheu HDMI2, use o controle MENOR para canais (Menu -> Canais).
        - Conforto: Toalhas e roupas de cama extras estão no armário branco do quarto de solteiro no andar de cima. Secador de cabelo e itens de banho ficam no rack da TV, prateleira esquerda. Ventiladores embaixo da escada.
        - Máquina de lavar roupas: Gire o botão para 'DESLIGAR' antes de selecionar o programa desejado. O enchimento de água é lento (normal).
        - Higiene: NUNCA jogue papel higiênico no vaso sanitário, use a lixeira do banheiro.
        - Cozinha: Equipada com geladeira, fogão, Air Fryer, torradeira, liquidificador, coador de café e garrafa térmica, café, açúcar, adoçante e chás. ALERTA: O forno a gás tem chama baixa e pode apagar sozinho, dê preferência para a Air Fryer como assadeira!
        - Regras: Horário de silêncio das 22h às 7h. Fumar só na varanda/área externa. Proibido pets sem aviso prévio. Proibido festas e som alto.
        - Segurança: Dois detectores de monóxido de carbono (corredor térreo e corredor 2º andar em frente quarto maior). Luz verde piscando à noite indica funcionamento normal.
        - Cuidado especial: DEGURAU ALTO na varanda em frente à cozinha. Piso laminado não pode molhar nem receber salto fino. Mesa de vidro temperado não deve receber panelas fervendo direto.
        - Lixo: Lixeira suspensa na calçada atrás da árvore à esquerda do portão. Caminhão passa 3as, 5as e sábados. Cesta de recicláveis na cozinha ao lado da geladeira.
        - Mobilidade: A cidade tem só 4 semáforos, pedestre tem prioridade absoluta na faixa. Aplicativos: G4 Mobile (24h) e UP Mobilidade Urbana. Ônibus em frente Le Sapé de hora em hora.
        - Passeios: Parque das Águas (águas minerais e balneário), Quinta do Cedro (comida mineira no fogão a lenha), Balonismo ao amanhecer, Trem das Águas (Maria Fumaça), Mirante do Morro do Cruzeiro.
        
        Responda em português brasileiro de forma acolhedora, concisa e formatada com tópicos quando útil. Se perguntarem algo fora do contexto da casa ou da cidade, responda gentilmente focando no auxílio à estadia.
    """.trimIndent()

    suspend fun askAssistant(userQuery: String): String = withContext(Dispatchers.IO) {
        // Fast instant local match if available
        val instantAnswer = HouseRepository.getInstantAnswer(userQuery)
        
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        // If no API key or default placeholder, use instant answer or structured knowledge
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            if (instantAnswer != null) return@withContext instantAnswer
            return@withContext "Olá! Aqui é o suporte da Casa em São Lourenço. " +
                    "Estou aqui para ajudar com qualquer informação sobre check-in, Wi-Fi, TV, comodidades, lixo e passeios. " +
                    "Você pode perguntar por exemplo: 'Qual a senha do Wi-Fi?', 'Como ligar a TV?' ou 'Onde fica a lixeira?'."
        }

        try {
            val url = "$BASE_URL/$MODEL_NAME:generateContent?key=$apiKey"

            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", userQuery)
                            })
                        })
                    })
                })
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", systemInstructions)
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.3)
                    put("topP", 0.9)
                })
            }

            val request = Request.Builder()
                .url(url)
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.w(TAG, "Gemini API error code: ${response.code}")
                    return@withContext instantAnswer
                        ?: "Como anfitriã digital, informo que você encontra tudo no menu do app! Wi-Fi: ${HouseRepository.WIFI_SSID} (senha: ${HouseRepository.WIFI_PASSWORD}). Horário de silêncio: 22h às 7h."
                }

                val responseBodyStr = response.body?.string() ?: ""
                val responseJson = JSONObject(responseBodyStr)
                val candidates = responseJson.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val candidate = candidates.getJSONObject(0)
                    val content = candidate.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        val text = parts.getJSONObject(0).optString("text")
                        if (text.isNotBlank()) return@withContext text.trim()
                    }
                }
                instantAnswer ?: "Entendido! Lembre-se de consultar os 10 tópicos do guia para detalhes completos."
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error generating AI response", e)
            instantAnswer ?: "Olá! Não consegui conectar à rede agora, mas o Wi-Fi da casa é '${HouseRepository.WIFI_SSID}' (senha: '${HouseRepository.WIFI_PASSWORD}') e o check-in é a partir das 13h. Qualquer emergência contate a anfitriã Valéria!"
        }
    }
}
