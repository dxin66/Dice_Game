package com.example.project1.diceGame


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.project1.R
import androidx.activity.ComponentActivity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class LoseGame : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loseCount=intent.getIntExtra("loseCount",0)
        val name=intent.getStringExtra("name")!!
        setContent {
            Lose(loseCount, name)
        }
    }
}
@Composable
fun Lose(loseCount:Int,name:String){
    val context= LocalContext.current as Activity
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)){

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text=name+":输了${loseCount}次",
                color=Color.Blue,
                fontSize=60.sp,
                modifier=Modifier.padding(16.dp)
            )
//            Spacer(modifier = Modifier.weight(0.1f)) // 用于在两个Button之间添加空间，使它们分开
            Image(modifier= Modifier
                .size(220.dp),
                painter = painterResource(id = R.mipmap.lose),
                contentDescription = "lose")
//            Spacer(modifier = Modifier.weight(0.1f)) // 用于在两个Button之间添加空间，使它们分开
            Button(
                onClick ={
                    /*点击继续按钮,切换到掷骰子界面*/
                    val intent= Intent()
                    intent.putExtra("toMain","继续游戏")
                    context.setResult(Activity.RESULT_OK,  intent)
                    context.finish()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("继续游戏")
            }
            Button(
                onClick = {
                    /*点击继续按钮,切换到退出界面*/
                    val intent= Intent(context,MainActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("退出游戏")
            }
        }
    }
}
