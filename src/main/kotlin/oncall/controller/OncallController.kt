package oncall.controller

import oncall.constant.DayOfWeek
import oncall.constant.EndOfMonth
import oncall.constant.Holiday
import oncall.model.Calendar
import oncall.model.Day
import oncall.model.Workers
import oncall.util.Extractor
import oncall.util.Validator
import oncall.view.InputView
import oncall.view.OutputView

private const val STARTOFMONTH = 1
private const val FIRSTDAYSETUPINDEX = 0

class OncallController(private val inputView: InputView,
                       private val outputView: OutputView,
                       private val validator: Validator,
                       private val extractor: Extractor)
{
    private val calendar = Calendar()
    private val weekDayWorkers = Workers()
    private val holidayWorkers = Workers()
    private var lastDay: Int = STARTOFMONTH

    fun run() {
        inputDateInfo()
    }

    fun inputDateInfo() {
        try{
            val inputDate = inputView.monthAndDayInput()
            checkComma(inputDate)

            val dateInfo = extractor.extractWithComma(inputDate)
            checkMonth(dateInfo[0])
            checkDay(dateInfo[1])

            createCalendar(dateInfo[0], dateInfo[1])
        } catch (e: IllegalArgumentException){
            println(e.message)
            inputDateInfo()
        }
    }

    fun createCalendar(month: String, startOfMonth: String){
        lastDay = EndOfMonth.getEndOfMonth(month.toInt())
        var day = startOfMonth

        for(date in STARTOFMONTH..lastDay){
            val isHoliday = checkHoliday(month.toInt(), date)
            val isWeekend = checkWeekend(day)

            calendar.addDay(Day(month.toInt(), date, day, isHoliday, isWeekend))
            day = DayOfWeek.nextDay(day)
        }
        inputWorker()
    }

    fun inputWorker() {
        val weekDayInput = inputView.weekDayWorkerInput()
        val holidayInput = inputView.holidayWorkerInput()
        try {
            checkWorker(weekDayInput)
            checkWorker(holidayInput)

            weekDayWorkers.setWorkers(weekDayInput.replace(" ", "").split(","))
            holidayWorkers.setWorkers(holidayInput.replace(" ", "").split(","))

            makeSchedule()
        } catch (e: IllegalArgumentException){
            println(e.message)
            inputWorker()
        }
    }

    fun makeSchedule() {
        setupFirstDay()
        createSchedule()
    }

    fun setupFirstDay() {
        if(calendar.getDayWithIndex(FIRSTDAYSETUPINDEX).isWeekend() || calendar.getDayWithIndex(0).isHoliday()){
            calendar.getDayWithIndex(FIRSTDAYSETUPINDEX).setNickName(holidayWorkers.getWorker())
            holidayWorkers.updateCurrent()
        } else {
            calendar.getDayWithIndex(FIRSTDAYSETUPINDEX).setNickName(weekDayWorkers.getWorker())
            weekDayWorkers.updateCurrent()
        }
    }

    fun createSchedule() {
        for(now in STARTOFMONTH..<lastDay){
            if (checkCalendarHoliday(now)) {
                if(checkContinuousWork(now, holidayWorkers)){
                    workArrangementWithSwitch(now, holidayWorkers)
                } else {
                    workArrangement(now, holidayWorkers)
                }
            } else {
                if(checkContinuousWork(now, weekDayWorkers)){
                    workArrangementWithSwitch(now, weekDayWorkers)
                } else {
                    workArrangement(now, weekDayWorkers)
                }
            }
        }
        printResult()
    }

    fun printResult(){
        val timeSheet = calendar.getAll()
        var description: String = ""
        for(time in timeSheet){
            if(time.isHoliday()){
                description = "${time.getMonth()}월 ${time.getDate()}일 ${time.getDay()}(휴일) ${time.getNickName()}\n"
            } else {
                description = "${time.getMonth()}월 ${time.getDate()}일 ${time.getDay()} ${time.getNickName()}\n"
            }
            outputView.printDayInfo(description)
        }
    }

    fun workArrangement(now: Int, workers: Workers) {
        calendar.getDayWithIndex(now).setNickName(workers.getWorker())
        workers.updateCurrent()
    }

    fun workArrangementWithSwitch(now: Int, workers: Workers){
        workers.switch()
        calendar.getDayWithIndex(now).setNickName(workers.getWorker())
        workers.updateCurrent()
    }

    fun checkContinuousWork(now: Int, workers: Workers): Boolean {
        return workers.getWorker() == calendar.getDayWithIndex(now-1).getNickName()
    }

    fun checkCalendarHoliday(now: Int): Boolean {
        return calendar.getDayWithIndex(now).isHoliday() || calendar.getDayWithIndex(now).isWeekend()
    }

    fun checkWorker(input: String) {
        checkComma(input)
        checkMinimumWorker(input)
        checkNameLength(input)
        checkDuplicate(input)
    }

    fun checkWeekend(day: String): Boolean {
        return DayOfWeek.isWeekend(day)
    }

    fun checkHoliday(month: Int, day: Int): Boolean {
        return Holiday.isHoliday(month, day)
    }

    fun checkMonth(month: String) {
        validator.monthInputValidator(month)
    }

    fun checkDuplicate (input: String) {
        validator.workerInputDuplicateValidator(input)
    }

    fun checkMinimumWorker (input: String) {
        validator.workerInputNumberValidator(input)
    }

    fun checkNameLength (input: String) {
        validator.workerInputNicknameSizeValidator(input)
    }

    fun checkDay(day: String){
        validator.dayInputValidator(day)
    }

    fun checkComma(input: String) {
        validator.commaValidation(input)
    }
}
