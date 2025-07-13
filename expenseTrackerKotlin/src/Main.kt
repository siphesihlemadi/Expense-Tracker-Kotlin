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
    for (expense in expenses){
        if(expense.containsValue(dateChoice)){
            println("(1) --> $expense")
        }
    }
}