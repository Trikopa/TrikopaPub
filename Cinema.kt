package cinema

fun main() {
    class Stats {
        var ticketsSold: Int = 0
        var currentProfit: Int = 0
        var maxProfit: Int = 0
        var percentage: Float = 0F
    }
    val stats = Stats()
    fun createCinema(rows:Int,seats:Int): StringBuilder {
        val cinemaMap = StringBuilder(" ")
        for (i in 1..seats) {
            cinemaMap.append(" $i")
        }
        cinemaMap.append("\n")
        for (i in 1..rows) {
            cinemaMap.append(i)
            for (j in 1..seats) {
                cinemaMap.append(" S")
            }
            cinemaMap.append("\n")
        }
        return cinemaMap
    }
    fun maxIncome(rows: Int, seats: Int): Int {
        return when {
            rows * seats < 60 -> rows * seats * 10
            else -> (rows / 2) * seats * 10 + (rows / 2) * seats * 8 + (rows % 2) * seats * 8
        }
    }
    fun statistics() {
        val formatPercentage = "%.2f".format(stats.percentage)
        println("\nNumber of purchased tickets: ${stats.ticketsSold}\nPercentage: $formatPercentage%")
        println("Current income: \$${stats.currentProfit}\nTotal income: \$${stats.maxProfit}")
    }
    fun printCinema(cinema: StringBuilder) {
        print("\nCinema:\n$cinema")
    }
    fun rightInput(rows: Int, seats: Int): Boolean {
        return when {
            rows > 9 || rows < 1 ->  false
            seats > 9 || seats < 1 ->  false
            else ->  true
        }
    }
    fun choice() {
        print("\n1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit\n")
    }
    fun ticketPrice(row: Int, rows: Int, seats: Int): Int {
        return when {
            rows * seats < 60 -> 10
            row <= rows / 2 -> 10
            else -> 8
        }
    }
    fun buyTicket(seats:Int,cinema:StringBuilder,rows:Int) {
        while (true) {
            println("\nEnter a row number:")
            val rowNumber = readln().toInt()
            println("Enter a seat number in that row:")
            val seatNumber = readln().toInt()
            val seatChange = (seats * 2 + 2) * rowNumber + seatNumber * 2
            when {
                !rightInput(rowNumber, seatNumber) -> println("Wrong input!")
                cinema[seatChange] == "B".single() -> println("\nThat ticket has already been purchased!")
                else -> {
                    cinema[seatChange] = "B".single()
                    println("\nTicket price: \$${ticketPrice(rowNumber, rows, seats)}")
                    stats.ticketsSold++
                    stats.currentProfit += ticketPrice(rowNumber, rows, seats)
                    stats.percentage = stats.ticketsSold.toFloat() / (rows * seats) * 100
                    break
                }
            }
        }
    }
    fun cycle(cinema: StringBuilder,seats:Int,rows:Int) {
        while (true) {
            choice()
            when (readln().toInt()) {
                1 -> printCinema(cinema)
                2 -> buyTicket(seats, cinema, rows)
                3 -> statistics()
                0 -> break
            }
        }
    }
    while (true) {
        println("Enter the number of rows:")
        val rows = readln().toInt()
        println("Enter the number of seats in each row:")
        val seats = readln().toInt()
        if (rightInput(rows, seats)) {
            val cinema = createCinema(rows, seats)
            stats.maxProfit = maxIncome(rows, seats)
            cycle(cinema, seats, rows)
            break
        } else {
            println("Wrong input!\n")
        }
    }
}