//package com.example.habittracker.workmanager
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import androidx.core.app.NotificationCompat
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//import com.example.habittracker.R
//
//class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
//
//    override fun doWork(): Result {
//        sendNotification()
//        return Result.success()
//    }
//
//    private fun sendNotification() {
//        val notificationManager =
//            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        val channelId = "reminder channel"
//        val channelName = "Water Drinking Reminder"
//
//        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
//        notificationManager.createNotificationChannel(channel)
//
//        val notification = NotificationCompat.Builder(applicationContext, channelId)
//            .setContentTitle("Water Drinking Reminder")
//            .setContentText("It's time to drink water!")
//            .setSmallIcon(R.drawable.ic_water)
//            .build()
//
//        notificationManager.notify(1, notification)
//    }
//}
