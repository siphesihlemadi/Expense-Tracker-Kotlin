import java.time.LocalDate
import java.time.format.DateTimeParseException

var expenses = mutableListOf<MutableMap<String, String>>()

fun main() {
    var userChoice = 0
    do {
        println("********************************")
        println("Expense Tracker\t\t${overallTotal()}")
        println("********************************")
        println(
            "1. Add Expense\n" +
                    "2. View All Expenses\n" +
                    "3. Filter By Category\n" +
                    "4. Filter By Amount Range\n" +
                    "5. Filter By Date\n" +
                    "6. Total Expense By Category\n" +
                    "7. Delete Expense\n" +
                    "99. Exit"
        )
        try {
            userChoice = readln().toIntOrNull() ?: throw NumberFormatException("Please enter the valid index.")

            when (userChoice) {
                1 -> addExpense()
                2 -> viewAllExpenses()
                3 -> filterByCategory()
                4 -> filterByAmountRange()
                5 -> filterByDate()
                6 -> totalExpenseByCategory()
                7 -> deleteExpense()
                99 -> userChoice = 99
                else -> throw NumberFormatException()
            }
        } catch (e: NumberFormatException) {
            println(e.message)
        } catch (e: NullPointerException) {
            println(e.message)
        } catch (e: DateTimeParseException) {
            println("Date must be YY-MM-DD. e.g. 2000-01-01")
        }
    } while (userChoice != 99)
}

fun addExpense() {
    print("Enter the description of the expense: ")
    val description =
        readln().takeIf { it.isNotBlank() } ?: throw NullPointerException("Description cannot be empty")
    print("Enter the amount paid: ")
    val amount = readln().toDoubleOrNull() ?: throw NumberFormatException("Please enter numeric value for amount.")
    print("Enter the category of the expense: ")
    val category = readln().takeIf { it.isNotBlank() } ?: throw NullPointerException("Category cannot be empty.")
    print("Enter the date paid: ")
    val date = readln().takeIf { it.isNotBlank() } ?: throw NullPointerException("Date cannot be empty.")
    val parsedDate = LocalDate.parse(date)

    val expense = mutableMapOf(
        "description" to description,
        "amount" to amount.toString(),
        "category" to category,
        "date" to parsedDate.toString()
    )

    expenses.add(expense)

}

fun viewAllExpenses() {
    if(printIfEmpty())
        return
    var count = 1
    for (expense in expenses) {
        println("$count ---> $expense")
        count++
    }
}

fun filterByCategory() {
    if(printIfEmpty()) return
    var found = false
    print("Enter the category you want: ")
    val categoryChoice =
        readln().takeIf { it.isNotBlank() } ?: throw NullPointerException("PLease enter a description.")

    for (expense in expenses) {
        if (expense.containsValue(categoryChoice)) {
            println("(1) ---> $expense")
            found = true
        }
    }
    if (!found)
        println("Category not found")
}

fun filterByDate() {
    if(printIfEmpty()) return
    var found = false
    val dateChoice: String

    print("What date would you like to see? ")
    val date = readln().takeIf { it.isNotBlank() } ?: throw NullPointerException("Date cannot be empty.")
    dateChoice = LocalDate.parse(date).toString()

    for (expense in expenses) {
        if (expense.containsValue(dateChoice)) {
            println("(1) --> $expense")
            found = true
        }
    }
    if (!found)
        println("Expense not found with that date.")

}

fun filterByAmountRange() {
    if(printIfEmpty()) return
    print("Enter lower limit in the range: ")
    val lowerLimitRange =
        readln().toDoubleOrNull() ?: throw NumberFormatException("Lower Limit value should be entered and be numeric.")
    print("Enter upper limit in the range: ")
    val upperLimitRange =
        readln().toDoubleOrNull() ?: throw NumberFormatException("Upper Limit value should be entered and be numeric.")

    for (expense in expenses) {
        if (lowerLimitRange >= 0 && upperLimitRange >= 0) {
            if (expense["amount"]?.toDouble()!! in lowerLimitRange..upperLimitRange) {
                println("(1) --> $expense")
            }
        } else {
            println("Values cannot be below zero.")
            break
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

fun totalExpenseByCategory() {
    if(printIfEmpty()) return
    var total = 0.0
    var isFound = false
    print("Enter the category you want: ")
    val categoryTotal =
        readln().takeIf { it.isNotBlank() } ?: throw NullPointerException("Category cannot be empty.")

    for (expense in expenses) {
        if (expense.containsValue(categoryTotal)) {
            total += expense["amount"]?.toDouble()!!
            isFound = true
        }
    }

    if (isFound) {
        println("The overall total with $categoryTotal is R$total")
    } else {
        println("Category does not exist")
    }
}

fun deleteExpense() {
    if(printIfEmpty()) return
    var index = 0
    for (expense in expenses) {
        index++
        println("\t\t($index) --> $expense\n")
    }

    print("Enter the index of the expense you want to remove: ")
    val elementRemove = readln().toIntOrNull() ?: throw NullPointerException("Enter element to remove.")
    for (i in 0..expenses.size) {
        if (elementRemove == i + 1)
            expenses.removeAt(i)
    }
}

enum class Message(val text: String){
    EMPTY_LIST("Expense List Is Empty.")
}

fun printIfEmpty():Boolean{
    if(expenses.isEmpty())
        println(Message.EMPTY_LIST.text)
    return true
}