package net.cakemc.translator.repository

import net.cakemc.translator.PlayerLanguageHook
import net.cakemc.translator.file.LanguageFile
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashMap

class LanguageRepository(
    val path: Path,
    val playerHook: PlayerLanguageHook
) {

    private var repositories = HashMap<String, LanguageFile>()

    init {
        if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(path)
        }

        Files.walk(path).filter { Files.isRegularFile(it) && it.toString().endsWith(".json") }.forEach { file ->

            val relativePath = path.relativize(file)
            val parts = relativePath.toString()
                .replace("\\", "#")
                .replace("/", "#")
                .split("#")
                .toTypedArray()

            if (parts.size == 2) {
                val language = parts[0]
                val category = parts[1].removeSuffix(".json")
                val languageFile = LanguageFile(file, language, category)
                repositories["$language/$category"] = languageFile
            }
        }

    }

    fun resolveLanguageFile(language: String, category: String): LanguageFile {
        if (repositories.containsKey("$language/$category")) {
            return repositories.get("$language/$category")!!
        }
        val languageFolder = Paths.get(path.toString(), language)
        val languageFile = Paths.get(languageFolder.toString(), "$category.json")

        if (!Files.exists(languageFolder, LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(languageFolder)
        }

        val fileObject = LanguageFile(languageFile, language, category)
        repositories.put("$language/$category", fileObject)
        return fileObject
    }


    fun resolveLanguage(uuid: UUID, category: String): LanguageFile {
        var selectedLanguage = playerHook.resolveLanguage(uuid)
        if (selectedLanguage == null) {
            selectedLanguage = "english"
        }
        return this.resolveLanguageFile(selectedLanguage, category)
    }

    fun resolveLanguageFile(): List<LanguageFile> {
        return repositories.values.toList()
    }

}