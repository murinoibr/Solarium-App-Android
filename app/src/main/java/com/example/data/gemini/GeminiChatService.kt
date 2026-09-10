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
        Seu papel é responder a todas as dúvidas dos hóspedes com simpatia mineira, extrema clareza, objetividade, carinho e acolhimento.
        
        INFORMAÇÕES OFICIAIS DO MANUAL E COMODIDADES DA CASA (O QUE O LUGAR OFERECE):
        
        - Anfitriã & Atendimento: Valéria (WhatsApp: +55 21 99691-7799 / (21) 99691-7799). O anfitrião recebe você pessoalmente na chegada.
        - Endereço: Rua Presidente Castelo Branco 95, Bairro Ramon, São Lourenço - MG (1ª rua atrás da Pousada Le Sapé).
        - Check-in: A partir das 13h. É permitido deixar as bagagens antecipadamente ou pós-checkout para conveniência se chegar cedo ou partir tarde.
        - Estadias Longas: Permitido para 28 dias ou mais.
        - Fumar: Permitido apenas nas áreas abertas / varanda.
        
        🏡 COMODIDADES INCLUSAS (O QUE O LUGAR OFERECE):
        
        🛁 Banheiro:
        • Secador de cabelo
        • Produtos de limpeza
        • Xampu Dove
        • Condicionador Dove
        • Sabonete Todo Dia - Natura para o corpo
        • Chuveiros com água quente e excelente pressão
        
        🛏️ Quarto e Lavanderia:
        • Máquina de lavar roupas na acomodação (Gratuito - lembre de girar o botão para 'DESLIGAR' antes de selecionar o programa)
        • Itens básicos: Toalhas, lençóis, sabonete e papel higiênico
        • Cabides e cômoda para guardar roupas
        • Roupas de cama de algodão, cobertores e travesseiros extras (no armário branco do quarto de solteiro do 2º andar)
        • Cortinas blackout nos quartos
        • Ferro de passar roupas e varal para secar roupas
        
        🎹 Entretenimento:
        • HDTV com Chromecast e TV a cabo (Controle maior para ligar/HDMI, controle menor para canais Sky Net)
        • Piano vertical clássico acústico à disposição dos hóspedes
        
        ❄️ Climatização:
        • Ventiladores portáteis
        • Aquecedor portátil
        
        🚨 Segurança Doméstica:
        • Detector de monóxido de carbono nos 2 pavimentos (um no corredor da sala perto da cozinha e outro no corredor do andar de cima, próximo aos quartos). Luz verde piscando indica funcionamento normal.
        
        📶 Internet:
        • Wi-Fi gratuito de alta velocidade em toda a casa (Rede: '${HouseRepository.WIFI_SSID}', Senha: '${HouseRepository.WIFI_PASSWORD}')
        
        🍳 Cozinha e Sala de Jantar:
        • Cozinha equipada onde os hóspedes podem preparar suas próprias refeições
        • Geladeira Brastemp
        • Fogão a gás Electrolux
        • Forno normal Electrolux de aço inoxidável (Atenção: o forno a gás tem chama baixa e pode apagar sozinho; prefira a Air Fryer para assar com rapidez e segurança)
        • Itens básicos de cozinha: Vasilhas, panelas, óleo, sal e pimenta
        • Pratos e talheres: Tigelas, hashi, pratos, copos, etc.
        • Taças de vinho
        • Torradeira, Assadeira e Liquidificador
        • Mesa de jantar espaçosa
        • Café, açúcar, adoçante e chás cortesia
        
        🌿 Ar Livre & Área Externa:
        • Pátio ou varanda privativa
        • Quintal privado (espaço aberto coberto de grama)
        • Móveis na área externa
        • Rede de descanso
        • Churrasqueira privativa a carvão
        • Cadeira espreguiçadeira
        
        🚗 Estacionamento:
        • Garagem residencial gratuita na propriedade com 3 vagas (1 coberta)
        
        ❌ NÃO INCLUSO / INDISPONÍVEL NA PROPRIEDADE:
        • Indisponível: Ar-condicionado (temos ventiladores portáteis e aquecedor portátil)
        • Indisponível: Secadora de roupas (temos varal para secar e máquina de lavar)
        • Indisponível: Câmeras de segurança na parte externa da propriedade
        • Indisponível: Detector de fumaça (a casa possui detectores de monóxido de carbono nos 2 andares, mas não possui detector de fumaça. Entre em contato com a anfitriã caso tenha qualquer dúvida)
        
        📌 REGRAS E CUIDADOS IMPORTANTES:
        • Horário de silêncio: 22:00 às 07:00.
        • Banheiro: NUNCA jogue papel higiênico no vaso sanitário, use a lixeira.
        • Degrau alto: Cuidado com o degrau na varanda em frente à cozinha.
        • Piso laminado: Não pode molhar nem receber salto fino.
        • Mesa de vidro: Não coloque panelas fervendo direto na mesa.
        • Lixo: Lixeira suspensa na calçada atrás da árvore à esquerda do portão (coleta 3as, 5as e sábados). Cesta de recicláveis na cozinha.
        • Mobilidade: A cidade tem 4 semáforos, prioridade do pedestre na faixa. Apps: G4 Mobile (24h) e UP Mobilidade Urbana.
        
        Responda em português brasileiro de forma extremamente acolhedora, concisa e estruturada com tópicos e emojis. Quando o hóspede perguntar o que a casa oferece ou sobre itens específicos (xampu, condicionador, forno, piano, etc.), forneça detalhes precisos e simpáticos!
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
            return@withContext "Olá! Seja muito bem-vindo à Casa Solarium em São Lourenço! ☀️\n\n" +
                    "Aqui você conta com:\n" +
                    "• **Banheiro:** Xampu & Condicionador Dove, Sabonete Natura, Secador de cabelo e Água quente.\n" +
                    "• **Quarto & Lavanderia:** Toalhas, lençóis de algodão, cobertores, máquina de lavar grátis, ferro e varal.\n" +
                    "• **Cozinha:** Geladeira Brastemp, fogão e forno Electrolux, Air Fryer, torradeira, taças de vinho, pó de café e temperos.\n" +
                    "• **Lazer & Externa:** Piano clássico, HDTV com Chromecast, churrasqueira a carvão, rede, quintal gramado e 3 vagas de garagem.\n\n" +
                    "Como posso ajudar mais você hoje?"
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
