package com.example.project1.diceGame



import android.content.Intent

import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast

import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.R
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.style.TextAlign


class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            // 意图结束后，执行这个「回调函数」
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK) {
                    // 返回的data数据是个intent类型，里面存储了一段文本内容
                    val text = it.data?.getStringExtra("toMain")
                    Toast.makeText(this, "接受：$text", Toast.LENGTH_LONG).show()
                }
            }
        )
        setContent {
            val name=rememberSaveable {
                mutableStateOf(intent.getStringExtra("key").toString())
            }
            GameScreen(name=name.value, resultLauncher)
            }
        }
}



@Composable
fun GameScreen(name:String,resultLauncher: ActivityResultLauncher<Intent>){
    val game=DiceGame()
    /*remember:记住变量的状态*/
    val firstStatus=remember{ mutableStateOf(0) }
    val secondStatus=remember{ mutableStateOf(0) }

    /*标记开始状态,每次掷骰子需要区分是第一次掷骰子还是第二次及以上掷骰子*/
    val startState= remember { mutableStateOf(true) }
    var status= remember {
        mutableStateOf(GameStatus.Goon)
    }
    val context= LocalContext.current
    val winCount= remember {
        mutableStateOf(0)
    }
    val loseCount= remember {
        mutableStateOf(0)
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)){
        Column{
            Row{
                if(firstStatus.value!=0&&secondStatus.value!=0){
                    Image(painter = painterResource(id = getResource(firstStatus.value)), contentDescription ="firstDiceError",modifier=Modifier.size(200.dp))
                    Image(painter = painterResource(id = getResource(secondStatus.value) ), contentDescription ="secondDiceError",modifier=Modifier.size(200.dp))
                }
            }
            Button(onClick = {
                firstStatus.value=game.rollDice()
                secondStatus.value=game.rollDice()
                /*第一次掷骰子*/
                if(startState.value) {
                    status.value = game.judgeGame(firstStatus.value, secondStatus.value)
                    /*startState本身不可修改，修改的是startState存储的可变状态变量*/
                    startState.value = false
                }else{
                    /*第二次及以上掷骰子*/
                    status.value=game.goonGame(firstStatus.value, secondStatus.value, status.value)
                }

                /*三种情况*/
                when (status.value) {
                    /*赢了*/
                    GameStatus.Win -> {
                        val intent = Intent(context, WinGame::class.java)
                        intent.putExtra("name",name)
                        intent.putExtra("winCount",++winCount.value)
                        startState.value=true
                        resultLauncher.launch(intent)
                    }
                    /*输了*/
                    GameStatus.Lose -> {
                        val intent = Intent(context, LoseGame::class.java)
                        intent.putExtra("name",name)
                        intent.putExtra("loseCount",++loseCount.value)
                        startState.value=true
                        resultLauncher.launch(intent)
                    }
                    /*继续，更新point值，返回游戏状态--标注status非空*/
                    else -> {
//                        game.goonGame(firstStatus.value, secondStatus.value, status!!)
                    }
                }
            }) {
                Text(
                    text = "掷骰子",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.Bottom)
                        .padding(bottom = 16.dp), // 使用 Modifier 来控制宽度并添加水平间距
                    textAlign = TextAlign.Center, // 设置文本的对齐方式为居中
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun getResource(dice:Int):Int{
    return when(dice){
        1-> R.mipmap.dice_1
        2-> R.mipmap.dice_2
        3-> R.mipmap.dice_3
        4-> R.mipmap.dice_4
        5-> R.mipmap.dice_5
        6-> R.mipmap.dice_6
        else-> 0
    }
}


/*项目优化：
* 1、在掷出骰子后不要过快切换Activity，而是等待几秒钟-->Handle()实现
* 2、在输了或者赢了过后，如果用户想继续游戏，所以添加”返回“按钮-->添加Button，并进行视图切换，成功
* 3、在开始界面加一个用户，用户输入用户名即创建成功，然后开始掷骰子
* 记录用户成功和失败的次数，可以显示，成功或者输了都可以返回继续掷骰子
* 也可以退出-->
* 4、最后来一个清除记录，将记录在案的所有闯关者的记录清除*/