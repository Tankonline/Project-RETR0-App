package com.example.projectretr0installer.installer

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/**
 * Advanced Music Metadata Engine.
 * Provides searching and matching for professional music profiles.
 */
object MetadataFetcher {
    private const val TAG = "RETR0_METADATA"
    private const val BASE_URL = "https://itunes.apple.com/search"

    /**
     * Searches for multiple potential matches on iTunes.
     */
    suspend fun searchMetadata(query: String): List<MusicMetadata> = withContext(Dispatchers.IO) {
        val resultsList = mutableListOf<MusicMetadata>()
        try {
            val cleanQuery = sanitizeQuery(query)
            if (cleanQuery.isBlank()) return@withContext emptyList()

            val url = URL("$BASE_URL?term=${URLEncoder.encode(cleanQuery, "UTF-8")}&media=music&limit=10")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 5000
            
            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val results = JSONObject(response).getJSONArray("results")
                
                for (i in 0 until results.length()) {
                    val res = results.getJSONObject(i)
                    resultsList.add(
                        MusicMetadata(
                            trackName = res.optString("trackName", "Unknown"),
                            artistName = res.optString("artistName", "Unknown"),
                            artworkUrl = res.optString("artworkUrl100", "").replace("100x100bb", "600x600bb"),
                            previewUrl = if (res.has("previewUrl")) res.getString("previewUrl") else null,
                            collectionName = if (res.has("collectionName")) res.getString("collectionName") else null
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Search Error", e)
        }
        resultsList
    }

    /**
     * Legacy function: Auto-picks the best match.
     */
    suspend fun fetchMetadata(query: String): Map<String, String>? = withContext(Dispatchers.IO) {
        val matches = searchMetadata(query)
        if (matches.isEmpty()) return@withContext null
        
        // Find best score (simple keyword matching for auto-pick)
        val cleanQuery = sanitizeQuery(query).lowercase()
        val queryWords = cleanQuery.split(" ").filter { it.length > 2 }
        
        val best = matches.maxByOrNull { m ->
            val title = m.trackName.lowercase()
            val artist = m.artistName.lowercase()
            queryWords.count { title.contains(it) || artist.contains(it) }
        }

        best?.let {
            mapOf(
                "trackName" to it.trackName,
                "artistName" to it.artistName,
                "artworkUrl" to (it.artworkUrl ?: "")
            )
        }
    }

    private fun sanitizeQuery(query: String): String {
        return query.substringBeforeLast(".")
            .replace(Regex("(?i)\\(.*?\\)"), " ")
            .replace(Regex("(?i)\\[.*?]"), " ")
            .replace(Regex("(?i)\\b(slowed|reverb|8d|official|video|lyric|hd|hq|audio|clip|remix|edit|loop|cover|bass boosted|high quality)\\b"), " ")
            .replace("~", " ")
            .replace(Regex("[^a-zA-Z0-9. ]"), " ")
            .trim()
            .replace(Regex("\\s+"), " ")
    }
}
