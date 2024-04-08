package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BasicEmulateRow(
    isChosen: Boolean,
    type: String,
    message: String,
    isLast: Boolean,
    onClickRemove: () -> Unit,
    onClickRow: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animateBorderSize by animateDpAsState(
        targetValue = if(isChosen) 2.dp else 1.dp,
        label = "animate border size",
        animationSpec = tween(500)
    )

    val animatePadding by animateDpAsState(
        targetValue = if(isChosen) 18.dp else 6.dp,
        label = "animate padding",
        animationSpec = tween(500)
    )

    val animateElevation by animateDpAsState(
        targetValue = if (isChosen) 10.dp else 0.dp,
        label = "animate elevation",
        animationSpec = tween(500)
    )

    OutlinedCard(
        border = BorderStroke(
            width = animateBorderSize,
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
            .padding(vertical = animatePadding)
            .shadow(
                elevation = animateElevation
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClickRow()
                }
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = type,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = message
                )
            }

            IconButton(onClick = { onClickRemove() }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 200,
    heightDp = 500
)
@Composable
fun BasicEmulationRowPreview() {
    Column {
        BasicEmulateRow(
            isChosen = false,
            type = "type",
            message = "message",
            isLast = false,
            onClickRemove = { },
            onClickRow = { }
        )

        BasicEmulateRow(
            isChosen = true,
            type = "type2",
            message = "message2",
            isLast = true,
            onClickRemove = { },
            onClickRow = { }
        )
    }
}