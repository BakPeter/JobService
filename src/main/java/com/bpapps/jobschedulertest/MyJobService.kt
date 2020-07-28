package com.bpapps.jobschedulertest

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class MyJobService : JobService() {

    companion object {
        private const val TAG = "TAG.MyJobService"
        private var jobCancelled = false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job Started")

        doBackgroundWork(params)

        return true
    }

    private fun doBackgroundWork(params: JobParameters?) {
        Thread(Runnable {
            (1..10).forEach { i ->
                if (jobCancelled)
                   return@Runnable

                Log.d(TAG, "run #$i")

                Thread.sleep(1000)
            }

            Log.d(TAG, "Job finished")
            jobFinished(params, false)
        }).start()
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job cancelled before finished")
        jobCancelled = true

        return false
    }
}
