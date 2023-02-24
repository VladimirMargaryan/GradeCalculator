package com.mobiledev.gradecalculator.enums

enum class Field(val percent: Double, val maxCount: Int) {

    HOMEWORK(0.2, 5),
    PARTICIPATION(0.1, 1),
    PRESENTATION(0.1, 1),
    MIDTERM_1(0.1, 1),
    MIDTERM_2(0.2, 1),
    FINAL_PROJECT(0.3, 1);
}