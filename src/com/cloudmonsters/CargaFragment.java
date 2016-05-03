package com.cloudmonsters;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CargaFragment extends Fragment {

	private int espera = 1000;
	private ProgressDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View interfaz = (View) inflater.inflate(R.layout.carga_combate,
				container, false);

		simulaCarga();
		return interfaz;

	}

	private void simulaCarga() {
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected void onPreExecute() {
				dialog = ProgressDialog.show(getActivity(), "",
						getString(R.string.buscando_oponentes), false);

			}

			@Override
			protected Boolean doInBackground(Void... params) {
				for (int i = 0; i < 5; i++) {
					sleep();
					/*Se crean los oponentes*/
				}
				return true;
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				dialog.dismiss();
			}
		}.execute();
	}

	private void sleep() {
		try {
			Thread.sleep(espera);
		} catch (InterruptedException e) {
		}
	}

}
