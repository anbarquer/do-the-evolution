package com.cloudmonsters;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.adapter.ObjetoListAdapter;
import com.model.Item;
import com.model.Monstruo;

public class ObjetosFragment extends ListFragment {
	private Monstruo monstruo = Monstruo.getMonstruo();
	private AccionesActivity parentActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View objetosView = (View) inflater.inflate(R.layout.fragment_objetos,
				container, false);

		objetosView.findViewById(android.R.id.empty).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						parentActivity = (AccionesActivity) getActivity();
						parentActivity
								.setTipoFragment(MainActivity.REQUEST_TIENDA);
						parentActivity.cargaFragment();
					}
				});

		return objetosView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		parentActivity = (AccionesActivity) getActivity();
		int tipoFragment = -1;
		switch (parentActivity.getTipoFragment()) {
		case MainActivity.REQUEST_EJERCICIO:
			tipoFragment = Item.EJERCICIO;
			break;
		case MainActivity.REQUEST_LAVAR:
			tipoFragment = Item.LAVAR;
			break;
		case MainActivity.REQUEST_DORMIR:
			tipoFragment = Item.DORMIR;
			break;
		case MainActivity.REQUEST_COMER:
			tipoFragment = Item.COMIDA;
			break;
		case MainActivity.REQUEST_JUGAR:
			tipoFragment = Item.JUEGO;
			break;
		case MainActivity.REQUEST_OBJETOS:
			tipoFragment = Item.POCION;
			break;
		}

		refreshFragment(tipoFragment);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Item objeto = (Item) getListAdapter().getItem(position);

		switch (parentActivity.getTipoFragment()) {
		case MainActivity.REQUEST_LAVAR:
			monstruo.Lavar(getActivity(), objeto);
			break;
		case MainActivity.REQUEST_DORMIR:
			monstruo.Dormir(getActivity(), objeto);
			break;
		case MainActivity.REQUEST_COMER:
			monstruo.Comer(getActivity(), objeto);
			break;
		case MainActivity.REQUEST_JUGAR:
			monstruo.Jugar(getActivity(), objeto);
			break;
		case MainActivity.REQUEST_OBJETOS:
			monstruo.Pocion(getActivity(), objeto);
			break;
		case MainActivity.REQUEST_EJERCICIO:
			monstruo.Ejercicio(getActivity(), objeto);
			break;
		}

		setRespuesta();

	}

	public void refreshFragment(int item) {

		ObjetoListAdapter listaObjetos = new ObjetoListAdapter(getActivity(),
				monstruo.getInventario(item));
		setListAdapter(listaObjetos);
	}

	private void setRespuesta() {
		getActivity().setResult(Activity.RESULT_OK, new Intent());
		getActivity().finish();
	}

}
