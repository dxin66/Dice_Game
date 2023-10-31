package com.example.project1.diceGame

enum class GameStatus(private val description:String,var point:Int) {
    Win("恭喜，你赢了",0),
    Lose("糟糕，你输了",0),
    Goon("继续游戏",0);
    /*改写toString()方法*/
    override fun toString()=description
}




