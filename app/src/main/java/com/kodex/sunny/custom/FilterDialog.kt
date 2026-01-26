package com.kodex.sunny.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kodex.spark.ui.custom.PricePicker2
import com.kodex.sunny.R
import com.kodex.sunny.mainScreen.MainScreenViewModel

@Preview(showBackground = true)
@Composable
fun FilterDialog(
    showDialog: Boolean = true,
    onDismiss: ()-> Unit  = {},
    onConfirm: ()-> Unit = {},
    title: String = "Order by",
    confirmButtonText: String = "Да",
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val filterByPrice = remember {mutableStateOf(false) }


    if (showDialog){
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            confirmButton ={
                Button(onClick = {
                    onConfirm()
                    viewModel.setFilter()
                }) {
                    Text(text = confirmButtonText)
                }
                Button(onClick = {
                    onDismiss()
                }) {
                    Text(text = "Cansel")
                }
            },
            title= {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 20.sp
                )
            },
            text = {
                val orderBySelection = stringArrayResource(R.array.order_by)[1]
                Column (modifier = Modifier.fillMaxWidth()){
                    RadioButtonSet(){option->
                       filterByPrice.value = option == orderBySelection
                    }


                    if (filterByPrice.value) {
                        Spacer(Modifier.height(5.dp))
                        Text(
                            text = "Price range",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(Modifier.height(5.dp))
                        PricePicker2(
                            priceValue = viewModel.minPriceValue.floatValue,
                           title = "Min:",
                            onValueChange = {value->
                                viewModel.minPriceValue.floatValue = value
                            }
                        )

                        Spacer(Modifier.height(5.dp))
                        PricePicker2(
                            priceValue = viewModel.maxPriceValue.floatValue,
                            title = "Max:",
                            onValueChange = {value->
                                viewModel.maxPriceValue.floatValue = value
                            }
                        )
                    }
                }
            }
        )
    }
}