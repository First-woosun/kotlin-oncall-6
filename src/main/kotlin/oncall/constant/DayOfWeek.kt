package oncall.constant

enum class DayOfWeek(val day: String) {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    companion object{
        fun isWeekend(day: String): Boolean {
            val today = entries.find {dayOfWeek: DayOfWeek -> day == dayOfWeek.day}
            return today in listOf(SATURDAY, SUNDAY)
        }

        fun nextDay(day: String): String {
            val today = entries.find { dayOfWeek: DayOfWeek -> day == dayOfWeek.day }
            val todayIndex = today?.ordinal
            todayIndex?.let {
                if(it + 1 <= 6){
                    val nextDayIndex = it + 1
                    return entries[nextDayIndex].day
                }
            }
            return entries[0].day
        }

        fun checkDay(day: String){
            require(entries.any { it.day == day }) {"$ERROR_LABEL $INVALID_DAY_LABEL"}
        }

        private const val INVALID_DAY_LABEL = "시작 요일은 월, 화, 수, 목, 금, 토, 일 중에 입력할 수 있습니다."
        private const val ERROR_LABEL = "[ERROR]"
    }
}