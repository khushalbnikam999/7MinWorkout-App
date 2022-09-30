package com.example.a7minworkoutapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkoutapp.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class exerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var exerciseList :ArrayList<ExerciseModel> ?=null
    private var currentExercisePosition=-1

    private var exerciseAdapter :ExerciseViewAdapter?=null

    private var tts :TextToSpeech?=null

    private var restTimer: CountDownTimer? = null
    private var restProgress =0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress =0

    private var binding :ActivityExerciseBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBar)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolBar?.setNavigationOnClickListener {
            onBackPressed()
        }
        exerciseList=constants.defaultExerciseList()

        tts= TextToSpeech(this,this)

        setupRestView()
        setRecyclerView()
    }

    private fun setupRestView() {

        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.upcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition + 1].getName()
        onSpeak("RELAX A BIT")
        setRestProgressBar()
    }

    private fun setRestProgressBar() {

        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView() {

        // Here according to the view make it visible as this is Exercise View so exercise view is visible and rest view is not.
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        binding?.upcomingLabel?.visibility = View.INVISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()
        onSpeak(exerciseList!![currentExercisePosition].getName().toString())
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {

        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()

                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    setupRestView()
                } else {

                    Toast.makeText(
                        this@exerciseActivity,
                        "Congratulations! You have completed the 7 minutes workout.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }

    public override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if(tts !=null){
            tts!!.stop()
            tts!!.shutdown()
        }

        super.onDestroy()
        binding = null
    }

    override fun onInit(status: Int) {
        if(status ==TextToSpeech.SUCCESS){
            val result=tts?.setLanguage(Locale.ENGLISH)
        }
    }

    private fun onSpeak(text :String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter= ExerciseViewAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter=exerciseAdapter
    }
}