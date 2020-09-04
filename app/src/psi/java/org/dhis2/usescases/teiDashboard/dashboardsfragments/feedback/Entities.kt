package org.dhis2.usescases.teiDashboard.dashboardsfragments.feedback

data class FeedbackOrder(val value: String) : Comparable<FeedbackOrder> {
    private val orderItems: List<Int> = value.split(".").map { it.toInt() }
    val parent =
        if (orderItems.size == 1)
            null
        else
            orderItems.subList(0, orderItems.size - 1)
        .joinToString(".")

    override fun compareTo(other: FeedbackOrder): Int {
        var result: Int? = null
        var position = 0

        val indexExists = { list: List<Int>, index: Int ->
            index >= 0 && index < list.size
        }

        while (result == null) {

            if (!indexExists(this.orderItems, position) && !indexExists(
                    other.orderItems,
                    position
                )
            ) {
                return 0 // equal
            } else if (!indexExists(this.orderItems, position)) {
                return -1 // this is minor
            } else if (!indexExists(other.orderItems, position)) {
                return 1 // other is minor
            } else if (this.orderItems[position] != other.orderItems[position]) {
                result = this.orderItems[position] - other.orderItems[position]
            }

            position++
        }

        return result
    }
}

data class Value(
    val dataElement:String,
    val name: String,
    val value: String,
    val feedbackOrder: FeedbackOrder,
    val colorByLegend: String? = null,
    val feedbackHelp: String? = null
)

data class Event(val name: String, val values: List<Value>)