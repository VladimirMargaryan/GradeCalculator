package com.mobiledev.gradecalculator

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mobiledev.gradecalculator.enums.Field

data class State(
    val participation: String = "100",
    val homeworks: SnapshotStateList<String> = SnapshotStateList<String>().apply { addAll(List(Field.HOMEWORK.maxCount) { "100" }) },
    val presentation: String = "100",
    val midterm1: String = "100",
    val midterm2: String = "100",
    val finalProject: String = "100",
    val finalGrade: String = ""
)