package com.example.composejettip

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composejettip.components.InputField
import com.example.composejettip.ui.theme.ComposeJetTipTheme
import com.example.composejettip.widgets.RoundIconBtn

@ExperimentalComposeUiApi
class MainActivity: ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MyApp {
				Column(
					modifier = Modifier.height(300.dp),
					verticalArrangement = Arrangement.Top,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Spacer(modifier = Modifier.height(5.dp))
					MainContent()
				}
			}
		}
	}
}

@Composable
fun MyApp(content : @Composable () -> Unit){
	ComposeJetTipTheme {
		Surface(
			modifier= Modifier
				.fillMaxHeight()
				.fillMaxWidth(),
			color = MaterialTheme.colors.background
		) {
			content()
		}
	}
}

@Preview
@Composable
fun TopHeader(totalPerson: Double = 152.0) {
	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.padding(15.dp)
			.height(150.dp)
			.clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
		color = Color(0xFFE9D7F7)
	) {
		Column(
			modifier = Modifier.padding(12.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center) {
			val total = "%.2f".format(totalPerson)
			Text(text = "Total per Person",
				 style = MaterialTheme.typography.h5)
			Text(text = "$$total",
				 style = MaterialTheme.typography.h4,
				 fontWeight = FontWeight.ExtraBold)
		}
	}
	
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun MainContent() {
	Column(modifier = Modifier.padding(all = 12.dp)) {
		BillForm(){ billAmount ->
			//catch data
		}
	}
}

@ExperimentalComposeUiApi
@Composable
fun BillForm(
	modifier: Modifier = Modifier,
	onValChange: (String) -> Unit = {}){
	val totalBillState = remember {
		mutableStateOf("")
	}
	val validState = remember(totalBillState.value) {
		totalBillState.value.trim().isNotEmpty()
	}
	val keyboardController = LocalSoftwareKeyboardController.current
	
	val sliderPositionState = remember {
		mutableStateOf(0f)
	}
	
	val tipPercentage = (sliderPositionState.value * 100).toInt()
	
	val splitByState = remember {
		mutableStateOf(1)
	}
	
	val splitRange = IntRange(1 , 100)
	
	TopHeader()
	
	Surface(modifier = modifier
		.padding(2.dp)
		.fillMaxWidth(),
		shape = RoundedCornerShape(corner = CornerSize(8.dp)),
		border = BorderStroke(width = 1.dp, color = Color.LightGray),) {
		Column(
			modifier = Modifier
				.padding(6.dp),
			verticalArrangement = Arrangement.Top,
			horizontalAlignment = Alignment.Start) {
			
			InputField(
				valueState = totalBillState,
				labelId = "Enter Bill",
				isEnabled = true,
				isSingleLine = true,
				onAction = KeyboardActions{
					if (!validState) return@KeyboardActions
					//onValueChanged
					onValChange(totalBillState.value.trim())
					keyboardController?.hide()
				})
			
//			if (validState) {
			//add & remove Row
				Row(
					modifier = Modifier.padding(3.dp),
					horizontalArrangement = Arrangement.Start
				) {
					Text("Split", modifier = Modifier.align(alignment = Alignment.CenterVertically))
					Spacer(modifier = Modifier.width(120.dp))
					Row(modifier = Modifier.padding(horizontal = 3.dp),
						horizontalArrangement = Arrangement.End) {
						RoundIconBtn(
							imageVector = Icons.Default.Minimize,
							onClick = {
								if(splitByState.value > 1) {
									splitByState.value -= 1
								} else {
									splitByState.value = 1
								}
							})
						Text(text = splitByState.value.toString(),
							 modifier = Modifier
								 .align(Alignment.CenterVertically)
								 .padding(start = 10.dp, end = 10.dp))
						RoundIconBtn(
							imageVector = Icons.Default.Add,
							onClick = {
								if(splitByState.value < splitRange.last) {
									splitByState.value += 1
								}
							})
					}
				}
//			}
//			else {
//				Box() {}
//			}
			
			//tip row
			Row(modifier = Modifier.padding(vertical = 3.dp, horizontal = 12.dp)) {
				Text(text = "Tip",
					 modifier = Modifier.align(alignment = Alignment.CenterVertically),
				)
				Spacer(modifier = Modifier.width(200.dp))
				Text(text = "$35.2",
					 modifier = Modifier.align(alignment = Alignment.CenterVertically),
				)
			}
			Column(
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(text = "$tipPercentage %")
				
				Spacer(modifier = Modifier.height(14.dp))
				
				//slider
				Slider(
					value = sliderPositionState.value,
					onValueChange = { newVal ->
						sliderPositionState.value = newVal
						Log.i("what_ ", "aaaaaBillForm: $newVal")
					},
					modifier = Modifier.padding(start = 16.dp, end = 16.dp),
					steps = 5
				)
				
			}
		}
	}
	
}