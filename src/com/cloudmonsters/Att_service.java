package com.cloudmonsters;

import com.model.Monstruo;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.model.Typifications;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class Att_service extends Service {
	private int min_elapsed;
	private Monstruo monstruo;
	public static boolean primeraNotif = true; 	//XXX: No se me ocurria otra manera.
	public static boolean notificar = true;

	private static String mensaje = "";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {

		Log.d("Servicio", "Servicio creado");
		min_elapsed = 0;
		monstruo = Monstruo.getMonstruo();

	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.d("Servicio", "Servicio empezado");

		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				min_elapsed = min_elapsed + 1;
				if (min_elapsed % Typifications.max_time_affect == 0)
					min_elapsed = 1;
				if(min_elapsed % Typifications.Tiempo_para_acciones == 0){
					monstruo.setNumAcciones(monstruo.getNumAcciones() + Typifications.Acciones_x_tiempo);
				}
				
				
				for (int i = 0; i < Typifications.basic_attNames.length; i++) {
					if (min_elapsed % Typifications.time_affect[i] == 0) {
						Log.d("Servicio", "Atributo cambiado");
						if (monstruo != null) {
							monstruo.setBasic_att(
									i,
									(monstruo.getBasic_att(i) + Typifications.affect_val[i]));
							
							if(notificar)
								notificarCambios(i);

						} else
							try {
								monstruo = Monstruo.getMonstruo();
								Log.d("Servicio", "Monster re-enganchado");
							} catch (Exception e) {
								Log.d("Servicio",
										"No se ha conseguido pillar monster");
							}
						;

					}
				}
			}
		}, 60000, 60000);
	}

	@Override
	public void onDestroy() {

	}
	
	public void resetCont(){
		min_elapsed = 1;
	}

	//XXX: Esto es para actualizar el mensaje y tal (si no se repiten cosas)
	private void setMessage(String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(mensaje);
		int cont = 0;

		if (primeraNotif) {
			mensaje = pattern;
			primeraNotif = false;
		} else {
			while (m.find())
				cont++;

			if (cont == 0)
				mensaje += ", " + pattern;
		}
	}
	
	public void notificarCambios(int cambio) {

		int id = 0;
		Notification.Builder notificationBuilder = null;
		RemoteViews mContentView = new RemoteViews(getApplicationContext()
				.getPackageName(), R.layout.custom_notification);

		PendingIntent pendingIntent = PendingIntent.getActivity(
				getApplicationContext(), 0, new Intent(getApplicationContext(),
						MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

		setMessage(Typifications.basic_attNames[cambio]);

		mContentView.setTextViewText(R.id.contenido_notificacion, mensaje);

		notificationBuilder = new Notification.Builder(getApplicationContext())
				.setAutoCancel(true)
				.setSmallIcon(android.R.drawable.stat_sys_warning)
				.setContent(mContentView)
				.setTicker(getString(R.string.app_name) + " te requiere")
				.setContentIntent(pendingIntent);

		try {
			NotificationManager notificationM = (NotificationManager) getApplicationContext()
					.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationM.notify(id, notificationBuilder.build());
			
			Log.d("Servicio", "He notificado");

		} catch (Exception e) {
			Log.i("K-bom",
					"Podria haber petado con las notificaicones, pero no lo hice");
		}
	}

}
