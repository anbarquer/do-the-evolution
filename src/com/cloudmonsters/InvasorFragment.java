package com.cloudmonsters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.adapter.InvasorListAdapter;
import com.model.Combat;
import com.model.Invasor;
import com.model.Monstruo;
import com.model.Randomix;
import com.model.Typifications;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class InvasorFragment extends ListFragment {

	private Monstruo monstruo = Monstruo.getMonstruo();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_listado_enemigos, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		CombateActivity.menuItems = false;
		getActivity().invalidateOptionsMenu();
		refreshFragment();
	}

	private void refreshFragment() {
		final ArrayList<Invasor> invaders = new ArrayList<Invasor>();

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected void onPreExecute() {

			}

			@Override
			protected Boolean doInBackground(Void... params) {
				int i = Combat.dX(Typifications.MAX_INVASORES);
				for (int j = 0; j < i; j++) {
					invaders.add(Randomix.RandInvader(monstruo.getNivel(),
							getActivity()));
				}

				// Ordenar alfabéticamente
				Collections.sort(invaders, new Comparator<Invasor>() {
					public int compare(Invasor invasor1, Invasor invasor2) {
						return invasor1.getNombre().compareToIgnoreCase(
								invasor2.getNombre());
					}
				});

				return true;
			}

			@Override
			protected void onPostExecute(final Boolean success) {

				InvasorListAdapter listaEnemigos = new InvasorListAdapter(
						getActivity(), invaders);
				setListAdapter(listaEnemigos);
			}
		}.execute();

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		Invasor invasor = (Invasor) getListAdapter().getItem(position);
		realizarCombate(invasor, position);
	}

	public void realizarCombate(Invasor invasor, int position) {

		CombateActivity.delay = 1000;

		ResultadoCombatFragment resultadoCombat = (ResultadoCombatFragment) getFragmentManager()
				.findFragmentByTag(CombateActivity.RESULTADO);

		if (resultadoCombat == null) {
			resultadoCombat = new ResultadoCombatFragment();
		}

		Bundle inv = new Bundle();
		inv.putSerializable(CombateActivity.INVASOR, invasor);
		inv.putInt("position", position);
		resultadoCombat.setArguments(inv);

		getFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, resultadoCombat,
						CombateActivity.RESULTADO).commit();
	}

}
