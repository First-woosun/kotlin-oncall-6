package oncall.constant

enum class Holiday (val month: Int, val day: Int){
    NEW_YEAR_DAY(1, 1),
    KOREAN_INDEPENDENCE_DAY(3, 1),
    CHILDREN_DAY(5, 5),
    MEMORIAL_DAY(6, 6),
    LIBERATION_DAY(8, 15),
    NATIONAL_FOUNDATION_DAY(10, 3),
    HANGEUL_DAY(10, 9),
    CHRISTMAS_DAY(12, 25);

    companion object{
        fun isHoliday(month: Int, day:Int): Boolean{
            return Holiday.entries.any {holiday: Holiday -> month == holiday.month && day == holiday.day}
        }
    }
}