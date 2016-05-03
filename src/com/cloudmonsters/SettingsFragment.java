package com.cloudmonsters;

import com.model.Item;
import com.model.Monstruo;
import com.model.Tienda;
import com.model.Typifications;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ListFragment;
import android.content.DialogInterface;

public class SettingsFragment extends ListFragment {
	private Monstruo monstruo = Monstruo.getMonstruo();
	private Tienda tienda = Tienda.getTienda();
	private final int LOTERIA = 5;
	private Toast mensaje;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mensaje = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);

		getListView().setOnItemLongClickListener(
				new ListView.OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> av, View v,
							int position, long id) {

						String titulo = (String) getListAdapter().getItem(
								position);
						String mensaje[] = getResources().getStringArray(
								R.array.info_settings);

						dialogItemsLongClick(titulo, mensaje[position]);
						return true;
					}
				});

		String[] values = getResources().getStringArray(
				R.array.settings_opciones);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_selectable_list_item, values);
		setListAdapter(adapter);
	}

	private void dialogItemsLongClick(String titulo, String mensaje) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(titulo);
		builder.setIcon(R.drawable.pregunta);
		builder.setMessage(mensaje);
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		AlertDialog dialog = builder.show();
		TextView messageText = (TextView) dialog
				.findViewById(android.R.id.message);
		messageText.setGravity(Gravity.CENTER);
		dialog.show();
	}

	private void cambiarNombre() {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle(getString(R.string.cambiar_nombre));
		alert.setMessage(getString(R.string.intro_nombre));

		// Set an EditText view to get user input
		final EditText input = new EditText(getActivity());
		alert.setView(input);

		alert.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String value = input.getText().toString().trim();
						if (!value.isEmpty()) {
							monstruo.setNombre(value);
							mostrarMensajeToast(getString(R.string.nombre_cambiado)
									+ ": " + value);
						} else {
							mostrarMensajeToast(getString(R.string.nombre_incorrecto));
						}
					}
				});

		alert.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();
	}

	private void mostrarMensajeToast(String mensaje) {
		this.mensaje.setText(mensaje);
		this.mensaje.show();
	}

	private void preguntaReset() {
		new AlertDialog.Builder(getActivity())
				.setTitle(R.string.borra_todo)
				.setMessage(R.string.pregunta_salir)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								monstruo.resetMonstruo();
								mostrarMensajeToast(getString(R.string.mons_reseteado));
							}
						})
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).setIcon(R.drawable.alert_exclam).show();

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		switch (position) {
		case 0:
			cambiarNombre();			
			break;
		case 1:
			preguntaReset();
			break;
		case 2:
			if (monstruo.getBasic_att(Typifications.basic_att.Vida.ordinal()) == Typifications.MIN) {
				if(monstruo.getCreditos() >= 200){
					monstruo.setBasic_att(Typifications.basic_att.Vida.ordinal(),
							Typifications.MAX_BASIC);
					mostrarMensajeToast(getString(R.string.vida_restablecida));
				}else{
					mostrarMensajeToast(getString(R.string.no_tienes_creditos));
				}
				
				
			} else {
				mostrarMensajeToast(getString(R.string.no_es_necesario));
			}
			break;

		case 3:
			
			if(monstruo.getCreditos() >= LOTERIA){
				Item regalo = tienda.getRandomItem();
				mostrarMensajeToast("¡" + getString(R.string.te_ha_tocado) + " "
						+ regalo.getNombre() + "!");
				monstruo.insertarItem(regalo);
				monstruo.setCreditos(monstruo.getCreditos() - LOTERIA);
			} else{
				mostrarMensajeToast(getString(R.string.no_tienes_creditos));
			}
			
			break;
		}

	}
}
