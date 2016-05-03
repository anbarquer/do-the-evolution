package com.cloudmonsters;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class AccionesActivity extends ActionBarActivity {
	private final static String TIENDA = "tienda";
	private final static String OBJETOS = "objetos";
	private final static String OPCIONES = "opciones";
	private final static String FICHA = "ficha";
	private int tipoRequest = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acciones);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		tipoRequest = getIntent().getIntExtra(MainActivity.TIPO, 0);
		String titulos[] = getResources().getStringArray(R.array.tipo_fragment);
		getSupportActionBar().setTitle(titulos[tipoRequest]);
		cargaFragment();

	}

	public int getTipoFragment() {
		return tipoRequest;
	}

	public void setTipoFragment(int tipoRequest) {
		this.tipoRequest = tipoRequest;
		String titulos[] = getResources().getStringArray(R.array.tipo_fragment);
		getSupportActionBar().setTitle(titulos[tipoRequest]);
	}

	public void cargaFragment() {
		Fragment fragment = null;
		String tag = "";
		switch (tipoRequest) {
		case MainActivity.REQUEST_TIENDA:
			fragment = (TiendaFragment) getFragmentManager().findFragmentByTag(
					TIENDA);
			tag = TIENDA;
			break;
		case MainActivity.REQUEST_SETTINGS:
			fragment = (SettingsFragment) getFragmentManager()
					.findFragmentByTag(OPCIONES);
			tag = OPCIONES;
			break;

		case MainActivity.REQUEST_FICHA:
			fragment = (FichaMonstruoFragment) getFragmentManager()
					.findFragmentByTag(FICHA);
			tag = FICHA;
			break;
		default:
			fragment = (ObjetosFragment) getFragmentManager()
					.findFragmentByTag(OBJETOS);
			tag = OBJETOS;
			break;

		}

		getFragment(tag, fragment);
	}

	private void getFragment(String tag, Fragment fragment) {

		if (fragment == null) {
			switch (tipoRequest) {
			case MainActivity.REQUEST_TIENDA:
				fragment = new TiendaFragment();
				break;
			case MainActivity.REQUEST_SETTINGS:
				fragment = new SettingsFragment();
				break;

			case MainActivity.REQUEST_FICHA:
				fragment = new FichaMonstruoFragment();
				break;
			default:
				fragment = new ObjetosFragment();
				break;
			}
		}

		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment, tag).commit();
	}

}
