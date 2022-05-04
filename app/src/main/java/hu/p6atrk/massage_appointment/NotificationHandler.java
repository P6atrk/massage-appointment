package hu.p6atrk.massage_appointment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private final static String CHANNEL_ID = "massage_appointment_channel";
    private static final int NOTIFICATION_ID = 0;

    private Context context;
    private NotificationManager notificationManager;

    public NotificationHandler(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    public void createChannel() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Masszázs Időpontfoglaló",
                NotificationManager.IMPORTANCE_DEFAULT);
        this.notificationManager.createNotificationChannel(channel);
    }

    public void send() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Foglalj ma időpontot!")
                .setContentText("Nálunk olcsóbban tudsz időpontot foglalni!")
                .setSmallIcon(R.drawable.icon_smile);
        this.notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
