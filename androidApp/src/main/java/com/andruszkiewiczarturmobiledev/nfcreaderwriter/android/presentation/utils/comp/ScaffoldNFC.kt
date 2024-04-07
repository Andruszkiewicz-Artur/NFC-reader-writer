package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.Type
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.comp.BasicWriteRow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldNFC(
    showFloatingButton: Boolean,
    onClickAddButton: () -> Unit,
    textFloatingButton: String,
    iconFloatingButton: ImageVector,
    onClickFloatingButton: () -> Unit,
    textFieldValue: String,
    textFieldPlaceholder: String,
    textFieldChangeValue: (String) -> Unit,
    messages: List<Pair<String, String>>,
    rowView: @Composable (Pair<String, String>) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AnimatedVisibility(visible = showFloatingButton) {
                ExtendedFloatingActionButton(
                    onClick = { onClickFloatingButton() },
                    icon = {
                        Icon(
                            imageVector = iconFloatingButton,
                            contentDescription = null
                        )
                    },
                    text = {
                        Text(text = textFloatingButton)
                    },
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding()
                )
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                item {
                    TextFieldNFCCard(
                        value = textFieldValue,
                        placeHolder = textFieldPlaceholder,
                        enteredValue = { textFieldChangeValue(it) },
                        onClickPlus = { onClickAddButton() }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(messages) { message ->
                    rowView(message)
                }
            }
        }
    }
}