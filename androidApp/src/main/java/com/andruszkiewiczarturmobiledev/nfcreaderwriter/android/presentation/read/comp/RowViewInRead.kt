package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.read.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowViewInRead(
    imageVector: ImageVector? = null,
    letter: Char? = null,
    title: String,
    content: String?,
    isLast: Boolean = false
) {
    if (content != null) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (imageVector != null) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                    )
                } else if (letter != null) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.onBackground,
                                shape = CircleShape
                            )
                            .size(50.dp)
                    ) {
                        Text(
                            text = letter.toString(),
                            fontSize = 36.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = content)
                }
            }

            if (!isLast) HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            else Spacer(modifier = Modifier.height(16.dp))
        }
    }
}