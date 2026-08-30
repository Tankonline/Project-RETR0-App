package com.example.projectretr0installer.installer

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Manages fetching and parsing the installation manifest.
 */
class ManifestManager(private val baseUrl: String) {

    private companion object {
        private const val TAG = "RETR0_MANIFEST"
    }

    /**
     * Fetches the manifest from the bucket.
     */
    suspend fun fetchManifest(): InstallManifest? = withContext(Dispatchers.IO) {
        try {
            val url = URL("$baseUrl/release.json")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            
            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Failed to fetch manifest: ${connection.responseCode}")
                return@withContext null
            }

            val jsonString = connection.inputStream.bufferedReader().use { it.readText() }
            parseManifest(jsonString)
        } catch (_: Exception) {
            Log.e(TAG, "Manifest fetch error")
            null
        }
    }

    private fun parseManifest(json: String): InstallManifest {
        val root = JSONObject(json)
        val filesArray = root.getJSONArray("files")
        val files = mutableListOf<InstallFile>()

        for (i in 0 until filesArray.length()) {
            val f = filesArray.getJSONObject(i)
            files.add(
                InstallFile(
                    id = f.getString("id"),
                    name = f.getString("name"),
                    url = f.getString("url"),
                    size = f.getLong("size"),
                    uncompressedSize = f.optLong("uncompressedSize", f.getLong("size")),
                    sha256 = f.getString("sha256"),
                    destination = f.getString("destination"),
                    type = f.optString("type", "file"),
                    required = f.optBoolean("required", true),
                )
            )
        }

        return InstallManifest(
            project = root.getString("project"),
            version = root.getString("version"),
            release = root.getString("release"),
            files = files,
        )
    }
}
