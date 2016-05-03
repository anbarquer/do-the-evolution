package com.cloudmonsters;

import java.util.Timer;
import java.util.TimerTask;
import com.model.Monstruo;
import com.model.Typifications;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private String[] accionesMonstruo;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private Monstruo monstruo = Monstruo.getMonstruo();
	private final static String ESTADOMONSTRUO = "EstadoMonstruo";
	public final static int REQUEST_OBJETOS = 0;
	public final static int REQUEST_TIENDA = 1;
	public final static int REQUEST_EJERCICIO = 2;
	public final static int REQUEST_LAVAR = 3;
	public final static int REQUEST_DORMIR = 4;
	public final static int REQUEST_COMER = 5;
	public final static int REQUEST_JUGAR = 6;
	public final static int REQUEST_FICHA = 7;
	public final static int REQUEST_SETTINGS = 8;
	public final static int REQUEST_COMBATE = 9;
	public final static String TIPO = "tipo";
	private Intent intent;
	private Toast mensaje;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intent = new Intent(this, Att_service.class);
		startService(intent);
		mensaje = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		Timer T = new Timer();
		T.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							fragmentEstadoMonstruo();
							Log.d("Servicio", "Actualizando fragment");
						} catch (Exception e) {
							Log.d("Servicio",
									"No se ha podido actualizar fragment");
						}
						;
					}
				});
			}
		}, 1000, 60000);
		monstruo.cargarEstado(getApplicationContext());
		accionesMonstruo = getResources().getStringArray(
				R.array.menu_lateral_items);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, accionesMonstruo));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.app_name) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			realizarAccion(position);
		}
	}

	private void realizarAccion(int position) {
		Intent iAcciones = null;
		int tipoAcciones = -1;

		// Si se selecciona combatir no se decrementa el contador de acciones
		if (position == 6) {
			if (monstruo.getNumAcciones() - Typifications.COSTE_COMBAT >= 0) {

				if (monstruo.getBasic_att(Typifications.basic_att.Vida
						.ordinal()) > Typifications.MIN_BASIC
						&& monstruo
								.getBasic_att(Typifications.basic_att.Cansancio
										.ordinal()) < Typifications.MAX_BASIC) {
					
					iAcciones = new Intent(this, CombateActivity.class);
					startActivityForResult(iAcciones, REQUEST_COMBATE);
				} else {
					mostrarMensajeToast(getString(R.string.no_suficiente_vida));
				}

			} else {
				mostrarMensajeToast(getString(R.string.no_mas_combates));
			}
			// Si se selecciona otra acción
		} else {

			iAcciones = new Intent(this, AccionesActivity.class);

			if (monstruo.getNumAcciones() > 0) {
				switch (position) {
				case 0:
					tipoAcciones = REQUEST_COMER;
					break;
				case 1:
					tipoAcciones = REQUEST_DORMIR;
					break;
				case 2:
					tipoAcciones = REQUEST_EJERCICIO;
					break;
				case 3:
					tipoAcciones = REQUEST_LAVAR;
					break;
				case 4:
					tipoAcciones = REQUEST_JUGAR;
					break;
				case 5:
					tipoAcciones = REQUEST_OBJETOS;
					break;
				}

				iAcciones.putExtra(TIPO, tipoAcciones);
				startActivityForResult(iAcciones, tipoAcciones);
			} else {
				mostrarMensajeToast(getString(R.string.no_mas_acciones));
			}
		}

		fragmentEstadoMonstruo();
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {

			switch (requestCode) {
			case REQUEST_COMBATE:
				break;

			default:
				monstruo.setNumAcciones(monstruo.getNumAcciones() - 1);
				break;
			}

			fragmentEstadoMonstruo();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		Intent iAcciones = new Intent(this, AccionesActivity.class);
		int tipoAcciones = -1;
		int id = item.getItemId();

		switch (id) {

		case R.id.action_ficha:
			tipoAcciones = REQUEST_FICHA;
			break;
		case R.id.action_settings:
			tipoAcciones = REQUEST_SETTINGS;
			break;
		case R.id.action_tienda:
			tipoAcciones = REQUEST_TIENDA;
			break;
		case R.id.action_compartir:
			startActivity(new Intent(this, LoginActivity.class).putExtra(
					Monstruo.NIVEL, monstruo.getNivel()).putExtra(
					Monstruo.NOMBRE, monstruo.getNombre()));
			break;

		}

		if (tipoAcciones != -1) {
			iAcciones.putExtra(TIPO, tipoAcciones);
			startActivityForResult(iAcciones, tipoAcciones);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	private void fragmentEstadoMonstruo() {

		EstadoMonstruoFragment estadoMonstruo = (EstadoMonstruoFragment) getFragmentManager()
				.findFragmentByTag(ESTADOMONSTRUO);

		if (estadoMonstruo == null) {
			estadoMonstruo = new EstadoMonstruoFragment();
		} else {
			estadoMonstruo.refreshFragment();
		}

		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, estadoMonstruo, ESTADOMONSTRUO)
				.commit();

	}

	private void mostrarMensajeToast(String mensaje) {
		this.mensaje.setText(mensaje);
		this.mensaje.show();

	}

	@Override
	public void onBackPressed() {
		salirApp();
	}

	private void salirApp() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.titulo_salir)
				.setMessage(R.string.pregunta_salir)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								monstruo.guardarEstado(getApplicationContext());
								setResult(RESULT_OK, new Intent());
								finish();
							}
						})
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).setIcon(R.drawable.alert_exclam).show();

	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

		if (drawerOpen)
			mDrawerLayout.closeDrawer(mDrawerList);

		// XXX: Esto lo pongo aquí para resetear el mensaje si volvemos a abrir.
		// Si no se quedaba con el anterior y era caca
		Att_service.primeraNotif = true;
		Att_service.notificar = false;
		fragmentEstadoMonstruo();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Att_service.notificar = true;

	}

}
