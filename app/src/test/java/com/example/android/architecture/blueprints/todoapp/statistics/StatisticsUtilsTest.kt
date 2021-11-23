package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Test

import org.junit.Assert.*

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_noTasksCompleted_returnsZeroHundred() {
        val tasks = listOf<Task>(
                Task("test", "test", false)
        )

        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(100f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_twoCompletedThreeActive_returnsFortySixty() {
        val tasks = listOf<Task>(
                Task("test", "test", true),
                Task("test", "test", true),
                Task("test", "test", false),
                Task("test", "test", false),
                Task("test", "test", false)
        )

        val result = getActiveAndCompletedStats(tasks)

        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)
    }
}