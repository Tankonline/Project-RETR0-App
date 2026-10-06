package com.retr0.projectretr0.installer

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
     * Fetches the manifest from the high-speed Worker KV endpoint.
     */
    suspend fun fetchManifest(): InstallManifest? = withContext(Dispatchers.IO) {
        try {
            // Point to the /manifest route on the worker for zero-latency handshake
            val url = URL("$baseUrl/manifest")
            val connection = url.openConnection() as HttpURLConnection
            connection.instanceFollowRedirects = false
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.setRequestProperty("Accept", "application/json")
            
            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Failed to fetch manifest from KV: ${connection.responseCode}")
                return@withContext null
            }

            val jsonString = connection.inputStream.bufferedReader().use { it.readText() }
            parseManifest(jsonString)
        } catch (_: Exception) {
            Log.e(TAG, "Manifest fetch error (KV)")
            null
        }
    }

    private fun parseManifest(json: String): InstallManifest {
        val root = JSONObject(json)
        val filesArray = root.getJSONArray("files")
        val files = mutableListOf<InstallFile>()

        for (i in 0 until filesArray.length()) {
            val f = filesArray.getJSONObject(i)
            
            val chunksList = mutableListOf<String>()
            if (f.has("chunks")) {
                val chunksArr = f.getJSONArray("chunks")
                for (j in 0 until chunksArr.length()) {
                    chunksList.add(chunksArr.getString(j))
                }
            }

            val parts = mutableListOf<ArchivePart>()
            if (f.has("parts")) {
                val partsArray = f.getJSONArray("parts")
                for (j in 0 until partsArray.length()) {
                    val part = partsArray.getJSONObject(j)
                    parts += ArchivePart(
                        name = part.getString("name"),
                        url = part.getString("url"),
                        size = part.getLong("size"),
                        sha256 = part.getString("sha256"),
                    )
                }
            }

            val installFile = InstallFile(
                id = f.getString("id"),
                name = f.getString("name"),
                url = f.getString("url"),
                size = f.getLong("size"),
                uncompressedSize = f.optLong("uncompressedSize", f.getLong("size")),
                sha256 = f.getString("sha256"),
                destination = f.getString("destination"),
                type = f.optString("type", "file"),
                required = f.optBoolean("required", true),
                chunks = chunksList,
                parts = parts,
            )
            if (parts.isNotEmpty()) SplitZipSupport.validate(installFile)
            if (installFile.type.equals("split_zip", ignoreCase = true)) {
                SplitZipSupport.validateChunkedArchive(installFile)
            }
            files += installFile
        }

        return InstallManifest(
            project = root.getString("project"),
            version = root.getString("version"),
            release = root.getString("release"),
            files = files,
        )
    }
}
