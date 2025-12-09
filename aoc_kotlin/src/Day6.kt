import java.io.File

/* Part 1 */
/* Take the lists of numbers and the list of operations and perform the operations */
fun calculate (nums : List<List<Int>>, ops: List<Char>) : Long {
    var total = 0L
    val size = nums.size

    for (i in nums[0].indices) {
        val op = ops[i]

        if (op == '+') {
            for (j in 0 until size) {
                total += nums[j][i]
            }
        } else if (op == '*') {
            var mult = 1L
            for (j in 0 until size) {
                mult *= nums[j][i]
            }
            total += mult
        }
    }
    return total
}

/* Part 2 */
fun calculatePart2 (nums : List<List<Int>>, ops : List<Char>) : Long {
    var total = 0L
    val size = nums.size
    for (i in 0 until size) {
        val op = ops[i]
        if (op == '+') {
            total += nums[i].sum()
        } else if (op == '*') {
            var mult = 1L
            for (j in 0 until nums[i].size) {
                mult *= nums[i][j]
            }
            total += mult
        }
    }
    return total
}

fun main () {
    val inputs : List<String> = File("data/day6.txt").readLines()
    val numNums = inputs.size - 1

    // Get all the numbers for each line depending on the input size,
    // and store each line as a list of integers
    val allNums = mutableListOf<List<Int>>()
    for (i in 0 until numNums) {
        allNums.add(
            inputs[i]
                .split("\\s+".toRegex())
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
        )

    }

    // For part 2
    // Go through each column to extract an integer
    // Add list to allNums list if a space is found o
    // on all numbers
    val colNums = mutableListOf<List<Int>>()
    val curList = mutableListOf<Int>()
    val inputNums = inputs.take(numNums)
    val len = inputs.maxOf { it.length }
    for (i in 0 until len) {

        val digits = inputNums.map { it.getOrNull(i) }

        if (digits.all { it == ' '}) {
            if (curList.isNotEmpty()) {
                colNums.add(curList.toList())
                curList.clear()
            }
            continue
        }

        val combined = digits
            .filterNotNull()
            .filter { it != ' ' }
            .joinToString("")

        if (combined.isNotEmpty()) {
            curList.add(combined.toInt())
        }

    }

    if (curList.isNotEmpty()) {
        colNums.add(curList.toList())
    }

    // Extract the operations
    val ops: List<Char> = inputs.last()
        .split("\\s+".toRegex())
        .filter { it.isNotEmpty() }
        .map { it[0] }

    println (calculate (allNums, ops))
    println (calculatePart2 (colNums, ops))

}