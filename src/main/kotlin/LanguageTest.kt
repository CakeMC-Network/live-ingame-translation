import net.cakemc.translator.DefaultTranslationReplacer
import net.cakemc.translator.PlayerLanguageHook
import net.cakemc.translator.repository.LanguageRepository
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

object LanguageTest {

    @JvmStatic
    fun main(args: Array<String>) {

        val replacer = DefaultTranslationReplacer(LanguageRepository(Path.of("./test"), object : PlayerLanguageHook {
            override fun resolveLanguage(player: UUID): String? {
                return "english"
            }
        }))

        val message = replacer.findTranslation(UUID.randomUUID(), "welcome.message [player_name, 123]")
        println(message)
    }

}