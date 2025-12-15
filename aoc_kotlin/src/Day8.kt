import java.io.File
import kotlin.math.sqrt

/* Each node of the tree for union find */
class Node {
    var parent: Node = this
    var size: Long = 1

    // Find the parent of each tree
    fun find() : Node {
        if (parent != this) {
            parent = parent.find()
        }
        return parent
    }

    // Join two sets together - join smaller tree to larger
    fun union (other: Node) {
        val root1 = this.find()
        val root2 = other.find()
        if (root1 == root2) return

        if (root1.size < root2.size) {
            root1.parent = root2
            root2.size += root1.size
        } else {
            root2.parent = root1
            root1.size += root2.size
        }
    }
}

/* Find distances between every two points and return the list */
fun computeDistances (
    coords: List<Triple<Long, Long, Long>>,
    nodes: List<Node>
) : List<Triple<Node, Node, Double>> {

    val distances = mutableListOf<Triple<Node, Node, Double>>()

    for (i in coords.indices) {
        for (j in i + 1 until coords.size) {
            val (x1, y1, z1) = coords[i]
            val (x2, y2, z2) = coords[j]
            val distance = sqrt (((x1-x2) * (x1-x2)).toDouble()
                                 + ((y1-y2) * (y1-y2)).toDouble()
                                 + ((z1-z2) * (z1-z2)).toDouble())

            distances.add (Triple(nodes[i], nodes[j], distance))
        }
    }
    return distances.sortedBy { it.third }
}

// Join trees
fun createCircuits (
    distances: List<Triple<Node, Node, Double>>,
    connections: Int)
{
    var count = 0

    while (count < connections && count < distances.size) {
        val (start, end, _) = distances[count]
        start.union(end)
        count++
    }
}

// Multiply largest 3 circuits
fun multiplyLargestSizes (nodes: List<Node>) : Long {
    val roots = nodes.map { it.find() }.toSet()
    val sizes = roots.map { it.size }.sortedDescending()
    return (sizes.take(3).reduce {a, b -> a * b})
}

fun main () {
    // Read in coordinates into a list of triples
    val coords = File ("data/day8.txt")
        .readLines()
        .map {
            val coord = it.split(',')
            Triple(coord[0].toLong(), coord[1].toLong(), coord[2].toLong())
        }
    // Create list of nodes
    val nodes : List<Node> = coords.map { Node () }
    // Calculate distances between every node
    val distances = computeDistances(coords, nodes)
    // Make 1000 circuits
    createCircuits(distances, 1000)
    // Multiply largest 3 circuits
    println (multiplyLargestSizes(nodes))
}
