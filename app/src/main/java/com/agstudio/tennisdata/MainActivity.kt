package com.agstudio.tennisdata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agstudio.tennisdata.databinding.ActivityMainBinding
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val adapterPlayer1 = SetsRecyclerViewAdapter(ArrayList())
    private val adapterPlayer2 = SetsRecyclerViewAdapter(ArrayList())
    private lateinit var binding: ActivityMainBinding
    private lateinit var match: Match

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(binding.root)

        val szabi = Player("Szabolcs", UUID.randomUUID(), ArrayList())
        val gabi = Player("Gabor", UUID.randomUUID(), ArrayList())

        binding.Player1NameTextView.text = szabi.name
        binding.Player2NameTextView.text = gabi.name
        binding.player1SetsRecyclerView.adapter = adapterPlayer1
        binding.player1SetsRecyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.player2SetsRecyclerView.adapter = adapterPlayer2
        binding.player2SetsRecyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

        match = Match(2, DateFormat.getDateInstance())
        match.startTheMatch()

        refreshScreen()
        binding.upperLayout.setOnClickListener{
            match.addPointToPlayer(0)
            refreshScreen()
        }
        binding.downerLayout.setOnClickListener {
            match.addPointToPlayer(1)
            refreshScreen()
        }
    }

    private fun refreshScreen() {
        adapterPlayer1.addAll(match.getRecyclerViewAdapterDataForPlayer(0))
        adapterPlayer2.addAll(match.getRecyclerViewAdapterDataForPlayer(1))
        binding.Player1PointsTextView.text = match.getPlayerPoints(0)
        binding.Player2PointsTextView.text = match.getPlayerPoints(1)
    }
}