package pl.training.runkeeper

import android.Manifest.permission.POST_NOTIFICATIONS
import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.util.Log
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class PushNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification!!.title
        val text = remoteMessage.notification!!.body

        val channelId = "HEADS_UP_NOTIFICATION"

        val channel = NotificationChannel(channelId, "Heads Up Notification", IMPORTANCE_HIGH)
        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)

        val notification = Notification.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.btn_plus)
            .setAutoCancel(true)
            .build()

        if (checkSelfPermission(this, POST_NOTIFICATIONS) != PERMISSION_GRANTED) {
            Log.i("###", "Required permission")
            return
        }
        NotificationManagerCompat.from(this)
            .notify(1, notification)

        super.onMessageReceived(remoteMessage)
    }

    override fun onNewToken(token: String) {
        Log.d("###", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        // sendRegistrationToServer(token)
    }


}