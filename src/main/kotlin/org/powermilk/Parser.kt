package org.powermilk

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.notExists

val gson: Gson = GsonBuilder()
    .registerTypeAdapter(
        LocalDateTime::class.java,
        JsonSerializer<LocalDateTime>() { obj, _, _ ->
            JsonPrimitive(obj.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        })
    .registerTypeAdapter(
        LocalDateTime::class.java,
        JsonDeserializer() { json, _, _ -> LocalDateTime.parse(json.asJsonPrimitive.asString, formatter) })
    .serializeNulls()
    .setPrettyPrinting()
    .create()

/**
 * [DateTimeFormatter] to parse a [LocalDateTime].
 */
private val formatter = DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("yyyy-MM-dd HH:mm:ss.SSS")
    .toFormatter()

/**
 * Function to load file to parse date and save updated content to new path. [this] is path to file with wrong date
 * format.
 *
 * @param pathToSave path to file with updated content.
 * @exception FileNotFoundException when [this] is wrong path (File doesn't exist).
 */
fun String.parse(pathToSave: String) {
    if (Path(this).notExists())
        throw FileNotFoundException("File to load doesn't exist")

    val frames = gson.fromJson<List<Frame>>(File(this).readText(), object : TypeToken<List<Frame>>() {}.type)
    File(pathToSave).writeText(gson.toJson(frames))
}

