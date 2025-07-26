/**
 * ExpenseTracker.kt
 *
 * A simple Kotlin console application to track user expenses.
 * Allows users to add, view, filter, and delete expenses based on different criteria.
 *
 * Author: Siphesihle Madi
 * Last updated: July 2025
 */


import java.time.LocalDate
import java.time.format.DateTimeParseException

/**
 * Represents an expense with description, amount, category, and date.
 */
data class Expense(val description: String, val amount: Double, val category: String, val date: LocalDate)

var expenses = mutableListOf<Expense>()

/**
 * Entry point of the expense tracking application.
 * Presents a menu to the user for various operations:
 * adding, viewing, filtering, deleting expenses, and exiting.
 */
fun main() {
    var userChoice = 0
    do {
        println("********************************")
        println("Expense Tracker\t\t${calculateOverallTotal()}")
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
        // Handle user input with exception handling for invalid types
        try {
            userChoice = readln().toIntOrNull() ?: throw NumberFormatException("Please enter the valid index.")

            when (userChoice) {
                1 -> addExpense()
                2 -> viewAllExpenses()
                3 -> filterByCategory()
                4 -> filterByAmountRange()
                5 -> filterByDate()
                6 -> calculateTotalByCategory()
                7 -> deleteExpense()
                99 -> userChoice = 99
                else -> println("Invalid Index Input")
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

/**
 * Adds a new expense to the global expenses list.
 *
 * @throws NullPointerException if any input is blank
 * @throws NumberFormatException if the amount is not a valid number
 * @throws DateTimeParseException if the date is not in correct format
 */
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

    val expense = Expense(description, amount, category, parsedDate)

    expenses.add(expense)
}

/**
 * View all expenses in the global expense list.
 *
 * returns early if global expenses list is empty.
 */
fun viewAllExpenses() {
    if (printIfEmpty()) return

    for (expense in expenses) {
        println("Description: ${expense.description}, Category: ${expense.category}, Amount: ${expense.amount}, Date: ${expense.date}")
    }
}

/**
 * Filters global expenses list by category and outputs it to the console.
 *
 * @throws NullPointerException if any input is blank
 */
fun filterByCategory() {
    if (printIfEmpty()) return
    var found = false
    print("Enter the category you want: ")
    val categoryChoice =
        readln().takeIf { it.isNotBlank() } ?: throw NullPointerException("PLease enter a description.")

    for (expense in expenses) {
        if (expense.category == categoryChoice) {
            println("Description: ${expense.description}, Category: ${expense.category}, Amount: ${expense.amount}, Date: ${expense.date}")
            found = true
        }
    }
    if (!found)
        println("Category not found")
}

/**
 * Filters global list by date and outputs it to the console.
 *
 * @throws NullPointerException if any input is blank
 * @throws DateTimeParseException if the date is not in correct format
 */
fun filterByDate() {
    if (printIfEmpty()) return
    var found = false

    print("What date would you like to see? ")
    val date = readln().takeIf { it.isNotBlank() } ?: throw NullPointerException("Date cannot be empty.")
    val dateChoice = LocalDate.parse(date)

    for (expense in expenses) {
        if (expense.date == dateChoice) {
            println("Description: ${expense.description}, Category: ${expense.category}, Amount: ${expense.amount}, Date: ${expense.date}")
            found = true
        }
    }
    if (!found)
        println("Expense not found with that date.")

}

/**
 * Filters global expenses list by amount range specified by user.
 *
 * @throws NullPointerException if any input is blank
 * @throws NumberFormatException if the amount is not a valid number
 */
fun filterByAmountRange() {
    if (printIfEmpty()) return
    print("Enter lower limit in the range: ")
    val lowerLimitRange =
        readln().toDoubleOrNull() ?: throw NumberFormatException("Lower Limit value should be entered and be numeric.")
    print("Enter upper limit in the range: ")
    val upperLimitRange =
        readln().toDoubleOrNull() ?: throw NumberFormatException("Upper Limit value should be entered and be numeric.")

    for (expense in expenses) {
        if (lowerLimitRange >= 0 && upperLimitRange >= 0) {
            if (expense.amount in lowerLimitRange..upperLimitRange) {
                println("Description: ${expense.description}, Category: ${expense.category}, Amount: ${expense.amount}, Date: ${expense.date}")
            }
        } else {
            println("Values cannot be below zero.")
            break
        }
    }
}

/**
 * Calculates overall total amount of the expenses list.
 *
 */
fun calculateOverallTotal(): Double {
    var total = 0.0
    for (expense in expenses) {
        total += expense.amount
    }

    return total
}

/**
 * Calculate overall total by category from global expenses list.
 *
 * @throws NullPointerException if any input is blank
 */
fun calculateTotalByCategory() {
    if (printIfEmpty()) return
    var total = 0.0
    var isFound = false
    print("Enter the category you want: ")
    val categoryTotal =
        readln().takeIf { it.isNotBlank() } ?: throw NullPointerException("Category cannot be empty.")

    for (expense in expenses) {
        if (expense.category == categoryTotal) {
            total += expense.amount
            isFound = true
        }
    }

    if (isFound) {
        println("The overall total with $categoryTotal is R$total")
    } else {
        println("Category does not exist")
    }
}

/**
 * Deletes expense from the global expenses list.
 *
 * @throws NullPointerException if any input is blank
 */
fun deleteExpense() {
    if (printIfEmpty()) return
    var index = 0
    for (expense in expenses) {
        index++
        println("\t\t($index). Description: ${expense.description}, Category: ${expense.category}, Amount: ${expense.amount}, Date: ${expense.date}")
    }

    print("Enter the index of the expense you want to remove: ")
    val elementRemove = readln().toIntOrNull() ?: throw NullPointerException("Enter element to remove.")
    for (i in 0..expenses.size) {
        if (elementRemove == i + 1)
            expenses.removeAt(i)
    }
}

/**
 * Standard messages used throughout the application.
 *
 */
enum class Message(val text: String) {
    EMPTY_LIST("Expense List Is Empty.")
}

/**
 * Checks if global expenses list is empty and prints to the console the enumeration constant message 'EMPTY_LIST' text.
 * Returns true if global list is empty and false otherwise.
 *
 */
fun printIfEmpty(): Boolean {
    if (expenses.isEmpty()) {
        println(Message.EMPTY_LIST.text)
        return true
    }
    return false
}