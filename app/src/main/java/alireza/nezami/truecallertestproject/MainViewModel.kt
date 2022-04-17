package alireza.nezami.truecallertestproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Alireza Nezami on 4/14/2022.
 */
class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private var _first10thCharacter = MutableStateFlow(' ')
    val first10thCharacter = _first10thCharacter.asStateFlow()

    private var _every10thCharacter = MutableStateFlow("")
    val every10thCharacter = _every10thCharacter.asStateFlow()

    private var _uniqueWordsCount = MutableStateFlow("")
    val uniqueWordsCount = _uniqueWordsCount.asStateFlow()

    private val _buttonState by lazy { MutableStateFlow(true) }
    var buttonState = _buttonState

    fun start() {
        viewModelScope.launch(Dispatchers.IO) {
            _buttonState.value = false

            val content = async {
                repository.grabContent("https://truecaller.blog/2018/01/22/life-as-an-android-engineer/")
            }

            // For starting this process, the content is needed to be provided, So we use await() to
            // wait for the value to be initialized
            launch(Dispatchers.Default) {
                _first10thCharacter.value = repository.get10thCharacter(content.await())
            }

            launch(Dispatchers.Default) {
                _every10thCharacter.value = repository.getEvery10thCharacter(content.await())
            }

            val array = async(Dispatchers.Default) {
                repository.createArray(content.await())
            }

            launch(Dispatchers.Default) {
                _uniqueWordsCount.value = repository.uniqueWordCounter(array.await())
            }

            _buttonState.value = true
        }
    }


}