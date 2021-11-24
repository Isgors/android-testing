package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Test
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.`is` as Is

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_noTasksCompleted_returnsZeroHundred() {
        val tasks = listOf(
                Task("test", "test", false)
        )

        val result = getActiveAndCompletedStats(tasks)

        assertThat(result.completedTasksPercent, Is(0f))
        assertThat(result.activeTasksPercent, Is(100f))
    }

    @Test
    fun getActiveAndCompletedStats_twoCompletedThreeActive_returnsFortySixty() {
        val tasks = listOf(
                Task("test", "test", true),
                Task("test", "test", true),
                Task("test", "test", false),
                Task("test", "test", false),
                Task("test", "test", false)
        )

        val result = getActiveAndCompletedStats(tasks)

        assertThat(result.completedTasksPercent, Is(40f))
        assertThat(result.activeTasksPercent, Is(60f))
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros() {
        val tasks = emptyList<Task>()

        val result = getActiveAndCompletedStats(tasks)

        assertThat(result.completedTasksPercent, Is(0f))
        assertThat(result.activeTasksPercent, Is(0f))
    }

    @Test
    fun getActiveAndCompletedStats_null_returnsZeros() {
        val tasks = null

        val result = getActiveAndCompletedStats(tasks)

        assertThat(result.completedTasksPercent, Is(0f))
        assertThat(result.activeTasksPercent, Is(0f))
    }
}