package com.mobiledev.gradecalculator

import com.mobiledev.gradecalculator.enums.Field

sealed class Action {
    data class Input(val input: String, val field: Field) : Action()
    data class InputWithIndex(val input: String, val field: Field, val index: Int) : Action()
    object Calculate : Action()
}