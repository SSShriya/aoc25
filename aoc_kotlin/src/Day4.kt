import java.io.File

/* Part 1 */
fun calculateAccesses (matrix : Array<CharArray>) : Pair<Int, Array<CharArray>> {
    val rows = matrix.size
    val cols = matrix[0].size
    var total = 0
    val result = Array(rows) {CharArray(cols)}

    val directions = listOf(
        -1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1,  1 to 0, 1 to 1
    )

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (matrix[i][j] != '@') continue
            var count = 0
            for ((dr, dc) in directions) {
                val r = i + dr
                val c = j + dc
                if (r in 0 until rows && c in 0 until cols && matrix[r][c] == '@') {
                    count++
                    if (count == 4) break
                }
            }
            if (count < 4) {
                result[i][j] = '.'
                total++
                continue
            }
            result[i][j] = matrix[i][j]
        }
    }
    //return total
    return Pair(total, result)
}

/* Part 2 */
fun calculateAllAccesses (matrix : Array<CharArray>) : Int {
    var total = 0
    var returnedTotal = 0
    var m = matrix
    do {
        val pair = calculateAccesses(m)
        returnedTotal = pair.first
        m = pair.second
        total += returnedTotal
    } while (returnedTotal > 0)

    return total
}

fun main () {
    val matrix = File ("data/day4.txt").readLines().map { line -> line.toCharArray() }.toTypedArray()
    //println (calculateAccesses(matrix))
    println (calculateAllAccesses(matrix))
}