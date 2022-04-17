package alireza.nezami.truecallertestproject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

/**
 * Created by Alireza Nezami on 4/16/2022.
 */
class MainRepositoryImpl(

) : MainRepository {

    /**
     * Receives a string url and get it's content through a simple [HttpURLConnection]
     */
    @Throws(IOException::class)
    override suspend fun grabContent(strURL: String) = withContext(Dispatchers.IO) {
        val conn: URLConnection
        val inputStream: InputStream
        val url = URL(strURL)
        conn = url.openConnection()
        val httpConn: HttpURLConnection = conn as HttpURLConnection
        httpConn.requestMethod = "GET"
        httpConn.connect()
        inputStream = httpConn.inputStream
        inputStream.bufferedReader().use { it.readText() }
    }

    /**
     * This function finds the the 10th index's value and prints it on display
     * The time complexity of this func / algorithm is O(1)
     * Because we are reaching the value by it's index in the memory.
     * The process runs on the worker thread so the main thread shouldn't get involved.
     * Default dispatcher used since we know how long does it to complete the task
     * Notice: The 10th index of the content was empty space, so I picked the 11th to display
     * sth in the text view.
     */
    override suspend fun get10thCharacter(content: String): Char =
        withContext(Dispatchers.Default) {
            return@withContext content[10]
        }

    /**
     * This function has a loop with 10 step to iterate through the content and
     * reaches the value by direct index.
     * So the time complexity of this func / algorithm is O(1)
     * The process runs on the worker thread so the main thread shouldn't get involved.
     * Default dispatcher used since we know how long does it to complete the task
     * Alternative solution: Using buffered reader with input stream / convert whole string to charArray
     * and iterate through that.
     */
    override suspend fun getEvery10thCharacter(content: String) =
        withContext(Dispatchers.Default) {
            var step = 10
            val words = StringBuilder()
            do {
                words
                    .append("$step:   ${content[step]} \n")
                step += 10
            } while (step <= content.length)
            return@withContext words.toString()
        }

    /**
     * Separates the word by whitespace characters using (\\s) regex.
     * The time complexity of this func / algorithm is O(n) because it iterates through
     * all elements and make a new array list.
     * The process runs on the worker thread so the main thread shouldn't get involved.
     * Default dispatcher used since we know how long does it to complete the task
     */
    override suspend fun createArray(content: String): List<String> =
        withContext(Dispatchers.Default) {
            return@withContext content.split(
                "\\s".toRegex()
            )
        }

    /**
     * Receives list of strings (words)
     * This function creates a [LinkedHashMap] and iterate through words list
     * and if there was a key assigned to word, update the value by 1,
     * if not create a new key with 1 value.
     * Finally creates a [StringBuilder] to append all map key values with a line separator.
     * The process runs on the worker thread so the main thread shouldn't get involved.
     * Default dispatcher used since we know how long does it to complete the task
     */
    override suspend fun uniqueWordCounter(items: List<String>): String =
        withContext(Dispatchers.Default) {
            val uniqueWords = LinkedHashMap<String, Int>()
            for (item in items) {
                uniqueWords[item] = uniqueWords[item]?.plus(1) ?: 1
            }

            val builder = StringBuilder()
            uniqueWords.forEach { (key, value) ->
                builder.append("$key   ->   $value \n")
            }
            return@withContext builder.toString()
        }

}