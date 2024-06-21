package com.flasshka.todoapp

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.todoapp.ui.listitems.ListUI
import com.flasshka.todoapp.utils.ActionsForTest
import com.flasshka.todoapp.utils.TodoItemRepositoryMock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Calendar

internal class ListUITest {
    @Rule
    @JvmField
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    private lateinit var repository: TodoItemRepository

    @Before
    fun setContent() {
        repository = TodoItemRepositoryMock()
        val actionsMock = ActionsForTest(repository)

        composeTestRule.setContent {
            ListUI(
                getDoneCount = { repository.getTodoItems().count { it.completed } },
                getVisibilityDoneON = { actionsMock.visibility },
                getItems = { repository.getTodoItems() },
                getAction = actionsMock::invoke
            )
        }
    }

    @Test
    fun `Have_мои_дела`() {
        composeTestRule.onNodeWithText("Мои дела")
            .assertExists()
    }

    @Test
    fun `Have_выполнено`() {
        composeTestRule.onNodeWithText(text = "Выполнено", substring = true)
            .assertExists()
    }

    @Test
    fun haveVisibilityIcon() {
        composeTestRule.onNodeWithContentDescription("visibility done icon")
            .assertExists()
    }

    @Test
    fun haveCreateButton() {
        composeTestRule.onNodeWithContentDescription("create_btn")
            .assertExists()
    }

    @Test
    fun haveNew() {
        composeTestRule.onNodeWithText("Новое")
            .assertExists()
    }

    @Test
    fun haveItem() {
        val name = addOne()

        composeTestRule.onNodeWithContentDescription("importance icon")
            .assertExists()

        composeTestRule.onNodeWithText(text = name)
            .assertExists()

        composeTestRule.onNodeWithContentDescription("info_btn")
            .assertExists()
    }

    @Test
    fun canDone() {
        addOne()

        assert(repository.getTodoItems().first().completed.not())

        composeTestRule.onNodeWithTag(TestTag.Checkbox.value)
            .performClick()

        assert(repository.getTodoItems().first().completed)
    }

    @Test
    fun deleteSwipe() {
        addOne()
        composeTestRule.onNodeWithTag(TestTag.ListItem.value)
            .assertExists()
            .performTouchInput {
                swipeLeft()
            }

        composeTestRule.onNodeWithTag(TestTag.ListItem.value)
            .assertDoesNotExist()
    }

    @Test
    fun doneSwipe() {
        addOne()
        composeTestRule.onNodeWithTag(TestTag.ListItem.value)
            .assertExists()
            .performTouchInput {
                swipeRight()
            }

        composeTestRule.onNodeWithTag(TestTag.ListItem.value)
            .assertExists()

        assert(repository.getTodoItems().first().completed)
    }

    @Test
    fun visibilityCount() {
        val names = (1..10).map {
            addOne(it.toString())
        }

        composeTestRule.onNodeWithText(text = "Выполнено — 0")
            .assertExists()

        names.forEach { name ->
            composeTestRule.onNodeWithText(name)
                .assertExists()
                .performTouchInput {
                    swipeRight()
                }

            composeTestRule.onNodeWithText(text = "Выполнено — $name")
                .assertExists()
        }
    }

    private fun addOne(idWithName: String = "testItem"): String {
        repository.addTodoItem(
            TodoItem(
                id = idWithName,
                text = idWithName,
                importance = TodoItem.Importance.Low,
                created = Calendar.getInstance().time,
                deadLine = Calendar.getInstance().time
            )
        )

        return idWithName
    }
}
