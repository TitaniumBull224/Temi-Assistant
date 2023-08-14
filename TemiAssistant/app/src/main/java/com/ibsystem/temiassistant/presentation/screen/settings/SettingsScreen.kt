@file:OptIn(ExperimentalMaterialApi::class)

package com.ibsystem.temiassistant.presentation.screen.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.ibsystem.temiassistant.R
import com.ibsystem.temiassistant.presentation.common.component.GifImage
import com.ibsystem.temiassistant.presentation.common.component.TextGradient
import com.ibsystem.temiassistant.presentation.common.component.TopBarSection
import com.ibsystem.temiassistant.ui.theme.Blue
import com.ibsystem.temiassistant.ui.theme.Chocolate
import com.ibsystem.temiassistant.ui.theme.DIMENS_16dp
import com.ibsystem.temiassistant.ui.theme.DIMENS_1dp
import com.ibsystem.temiassistant.ui.theme.DIMENS_24dp
import com.ibsystem.temiassistant.ui.theme.DIMENS_8dp
import com.ibsystem.temiassistant.ui.theme.Maroon
import com.ibsystem.temiassistant.ui.theme.Transparent
import com.ibsystem.temiassistant.ui.theme.White

@Composable
fun SettingsScreen(navController: NavController) {
    val viewModel = SettingsViewModel.getInstance()
    Scaffold(
        topBar = {
            TopBarSection(
                username = "Map",
                onBack = { navController.navigateUp() }
            )
        }
    ) {
        GifImage(
            modifier = Modifier.fillMaxSize(),
            gif = R.drawable.lantern
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(DIMENS_16dp)
        ) {
            SettingsSwitchComp(
                name = "Robot Speaker",
                icon = R.drawable.ic_final_icon,
                iconDesc = "SwitchComp",
                state = viewModel.isSpeakerOn.collectAsState()
            ) {
                viewModel.toggleSwitch(SettingsViewModel.SPEAKER_SWITCH)
            }

            SettingsSwitchComp(
                name = "Robot Detection",
                icon = R.drawable.ic_final_icon,
                iconDesc = "SwitchComp",
                state = viewModel.isDetectionOn.collectAsState()
            ) {
                viewModel.toggleSwitch(SettingsViewModel.DETECTION_SWITCH)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsSwitchComp(
    @DrawableRes icon: Int,
    iconDesc: String,
    name: String,
    state: State<Boolean>,
    onClick: () -> Unit
) {
    Surface(
        color = Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(DIMENS_16dp),
        onClick = onClick,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(id = icon),
                        contentDescription = iconDesc,
                        modifier = Modifier.size(DIMENS_24dp),
                        tint = White
                    )
                    Spacer(modifier = Modifier.width(DIMENS_8dp))
                    TextGradient(
                        text = name,
                        gradientColors = listOf(Maroon, Chocolate),
                        roundedCornerShape = RoundedCornerShape(DIMENS_1dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = state.value,
                    onCheckedChange = { onClick() }
                )
            }
            Divider()
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsClickableComp(
    @DrawableRes icon: Int,
    iconDesc: String,
    name: String,
    onClick: () -> Unit
) {
    Surface(
        color = Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(DIMENS_16dp),
        onClick = onClick,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(id = icon),
                        contentDescription = iconDesc,
                        modifier = Modifier
                            .size(DIMENS_24dp)
                    )
                    Spacer(modifier = Modifier.width(DIMENS_8dp))
                    Text(
                        text = name,
                        modifier = Modifier
                            .padding(DIMENS_16dp),
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Icon(
                    Icons.Rounded.KeyboardArrowRight,
                    contentDescription = "Forward Arrow"
                )
            }
            Divider()
        }

    }
}

@Composable
fun SettingsTextComp(
    @DrawableRes icon: Int,
    iconDesc: String,
    name: String,
    state: State<String>, // current value
    onSave: (String) -> Unit, // method to save the new value
    onCheck: (String) -> Boolean // check if new value is valid to save
) {

    // if the dialog is visible
    var isDialogShown by remember {
        mutableStateOf(false)
    }

    // conditional visibility in dependence to state
    if (isDialogShown) {
        Dialog(onDismissRequest = {
            // dismiss the dialog on touch outside
            isDialogShown = false
        }) {
            TextEditDialog(name, state, onSave, onCheck) {
                // to dismiss dialog from within
                isDialogShown = false
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DIMENS_16dp),
        onClick = {
            // clicking on the preference, will show the dialog
            isDialogShown = true
        },
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painterResource(id = icon),
                    contentDescription = iconDesc,
                    modifier = Modifier.size(DIMENS_24dp)
                )
                Spacer(modifier = Modifier.width(DIMENS_16dp))
                Column(modifier = Modifier.padding(DIMENS_8dp)) {
                    // setting text title
                    Text(
                        text = name,
                        textAlign = TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.height(DIMENS_8dp))
                    // current value shown
                    Text(
                        text = state.value,
                        textAlign = TextAlign.Start,
                    )
                }
            }
            Divider()
        }
    }
}

@Composable
private fun TextEditDialog(
    name: String,
    storedValue: State<String>,
    onSave: (String) -> Unit,
    onCheck: (String) -> Boolean,
    onDismiss: () -> Unit // internal method to dismiss dialog from within
) {

    // storage for new input
    var currentInput by remember {
        mutableStateOf(TextFieldValue(storedValue.value))
    }

    // if the input is valid - run the method for current value
    var isValid by remember {
        mutableStateOf(onCheck(storedValue.value))
    }

    Surface(
        color = Blue
    ) {

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(DIMENS_8dp)
        ) {
            Text(name)
            Spacer(modifier = Modifier.height(DIMENS_8dp))
            TextField(currentInput, onValueChange = {
                // check on change, if the value is valid
                isValid = onCheck(it.text)
                currentInput = it
            })
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    // save and dismiss the dialog
                    onSave(currentInput.text)
                    onDismiss()
                    // disable / enable the button
                }, enabled = isValid) {
                    Text("Next")
                }
            }
        }
    }
}