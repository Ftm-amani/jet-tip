package com.example.composejettip.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
	valueState: MutableState<String>,
	labelId: String,
	isEnabled: Boolean,
	isSingleLine: Boolean,
	keyboardType: KeyboardType = KeyboardType.Number,
	imeAction: ImeAction = ImeAction.Next,
	onAction: KeyboardActions = KeyboardActions.Default
) {
	OutlinedTextField(
		value = valueState.value,
		onValueChange = { valueState.value = it },
		label = { Text(text = labelId) },
		leadingIcon = { Icon(imageVector = Icons.Rounded.Done, contentDescription = "MoneyIcon")},
		modifier = Modifier
			.padding(bottom = 10.dp, start= 10.dp,end= 10.dp)
			.fillMaxWidth(),
		singleLine= isSingleLine,
		enabled = isEnabled,
		keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
		keyboardActions = onAction
		)
}