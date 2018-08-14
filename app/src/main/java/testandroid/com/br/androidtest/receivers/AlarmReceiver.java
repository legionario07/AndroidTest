package testandroid.com.br.androidtest.receivers;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import testandroid.com.br.androidtest.R;
import testandroid.com.br.androidtest.views.UserLogin;

/**
 * Created by PauLinHo on 16/09/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private Context context;


    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        criarNotificacao(context, "APP Para Teste");

    }


    /**
     * Criar uma notificaçao para exibir
     *
     * @param context
     */

    @SuppressLint("WrongConstant")
    private void criarNotificacao(Context context, String descricao) {

        Intent intent = new Intent(context, UserLogin.class);
        intent.addFlags(100);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker("Agence Test")
                .setContentTitle("AgenceTest")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        builder.setContentText(descricao);

        Notification not = builder.build();
        not.vibrate = new long[]{50, 300, 50, 900};
        not.flags = Notification.FLAG_AUTO_CANCEL;


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, not);

    }


}
