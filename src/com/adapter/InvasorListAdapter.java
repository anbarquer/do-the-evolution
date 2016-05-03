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
import com.model.Invasor;
import com.model.Randomix;

public class InvasorListAdapter extends ArrayAdapter<Invasor> {
	private final Context context;
	private final ArrayList<Invasor> invasores;

	public InvasorListAdapter(Context context, ArrayList<Invasor> invasores) {
		super(context, R.layout.tienda_view, invasores);
		this.context = context;
		this.invasores = invasores;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		Invasor invasor = invasores.get(position);
		View invasorView = inflater
				.inflate(R.layout.tienda_view, parent, false);

		if (invasor != null) {
			TextView nombre = (TextView) invasorView
					.findViewById(R.id.objeto_nombre);
			TextView nivel = (TextView) invasorView
					.findViewById(R.id.objeto_cantidad);
			ImageView imagen = (ImageView) invasorView
					.findViewById(R.id.objeto_imagen);

			Drawable icono = Randomix.avatarsSelec.get(position);
		
			imagen.setImageDrawable(icono);
			nombre.setText(invasor.getNombre());
			nivel.setText("Nivel:" + " " +Integer.toString(invasor.getNivel()));

		}

		return invasorView;
	}

}
