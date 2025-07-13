var expenses = mutableListOf<MutableMap<String, String>>()

fun main() {

}

fun addExpense() {
    print("Enter the description of the expense: ")
    val description = readln()
    print("Enter the amount paid: ")
    val amount = readln()
    print("Enter the category of the expense: ")
    val category = readln()
    print("Enter the date paid: ")
    val date = readln()

    val expense = mutableMapOf(
        "description" to description,
        "amount" to amount,
        "category" to category,
        "date" to date
    )

    expenses.add(expense)
}

fun viewAllExpenses() {
    var count = 1
    for (expense in expenses) {
        println("$count ---> $expense")
        count++
    }
}

fun filterByCategory() {
    print("Enter the category you want: ")
    val categoryChoice = readln()
    for (expense in expenses) {
        if (expense.containsValue(categoryChoice)) {
            println("(1) ---> $expense")
        }
    }
}

fun filterByDate() {
    print("What date would you like to see? ")
    val dateChoice = readln()
    for (expense in expenses) {
        if (expense.containsValue(dateChoice)) {
            println("(1) --> $expense")
        }
    }
}

fun filterByAmountRange() {
    print("Enter lower limit in the range: ")
    val lowerLimitRange = readln().toDoubleOrNull()
    print("Enter upper limit in the range: ")
    val upperLimitRange = readln().toDoubleOrNull()

    for (expense in expenses) {
        if (lowerLimitRange == null || upperLimitRange == null) {
            println("Invalid Input")
            break
        } else if (expense["amount"]?.toDouble()!! > lowerLimitRange && expense["amount"]?.toDouble()!! < upperLimitRange) {
            println("(1) --> $expense")
        }
    }
}

fun overallTotal(): Double {
    var total = 0.0
    for (expense in expenses) {
        total += expense["amount"]?.toDouble()!!
    }

    return total
}