package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.RoomDatabaseRule
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @get:Rule
    var todoDatabaseRule = RoomDatabaseRule(ToDoDatabase::class.java)

    @Test
    fun insertTaskAndGetById() = mainCoroutineRule.runBlockingTest {
        val task = Task("title", "description")
        todoDatabaseRule.database.taskDao().insertTask(task)

        val loadedTask = todoDatabaseRule.database.taskDao().getTaskById(task.id)

        assertThat(loadedTask!!.id, `is`(task.id))
        assertThat(loadedTask.title, `is`(task.title))
        assertThat(loadedTask.description, `is`(task.description))
        assertThat(loadedTask.isCompleted, `is`(task.isCompleted))
    }

    @Test
    fun updateTaskAndGetById() = mainCoroutineRule.runBlockingTest {
        // When inserting a task
        val originalTask = Task("title", "description")
        todoDatabaseRule.database.taskDao().insertTask(originalTask)

        // When the task is updated
        val updatedTask = Task("new title", "new description", true, originalTask.id)
        todoDatabaseRule.database.taskDao().updateTask(updatedTask)

        // THEN - The loaded data contains the expected values
        val loaded = todoDatabaseRule.database.taskDao().getTaskById(originalTask.id)
        assertThat(loaded?.id, `is`(originalTask.id))
        assertThat(loaded?.title, `is`(updatedTask.title))
        assertThat(loaded?.description, `is`(updatedTask.description))
        assertThat(loaded?.isCompleted, `is`(updatedTask.isCompleted))
    }
}