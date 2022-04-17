package alireza.nezami.truecallertestproject

/**
 * Created by Alireza Nezami on 4/16/2022.
 */
interface MainRepository {

    suspend fun grabContent(strURL: String) : String

    suspend fun get10thCharacter(content: String) : Char

    suspend fun getEvery10thCharacter(content: String) : String

    suspend fun createArray(content: String): List<String>

    suspend fun uniqueWordCounter(items: List<String>) : String
}