import java.io.File

object CoderHelper {
    fun byteToChar(msg: String): String {
        var symbols = ""
        msg.split(" ").forEach {
            symbols += Char(Integer.parseInt(it, 2))
        }
        return symbols
    }

    // ab$ +
//b$a +
//$ab +
    fun bwtEncode(msg: String): String {
        var msg = msg
        msg += "$"
        var table = arrayListOf(msg)
        while (msg.first() != '$') {
            val newMsg = msg.substring(1) + msg.first()
            msg = newMsg
            table.add(newMsg)
        }
        table.sort()
        var bwt = ""
        table.forEach {
            bwt += it.last()
        }
        return bwt

    }

    fun lzwCompress(msg: String): List<String> {
        var size = 256
        var w = ""
        var result = arrayListOf<String>()
        var dictionary = HashMap<String, String>()
        for( i in 0.. 256 ) dictionary["${Char(i)}"] = "${Char(i)}"
        msg.forEach {  ch ->
            var wc = w + ch
            if(wc in dictionary) w = wc
            else {
                result.add(dictionary[w] ?: "")
                dictionary[wc] = size.toString()
                size += 1
                w = "$ch"
            }
        }
        if(w.isNotEmpty()) {
            result.add(dictionary[w] ?: "")
        }
        return result
    }

    fun processNum(minNum: Float, maxNum: Float): HashMap<String, Pair<Float, Float>> {
        var minNumber = minNum
        val probs = HashMap<String, Pair<Float, Float>>()
        var numDiff = maxNum - minNumber
        pTable.keys.forEach { key ->
            val term = pTable[key] ?: 0.0f
            var cumProb = term * numDiff + minNumber
            probs[key] = Pair(minNumber, cumProb)
            minNumber = cumProb
        }
        return probs
    }

    fun encodedValue(list: List<HashMap<String, Pair<Float, Float>>>): String {
        var lastStage = list.reversed().first()
        var lastStageValues = arrayListOf<Float>()
        lastStage.forEach { element ->
            lastStageValues.add(element.value.first)
            lastStageValues.add(element.value.second)
        }
        val min = lastStageValues.min()
        val max = lastStageValues.max()
        return ((min + max) / 2f).toString()
    }

    fun readFile(filename: String) = File(filename).readLines()
}