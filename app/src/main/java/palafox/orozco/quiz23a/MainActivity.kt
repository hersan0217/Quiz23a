package palafox.orozco.quiz23a

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import palafox.orozco.quiz23a.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel:QuizViewModel by viewModels()
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
// Handle the result

        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }

    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.trueButton.setOnClickListener {view: View->
            checkAnswer(true)

        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }
        binding.nexButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        binding.cheatButton.setOnClickListener {
        // Start CheatActivity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)

        }

        binding.prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }
        updateQuestion()
        Log.d(TAG, "Pase por el metodo onCreate")
        Log.d(TAG, "Tengo un QuizViewModel: $quizViewModel")
    }


    private fun updateQuestion(){
        val questionTexResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTexResId)
    }

    private  fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer


        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()


    }
}