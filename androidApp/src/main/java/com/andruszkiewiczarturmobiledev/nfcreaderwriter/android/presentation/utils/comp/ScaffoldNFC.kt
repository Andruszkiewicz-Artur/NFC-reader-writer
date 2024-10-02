package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Cached
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TagValue
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TypeData
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.MessageTypeView
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils.Static

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldNFC(
    showFloatingButton: Boolean,
    onClickAddButton: (String) -> Unit,
    textFloatingButton: String,
    iconFloatingButton: ImageVector,
    onClickFloatingButton: () -> Unit,
    typeData: TypeData,
    messages: List<TagValue>,
    onChangeTypeValue: (TypeData) -> Unit,
    rowView: @Composable (TagValue) -> Unit
) {
    var isPresent by remember { mutableStateOf(false) }
    var isPresentedTextFields by remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AnimatedVisibility(visible = showFloatingButton) {
                ExtendedFloatingActionButton(
                    shape = RoundedCornerShape(16.dp),
                    onClick = { onClickFloatingButton() },
                    icon = {
                        Icon(
                            imageVector = iconFloatingButton,
                            contentDescription = null
                        )
                    },
                    text = {
                        Text(text = textFloatingButton)
                    }
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
//                    Row(
//                        horizontalArrangement = Arrangement.End,
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    ) {
//                        Row (
//                            modifier = Modifier
//                                .clickable {
//                                    isPresent = true
//                                }
//                        ) {
//                            Text(
//                                text = stringResource(id = Static.listOfTypes.get(key = typeData)?.name ?: 0),
//                                fontWeight = FontWeight.Bold
//                            )
//
//                            Spacer(modifier = Modifier.width(8.dp))
//
//                            Icon(
//                                imageVector = Icons.Rounded.Cached,
//                                contentDescription = null
//                            )
//                        }
//
//                        IconButton(onClick = { isPresentedTextFields = !isPresentedTextFields }) {
//                            Icon(
//                                imageVector = if (isPresentedTextFields) Icons.Rounded.ArrowUpward else Icons.Rounded.ArrowDownward,
//                                contentDescription = null
//                            )
//                        }
//                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedVisibility(visible = isPresentedTextFields) {
                        MessageTypeView(
                            typeData = typeData,
                            onClickAddButton = { onClickAddButton(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(messages) { message ->
                    rowView(message)
                }

                item {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }

    DialogChooseType(
        isPresent = isPresent,
        changeValue = { tagValue ->
            onChangeTypeValue(tagValue)
        },
        onClickDismiss = { isPresent = false }
    )
}
