package oncall.model

class Calendar {
    val calendar = mutableListOf<Day>()

    fun addDay(input: Day){
        calendar.add(input)
    }

    fun getDayWithIndex(input: Int): Day {
        return calendar[input]
    }

    fun getAll(): MutableList<Day> {
        return this.calendar
    }
}