import java.io.File

/* Part 1 */
/* Take a number and list of ranges, and check if it is fresh */
fun checkFresh (num : Long, ranges : List<Pair<Long, Long>>) : Boolean {
    for ((start, end) in ranges) {
        // a number is fresh if it is in a range
        if (num in start..end) {
            return true
        }
    }
    return false
}

/* Take a list of ranges, and a list of numbers and count how many numbers are fresh */
fun findFreshIngredients(
    ranges: List<Pair<Long, Long>>,
    nums: List<Long>
) : Int {
    var count = 0
    // go through every number and check if it is fresh
    for (num in nums) {
        if (checkFresh (num, ranges)) count++
    }
    return count
}

/* Part 2 */
/* Count how many numbers there are in total in all of the merged ranges */
fun sumFreshIngredients (ranges : List<Pair<Long, Long>>) : Long {
    var sum = 0L
    ranges.forEach { sum += it.second - it.first + 1 }
    return sum
}

// Split file based on newline
fun <T> List<T>.splitOn (predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
    val firstPart = mutableListOf<T>()
    val secondPart = mutableListOf<T>()
    var found = false

    for (item in this) {
        if (!found && predicate(item)) {
            found = true
            continue
        }

        if (!found) {
            firstPart.add(item)
        } else {
            secondPart.add(item)
        }
    }

    return Pair(firstPart, secondPart)
}

fun mergeRanges(splitRanges: List<Pair<Long, Long>>): MutableList<Pair<Long, Long>> {
    val merged = mutableListOf<Pair<Long, Long>>()

    for ((start, end) in splitRanges) {
        if (merged.isEmpty()) {
            merged.add(start to end)
        } else {
            val (start2, end2) = merged.last()
            if (start <= end2) {
                merged[merged.lastIndex] = start2 to maxOf(end, end2)
            } else {
                merged.add(start to end)
            }
        }
    }
    return merged
}

fun main () {
    //split string by newline
    val input = File ("data/day5.txt").readLines()
    val (ranges, nums) = input.splitOn { it.isBlank() }

    //Take the ranges and turn them into a list of pairs
    var splitRanges = ranges.map {
        it.split('-').let { (a, b) -> a.toLong() to b.toLong() }
    }

    splitRanges = splitRanges.sortedBy { it.first }

    //combine ranges if they overlap
    val merged = mergeRanges(splitRanges)

    val intNums = nums.map { it.toLong() }

    println (findFreshIngredients (merged, intNums))
    println (sumFreshIngredients (merged))
}


