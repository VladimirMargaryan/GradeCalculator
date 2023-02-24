package com.mobiledev.gradecalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mobiledev.gradecalculator.enums.Field
import java.math.RoundingMode


class CalculatorVM : ViewModel() {

    var state by mutableStateOf(State())

    fun onAction(action: Action) {
        when (action) {
            is Action.Input -> input(action.input, action.field)
            is Action.InputWithIndex -> input(action.input, action.field, action.index)
            is Action.Calculate -> calculate()
        }
    }

    fun isValid(text: String): Boolean {
        val value = text.toIntOrNull() ?: return false
        return value in 0..100
    }

    fun isValidState(): Boolean {
        val isValidHws = state.homeworks
            .toList().any { isValid(it) }

        val isValidMidterm1 = isValid(state.midterm1)
        val isValidMidterm2 = isValid(state.midterm2)
        val isValidParticipation = isValid(state.participation)
        val isValidPresentation = isValid(state.presentation)
        val isValidFinalProject = isValid(state.finalProject)

        return isValidHws
                && isValidMidterm1
                && isValidMidterm2
                && isValidParticipation
                && isValidPresentation
                && isValidFinalProject
    }

    private fun input(input: String, field: Field, index: Int = -1) {
        when (field) {
            Field.HOMEWORK -> {
                if (index in 0 until state.homeworks.size) {
                    val homeworks = state.homeworks
                    homeworks[index] = input
                    state = state.copy(homeworks = homeworks)
                }
            }
            Field.PARTICIPATION -> state = state.copy(participation = input)
            Field.PRESENTATION -> state = state.copy(presentation = input)
            Field.MIDTERM_1 -> state = state.copy(midterm1 = input)
            Field.MIDTERM_2 -> state = state.copy(midterm2 = input)
            Field.FINAL_PROJECT -> state = state.copy(finalProject = input)
        }
    }

    private fun calculate() {
        val homeworks = state.homeworks
            .toList()
            .map { it.toInt() }
            .average().times(Field.HOMEWORK.percent)

        val midterm1 = state.midterm1.toInt().times(Field.MIDTERM_1.percent)
        val midterm2 = state.midterm2.toInt().times(Field.MIDTERM_2.percent)
        val participation = state.participation.toInt().times(Field.PARTICIPATION.percent)
        val presentation = state.presentation.toInt().times(Field.PRESENTATION.percent)
        val finalProject = state.finalProject.toInt().times(Field.FINAL_PROJECT.percent)

        val finalGrade =
            homeworks + midterm1 + midterm2 + participation + presentation + finalProject
        state = state.copy(
            finalGrade = finalGrade.toBigDecimal().setScale(1, RoundingMode.UP).toString()
        )
    }
}