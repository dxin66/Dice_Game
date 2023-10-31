package com.example.project1.diceGame

import kotlin.random.Random

/**
 * 骰子游戏业务代码
 */
class DiceGame{
    fun rollDice()=Random.nextInt(6)+1

    fun judgeGame(first:Int,second:Int):GameStatus=when(first+second){
        7,11->GameStatus.Win
        2,3,12->GameStatus.Lose
        else->GameStatus.Goon
    }
    /**
     * first:第一颗骰子点数
     * second：第二颗骰子点数
     * status：上一次的状态
     */
    fun goonGame(first: Int,second: Int,status: GameStatus):GameStatus{
        return when(val total=first+second){
            7->GameStatus.Lose
            status.point->GameStatus.Win
            else->{
                /*更新total为status的point，同时返回status*/
                status.point=total
                status
            }
        }
    }
}
