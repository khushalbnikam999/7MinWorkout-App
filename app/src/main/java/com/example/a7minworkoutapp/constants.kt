package com.example.a7minworkoutapp

import android.app.Activity

object constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel> {

        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks =
            ExerciseModel(1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false)
        exerciseList.add(jumpingJacks)

        val pushUp = ExerciseModel(2, "Push Up", R.drawable.ic_push_up, false, false)
        exerciseList.add(pushUp)

        val abdominalCrunch =
            ExerciseModel(3, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false)
        exerciseList.add(abdominalCrunch)

        val plank = ExerciseModel(4, "Plank", R.drawable.ic_plank, false, false)
        exerciseList.add(plank)

        return exerciseList
    }
}