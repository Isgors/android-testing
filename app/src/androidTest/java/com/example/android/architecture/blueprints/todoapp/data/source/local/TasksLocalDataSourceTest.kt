package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.RoomDatabaseRule
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class TasksLocalDataSourceTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @get:Rule
    var todoDatabaseRule = RoomDatabaseRule(ToDoDatabase::class.java)

    private lateinit var localDataSource: TasksLocalDataSource

    @Before
    fun setup() {
        localDataSource =
            TasksLocalDataSource(
                todoDatabaseRule.database.taskDao(),
                Dispatchers.Main
            )
    }

    @Test
    fun saveTask_retrievesTask() = mainCoroutineRule.runBlockingTest {
        // GIVEN - A new task saved in the database.
        val newTask = Task("title", "description")
        localDataSource.saveTask(newTask)

        // WHEN  - Task retrieved by ID.
        val result = localDataSource.getTask(newTask.id)

        // THEN - Same task is returned.
        assertThat(result.succeeded, `is`(true))
        result as Result.Success
        assertThat(result.data.title, `is`("title"))
        assertThat(result.data.description, `is`("description"))
        assertThat(result.data.isCompleted, `is`(false))
    }

    @Test
    fun completeTask_retrievedTaskIsComplete() = mainCoroutineRule.runBlockingTest {
        // 1. Save a new active task in the local data source.
        val newTask = Task("title", "description", true)
        localDataSource.saveTask(newTask)

        // 2. Mark it as complete.
        localDataSource.completeTask(newTask)

        // 3. Check that the task can be retrieved from the local data source and is complete.
        val result = localDataSource.getTask(newTask.id) as Result.Success
        assertThat(result.data.isCompleted, `is`(true))
    }
}