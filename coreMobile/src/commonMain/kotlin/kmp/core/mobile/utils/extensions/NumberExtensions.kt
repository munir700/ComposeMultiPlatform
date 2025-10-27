package kmp.core.mobile.utils.extensions

fun Double.isPositive() = this > 0

fun Double.isNegative() = this < 0

fun Float.isPositive() = this > 0

fun Float.isNegative() = this < 0

fun Int.isPositive() = this > 0

fun Int.isNegative() = this < 0

fun Int.isOdd() = (this % 2) != 0

fun Int.isEven() = (this % 2) == 0

fun Int.half() = this.toFloat().div(2).toInt()

fun Int.divOrZero(number: Int): Int {
    return if (number == 0) 0
    else this / number
}

fun Double?.orZero() = this ?: 0.toDouble()

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0

private val suffixes = charArrayOf('k', 'm', 'b', 't')

fun Int.formatNumber(iteration: Int = 0): String {
    // Base case: If the number is less than 1000, return it as an integer string
    if (this < 1000) {
        return this.toString()
    }

    // Reduce the number for the next iteration
    val reducedNumber = ((this / 100).toLong() / 10.0)
    val isRound = (reducedNumber * 10) % 10 == 0.0 // True if no fractional part

    val formattedNumber = when {
        reducedNumber >= 100 -> reducedNumber.toInt().formatNumber( iteration + 1)
        isRound || reducedNumber > 9.99 -> reducedNumber.toInt().toString()
        else -> reducedNumber.toString()
    }

    // Add the corresponding suffix
    return formattedNumber + suffixes.getOrNull(iteration).orEmpty()
}
