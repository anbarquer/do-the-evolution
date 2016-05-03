package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.cloudmonsters.R;
import com.model.Item;

public class ObjetoListAdapter extends ArrayAdapter<Item> {
	private final Context context;
	private final ArrayList<Item> objetos;

	public ObjetoListAdapter(Context context, ArrayList<Item> objetos) {
		super(context, R.layout.objeto_view, objetos);
		this.context = context;
		this.objetos = objetos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Item objeto = objetos.get(position);
		View objetoView = inflater.inflate(R.layout.objeto_view, parent, false);
		if (objeto != null) {

			TextView nombre = (TextView) objetoView
					.findViewById(R.id.objeto_nombre);
			TextView cantidad = (TextView) objetoView
					.findViewById(R.id.objeto_cantidad);
			ImageView imagen = (ImageView) objetoView
					.findViewById(R.id.objeto_imagen);
			Drawable icono = null;
			
			switch (objeto.getTipo()) {
			case Item.COMIDA:
				icono = context.getResources()
						.getDrawable(R.drawable.ic_comida);
				break;
			case Item.DORMIR:
				icono = context.getResources()
						.getDrawable(R.drawable.ic_dormir);
				break;
			case Item.EJERCICIO:
				icono = context.getResources().getDrawable(
						R.drawable.ic_ejercicio);
				break;
			case Item.JUEGO:
				icono = context.getResources().getDrawable(R.drawable.ic_jugar);
				break;
			case Item.LAVAR:
				icono = context.getResources().getDrawable(
						R.drawable.ic_limpiar);
				break;
			default:
				icono = context.getResources().getDrawable(
						R.drawable.ic_pocion);
				break;

			}

			imagen.setImageDrawable(icono);
			nombre.setText(objeto.getNombre());
			cantidad.setText("Cantidad: " + Long.toString(objeto.getCantidad())
					+ " Efecto: " + Integer.toString(objeto.getMultiplicador()));
		}
		return objetoView;
	}
}
