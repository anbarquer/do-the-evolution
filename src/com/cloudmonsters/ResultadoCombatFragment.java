package com.cloudmonsters;

import java.util.ArrayList;
import com.model.Combat;
import com.model.Invasor;
import com.model.Monstruo;
import com.model.Randomix;
import com.model.TextProgressBar;
import com.model.RegistroCombat;
import com.model.Typifications;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultadoCombatFragment extends Fragment {

	private Invasor invasor = null;
	private TextView logCombate = null;
	private TextView nombreMonstruo = null;
	private TextView nivelMonstruo = null;
	private TextView nombreInvasor = null;
	private TextView nivelInvasor = null;
	private Monstruo monstruo = Monstruo.getMonstruo();
	private Invasor monstruoInv = null;
	private ImageView avatarMonstruo = null;
	private ImageView avatarEnemigo = null;
	private TextProgressBar vidaInvasor = null;
	private TextProgressBar vidaMonstruo = null;
	private ArrayList<RegistroCombat> logArray = null;
	private int maxInv = 0;
	private int maxMonst = 0;
	private int positionAvatar = 0;
	private int registroVida = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View interfaz = (View) inflater.inflate(R.layout.fragment_combate,
				container, false);
		logCombate = (TextView) interfaz.findViewById(R.id.message_scroll);
		logCombate.setMovementMethod(new ScrollingMovementMethod());
		nombreMonstruo = (TextView) interfaz.findViewById(R.id.nombre_monstruo);
		nivelMonstruo = (TextView) interfaz.findViewById(R.id.nivel_monstruo);
		nombreInvasor = (TextView) interfaz.findViewById(R.id.nombre_enemigo);
		nivelInvasor = (TextView) interfaz.findViewById(R.id.nivel_enemigo);
		avatarMonstruo = (ImageView) interfaz
				.findViewById(R.id.imagen_monstruo);
		avatarEnemigo = (ImageView) interfaz.findViewById(R.id.imagen_enemigo);

		invasor = (Invasor) getArguments().getSerializable(
				CombateActivity.INVASOR);

		positionAvatar = getArguments().getInt("position");

		vidaInvasor = (TextProgressBar) interfaz
				.findViewById(R.id.progressBarVidaEnemigo);
		vidaMonstruo = (TextProgressBar) interfaz
				.findViewById(R.id.progressBarVidaMonstruo);

		refreshFragment();
		return interfaz;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		CombateActivity.menuItems = true;
		getActivity().invalidateOptionsMenu();
	}

	private void sleep() {
		try {
			Thread.sleep(CombateActivity.delay);
		} catch (InterruptedException e) {
		}
	}

	private void setInterfazPreCombat() {

		monstruoInv = new Invasor(monstruo, getActivity());

		nombreMonstruo.setText(monstruo.getNombre());
		nombreInvasor.setText(invasor.getNombre());
		nivelMonstruo.setText(getString(R.string.nivel) + " "
				+ Integer.toString(monstruo.getNivel()));
		nivelInvasor.setText(getString(R.string.nivel) + " "
				+ Integer.toString(invasor.getNivel()));

		avatarMonstruo.setImageDrawable(monstruoInv.getAvatar());
		avatarEnemigo.setImageDrawable(Randomix.avatarsSelec
				.get(positionAvatar));

		maxMonst = monstruoInv.getVida();
		maxInv = invasor.getVida();

		vidaMonstruo.setMax(maxMonst);
		vidaMonstruo.setText(Integer.toString(maxMonst) + "/"
				+ Integer.toString(monstruoInv.getVida()));
		vidaMonstruo.setProgress(maxMonst);

		vidaInvasor.setMax(maxInv);
		vidaInvasor.setProgress(maxInv);
		vidaInvasor.setText(Integer.toString(maxInv) + "/"
				+ Integer.toString(maxInv));
	}

	private void setProgressBars(RegistroCombat registro) {
		vidaMonstruo.setProgress(registro.getVidaMonstruo());
		vidaInvasor.setProgress(registro.getVidaInvasor());

		vidaMonstruo.setText(Integer.toString(registro.getVidaMonstruo()) + "/"
				+ Integer.toString(maxMonst));

		vidaInvasor.setText(Integer.toString(registro.getVidaInvasor()) + "/"
				+ Integer.toString(maxInv));
	}

	private static void appendColoredText(TextView tv, String text, int color) {
		int start = tv.getText().length();
		tv.append(text);
		int end = tv.getText().length();

		Spannable spannableText = (Spannable) tv.getText();
		spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
	}

	private void refreshFragment() {
		
		if (invasor != null) {
			new AsyncTask<Void, RegistroCombat, Boolean>() {

				@Override
				protected void onPreExecute() {
					setInterfazPreCombat();
					registroVida = monstruoInv.getVida();
					Combat.combatir(monstruoInv, invasor);
				}

				@Override
				protected Boolean doInBackground(Void... params) {

					logArray = Combat.getLog();

					for (int i = 0; i < logArray.size(); i++) {
						publishProgress(logArray.get(i));
						sleep();
					}

					return true;
				}

				@Override
				protected void onProgressUpdate(RegistroCombat... values) {

					if (values[0].getQuienPega())
						appendColoredText(logCombate, values[0].getSuceso()
								+ "\n", Color.GREEN);
					else
						appendColoredText(logCombate, values[0].getSuceso()
								+ "\n", Color.RED);

					if(values[0].getFin() != 1)
						setProgressBars(values[0]);

					registroVida = values[0].getVidaMonstruo();

				}

				@Override
				protected void onPostExecute(final Boolean success) {

					appendColoredText(
							logCombate,
							"Has ganado "
									+ Integer.toString(monstruo
											.get_commit_XP(invasor,getActivity()))
									+ " experiencia" + "\n", Color.YELLOW);
					appendColoredText(
							logCombate,
							"Has ganado "
									+ Integer.toString(monstruo
											.get_commit_CombatCredits(invasor,
													Combat.ganador))
									+ " créditos" + "\n", Color.YELLOW);

					// Limpia del log del combate
					Combat.clearLog();

					// El monstruo se cansa un poco
					monstruo.secuelasCombate(registroVida);
					monstruo.setNumAcciones(monstruo.getNumAcciones()
							- Typifications.COSTE_COMBAT);
					
				}
			}.execute();
		}

	}

}
