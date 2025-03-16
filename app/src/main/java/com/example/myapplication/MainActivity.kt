package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.MyApplicationTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    MyConstraintLayout(modifier = Modifier.padding(padding))
                }

            }
        }
    }
}


//region presentacio_07 MutableState

//No funciona perquè no es guarda el valor de la variable counter
@Composable
fun MyStateExample_07_1() {
    var counter = 0
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { counter += 1 }) {
            Text(text = "Push")
        }
        Text(text = "$counter times clicked")
    }
}


//Ja funciona, però perdem el seu valor quan rotem el dispositiu
@Composable
fun MyStateExample_07_2() {
    var counter = remember { mutableStateOf(0) }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { counter.value += 1 }) {
            Text(text = "Push")
        }
        Text(text = "${counter.value} times clicked")
    }
}


//Amb rememberSaveable guardem el valor encara que girem el dispositiu
@Composable
fun MyStateExample_07_3() {
    var counter = rememberSaveable { mutableStateOf(0) }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { counter.value += 1 }) {
            Text(text = "Push")
        }
        Text(text = "${counter.value} times clicked")
    }
}


//Amb "remember by" simplifiquem l'accés al valor de les variables
// però cal importar (manualment) aquestes llibreries:
// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.setValue
@Composable
fun MyStateExample_07_4() {
    var counter by rememberSaveable { mutableStateOf(0) }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { counter += 1 }) {
            Text(text = "Push")
        }
        Text(text = "${counter} times clicked")
    }
}

// endregion presentacio_07

//region presentacio_08 TextField

//Funciona ok, es va guardant el contingut del text a mesura que es va escrivint
@Composable
fun MyStateExample_08_1() {
    var myText by remember { mutableStateOf("") }
    TextField(value = myText, onValueChange = { myText = it })
}

//Afegim un label
@Composable
fun MyStateExample_08_2() {
    var myText by remember { mutableStateOf("") }
    TextField(
        value = myText,
        onValueChange = { myText = it },
        label = { Text(text = "Enter your name") }
    )
}

//Limitem a números definint un tipus de teclat
//Si no es mostra, fer Alt+K (mostrar teclat), però abans verificar que tenim activat el Hardware Input (Ctrl+W)
@Composable
fun MyStateExample_08_3() {
    var myText by remember { mutableStateOf("") }
    TextField(
        value = myText,
        onValueChange = { myText = it },
        label = { Text(text = "Enter your phone number") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}


//Limitem a números fent servir una expresió regular
@Composable
fun MyStateExample_08_4() {
    val pattern = remember { Regex("^\\d+\$") }
    var myText by remember { mutableStateOf("") }
    TextField(
        value = myText,
        onValueChange = {
            if (it.isEmpty() || it.matches(pattern)) myText = it
        },
        label = { Text(text = "Enter your phone number") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

//Ens creem un nou objecte que sigui un camp de password
@Composable
fun MyStateExample_08_5() {
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Enter your password") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisibility) { Icons.Filled.VisibilityOff }
            else { Icons.Filled.Visibility }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = "Password visibility")
            }
        },
        visualTransformation = if (passwordVisibility) { VisualTransformation.None }
                               else { PasswordVisualTransformation() }
    )
}

//Outlined textfield, com un TextField, però amb una vora al voltant
@Composable
fun MyStateExample_08_6() {
    var myText by remember { mutableStateOf("") }
    OutlinedTextField(
        value = myText,
        onValueChange = { myText = it },
        label = { Text("Enter your name") }
    )

    Spacer(Modifier.padding(10.dp))

    val pattern = remember { Regex("^\\d+\$") }
    TextField(
        value = myText,
        onValueChange = {
            if (it.isEmpty() || it.matches(pattern)) myText = it
        },
        label = { Text(text = "Enter your name") }

    )

}


//Outlined textfield o TextField, amb colors depenent del focus
@Composable
fun MyStateExample_08_7() {
    var myText by remember { mutableStateOf("") }
    OutlinedTextField(
        value = myText,
        onValueChange = { myText = it },
        label = { Text("Enter your name") }
    )

    Spacer(Modifier.padding(10.dp))

    val pattern = remember { Regex("^\\d+\$") }
    TextField(
        value = myText,
        onValueChange = {
            if (it.isEmpty() || it.matches(pattern)) myText = it
        },
        label = { Text(text = "Enter your name") },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Green,
            unfocusedContainerColor = Color.Gray
        )

    )

}



//endregion presentacio_08

//region presentacio_09 Buttons

//Butó simple
@Composable
fun MyStateExample_09_1() {
    Button(onClick = {  }) {
        Text(text = "Push")
    }

}

//Butó customitzat
@Composable
fun MyStateExample_09_2() {

    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White
        ),
        border = BorderStroke(5.dp, Color.Green)
    ) {
        Text(text = "Push")
    }
}

//Butó habilitat / deshabilitat. Un botó que es desactiva a ell mateix
@Composable
fun MyStateExample_09_3() {
    var enabled by remember { mutableStateOf(true) }
    Button(
        onClick = { enabled = false},
        enabled = enabled
    ) {
        Text(text = "Push")
    }

}

//Butó habilitat / deshabilitat. Un botó que es desactiva a ell mateix
@Composable
fun MyStateExample_09_4() {

    Button(onClick = {  }) {
        Text(text = "Push Button")
    }

    Spacer(Modifier.padding(10.dp))

    OutlinedButton(onClick = {  }) {
        Text(text = "Push OutlinedButton")
    }

    Spacer(Modifier.padding(10.dp))

    TextButton(onClick = {  }){
        Text(text = "Push TextButton")
    }


}


//endregion presentacio_09


//region presentacio_10 ProgressBar

//Butó simple
@Composable
fun MyStateExample_10_1() {
    var progressStatus1 by rememberSaveable() { mutableStateOf(0f) }
    var progressStatus2 by rememberSaveable() { mutableStateOf(0f) }
    Column() {
        CircularProgressIndicator(
            progress = { progressStatus1 },
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
        )
        Row() {
            Button(onClick = { if (progressStatus1 > 0f) progressStatus1 -= 0.1f }) {
                Text(text = "Decrease")
            }
            Button(onClick = { if (progressStatus1 < 1f) progressStatus1 += 0.1f }) {
                Text(text = "Increase")
            }
        }
    }

    Spacer(Modifier.padding(10.dp))

    Column() {
        LinearProgressIndicator(progress = { progressStatus2 },color = Color.Cyan, trackColor = Color.Blue)

        Row() {
            Button(onClick = { if (progressStatus2 > 0f) progressStatus2 -= 0.1f }) {
                Text(text = "Decrease")
            }
            Button(onClick = { if (progressStatus2 < 1f) progressStatus2 += 0.1f }) {
                Text(text = "Increase")
            }
        }
    }

}

//endregion presentacio_10




@Composable
fun MyConstraintLayout(modifier: Modifier = Modifier) {
    Column( modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyStateExample_10_1()
    }
}