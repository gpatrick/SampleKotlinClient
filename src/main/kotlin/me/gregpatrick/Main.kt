package me.gregpatrick

import org.w3c.dom.Element
import kotlin.browser.document

/**
 * This sample project makes a call to a simple REST service to retrieve a list of messages, and then places them in a
 * table on the page. Table elements are created using standard Kotlin calls, in order to show a basic way to do it.
 * A more readable (and cleaner) solution would be to use kotlinx.html.
 *
 * In order to run this, please:
 *   * run `mvn package`
 *   * open index.html
 *
 * NOTE: This sample uses top-level functions for simplicity. For a real implementation, it would be advisable to figure
 * out where functionality could be better encapsulated in classes.
 */



/**
 * A class representing a message in the system.
 *
 * @param from the name of the person who sent the message
 * @param to the name of the person who received the message
 * @param message the contents of the message
 */
data class Message(val from:String, val to:String, val message:String)


/**
 * Entry point to the application.
 */
fun main(args: Array<String>) {
    getData("http://localhost:4567/messages")
}


/**
 * References the underlying XMLHttpRequest class provided by Javascript
 */
external class XMLHttpRequest


/**
 * Registers a callback, and invokes a call on the supplied endpoint. The callback locates the content div in index.html
 * and adds a table of messages to it.
 *
 * @param endpoint the REST endpoint to call
 */
fun getData(endpoint: String) {
    val httpRequest:dynamic = XMLHttpRequest()
    httpRequest.open("GET", endpoint)

    httpRequest.onload = {
        val content = document.getElementById("content")
        content!!.appendChild(createTable(httpRequest.responseText as String))
    }

    httpRequest.send()
}


/**
 * Creates a table from a JSON array of messages. It creates the table header and then appends a row for each message
 * it finds in the messages array. (Note the use of the forEach function instead of an imperative for loop).
 *
 * @param messageData a JSON string of message data.
 * @return Element a table containing a header and messages
 */
fun createTable(messageData:String): Element {
    val table = document.createElement("table")
    table.appendChild(getTableHeader())
    getMessageRows(messageData).forEach{
        message -> table.appendChild(message)
    }

    return table
}


/**
 * Constructs the table header. This function demonstrates the instantiation of a collection (list of header names), and
 * shows how one would create and attach DOM elements. It also shows the use of imperative for loops.
 *
 * @return Element a table header as a DOM element
 */
fun getTableHeader(): Element {
    val headerNames = listOf("From", "To", "Message")
    val tableHeaderRow = document.createElement("tr")

    for(header in headerNames) {
        val tableHeaderColumn = document.createElement("th")
        tableHeaderColumn.textContent = header
        tableHeaderRow.appendChild(tableHeaderColumn)
    }

    return tableHeaderRow
}


/**
 * Parses a JSON array, and produces an ArrayList of Message objects. It then creates a DOM subtree representing the
 * message and appends it to the result (which is a list of DOM elements)
 *
 * @return Element a list of "Message" DOM elements
 */
fun getMessageRows(messageData:String): List<Element> {
    val messages = JSON.parse<Array<Message>>(messageData)
    val messageRows = arrayListOf<Element>()

    messages.forEach { entry ->
        messageRows.add(createRow(entry))
    }

    return messageRows
}


/**
 * Takes a message object and converts it into a DOM representation. This function demonstrates the use of the
 * Kotlin "when" statement. Note that since the when statement is used as an expression, an "else" clause is required.
 * The code uses a forEach function on a range (0 to 2 inclusive), so it is save to assume that the else clause will
 * only be the value "2".
 *
 * @param entry the Message to convert to a DOM element
 * @return Element the DOM element
 */
fun createRow(entry:Message): Element {
    val messageRow = document.createElement("tr")

    (0..2).forEach{ index ->
        val messageColumn = document.createElement("td")
        messageColumn.textContent = when (index) {
            0 -> entry.from
            1 -> entry.to
            else -> entry.message
        }

        messageRow.appendChild(messageColumn)
    }

    return messageRow
}
