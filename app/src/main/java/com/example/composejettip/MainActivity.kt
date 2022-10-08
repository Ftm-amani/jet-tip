package com.example.composejettip

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
					TopHeader()
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
			.padding(10.dp)
			.fillMaxWidth()
			.height(150.dp)
			.clip(shape = RoundedCornerShape(corner = CornerSize(10.dp))),
		color = Color(0xFFE9D7F7)
	) {
		Column(
			modifier = Modifier
				.padding(20.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			val total = "%.2f".format(totalPerson)
			Text(text = "Total per Person",
				 style = MaterialTheme.typography.h5
			)
			Text(text = "$$total",
				 style = MaterialTheme.typography.h4,
				 fontWeight = FontWeight.ExtraBold
			)
		}
	}
	
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun MainContent() {
	BillForm(){ billAmount ->
		//catch data
	}
}

@ExperimentalComposeUiApi
@Composable
fun BillForm(modifier: Modifier = Modifier,
			 onValChange: (String) -> Unit = {}){
	val totalBillState = remember {
		mutableStateOf("")
	}
	val validState = remember(totalBillState.value) {
		totalBillState.value.trim().isNotEmpty()
	}
	val keyboardController = LocalSoftwareKeyboardController.current
	
	Surface(
		modifier = modifier
			.padding(10.dp)
			.fillMaxWidth(),
		shape = RoundedCornerShape(corner = CornerSize(10.dp)),
		color = Color.White,
		border = BorderStroke(width = 1.dp, color = Color.LightGray)
	) {
		Column(
			modifier = Modifier
				.padding(12.dp),
			verticalArrangement = Arrangement.Top,
			horizontalAlignment = Alignment.Start
		) {
			InputField(
				modifier = Modifier,
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
			
			if (validState) {
				Row(
					modifier = Modifier.padding(3.dp),
					horizontalArrangement = Arrangement.Start
				) {
					Text("split", modifier = Modifier.align(alignment = Alignment.CenterVertically))
					Spacer(modifier = Modifier.width(120.dp))
				}
			}
			else {
				Box() {}
			}
		}
	}
	
}