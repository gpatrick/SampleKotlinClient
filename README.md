This sample project makes a call to a simple REST service to retrieve a list of messages, and then places them in a 
table on the page. Table elements are created using standard Kotlin calls, in order to show a basic way to do it.
A more readable (and cleaner) solution would be to use kotlinx.html.

Before running this project, please open the [MessageServer](https://github.com/gpatrick/SampleKotlinServer.git), 
and run it first.

In order to run this, please:
* run `mvn package`
* open index.html
 
NOTE: This sample uses top-level functions for simplicity. For a real implementation, it would be advisable to figure 
out where functionality could be better encapsulated in classes.