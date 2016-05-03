package com.cloudmonsters;

import com.model.Monstruo;
import com.model.TextProgressBar;
import com.model.Typifications;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class EstadoMonstruoFragment extends Fragment {

	private TextProgressBar felicidad = null;
	private TextProgressBar hambre = null;
	private TextProgressBar peso = null;
	private TextProgressBar somnolencia = null;
	private TextProgressBar vida = null;
	private TextProgressBar suciedad = null;
	private TextProgressBar cansancio = null;

	private TextView acciones = null;
	private TextView creditos = null;
	private Monstruo monstruo = Monstruo.getMonstruo();
	private ImageView imagenMonstruo = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View interfaz = (View) inflater.inflate(R.layout.fragment_monstruo,
				container, false);

		if (monstruo != null) {
			felicidad = (TextProgressBar) interfaz
					.findViewById(R.id.progressBarFelicidad);
			hambre = (TextProgressBar) interfaz
					.findViewById(R.id.progressBarHambre);
			peso = (TextProgressBar) interfaz
					.findViewById(R.id.progressBarPeso);
			somnolencia = (TextProgressBar) interfaz
					.findViewById(R.id.progressBarSomnolencia);
			vida = (TextProgressBar) interfaz
					.findViewById(R.id.progressBarVida);
			suciedad = (TextProgressBar) interfaz
					.findViewById(R.id.progressBarSuciedad);
			cansancio = (TextProgressBar) interfaz
					.findViewById(R.id.progressBarCansancio);

			felicidad.setMax(Typifications.MAX_BASIC);
			hambre.setMax(Typifications.MAX_BASIC);
			peso.setMax(Typifications.MAX_BASIC);
			somnolencia.setMax(Typifications.MAX_BASIC);
			vida.setMax(Typifications.MAX_BASIC);
			suciedad.setMax(Typifications.MAX_BASIC);
			cansancio.setMax(Typifications.MAX_BASIC);

			acciones = (TextView) interfaz
					.findViewById(R.id.textViewNumAcciones);

			creditos = (TextView) interfaz
					.findViewById(R.id.textViewNumCreditos);

			imagenMonstruo = (ImageView) interfaz
					.findViewById(R.id.imageMonstruo);

			refreshFragment();

		}

		return interfaz;
	}

	public void refreshFragment() {

		felicidad.setProgress(monstruo
				.getBasic_att(Typifications.basic_att.Felicidad.ordinal()));
		hambre.setProgress(monstruo.getBasic_att(Typifications.basic_att.Hambre
				.ordinal()));
		peso.setProgress(monstruo.getBasic_att(Typifications.basic_att.Peso
				.ordinal()));
		somnolencia.setProgress(monstruo
				.getBasic_att(Typifications.basic_att.Somnolencia.ordinal()));
		vida.setProgress(monstruo.getBasic_att(Typifications.basic_att.Vida
				.ordinal()));
		suciedad.setProgress(monstruo
				.getBasic_att(Typifications.basic_att.Suciedad.ordinal()));
		cansancio.setProgress(monstruo
				.getBasic_att(Typifications.basic_att.Cansancio.ordinal()));

		felicidad.setText(Integer.toString(monstruo
				.getBasic_att(Typifications.basic_att.Felicidad.ordinal()))
				+ "/" + Integer.toString(Typifications.MAX_BASIC));
		hambre.setText(Integer.toString(monstruo
				.getBasic_att(Typifications.basic_att.Hambre.ordinal()))
				+ "/"
				+ Integer.toString(Typifications.MAX_BASIC));
		peso.setText(Integer.toString(monstruo
				.getBasic_att(Typifications.basic_att.Peso.ordinal()))
				+ "/"
				+ Integer.toString(Typifications.MAX_BASIC));
		somnolencia.setText(Integer.toString(monstruo
				.getBasic_att(Typifications.basic_att.Somnolencia.ordinal()))
				+ "/" + Integer.toString(Typifications.MAX_BASIC));
		vida.setText(Integer.toString(monstruo
				.getBasic_att(Typifications.basic_att.Vida.ordinal()))
				+ "/"
				+ Integer.toString(Typifications.MAX_BASIC));
		suciedad.setText(Integer.toString(monstruo
				.getBasic_att(Typifications.basic_att.Suciedad.ordinal()))
				+ "/" + Integer.toString(Typifications.MAX_BASIC));
		cansancio.setText(Integer.toString(monstruo
				.getBasic_att(Typifications.basic_att.Cansancio.ordinal()))
				+ "/" + Integer.toString(Typifications.MAX_BASIC));

		acciones.setText(Integer.toString(monstruo.getNumAcciones()));
		creditos.setText(Integer.toString(monstruo.getCreditos()));

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

}
