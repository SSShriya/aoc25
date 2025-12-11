import java.io.File
import kotlin.math.abs
import kotlin.math.max

/* Part 1 */
/* Find all the areas and return the highest area */
fun findHighestArea (coords : List<Pair<Long, Long>>) : Long {
    var highestArea = 0L

    for ((x, y) in coords) {
        for ((x1, y1) in coords) {
            val area : Long = (abs(x - x1) + 1) * (abs (y - y1) + 1)
            highestArea = max (area, highestArea)
        }
    }

    return highestArea
}

fun findGreenArea (coords : List<Pair<Long, Long>>) : Long {
    var highestArea = 0L

    return highestArea
}


fun main () {
    val coords : List<Pair<Long, Long>> = File ("data/day9.txt")
        .readLines()
        .map {
            val list = it.split(',')
            Pair(list[0].toLong(), list[1].toLong())
        }

    println (findHighestArea (coords))
}