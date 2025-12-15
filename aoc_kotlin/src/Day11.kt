import java.io.File

/* Class for a device */
/* Has a name and an output list of devices */
class Device (val name : String, ) {
    override fun toString() : String = name
    val outputs = mutableListOf<Device>()
}

/* Part 1: Memoised solution to find paths */
fun findNumPaths (device : Device, paths: MutableMap<Device, Int>) : Int {
    // If the current device is output
    if (device.name == "out") return 0
    // If the current device leads to output
    if (device.outputs.find {it.name == "out"} != null) return 1

    if (paths.containsKey(device)) return paths[device]!!

    // Go through each output of the current device, and add up paths
    var numPaths = 0
    device.outputs.forEach {
        numPaths += findNumPaths(it, paths)
    }

    paths[device] = numPaths
    return numPaths
}

fun main () {
    val devices = mutableListOf<Device>()
    //For part 1:
    //val start = Device("you")

    //For part 2:
    val start = Device("svr")
    devices.add(start)

    File("data/day11.txt")
        .readLines()
        .forEach {
            // Split by colon
            val splits = it.split(": ")

            // Find the device in devices list
            var curDevice = devices.find { it.name == splits[0] }
            // If the device isn't in the devices list then
            // make a new device and add it to the list
            if (curDevice == null) {
                curDevice = Device(splits[0])
                devices.add(curDevice)
            }

            // Add all the outputs to the device
            val outputs = splits[1].split(" ")
            outputs.forEach { o ->
                var curOutput = devices.find { d -> d.name == o }
                if (curOutput == null) {
                    curOutput = Device (o)
                    devices.add(curOutput)
                }
                curDevice.outputs.add (curOutput)
            }
        }

    println(findNumPaths(start, mutableMapOf()))
}