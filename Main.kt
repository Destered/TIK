
import CoderHelper.bwtEncode
import CoderHelper.byteToChar
import CoderHelper.encodedValue
import CoderHelper.lzwCompress
import CoderHelper.processNum
import CoderHelper.readFile

var pTable = HashMap<String, Float>()

fun main(args: Array<String>) {
    initProbabilityTable()
    while (true) {
        println("Введите 1 для кодирования Арифметическим способом")
        println("Введите 2 для декодирования методом LZW + БУ")
        when (readlnOrNull()) {
            "1" -> {
                startCoder()
            }

            "2" -> {
                startDecoder()
            }

            else -> {
                println("Одна ошибка и ты ошибся")
            }
        }
    }
}

fun startCoder() {
    print("Введите сообщение для кодирования: ")
    val inputMsg = readlnOrNull() ?: ""
    println(inputMsg)
    val encodedMsg = encodeMsg(inputMsg)
    println("Закодировано: $encodedMsg")
    println("Длина сообщения: ${inputMsg.length}")

}

private fun encodeMsg(msg: String): String {
    val encoder = arrayListOf<HashMap<String, Pair<Float, Float>>>()
    var minNum = 0.0f
    var maxNum = 1.0f
    for (element in msg) {
        var stageP = processNum(minNum, maxNum)
        var messageChar = element.toString()
        minNum = stageP[messageChar]?.first ?: 0.0f
        maxNum = stageP[messageChar]?.second ?: 0.0f

        encoder.add(stageP)
    }

    var stageP = processNum(minNum, maxNum)
    encoder.add(stageP)

    return encodedValue(encoder)
}

fun startDecoder() {
    var msg = readFile("C:\\Users\\Dester\\IdeaProjects\\TikKotlin\\src\\main\\kotlin\\inputFileDecode.txt").first()
    println("Декодируем: $msg")
    msg = byteToChar(msg)
    println("Декодируем в символы: $msg")
    var bwtCode = bwtEncode(msg)
    println("Кодирум в BWT: $bwtCode")
    var lzw = lzwCompress(bwtCode)
    println("LZW: $lzw")
}

private fun initProbabilityTable() {
    var lines = readFile("C:\\Users\\Dester\\IdeaProjects\\TikKotlin\\src\\main\\kotlin\\inputFileAlphabet.txt")
    var alphabet = lines.first().split(" ")
    var pElement = lines[1].split(" ")
    for (i in alphabet.indices) {
        pTable[alphabet[i]] = pElement[i].toFloat()
    }
    println("Таблица: $pTable\n\n")
}

