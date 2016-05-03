package com.cloudmonsters;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.adapter.TiendaListAdapter;
import com.model.Item;
import com.model.Monstruo;
import com.model.Tienda;

public class TiendaFragment extends ListFragment {
	private Monstruo monstruo = Monstruo.getMonstruo();
	private Tienda tienda = Tienda.getTienda();
	private Toast mensaje;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mensaje = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
		TiendaListAdapter listaObjetos = new TiendaListAdapter(getActivity(),
				tienda.getObjetos());
		setListAdapter(listaObjetos);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		final Item obj = (Item) getListAdapter().getItem(position);

		RelativeLayout linearLayout = new RelativeLayout(getActivity());
		final NumberPicker aNumberPicker = new NumberPicker(getActivity());
		aNumberPicker.setMaxValue(monstruo.getCreditos() / obj.getPrecio());
		int min = 1;
		if (monstruo.getCreditos() < obj.getPrecio()) {
			min = 0;
			mensaje.setText(getString(R.string.no_tienes_creditos));
			mensaje.show();

		}

		if (min > 0) {
			aNumberPicker.setMinValue(min);

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					50, 50);
			RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

			linearLayout.setLayoutParams(params);
			linearLayout.addView(aNumberPicker, numPicerParams);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());
			alertDialogBuilder.setTitle("Cantidad");
			alertDialogBuilder.setView(linearLayout);
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("Comprar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									obj.setCantidad((long) aNumberPicker
											.getValue());
									monstruo.comprarItem(getActivity(), obj);
								}
							})
					.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}
	}

}
