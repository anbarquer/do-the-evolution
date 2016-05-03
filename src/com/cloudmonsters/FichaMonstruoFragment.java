package com.cloudmonsters;

import com.model.Monstruo;
import com.model.Typifications;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FichaMonstruoFragment extends Fragment {
	private Monstruo monstruo = Monstruo.getMonstruo();

	private TextView nombre = null;
	private TextView nivel = null;
	private TextView experiencia = null;
	private TextView fuerza = null;
	private TextView destreza = null;
	private TextView resistencia = null;
	private ImageView imagenMonstruo = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View interfaz = (View) inflater.inflate(R.layout.fragment_ficha,
				container, false);
		if (monstruo != null) {
			nombre = (TextView) interfaz.findViewById(R.id.textViewNumNombre);
			nombre.setText(monstruo.getNombre());
			nivel = (TextView) interfaz.findViewById(R.id.textViewNumNivel);
			nivel.setText(Integer.toString(monstruo.getNivel()));
			experiencia = (TextView) interfaz
					.findViewById(R.id.textViewNumExperiencia);
			experiencia.setText(Integer.toString(monstruo.getExperiencia()));
			fuerza = (TextView) interfaz.findViewById(R.id.textViewNumFuerza);
			fuerza.setText(Integer.toString(monstruo
					.getCombat_att(Typifications.combat_att.Fuerza.ordinal())));
			destreza = (TextView) interfaz
					.findViewById(R.id.textViewNumDestreza);
			destreza.setText(Integer.toString(monstruo
					.getCombat_att(Typifications.combat_att.Destreza.ordinal())));
			resistencia = (TextView) interfaz
					.findViewById(R.id.textViewNumResistencia);
			resistencia.setText(Integer.toString(monstruo
					.getCombat_att(Typifications.combat_att.Resistencia
							.ordinal())));

			imagenMonstruo = (ImageView) interfaz
					.findViewById(R.id.imageMonstruo);

			Drawable imagen;
			switch (monstruo.getNivel()) {

			case 1:
				imagen = getActivity().getResources().getDrawable(
						R.drawable.m0);
				break;
			case 2:
				imagen = getActivity().getResources().getDrawable(R.drawable.m11);
				break;
			case 3:
				imagen = getActivity().getResources().getDrawable(R.drawable.m12);
				break;
			default:
				imagen = getActivity().getResources().getDrawable(R.drawable.m13);
				break;

			}

			imagenMonstruo.setImageDrawable(imagen);
		}

		return interfaz;

	}
}
