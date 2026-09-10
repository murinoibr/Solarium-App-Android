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
        
        - Endereço: Rua Presidente Castelo Branco 95, Bairro Ramon, São Lourenço - MG (1ª rua atrás da Pousada Le Sapé).
        
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
