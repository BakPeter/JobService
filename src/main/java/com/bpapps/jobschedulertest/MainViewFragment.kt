package com.bpapps.jobschedulertest

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment

class MainViewFragment : Fragment() {

    companion object {
        const val TAG = "TAG.MainViewFragment"
        const val JOB_ID = 101
        var counter = 0

        fun countJobs() {
            counter++
        }
    }

    private lateinit var b1: AppCompatButton
    private lateinit var b2: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_main_view, container, false)

        b1 = v.findViewById(R.id.b1)
        b1.setOnClickListener { b1 ->
            scheduleJob(b1)
        }

        b2 = v.findViewById(R.id.b2)
        b2.let { b2 ->
            b2.setOnClickListener {
                cancelJob(it)
            }
        }

        return v
    }

    private fun scheduleJob(v: View) {
        Log.d(TAG, "scheduleJob")

        val cn = ComponentName(requireContext(), MyJobService::class.java)
        val info = JobInfo.Builder(JOB_ID, cn)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .setPersisted(true)
            .setPeriodic(15 * 60 * 1000)
            .build()

        val js = activity?.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        val resultCode = js.schedule(info)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled")
        } else {
            Log.d(TAG, "Job not scheduled")
        }
    }

    private fun cancelJob(v: View) {
        Log.d(TAG, "cancelJob")

        val js  = activity?.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        js.cancel(JOB_ID)
    }
}