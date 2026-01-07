package oncall.model

class Workers() {
    private val wokers = mutableListOf<String>()
    private val tempWorkers = mutableListOf<String>()
    private var current = 0
    private var lastIndex = 0

    fun setWorkers(input: List<String>) {
        this.wokers.addAll(input)
        this.tempWorkers.addAll(this.wokers)
        this.lastIndex = input.size - 1
    }

    fun getWorker(): String{
        val value = this.tempWorkers[current]
        return value
    }

    fun switch() {
        val now = this.tempWorkers[current]
        var next: String
        if(current == lastIndex){
            next = this.tempWorkers[0]
            this.tempWorkers[current] = next
            this.tempWorkers[0] = now
        } else {
            next = this.tempWorkers[current + 1]
            this.tempWorkers[current] = next
            this.tempWorkers[current + 1] = now
        }
    }

    fun updateCurrent(){
        if(this.current == this.lastIndex){
            this.current = 0
            resetTempWorkers()
        } else {
            this.current++
        }
    }

    fun resetTempWorkers(){
        this.tempWorkers.clear()
        this.tempWorkers.addAll(this.wokers)
    }
}