package com.agstudio.tennisdata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agstudio.tennisdata.databinding.ActivityMainBinding
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val adapterPlayer1 = SetsRecyclerViewAdapter(arrayListOf("0", "0"))
    private val adapterPlayer2 = SetsRecyclerViewAdapter(arrayListOf("0", "0"))
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
            match.addPointToPlayer1()
            refreshScreen()
        }
        binding.downerLayout.setOnClickListener {
            match.addPointToPlayer2()
            refreshScreen()
        }
    }

    private fun refreshScreen() {
        val player1SetsStrings = ArrayList<String>()
        match.getPlayer1Sets().forEach { set ->
            player1SetsStrings.add(set.toString())
        }
        adapterPlayer1.addAll(player1SetsStrings)
        val player2SetsStrings = ArrayList<String>()
        match.getPlayer2Sets().forEach { set ->
            player2SetsStrings.add(set.toString())
        }
        adapterPlayer2.addAll(player2SetsStrings)
        binding.Player1PointsTextView.text = match.getPlayer1Points()
        binding.Player2PointsTextView.text = match.getPlayer2Points()
    }
}