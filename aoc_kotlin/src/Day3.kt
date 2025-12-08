import java.io.File

/* Part 1 - go through each bank and find the
   highest 'joltage' for each line
*/
fun findLargestJoltage (banks : List<String>): Int {
    var totalJoltage = 0
    val strlen = banks.first().length
    for (bank in banks) {
        val bankDigits = bank.map { it.digitToInt() }
        var highestIndex = 0
        // Go through each character until the second last
        // to find the max character
        val firstPair = findHighestNum(bankDigits, 0, strlen - 1)
        val num1 = firstPair.first
        highestIndex = firstPair.second
        // Go through each character starting from the next index
        // and find the max character
        val num2 = findHighestNum(bankDigits, highestIndex + 1, strlen).first
        totalJoltage += num1 * 10 + num2
    }
    return totalJoltage
}

fun findLargestJoltage2(banks : List<String>): Long {
    var totalJoltage = 0L
    val strlen = banks.first().length
    for (bank in banks) {
        val bankDigits = bank.map { it.digitToInt() }
        var highestIndex = -1
        var pair = Pair(0, 0)
        var multiplier = 100000000000

        for (i in 11 downTo 0) {
            pair = findHighestNum(bankDigits, highestIndex + 1, strlen - i)
            val num = pair.first
            highestIndex = pair.second
            totalJoltage += num.toLong() * multiplier
            multiplier /= 10
        }
    }

    return totalJoltage
}

fun findHighestNum (bankDigits : List<Int>, start : Int, end: Int) : Pair<Int, Int> {
    var num = 0
    var index = 0
    for (i in start until end) {
        val curNum = bankDigits[i]
        if (curNum == 9) {
            num = curNum
            index = i
            break
        }
        if (curNum > num) {
            num = curNum
            index = i
        }
    }
    return Pair(num, index)
}

fun main() {
    val banks = File("data/day3.txt").readLines()
    println (findLargestJoltage (banks))
    println (findLargestJoltage2(banks))
}