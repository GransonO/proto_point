package com.granson.protopoint.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.granson.protopoint.interfaces.ResponseListener
import com.granson.protopoint.models.ProtoData
import com.granson.protopoint.ui.theme.*
import com.granson.protopoint.utils.Utils
import com.granson.protopoint.viewModels.ProtoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val protoViewModel: ProtoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Use view box to display UI
            ProtoPointTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color.White) {
                    Column{
                        TopAppBar(
                            title = {
                                Text(text = "Running Jobs")
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = { /*TODO*/ },
                                ) {
                                    Icon(
                                        Icons.Filled.Home,
                                        contentDescription = "The Home Page",
                                        tint = Color.White
                                    )
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = {
                                              protoViewModel.toggleMenu()
                                              },
                                ) {
                                    var expanded = protoViewModel.menuExpanded.value
                                    var suggestions = protoViewModel.suggestions.value
                                    Icon(
                                        Icons.Filled.List,
                                        contentDescription = "Filter entries",
                                        tint = Color.White
                                    )
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        suggestions.forEach { label ->
                                            DropdownMenuItem(onClick = {
                                                protoViewModel.filterData(label)
                                            }) {
                                                Text(text = label)
                                            }
                                        }
                                    }
                                }
                            },
                            elevation = 5.dp
                        )
                        Column(
                            modifier = Modifier.verticalScroll(rememberScrollState(), enabled = true)
                        ) {
                            var loading = protoViewModel.isLoading.value
                            if (loading){
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(modifier = Modifier.height(20.dp))
                                    CircularProgressIndicator(
                                        color = Teal700,
                                    )
                                }
                            }
                            var list = protoViewModel.protoDataList.value
                            list.forEach {
                                ItemCard(it)
                            }
                        }

                    }

                }
            }
        }

    }

}

@Composable
fun ItemCard(data: ProtoData) {
    var context = LocalContext.current
    var isClicked = false
    Column {
        Spacer(modifier = Modifier.height(5.dp))
        Card(
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    isClicked = !isClicked
                },
            shape = RoundedCornerShape(20),
            elevation = 5.dp,

        ) {

            Row(
                modifier = Modifier
                    .background(color = GrayBack)
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                    Column() {
                        Row() {
                            Icon(
                                Icons.Filled.Place,
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(15.dp),
                                contentDescription = "deliveryPointName",
                                tint = Teal700
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = data.deliveryPointName,
                                style = TextStyle(
                                    fontSize = 13.sp,
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        Row() {
                            Icon(
                                Icons.Filled.AccountCircle,
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(15.dp),
                                contentDescription = "Filter entries",
                                tint = Teal700
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = data.customerName,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        Row() {
                            Icon(
                                Icons.Filled.Star,
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(15.dp),
                                contentDescription = "Filter entries",
                                tint = Teal700
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = data.status,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = getColor(data.status)
                                )
                            )
                        }
                    }

                    Column() {
                        Row {
                            Icon(
                                Icons.Filled.DateRange,
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(15.dp),
                                contentDescription = "Date",
                                tint = Teal700
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = Utils().showDate(data.dateCreated),
                                style = TextStyle(
                                    fontSize = 12.sp,
                                )
                            )
                        }
                    }
                }

        }
    }
}

fun getColor(status: String): Color{
    return when(status){
        "pending" -> MyGreen
        "canceled" -> Color.Red
        else -> Color.Black
    }
}
