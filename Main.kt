

package converter

import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.floor

fun main() {
    class Magic {
        var exponent = 0
    }
    val king = Magic()
    val alphabet = ('a'..'z').toMutableList()
    val magicList = mutableListOf<String>()
    for (i in 0..9) {
        magicList.add(i.toString())
    }
    for (i in alphabet) {
        magicList.add(i.toString())
    }
    fun buggyTests(boolean: Boolean):Boolean {
        return  boolean
    }
    fun stringToList(baseNum:String): MutableList<Int> {
        val anyBaseList = mutableListOf<Char>()
        val numberBaseList = mutableListOf<Int>()
        for (i in baseNum) {
            anyBaseList.add(i)
        }
        for (i in anyBaseList.indices) {
            if(anyBaseList[i].isDigit()) {
                numberBaseList.add(anyBaseList[i].toString().toInt())
            }
            else {
                numberBaseList.add(anyBaseList[i].lowercaseChar().code - 87)
            }
        }
        return numberBaseList
    }
    fun pow(power: Int, number: BigDecimal, base: Int): BigDecimal {
        var result = BigDecimal(1).setScale(64)
        var absExponent = abs(power)
        val bigBase = BigDecimal(base)
        while (absExponent != 0) {
            result *= bigBase
            absExponent--
            }
        return when (power) {
            abs(power) -> {
                result * number
            }
            else -> {
                (BigDecimal(1).setScale(64) / result) * number
            }
        }
    }
    fun whatPowerToUse(str: String): Int {
        val beforeDot = str.substringBefore(".")
        return beforeDot.length - 1
    }
    fun convertToDecimal(numberBaseList: MutableList<Int>, baseNum:String, source: Int): BigDecimal {
        king.exponent = whatPowerToUse(baseNum)
        var decimalNumber = BigDecimal(0)
        for (i in numberBaseList) {
            decimalNumber += pow(king.exponent, BigDecimal(i),source)
            king.exponent--
        }
        return  decimalNumber
    }
    fun integerPart(str:String): String {
        return str.substringBefore(".")
    }
    fun fractalPart(str: String): String {
        return  str.substring(str.indexOf("."),str.indexOf(".") + 60)
    }
    fun convertIntegerPart(string: String, target: Int, magicList: MutableList<String>): StringBuilder {
        val newString = StringBuilder()
        var integer = string.toBigInteger()
        val targetBase = target.toBigInteger()
       do {
           val stringToAppend= magicList[(integer.mod(targetBase)).toInt()]
           newString.append(stringToAppend)
           integer /= targetBase
       } while (integer / targetBase != 0.toBigInteger())
       if(magicList[(integer.mod(targetBase)).toInt()] != "0") newString.append(magicList[(integer.mod(targetBase)).toInt()])
       return  newString.reverse()
    }
    fun convertFractalPart(string: String, target: Int, magicList: MutableList<String>, boolean: Boolean): StringBuilder {
        val newString = StringBuilder(".")
        var fraction = string.toDouble()
        val fractionAssembler = "0."
        do {
            fraction *= target
            if(fraction > 1) {
                newString.append(magicList[floor(fraction).toInt()])
                val getFraction = fractionAssembler.plus(fraction.toString().substringAfter("."))
                fraction = getFraction.toDouble()
            } else {
                newString.append("0")
            }
        } while (newString.length < 6)
        return when {
            boolean && newString.toString() != ".00000" -> newString
            newString.toString() ==  ".00000" && !boolean -> StringBuilder()
            else -> StringBuilder(".00000")
        }
    }
    while (true){
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ")
        val input = readln()
        if (input=="/exit") {
            break
        }
        val (source, target) = input.split(" ")
        while(true) {
            print("Enter number in base $source to convert to base $target (To go back type /back) ")
            val baseNum = readln()
            var boolean = false
            if(baseNum.contains(".")) {
                boolean = true
            }
            if (baseNum == "/back") {
                println()
                break
            }
            else if(baseNum == "0") {
                println("Conversion result: 0\n")
            }
            else {
                val withoutDot = baseNum.replace(".","")
                val numList = stringToList(withoutDot)
                val conversion = convertToDecimal(numList,baseNum,source.toInt())
                val convertedString = conversion.toString()
                val integerPart = convertIntegerPart(integerPart(convertedString),target.toInt(),magicList)
                val fractionPart = convertFractalPart(fractalPart(convertedString),target.toInt(),magicList,buggyTests(boolean))
                println("Conversion result: $integerPart$fractionPart\n")
            }
        }
    }
}