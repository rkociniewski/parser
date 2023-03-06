package org.powermilk

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.io.FileNotFoundException
import java.io.FileReader
import kotlin.io.path.Path
import kotlin.io.path.deleteIfExists
import kotlin.io.path.readText
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParserTest {

    private val pathToLoad = "src/test/resources/test.json"
    private val pathToSave = "src/test/resources/test2.json"

    @AfterEach
    fun `remove saved file`() {
        Path(pathToSave).deleteIfExists()
    }

    @Test
    fun `should parse correctly`() {
        val loadJson = gson.toJson(
            gson.fromJson<List<Frame>>(
                JsonReader(FileReader(pathToLoad)), object : TypeToken<List<Frame>>() {}.type
            )
        )

        pathToLoad.parse(pathToSave)
        assertEquals(loadJson, Path(pathToSave).readText())
    }

    @Test
    fun `should throw exception when file is not found`() = assertEquals(
        "File to load doesn't exist", assertThrows<FileNotFoundException> { pathToSave.parse(pathToSave) }.message
    )
}
