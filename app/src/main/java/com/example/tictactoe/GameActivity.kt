package com.example.tictactoe

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.example.tictactoe.databinding.ActivityGameBinding
import com.example.tictactoe.databinding.ResultLayoutBinding

class GameActivity : AppCompatActivity() {

    lateinit var binding : ActivityGameBinding
    lateinit var dialogbinding : ResultLayoutBinding
    var activeplayer = 1
    var player1moves = mutableSetOf<Int>()
    var player2moves = mutableSetOf<Int>()
    var robotmultiplayer = false
    var offlinemultiplayer = false
    var win = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val intent = intent
        val type = intent.getIntExtra("gametype",0)

        if(type==1) offlinemultiplayer = true
        else if(type==2)
        {
            robotmultiplayer = true
            binding.turn.setText("Your Turn")
        }

        binding.titleicon.translationY = -1000f
        binding.appname.translationX = 1000f
        binding.gamelayout.translationY = 1000f
        binding.turn.translationX = -1000f

        binding.titleicon.animate().translationY(0f).duration = 750
        binding.appname.animate().translationX(0f).duration = 750
        binding.gamelayout.animate().translationY(0f).duration = 750
        binding.turn.animate().translationX(0f).duration = 750
    }

    fun onclick(view : View)
    {
        val button = view as Button
        var cellid = 0
        when(button.id)
        {
            binding.bu1.id-> cellid = 1
            binding.bu2.id-> cellid = 2
            binding.bu3.id-> cellid = 3
            binding.bu4.id-> cellid = 4
            binding.bu5.id-> cellid = 5
            binding.bu6.id-> cellid = 6
            binding.bu7.id-> cellid = 7
            binding.bu8.id-> cellid = 8
            binding.bu9.id-> cellid = 9
        }
        if(offlinemultiplayer) {
            playofflinemultiplayergame(cellid, button)
        }
        else
        {
            if(activeplayer==1) playofflinemultiplayergame(cellid, button)
            Handler(Looper.getMainLooper()).postDelayed({
                if(player1moves.size + player2moves.size != 9 && !win) playrobotmultiplayer()
            }, 1000)
        }
    }

    private fun playrobotmultiplayer()
    {
        val cellid  = (1..9).random()
        if(player1moves.contains(cellid) || player2moves.contains(cellid))
            playrobotmultiplayer()
        else
        {
            val button : Button?
            if(cellid==1) button = binding.bu1
            else if(cellid==2) button = binding.bu2
            else if(cellid==3) button = binding.bu3
            else if(cellid==4) button = binding.bu4
            else if(cellid==5) button = binding.bu5
            else if(cellid==6) button = binding.bu6
            else if(cellid==7) button = binding.bu7
            else if(cellid==8) button = binding.bu8
            else button = binding.bu9

            button.setText("O")
            button.setBackgroundResource(R.drawable.y_background)
            player2moves.add(cellid)
            activeplayer = 1
            if(checkresult(cellid,player2moves)) {
                win = true
                showresult("Computer wins !!!")
            }
            binding.turn.setText("Your Turn")
        }
    }

    fun playofflinemultiplayergame(cellid : Int , button : Button)
    {
        if(!player1moves.contains(cellid) && !player2moves.contains(cellid))
        {
            if(activeplayer == 1)
            {
                button.setText("X")
                button.setBackgroundResource(R.drawable.x_background)
                player1moves.add(cellid)
                activeplayer = 2
                if(checkresult(cellid,player1moves))
                {
                    win = true
                    if(offlinemultiplayer)
                        showresult("Player 1 wins !!!")
                    else
                        showresult("You Win !!!")
                }
            }
            else
            {
                button.setText("O")
                button.setBackgroundResource(R.drawable.y_background)
                player2moves.add(cellid)
                activeplayer = 1
                if(checkresult(cellid,player2moves))
                {
                    showresult("Player 2 wins !!!")
                }
            }
            if(offlinemultiplayer)
                binding.turn.setText("Player " + activeplayer + " turn")
            else if(robotmultiplayer)
                binding.turn.setText("Computer's turn")
        }
    }

    private fun showresult(s: String)
    {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialogbinding = ResultLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(dialogbinding.root)
        dialogbinding.result.text = s
        dialog.show()
        dialogbinding.exit.setOnClickListener { view->
            finish()
        }
        dialogbinding.playagain.setOnClickListener { view->
            resetgame()
            dialog.dismiss()
            win = false
            if(activeplayer == 2 && robotmultiplayer) playrobotmultiplayer()
        }
    }

    private fun resetgame()
    {
        player1moves.clear()
        player2moves.clear()
        for(cellid in (1..9))
        {
            var button : Button?
            if(cellid==1) button = binding.bu1
            else if(cellid==2) button = binding.bu2
            else if(cellid==3) button = binding.bu3
            else if(cellid==4) button = binding.bu4
            else if(cellid==5) button = binding.bu5
            else if(cellid==6) button = binding.bu6
            else if(cellid==7) button = binding.bu7
            else if(cellid==8) button = binding.bu8
            else  button = binding.bu9

            button.setText("")
            button.setBackgroundResource(R.drawable.game_button_background)
        }
    }

    private fun checkresult(cellid: Int, playermoves: MutableSet<Int>) : Boolean
    {
        if(cellid == 1)
        {
            if(playermoves.contains(2) && playermoves.contains(3)) return true
            if(playermoves.contains(4) && playermoves.contains(7)) return true
            if(playermoves.contains(5) && playermoves.contains(9)) return true
        }
        else if(cellid==2)
        {
            if(playermoves.contains(1) && playermoves.contains(3)) return true
            if(playermoves.contains(5) && playermoves.contains(8)) return true
        }
        else if(cellid==3)
        {
            if(playermoves.contains(1) && playermoves.contains(2)) return true
            if(playermoves.contains(6) && playermoves.contains(9)) return true
            if(playermoves.contains(5) && playermoves.contains(7)) return true
        }
        else if(cellid==4)
        {
            if(playermoves.contains(1) && playermoves.contains(7)) return true
            if(playermoves.contains(5) && playermoves.contains(6)) return true
        }
        else if(cellid==5)
        {
            if(playermoves.contains(2) && playermoves.contains(8)) return true
            if(playermoves.contains(4) && playermoves.contains(6)) return true
            if(playermoves.contains(1) && playermoves.contains(9)) return true
            if(playermoves.contains(3) && playermoves.contains(7)) return true
        }
        else if(cellid==6)
        {
            if(playermoves.contains(3) && playermoves.contains(9)) return true
            if(playermoves.contains(4) && playermoves.contains(5)) return true
        }
        else if(cellid==7)
        {
            if(playermoves.contains(1) && playermoves.contains(4)) return true
            if(playermoves.contains(8) && playermoves.contains(9)) return true
            if(playermoves.contains(5) && playermoves.contains(3)) return true
        }
        else if(cellid==8)
        {
            if(playermoves.contains(2) && playermoves.contains(5)) return true
            if(playermoves.contains(7) && playermoves.contains(9)) return true
        }
        else
        {
            if(playermoves.contains(3) && playermoves.contains(6)) return true
            if(playermoves.contains(7) && playermoves.contains(8)) return true
            if(playermoves.contains(1) && playermoves.contains(5)) return true
        }
        if(player1moves.size + player2moves.size == 9)
            showresult("Match Draw !!!")
        return false
    }
}