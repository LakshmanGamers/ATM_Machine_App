package com.example.atmmachine

import android.os.Bundle
import android.view.Display
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.atmmachine.ui.theme.ATMMachineTheme
import com.example.atmmachine.ATM
import com.example.atmmachine.ATMException
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ATMMachineTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
mainscreen()
                }
            }
        }
    }
}

@Composable
fun mainscreen(){

    Scaffold(topBar = { TopAppBar(title = {Text("ATM Machine",color = Color.White)},backgroundColor = Color(0xFF1976D2)) },
        content = {  Screen()
        })



}

@Composable
fun Screen() {

    var mexpanded by remember { mutableStateOf(false) }
    var op = listOf("Withdraw","Deposit","Display Balance")
    var menutext by remember { mutableStateOf("") }
    var fieldsize by remember { mutableStateOf(Size.Zero) }
    var option by remember {
        mutableStateOf("")
    }
    val icon = if(mexpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(modifier = Modifier.fillMaxSize() .wrapContentSize(Alignment.Center)) {

        Column(Modifier.padding(20.dp)) {
            OutlinedTextField(value = menutext, onValueChange = { menutext = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->

                        fieldsize = coordinates.size.toSize()
                    },
                label = { Text("Select Operation") },
                trailingIcon = {
                    Icon(icon, "Contentdesc",
                        Modifier.clickable { mexpanded = !mexpanded }
                    )
                }

            )
            DropdownMenu(
                expanded = mexpanded, onDismissRequest = { mexpanded = false },
                modifier = Modifier.width(with(LocalDensity.current) { fieldsize.width.toDp() })
            ) {
                op.forEach { label ->
                    DropdownMenuItem(onClick = {
                        menutext = label
                        mexpanded = false
                        option = label
                    }) {
                        Text(text = label)

                    }
                }
            }
        }



        var result by  remember { mutableStateOf("") }
        var input by remember { mutableStateOf("") }
        if(option!="Display Balance") {

            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.padding(start = 20.dp),
                label = { Text("Enter Amount") })

        }
            Row(
                modifier = Modifier.padding(end = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {

                    var atm = ATM()
                    try {
                        result = when (option) {
                            "Withdraw" ->    atm.withdraw(input.toDouble())
                            "Deposit" -> atm.deposit(input.toDouble())
                            "Display Balance" -> atm.printBalance()
                            else -> "Invalid Choice"
                        }
                    } catch (e: ATMException) {
                        result = "Invalid Amount , Please Check Again"
                    }
                }
                ) {
                    Text(text = "Action Button")
                }
            }



        Text(result , modifier = Modifier.padding(start=20.dp) , fontWeight = FontWeight.Bold )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ATMMachineTheme {
      Screen()
    }
}