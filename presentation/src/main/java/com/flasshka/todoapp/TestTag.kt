package com.flasshka.todoapp

/**
 * Tags for Modifier.testTag(value)
 */
sealed class TestTag(val value: String) {
    data object Checkbox : TestTag("checkbox_testTag")
    data object ListItem : TestTag("listItem_testTag")
}