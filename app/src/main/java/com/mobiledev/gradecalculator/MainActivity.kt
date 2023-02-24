package com.mobiledev.gradecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobiledev.gradecalculator.composables.Button
import com.mobiledev.gradecalculator.composables.TextField
import com.mobiledev.gradecalculator.enums.Field
import com.mobiledev.gradecalculator.ui.theme.GradeCalculatorTheme
import com.mobiledev.gradecalculator.ui.theme.Purple

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GradeCalculatorTheme {
                val viewModel = viewModel<CalculatorVM>()
                val valuesState = viewModel.state

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {

                    TextField(
                        input = valuesState.midterm1,
                        label = "Midterm 1",
                        isValid = viewModel.isValid(valuesState.midterm1),
                        onValueChange = {
                            viewModel.onAction(
                                Action.Input(
                                    it,
                                    Field.MIDTERM_1
                                )
                            )
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    TextField(
                        input = valuesState.midterm2,
                        label = "Midterm 2",
                        isValid = viewModel.isValid(valuesState.midterm2),
                        onValueChange = {
                            viewModel.onAction(
                                Action.Input(
                                    it,
                                    Field.MIDTERM_2
                                )
                            )
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    TextField(
                        input = valuesState.participation,
                        label = "Participation",
                        isValid = viewModel.isValid(valuesState.participation),
                        onValueChange = {
                            viewModel.onAction(
                                Action.Input(
                                    it,
                                    Field.PARTICIPATION
                                )
                            )
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    TextField(
                        input = valuesState.presentation,
                        label = "Group Presentation",
                        isValid = viewModel.isValid(valuesState.presentation),
                        onValueChange = {
                            viewModel.onAction(
                                Action.Input(
                                    it,
                                    Field.PRESENTATION
                                )
                            )
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    TextField(
                        input = valuesState.finalProject,
                        label = "Final Project",
                        isValid = viewModel.isValid(valuesState.finalProject),
                        onValueChange = {
                            viewModel.onAction(
                                Action.Input(
                                    it,
                                    Field.FINAL_PROJECT
                                )
                            )
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    var itemCount by remember { mutableStateOf(0) }

                    repeat(itemCount) { index ->
                        val isValid = viewModel.isValid(viewModel.state.homeworks[index])

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {

                            TextField(
                                input = viewModel.state.homeworks[index],
                                label = "Homework ${index + 1}",
                                isValid = isValid,
                                onValueChange = {
                                    viewModel.onAction(
                                        Action.InputWithIndex(
                                            it,
                                            Field.HOMEWORK,
                                            index
                                        )
                                    )
                                },
                                trailingIcon = {
                                    IconButton(
                                        modifier = Modifier.weight(1f, fill = false),
                                        onClick = {
                                            itemCount--
                                            viewModel.onAction(
                                                Action.InputWithIndex(
                                                    "100",
                                                    Field.HOMEWORK,
                                                    index
                                                )
                                            )
                                        },
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Delete,
                                            tint = Purple,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            )
                        }
                    }

                    Button(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .height(40.dp)
                            .width(150.dp)
                            .padding(start = 0.dp)
                            .align(Alignment.End),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        text = "Add Homework",
                        enabled = itemCount in 0..4
                    ) {
                        itemCount = when (itemCount) {
                            Field.HOMEWORK.maxCount -> itemCount
                            else -> itemCount + 1
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(60.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                text = "Final Grade: ${viewModel.state.finalGrade}"
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15.dp))
                                    .align(Alignment.BottomCenter)
                                    .width(200.dp)
                                    .height(50.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                text = "Calculate",
                                onClick = { viewModel.onAction(Action.Calculate) },
                                enabled = viewModel.isValidState()
                            )
                        }
                    }
                }
            }
        }
    }
}