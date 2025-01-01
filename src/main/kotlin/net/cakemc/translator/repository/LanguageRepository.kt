package net.cakemc.translator.repository

import net.cakemc.translator.PlayerLanguageHook
import net.cakemc.translator.file.LanguageFile
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID

class LanguageRepository(
    val path: Path,
    val playerHook: PlayerLanguageHook
) {

    // repo structure:
    // language/
    // language/english/category_name.json
    // language/spanish/category_name.json

    private var repositories = HashMap<String, LanguageFile>(40)

    init {
        if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(path)
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

}