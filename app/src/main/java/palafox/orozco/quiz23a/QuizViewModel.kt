package palafox.orozco.quiz23a

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel(){
    private val questionBank = listOf(
        Question(R.string.question_jalisco,true),
        Question(R.string.question_ameca,false),
        Question(R.string.question_zacatecas,false),
        Question(R.string.question_tlaxcala,false)

    )
    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    private var currentIndex:Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY)?:0
        set(value)=savedStateHandle.set(CURRENT_INDEX_KEY,value)

    val currentQuestionAnswer:Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText:Int
        get() = questionBank[currentIndex].textResId
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev(){
        currentIndex = if (currentIndex == 0) {
            questionBank.size-1
        }else

            (currentIndex-1) % questionBank.size
    }


}