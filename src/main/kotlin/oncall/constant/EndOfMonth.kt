package oncall.constant

enum class EndOfMonth(val month: Int, val endOfMonth: Int) {
    JANUARY(1,31),
    FEBRUARY(2, 28),
    MARCH(3, 31),
    APRIL(4, 30),
    MAY(5, 31),
    JUNE(6, 30),
    JULY(7, 31),
    AUGUST(8, 31),
    SEPTEMBER(9, 30),
    OCTOBER(10, 31),
    NOVEMBER(11, 30),
    DECEMBER(12, 31);

    companion object{
        fun getEndOfMonth(month: Int): Int{
            val end = EndOfMonth.entries.find{ endOfMonth: EndOfMonth -> endOfMonth.month == month}
            if (end != null) {
                return end.endOfMonth
            }
            return 0
        }

        fun checkMonth(month: Int) {
            require(EndOfMonth.entries.any { it.month == month }) {"${ERROR_LABEL} ${INVALID_MONTH_INPUT_LABEL}"}
        }

        private const val INVALID_MONTH_INPUT_LABEL = "비상 근무를 배정할 월은 1~12만 가능합니다."
        private const val ERROR_LABEL = "[ERROR]"
    }
}