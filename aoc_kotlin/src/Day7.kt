import java.io.File

/* Part 1 - recursive DFS for finding total splits */
fun findTotalSplits (grid : Array<CharArray>,
                    startingPoint: Pair<Int, Int>,
                    tachyonSet : MutableSet<Pair<Int, Int>>,
                    visited : MutableSet<Pair<Int, Int>>)  {

    val (i, j) = startingPoint
    if (i !in grid.indices || j !in grid[0].indices) return
    if (!visited.add(startingPoint)) return

    var (curI, curJ) = Pair(i + 1, j)
    while (curI in grid.indices) {
        if (!visited.add (Pair(curI, curJ))) return

        if (grid[curI][curJ] == '^') {
            if (!tachyonSet.add(Pair(curI, curJ))) return
            val leftPoint = Pair(curI, curJ - 1)
            val rightPoint = Pair(curI, curJ + 1)

            if (curJ - 1 in grid[0].indices && !visited.contains(leftPoint)) {
                findTotalSplits(grid, leftPoint, tachyonSet, visited)
            }

            if (curJ + 1 in grid[0].indices && !visited.contains(rightPoint)) {
                findTotalSplits(grid, rightPoint, tachyonSet, visited)
            }
            return
        }

        curI++
    }
}

/* Part 2: count total possible paths
   Using memoized approach
   Takes in a map - from coordinate to Long
   which stores previously computed timelines */
fun findTotalTimelines (grid : Array<CharArray>,
                        startingPoint: Pair<Int, Int>,
                        paths: MutableMap<Pair<Int, Int>, Long>) : Long {

    val (i, j) = startingPoint

    // If we have gone off the grid then return 1 as there will
    // only be one path from the previous point
    if (i !in grid.indices || j !in grid[0].indices) {
        paths[(Pair(i, j))] = 1
        return 1
    }

    // if the path is already in the map then return it
    if (paths.containsKey(startingPoint)) {
        return paths[startingPoint]!!
    }

    // otherwise check if the coordinate is a splitter
    return if (grid[i][j] == '^') {
        var total = 0L
        // split off to left and right paths
        if (j - 1 in grid[0].indices) {
            total += findTotalTimelines(grid, Pair(i + 1, j - 1), paths)
        }
        // Branch right
        if (j + 1 in grid[0].indices) {
            total += findTotalTimelines(grid, Pair(i + 1, j + 1), paths)
        }
        // save the number of paths in the map
        paths[startingPoint] = total
        total
    } else {
        // otherwise, just go downwards, and save it into the map
        val res = findTotalTimelines(grid, Pair(i + 1, j), paths)
        paths[startingPoint] = res
        res
    }
}

fun main () {

    /* Read in the input as a 2D char array */
    val grid : Array<CharArray> = File ("data/day7.txt")
        .readLines()
        .map { it.toCharArray() }
        .toTypedArray()

    val startingPoint = grid[0].indexOf('S')
    val tachyonSet : MutableSet<Pair<Int, Int>> = mutableSetOf()
    val paths : MutableMap<Pair<Int, Int>, Long> = mutableMapOf()

    findTotalSplits(grid, Pair(0, startingPoint), tachyonSet, mutableSetOf())
    println(tachyonSet.size)
    println(findTotalTimelines(grid, Pair (0, startingPoint), paths))
}