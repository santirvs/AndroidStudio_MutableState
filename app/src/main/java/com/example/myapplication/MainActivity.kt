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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TriStateCheckbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.MyApplicationTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

//Exemples de progress Bar linear i circular
@Composable
fun MyStateExample_10_1() {
    var progressStatus1 by rememberSaveable() { mutableStateOf(0f) }
    var progressStatus2 by rememberSaveable() { mutableStateOf(0f) }
    Column() {
        CircularProgressIndicator(
            progress = { progressStatus1 },
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor
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

//region presentacio_11 Switch, CheckBox i RadioButton

//Switch
@Composable
fun MyStateExample_11_1() {
    var state1 by rememberSaveable { mutableStateOf(true) }
    var state2 by rememberSaveable { mutableStateOf(true) }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Switch(checked = state1, onCheckedChange = {state1 = !state1})

        Spacer(Modifier.padding(10.dp))

        //Amb colors personalitzats
        Switch(
            checked = state2,
            onCheckedChange = { state2 = !state2 },
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.Red,
                checkedThumbColor = Color.Green,
                checkedTrackColor = Color.Yellow,
                uncheckedTrackColor = Color.Magenta
            )
        )

        Spacer(Modifier.padding(10.dp))


    }
}

//CheckBox
@Composable
fun MyStateExample_11_2() {
    var state1 by rememberSaveable { mutableStateOf(true) }
    var state2 by rememberSaveable { mutableStateOf(true) }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Checkbox simple (no funciona perquè no gestiona el onCheckedChange)
        Checkbox( checked = true, onCheckedChange = {})

        Spacer(Modifier.padding(10.dp))

        //Amb colors personalitzats
        Checkbox(
            checked = state1, onCheckedChange = { state1 = it },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Cyan,
                uncheckedColor = Color.Red,
                checkmarkColor = Color.Blue
            )
        )

        Spacer(Modifier.padding(10.dp))

        //Acompanyat d'un text
        Row(Modifier.wrapContentSize()) {
            Checkbox(checked = state2, onCheckedChange = { state2 = it} )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Option is checked? " + state2, Modifier.align(CenterVertically))
        }

        Spacer(Modifier.padding(10.dp))

        // TriState
        var status by rememberSaveable { mutableStateOf(ToggleableState.Off) }
        TriStateCheckbox(state = status, onClick = {
            status = when(status){
                ToggleableState.On -> ToggleableState.Off
                ToggleableState.Off -> ToggleableState.Indeterminate
                ToggleableState.Indeterminate -> ToggleableState.On
            }
        })

    }
}

//RadioButton
@Composable
fun MyStateExample_11_3() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Radio Button
        var selectedOption by remember { mutableStateOf("Option 1") }
        Row(modifier = Modifier.fillMaxWidth()){
            RadioButton(selected = selectedOption == "Option 1",
                onClick = { selectedOption = "Option 1" },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Green,
                    unselectedColor = Color.Red
                ))
            Text(text = "Option 1", Modifier.align(CenterVertically))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            RadioButton(selected = selectedOption == "Option 2",
                onClick = { selectedOption = "Option 2" })
            Text(text = "Option 2", Modifier.align(CenterVertically))
        }


    }
}


//endregion presentacio_11

//region presentacio_12 State Hoisting

//TextField amb state Hoisting
@Composable
fun MyTextField(name: String, onValueChange:(String) -> Unit) {
    TextField(value = name, onValueChange = {onValueChange(it)})
}

@Composable
fun MyStateExample_12_1() {
    var myText by remember { mutableStateOf("") }
    MyTextField(myText) { myText = it }
}
//***********************************
//CheckBox amb objecte CheckInfo
//***********************************
data class CheckInfo(
    val title: String,
    var checked: Boolean = false,
    var onCheckedChange: (Boolean) -> Unit
)

@Composable
fun MyCheckBox(checkInfo: CheckInfo) {
    Row(Modifier.wrapContentSize()) {
        Checkbox(
            checked = checkInfo.checked,
            onCheckedChange = { checkInfo.onCheckedChange(!checkInfo.checked) })
        Spacer(modifier = Modifier.width(8.dp))
        Text(checkInfo.title, Modifier.align(CenterVertically))
    }
}

@Composable
fun MyStateExample_12_2() {
    var status by rememberSaveable { mutableStateOf(false) }
    val checkInfo = CheckInfo("Example 1", status, { status = it })
    MyCheckBox(checkInfo)
}

//***********************************
// Llista de radioButtons
//***********************************
@Composable
fun MyRadioButtonList(options: List<String>, name: String, OnItemSelected: (String) -> Unit) {
    options.forEach {
        Row(Modifier.wrapContentSize()) {
            RadioButton(selected = name == it, onClick = { OnItemSelected(it) })
            Spacer(modifier = Modifier.width(8.dp))
            Text(it, Modifier.align(CenterVertically))
        }
    }
}

@Composable
fun MyStateExample_12_3() {
    val myOptions = listOf("Option 1", "Option 2", "Option 3")
    var selected by rememberSaveable {
        mutableStateOf(myOptions[0])
    }
    Column {
        MyRadioButtonList(myOptions, selected, {selected = it})
    }

}

//endregion presentacio_12

//region presentacio_13 Card
@Composable
fun MyStateExample_13_1() {

    Card(
        Modifier.fillMaxWidth().padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = "Title", fontSize = 24.sp, fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
            Text(text = "Subtitle", fontSize = 20.sp, fontStyle = FontStyle.Italic)
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                maxLines = 3
            )
        }
    }

    Spacer(Modifier.padding(10.dp))

    // La mateixa Card, però amb atributs modificats
    Card(
        Modifier.fillMaxWidth().padding(16.dp), shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = Color.Cyan,
            contentColor = Color.Red),
        border = BorderStroke(5.dp, Color.Yellow),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = "Title", fontSize = 24.sp, fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
            Text(text = "Subtitle", fontSize = 20.sp, fontStyle = FontStyle.Italic)
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                maxLines = 3
            )
        }
    }

}



//endregion presentacio_13



@Composable
fun MyConstraintLayout(modifier: Modifier = Modifier) {
    Column( modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyStateExample_13_1()
    }
}