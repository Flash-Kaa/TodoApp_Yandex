package com.flasshka.todoapp

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.flasshka.todoapp.ui.listitems.ListUI
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListUITest {
    @Rule
    @JvmField
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Before
    fun setContent() {
        composeTestRule.setContent {
        }
    }

    @Test
    fun `nothing`() {

    }
}