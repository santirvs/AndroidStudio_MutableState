package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    MyConstraintLayout( modifier = Modifier.padding(padding), 4)
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hola $name!",
        modifier = modifier
    )
}

@Composable
fun MyConstraintLayout( modifier: Modifier = Modifier, tipus:Int = 1) {

    when (tipus) {
        1 -> MyConstraintLayout_withGuidelines(modifier)
        2 -> MyConstraintLayout_withoutGuidelines(modifier)
        3 -> MyConstraintLayout_chains(modifier)
        4 -> MyConstraintLayout_text(modifier)
        5 -> MyConstraintLayout_Images(modifier)
        else -> Greeting("ERROR", modifier)
    }
}


@Composable
fun MyConstraintLayout_withoutGuidelines(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize()) {
        val (boxRed, boxYellow, boxGreen, boxCyan, boxMagenta) = createRefs()

        Box(modifier = Modifier.size(120.dp).background(Color.Red).constrainAs(boxRed) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        Box(modifier = Modifier.size(120.dp).background(Color.Yellow).constrainAs(boxYellow) {
            bottom.linkTo(boxRed.top)
            start.linkTo(boxRed.start)
        })
        Box(modifier = Modifier.size(120.dp).background(Color.Green).constrainAs(boxGreen) {
            top.linkTo(boxRed.bottom)
            start.linkTo(boxRed.start)
        })
        Box(modifier = Modifier.size(120.dp).background(Color.Cyan).constrainAs(boxCyan) {
            bottom.linkTo(boxRed.bottom)
            end.linkTo(boxRed.start)
        })
        Box(modifier = Modifier.size(120.dp).background(Color.Magenta).constrainAs(boxMagenta) {
            bottom.linkTo(boxRed.bottom)
            start.linkTo(boxRed.end)
        })

    }
}

@Composable
fun MyConstraintLayout_withGuidelines(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize()) {
        val topGuide = createGuidelineFromTop(0.25f)
        val leftGuide = createGuidelineFromStart(0.25f)
        //val bottomGuide = createGuidelineFromBottom(0.25f)
        //val rightGuide = createGuidelineFromEnd(0.25f)

        val boxRed = createRef()
        Box(modifier = Modifier.size(120.dp).background(Color.Red).constrainAs(boxRed){
            top.linkTo(topGuide)
            start.linkTo(leftGuide)
        })

    }
}

@Composable
fun MyConstraintLayout_chains(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize().padding(vertical=100.dp)) {
        val (boxRed, boxYellow, boxGreen) = createRefs()
        Box(modifier.size(50.dp).background(Color.Red).constrainAs(boxRed){
            start.linkTo(parent.start)
            end.linkTo(boxYellow.start)
        })
        Box(modifier.size(50.dp).background(Color.Yellow).constrainAs(boxYellow){
            start.linkTo(boxRed.end)
            end.linkTo(boxGreen.start)
        })
        Box(modifier.size(50.dp).background(Color.Green).constrainAs(boxGreen){
            start.linkTo(boxYellow.end)
            end.linkTo(parent.end)
        })

        //createHorizontalChain(boxRed, boxYellow, boxGreen, chainStyle = ChainStyle.Packed)
        //createHorizontalChain(boxRed, boxYellow, boxGreen, chainStyle = ChainStyle.Spread)
        createHorizontalChain(boxRed, boxYellow, boxGreen, chainStyle = ChainStyle.SpreadInside)
    }
}

@Composable
fun MyConstraintLayout_text(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().padding(vertical=100.dp)) {
        Text(text = "This is my text example")
        Text(
            "This is my text example",
            color = Color.Red,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            textDecoration = TextDecoration.Underline
        )

        val myStyle = TextStyle(color = Color.Red,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            textDecoration = TextDecoration.Underline)

        Text(
            "This is my text example",
            style = myStyle
        )


    }
}

@Composable
fun MyConstraintLayout_Images(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize().padding(vertical=100.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Example",
            alpha = 0.75f,
            //modifier = Modifier.clip(RoundedCornerShape(50f))
            //modifier = Modifier.clip(CircleShape)
            //modifier = Modifier.clip(CircleShape).border(4.dp, Color.Red)
            modifier = Modifier.clip(CircleShape).border(4.dp, Color.Red, CircleShape)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Example",
            alpha = 1f
        )
        Icon(imageVector = Icons.Sharp.Star,
            contentDescription = "Icon Example",
            tint = Color.Blue)
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MyConstraintLayout(tipus = 5)
    }
}