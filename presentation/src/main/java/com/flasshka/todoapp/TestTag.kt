package com.flasshka.todoapp

sealed class TestTag(val value: String) {
    data object Checkbox : TestTag("checkbox_testTag")
    data object ListItem : TestTag("listItem_testTag")
}