import java.io.File

/* Class for a device */
/* Has a name and an output list of devices */
class Device (val name : String, ) {
    override fun toString() : String = name
    val outputs = mutableListOf<Device>()
}

/* Memoised solution to find number of paths */
fun findNumPaths (device : Device, paths: MutableMap<Device, Long>, dest : String) : Long {
    // If the current device is output
    if (device.name == dest) return 0L
    // If the current device leads to output
    if (device.outputs.find {it.name == dest} != null) return 1L

    if (paths.containsKey(device)) return paths[device]!!

    // Go through each output of the current device, and add up paths
    var numPaths = 0L
    device.outputs.forEach {
        numPaths += findNumPaths(it, paths, dest)
    }

    paths[device] = numPaths
    return numPaths
}

fun main () {
    val devices = mutableListOf<Device>()
    //For part 1:
    //val you = Device("you")

    //For part 2:
    val svr = Device("svr")
    val dac = Device("dac")
    val fft = Device("fft")
    devices.add(svr)
    devices.add(dac)
    devices.add(fft)

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

    // Part 1
    //println(findNumPaths(you, mutableMapOf(), "out"))

    // Part 2
    val numPaths = (findNumPaths(svr, mutableMapOf(), "fft") *
                    findNumPaths(fft, mutableMapOf(), "dac") *
                    findNumPaths(dac, mutableMapOf(), "out"))
    println(numPaths)
}