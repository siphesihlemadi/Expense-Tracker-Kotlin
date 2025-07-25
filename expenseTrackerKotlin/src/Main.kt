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
            userChoice = readln().toInt()

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
            println("Please enter a numeric value")
        }
    } while (userChoice != 99)
}

fun addExpense() {
    try {
        print("Enter the description of the expense: ")
        val description = readln().takeIf { it.isNotBlank() } ?: throw NullPointerException("Description cannot be empty")
        print("Enter the amount paid: ")
        val amount = readln().toDouble()
        print("Enter the category of the expense: ")
        val category = readln().takeIf { it.isNotBlank() } ?: throw NullPointerException("Category cannot be empty.")
        print("Enter the date paid: ")
        val date = readln().takeIf { it.isNotBlank() }?:throw NullPointerException("Date cannot be empty.")
        val parsedDate = LocalDate.parse(date)

        val expense = mutableMapOf(
            "description" to description,
            "amount" to amount.toString(),
            "category" to category,
            "date" to parsedDate.toString()
        )

        expenses.add(expense)
    } catch (e: NumberFormatException) {
        println("Please enter numeric value for amount.")
    } catch (e: DateTimeParseException) {
        println("Please enter date in YY-MM-DD format. e.g. 2000-01-01")
    } catch (e: NullPointerException) {
        println("Message: ${e.message}")
    }

}

fun viewAllExpenses() {
    var count = 1
    for (expense in expenses) {
        println("$count ---> $expense")
        count++
    }
}

fun filterByCategory() {
    var found = false
    print("Enter the category you want: ")
    val categoryChoice = readln()
    if(categoryChoice.isEmpty()){
        println("PLease enter a description.")
        return
    }

    for (expense in expenses) {
        if (expense.containsValue(categoryChoice)) {
            println("(1) ---> $expense")
            found = true;
        }
    }
    if (!found)
        println("Category not found")
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

fun totalExpenseByCategory() {
    var total = 0.0
    print("Enter the category you want: ")
    val categoryTotal = readln()

    for (expense in expenses) {
        if (expense.containsValue(categoryTotal)) {
            total += expense["amount"]?.toDouble()!!
        }
    }
    println("The overall total with $categoryTotal is R$total")
}

fun deleteExpense() {
    var index = 0
    for (expense in expenses) {
        index++
        println("\t\t($index) --> $expense\n")
    }

    print("Enter the index of the expense you want to remove: ")
    val elementRemove = readln().toInt()
    for (i in 0..expenses.size) {
        if (elementRemove == i + 1)
            expenses.removeAt(i)
    }
}