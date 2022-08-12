package com.dipizi007.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dipizi007.rickandmorty.UI.characterFragment.CharacterFragment
import com.dipizi007.rickandmorty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}