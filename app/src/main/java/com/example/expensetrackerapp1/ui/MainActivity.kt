package com.example.expensetrackerapp1.ui


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import com.example.expensetrackerapp1.R


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(HeaderFragment(), R.id.fragment_container_header)
        replaceFragment(FooterFragment(), R.id.fragment_container_footer)

    }
    private fun replaceFragment(fragment: Fragment, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

}

