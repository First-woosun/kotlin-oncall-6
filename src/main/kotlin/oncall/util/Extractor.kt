package oncall.util

class Extractor {
    fun extractWithComma(input: String): List<String> {
        return input.replace(" ", "").split(",")
    }

}