package com.technobd.basickotlinprogramming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // calling the printSomething method here.
        printSomething()

    }

    fun printSomething(){
        println("Happy Birthday!")
        println("Jhansi")
        println("You are 25!")
    }
}