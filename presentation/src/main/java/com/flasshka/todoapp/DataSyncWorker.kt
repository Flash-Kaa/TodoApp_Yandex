package com.flasshka.todoapp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.flasshka.domain.interfaces.TodoItemRepository
import java.util.concurrent.TimeUnit

/**
 * Sync net data with local data
 */
class DataSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository: TodoItemRepository = applicationContext.itemsRepository

    companion object {
        fun scheduleDataSyncWork(applicationContext: Context) {
            val dataSyncWorkRequest = PeriodicWorkRequestBuilder<DataSyncWorker>(8, TimeUnit.HOURS)
                .build()

            WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                "DataSyncWork",
                ExistingPeriodicWorkPolicy.UPDATE,
                dataSyncWorkRequest
            )
        }
    }

    override suspend fun doWork(): Result {
        repository.fetchItems()
        return Result.success()
    }
}