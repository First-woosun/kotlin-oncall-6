package oncall

import oncall.controller.OncallController
import oncall.util.Extractor
import oncall.util.Validator
import oncall.view.InputView
import oncall.view.OutputView


fun main() {
    // TODO("프로그램 구현")
    val inputView = InputView()
    val outputView = OutputView()
    val validator = Validator()
    val extractor = Extractor()
    OncallController(inputView, outputView,validator, extractor).run()
}
