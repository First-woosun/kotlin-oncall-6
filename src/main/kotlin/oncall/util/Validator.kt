package oncall.util
import oncall.constant.DayOfWeek
import oncall.constant.EndOfMonth

class Validator {
    fun monthInputValidator(input: String){
        EndOfMonth.checkMonth(input.toInt())
    }

    fun dayInputValidator(input: String){
        DayOfWeek.checkDay(input)
    }

    fun commaValidation (input: String) {
        require(input.indexOf(",") != -1) { "${ERROR_LABEL} ${CHECK_COMMA_LABEL}" }
    }

    fun workerInputNumberValidator(input : String){
        val inputValue = input.replace(" ", "").split(",")

        require(inputValue.size >= MIN_NUMBER_OF_WORKER) { "${ERROR_LABEL} ${MIN_WORKER_LABEL}"}
        require(inputValue.size <= MAX_NUMBER_OF_WORKER) {"${ERROR_LABEL} ${MAX_WORKER_LABEL}"}
    }

    fun workerInputDuplicateValidator(input : String){
        val inputValue = input.replace(" ", "").split(",")

        require(inputValue.size == inputValue.toSet().size) {"${ERROR_LABEL} ${DUPLICATE_LABEL}"}
    }

    fun workerInputNicknameSizeValidator(input : String){
        val inputValue = input.trim().split(",")

        for(value in inputValue){
            require(value.length <= 5) {"${ERROR_LABEL} ${TOO_LONG_NICKNAME_LABEL}"}
        }
    }

    companion object{
        private const val ERROR_LABEL = "[ERROR]"
        private const val MIN_WORKER_LABEL = "비상 근무 인원은 최소 5명입니다."
        private const val MAX_WORKER_LABEL = "비상 근무 인원은 최대 35명 입니다."
        private const val DUPLICATE_LABEL = "비상 근무 배정은 인당 1회로 제한합니다."
        private const val TOO_LONG_NICKNAME_LABEL = "닉네임은 최대 5자까지 입력 가능합니다."
        private const val CHECK_COMMA_LABEL = "콤마(,)로 구분되어야 합니다."
        private const val MIN_NUMBER_OF_WORKER = 5
        private const val MAX_NUMBER_OF_WORKER = 35
    }
}