package net.cakemc.translator.file

import java.nio.file.Path

class LanguageFile (
    var filePath: Path,
    var name: String,
    var category: String,
){

    private var container: JsonContainer

    init {
        container = JsonContainer.loadConfig(filePath)
    }

    fun isKeyPresent(value: String): Boolean {
        return container.containsKey(value)
    }

    fun toWebString(): String {
        return this.container.convertToJsonString()
    }

    fun resolveForKey(value: String): String {
        if (container.containsKey(value.lowercase())) {
            return container.getString(value.lowercase())!!
        }

        val defaultText = "§c translation for $value not found in $name"
        container.append(value, defaultText)
        this.saveLanguageFile()
        return defaultText
    }

    fun saveLanguageFile() {
        container.saveAsConfig(filePath.toFile())
    }

    fun reload() {
        this.container = JsonContainer.loadConfig(filePath)
    }

}