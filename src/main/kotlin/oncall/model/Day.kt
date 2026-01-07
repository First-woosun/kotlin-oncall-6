package oncall.model

class Day(
    private val month: Int,
    private val date: Int,
    private val day: String,
    private val isHoliday: Boolean,
    private val isWeekend: Boolean
) {
    private var nickName: String = ""

    fun setNickName(input: String) {
        this.nickName = input
    }

    fun getNickName(): String { return this.nickName }

    fun getMonth(): Int { return this.month }

    fun getDate(): Int { return this.date }

    fun getDay(): String { return this.day }

    fun isHoliday(): Boolean { return this.isHoliday }

    fun isWeekend(): Boolean { return this.isWeekend }
}