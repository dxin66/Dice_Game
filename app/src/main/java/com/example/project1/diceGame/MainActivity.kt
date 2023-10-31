package com.example.project1.diceGame


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.project1.R


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*Android Compose入口点，设置应用的主布局*/
        setContent {
            MainScreen()
        }
    }
}

@Preview
@Composable
fun MainScreen(){
    /*获取当前Activity的上下文*/
    val context= LocalContext.current

    var text by remember {
        mutableStateOf("")
    }
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)){
        Column(modifier= Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
            verticalArrangement=Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(modifier= Modifier
                .size(150.dp)
                .border(8.dp,Color.LightGray)
                .clickable {
                    /*设置点击触发事件,点击即切换到GameActivity*/
                    val intent = Intent(context, GameActivity::class.java)
                    context.startActivity(intent)
                },
                painter = painterResource(id = R.mipmap.start),
                contentDescription = "start")
            Spacer(modifier = Modifier.height(25.dp))
            Row {
                BasicTextField(
                    value = text,
                    onValueChange={ newText-> text=newText },
                    modifier= Modifier
                        .width(100.dp)
                        .height(80.dp)
                        .padding(10.dp)
                        .border(2.dp, Color.Black),
                    textStyle = TextStyle(fontSize = 25.sp)
                )
                Button(onClick = {
                    val intent=Intent(context,GameActivity::class.java)
                    val data=text
                    intent.putExtra("key",data)
                    context.startActivity(intent)
                    showToast(context,"创建记录完成！") }
                    , modifier = Modifier.padding(20.dp)) {
                    Text("确定")
                }
            }
        }


    }
}
// 在用户输入完成后调用此函数以显示提示消息
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}



