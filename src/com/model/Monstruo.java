package com.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.ListIterator;
import com.cloudmonsters.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Monstruo {
	private String nombre;
	private ArrayList<Atributo> atributos_basicos = new ArrayList<Atributo>();
	private ArrayList<Atributo_combate> atributos_combate = new ArrayList<Atributo_combate>();
	private int nivel;
	private int experiencia;
	private int numAcciones;
	private int creditos;
	private Toast mensaje = null;
	private Context mAppcontext;

	public Context getmAppcontext() {
		return mAppcontext;
	}

	public void setmAppcontext(Context mAppcontext) {
		this.mAppcontext = mAppcontext;
	}

	private ArrayList<Item> inventario = new ArrayList<Item>();
	public final static String NIVEL = "nivel";
	public final static String EXPERIENCIA = "experiencia";
	public final static String NOMBRE = "nombre";
	public final static String COMBATES = "combates";
	public final static String ACCIONES = "acciones";
	public final static String CREDITOS = "creditos";
	public final static String INVENTARIO = "inventario";
	public final static String NOMDEFAULT = "Cloud Monster";
	public final static int NCREDITOSDEFAULT = 100;

	public final static int NACCIONDEFAULT = 50;

	private static class SingletonHelper {
		private static final Monstruo INSTANCE = new Monstruo();
	}

	// Contructores y útiles
	// -----------------------------------------------------------------------------------------------
	public static Monstruo getMonstruo() {
		return SingletonHelper.INSTANCE;
	}

	private Monstruo() {
		super();
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				for (int i = 0; i < Typifications.basic_attNames.length; i++) {
					atributos_basicos.add(new Atributo(
							Typifications.STANDARD_BASIC_ATRIBUTE,
							Typifications.basic_attNames[i]));
				}
				for (int i = 0; i < Typifications.combat_attNames.length; i++) {
					atributos_combate.add(new Atributo_combate(
							Typifications.STANDARD_COMBAT_ATRIBUTE,
							Typifications.combat_attNames[i]));
				}
				nivel = Typifications.MIN_BASIC + 1;
				experiencia = Typifications.MIN_BASIC;
				nombre = NOMDEFAULT;
				numAcciones = NACCIONDEFAULT;
				creditos = NCREDITOSDEFAULT;
				return true;
			}

		}.execute();

	}

	// Retorna en un Intent toda la información sobre el monstruo
	public void packageIntent(Intent intent) {
		Bundle bundle = new Bundle();
		packageBundle(bundle);
		intent.putExtras(bundle);
	}

	public void packageBundle(final Bundle bundle) {
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				for (int i = 0; i < Typifications.basic_attNames.length; i++) {
					bundle.putInt(Typifications.basic_attNames[i],
							atributos_basicos.get(i).getValor());
				}
				for (int i = 0; i < Typifications.combat_attNames.length; i++) {
					bundle.putInt(Typifications.combat_attNames[i],
							atributos_combate.get(i).getValor());
				}

				bundle.putInt(NIVEL, nivel);
				bundle.putInt(EXPERIENCIA, experiencia);
				bundle.putString(NOMBRE, nombre);
				bundle.putInt(ACCIONES, numAcciones);
				bundle.putParcelableArrayList(INVENTARIO, inventario);
				bundle.putInt(CREDITOS, creditos);

				return true;
			}

		}.execute();

	}

	// -----------------------------------------------------------------------------------------------
	// Getters y setters
	// -----------------------------------------------------------------------------------------------
	public void setParamsBundle(final Bundle bundle) {
		atributos_basicos.clear();
		atributos_combate.clear();

		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				for (int i = 0; i < Typifications.basic_attNames.length; i++) {
					atributos_basicos.add(new Atributo(bundle
							.getInt(Typifications.basic_attNames[i]),
							Typifications.basic_attNames[i]));
				}
				for (int i = 0; i < Typifications.combat_attNames.length; i++) {
					atributos_combate.add(new Atributo_combate(bundle
							.getInt(Typifications.basic_attNames[i]),
							Typifications.basic_attNames[i]));
				}

				nivel = bundle.getInt(NIVEL);
				experiencia = bundle.getInt(EXPERIENCIA);
				nombre = bundle.getString(NOMBRE);
				numAcciones = bundle.getInt(ACCIONES);
				inventario = bundle.getParcelableArrayList(INVENTARIO);
				creditos = bundle.getInt(CREDITOS);

				return true;
			}

		}.execute();

	}

	public void setCombat_att(int index, int val) {
		Atributo_combate actualizado = atributos_combate.get(index);
		actualizado.setValor(val);
		atributos_combate.set(index, actualizado);
	}

	public void setBasic_att(int index, int val) {
		Atributo actualizado = atributos_basicos.get(index);
		actualizado.setValor(val);
		atributos_basicos.set(index, actualizado);
	}

	public int getBasic_att(int index) {
		return atributos_basicos.get(index).getValor();
	}

	public int getCombat_att(int index) {
		return atributos_combate.get(index).getValor();

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		if (nivel >= Typifications.MIN_BASIC)
			this.nivel = nivel;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		if (experiencia >= Typifications.MIN_BASIC)
			this.experiencia = experiencia;
	}

	public int getNumAcciones() {
		return numAcciones;
	}

	public void setNumAcciones(int numAcciones) {
		if (numAcciones >= Typifications.MIN_BASIC)
			this.numAcciones = numAcciones;
	}

	public void setCreditos(int creditos) {
		if (creditos >= Typifications.MIN_BASIC)
			this.creditos = creditos;
	}

	public int getCreditos() {
		return creditos;
	}

	public void resetMonstruo() {
		inventario.clear();
		atributos_basicos.clear();
		atributos_combate.clear();
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				for (int i = 0; i < Typifications.basic_attNames.length; i++) {
					atributos_basicos.add(new Atributo(
							Typifications.STANDARD_BASIC_ATRIBUTE,
							Typifications.basic_attNames[i]));
				}
				for (int i = 0; i < Typifications.combat_attNames.length; i++) {
					atributos_combate.add(new Atributo_combate(
							Typifications.STANDARD_COMBAT_ATRIBUTE,
							Typifications.combat_attNames[i]));
				}
				nivel = Typifications.MIN + 1;
				experiencia = Typifications.MIN;
				nombre = NOMDEFAULT;
				numAcciones = NACCIONDEFAULT;
				creditos = NCREDITOSDEFAULT;
				return true;
			}

		}.execute();
	}

	// -----------------------------------------------------------------------------------------------
	// Acciones del monstruo
	// -----------------------------------------------------------------------------------------------
	private int setParamNivelBasic(int param) {

		int parametro = (param * getNivel()) / Typifications.MAX_BASIC;
		if (parametro == 0) {
			parametro = 1;
		}

		return parametro;
	}

	private int setParamNivelCombat(int param) {

		int parametro = (param * getNivel()) / Typifications.MAX_COMBAT;
		if (parametro == 0) {
			parametro = 1;
		}

		return parametro;
	}

	private void cambiaBasico(int index, int mult, int signo) {
		setBasic_att(index, getBasic_att(index) + signo
				* setParamNivelBasic(getBasic_att(index) + mult));
	}

	private void cambiaCombate(int index, int mult, int signo) {
		setCombat_att(index, getCombat_att(index) + signo
				* setParamNivelCombat(getBasic_att(index) + mult));
	}

	public boolean Comer(Context context, Item comida) {

		if (mensaje == null)
			mensaje = Toast.makeText(context, "", Toast.LENGTH_SHORT);

		String mensaje = "No tengo hambre";
		boolean result = false;
		// Si se tiene hambre
		if (getBasic_att(Typifications.basic_att.Hambre.ordinal()) > Typifications.MIN_BASIC) {

			// Aumenta el peso
			cambiaBasico(Typifications.basic_att.Peso.ordinal(),
					comida.getMultiplicador(), 1);
			// Aumenta la somnolencia
			cambiaBasico(Typifications.basic_att.Somnolencia.ordinal(),
					comida.getMultiplicador(), 1);
			// Disminuye el hambre
			cambiaBasico(Typifications.basic_att.Hambre.ordinal(),
					comida.getMultiplicador(), -1);
			// Aumenta la felicidad
			cambiaBasico(Typifications.basic_att.Felicidad.ordinal(),
					comida.getMultiplicador(), 1);

			mensaje = comida.getNombre() + " comido";
			result = true;
			eliminarItem(comida);

		}

		this.mensaje.setText(mensaje);
		this.mensaje.show();
		return result;
	}

	public boolean Ejercicio(Context context, Item ejercicio) {
		if (mensaje == null)
			mensaje = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		String mensaje = "Estoy cansado";
		boolean result = false;
		// Si no se está cansado
		if (getBasic_att(Typifications.basic_att.Cansancio.ordinal()) < Typifications.MAX_BASIC) {

			// Disminuye el peso
			cambiaBasico(Typifications.basic_att.Peso.ordinal(),
					ejercicio.getMultiplicador(), -1);
			// Aumenta la somnolencia
			cambiaBasico(Typifications.basic_att.Somnolencia.ordinal(),
					ejercicio.getMultiplicador(), 1);
			// Disminiye la felicidad
			cambiaBasico(Typifications.basic_att.Felicidad.ordinal(),
					ejercicio.getMultiplicador(), -1);
			// Aumenta la suciedad
			cambiaBasico(Typifications.basic_att.Suciedad.ordinal(),
					ejercicio.getMultiplicador(), 1);
			// Aumenta el hambre
			cambiaBasico(Typifications.basic_att.Hambre.ordinal(),
					ejercicio.getMultiplicador(), 1);
			// Aumenta el cansancio
			cambiaBasico(Typifications.basic_att.Cansancio.ordinal(),
					ejercicio.getMultiplicador(), 1);

			mensaje = ejercicio.getNombre() + " realizado";
			eliminarItem(ejercicio);

		}
		this.mensaje.setText(mensaje);
		this.mensaje.show();
		return result;
	}

	public boolean Lavar(Context context, Item lavar) {
		if (mensaje == null)
			mensaje = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		String mensaje = "No estoy sucio";
		boolean result = false;
		if (getBasic_att(Typifications.basic_att.Suciedad.ordinal()) > Typifications.MIN_BASIC) {
			// Disminuye la suciedad
			cambiaBasico(Typifications.basic_att.Suciedad.ordinal(),
					lavar.getMultiplicador(), -1);
			// Aumenta la somnolencia
			cambiaBasico(Typifications.basic_att.Somnolencia.ordinal(),
					lavar.getMultiplicador(), 1);
			// Aumenta la felicidad
			cambiaBasico(Typifications.basic_att.Felicidad.ordinal(),
					lavar.getMultiplicador(), 1);
			mensaje = lavar.getNombre() + " utilizado";
			eliminarItem(lavar);
		}
		this.mensaje.setText(mensaje);
		this.mensaje.show();
		return result;
	}

	public boolean Dormir(Context context, Item dormir) {
		if (mensaje == null)
			mensaje = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		String mensaje = "No tengo sueño";
		boolean result = false;
		if (getBasic_att(Typifications.basic_att.Somnolencia.ordinal()) > Typifications.MIN_BASIC) {
			// Disminuye la somnolencia
			cambiaBasico(Typifications.basic_att.Somnolencia.ordinal(),
					dormir.getMultiplicador(), -1);
			mensaje = dormir.getNombre() + " realizado";
			eliminarItem(dormir);
		}
		this.mensaje.setText(mensaje);
		this.mensaje.show();
		return result;
	}

	public boolean Jugar(Context context, Item jugar) {
		if (mensaje == null)
			mensaje = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		String mensaje = "No tengo ánimo";
		boolean result = false;
		if (getBasic_att(Typifications.basic_att.Felicidad.ordinal()) > Typifications.MIN_BASIC) {
			// Aumenta la felicidad
			cambiaBasico(Typifications.basic_att.Felicidad.ordinal(),
					jugar.getMultiplicador(), 1);
			// Aumenta el cansancio
			cambiaBasico(Typifications.basic_att.Cansancio.ordinal(),
					jugar.getMultiplicador(), 1);
			// Aumenta el hambre
			cambiaBasico(Typifications.basic_att.Hambre.ordinal(),
					jugar.getMultiplicador(), 1);
			// Disminuye la somnolencia
			cambiaBasico(Typifications.basic_att.Somnolencia.ordinal(),
					jugar.getMultiplicador(), -1);
			mensaje = jugar.getNombre() + " usado";
			eliminarItem(jugar);
			result = true;

		}
		this.mensaje.setText(mensaje);
		this.mensaje.show();
		return result;
	}

	public boolean Pocion(Context context, Item pocion) {
		if (mensaje == null)
			mensaje = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		String mensaje = "No es necesario";
		boolean result = false;

		if (getBasic_att(Typifications.basic_att.Vida.ordinal()) < Typifications.MAX_BASIC
				|| getBasic_att(Typifications.basic_att.Cansancio.ordinal()) > Typifications.MIN_BASIC) {
			// Aumenta la vida
			cambiaBasico(Typifications.basic_att.Vida.ordinal(),
					pocion.getMultiplicador(), 1);
			// Disminuye el cansancio
			cambiaBasico(Typifications.basic_att.Cansancio.ordinal(),
					pocion.getMultiplicador(), -1);
			mensaje = pocion.getNombre() + " usado";
			eliminarItem(pocion);
			result = true;

		}

		this.mensaje.setText(mensaje);
		this.mensaje.show();
		return result;

	}

	// -----------------------------------------------------------------------------------------------
	// Gestión del inventario
	// -----------------------------------------------------------------------------------------------

	public void insertarItem(Item nuevo) {
		ListIterator<Item> objetos = inventario.listIterator();
		boolean encontrado = false;
		while (objetos.hasNext() && !encontrado) {
			Item item = objetos.next();
			if (item.getId() == nuevo.getId()) {
				objetos.remove();
				objetos.add(new Item(item.getNombre(), item.getCantidad()
						+ nuevo.getCantidad(), item.getMultiplicador(), item
						.getId(), item.getTipo(), item.getPrecio()));
				encontrado = true;
			}
		}

		if (!encontrado) {
			inventario.add(nuevo);
		}
	}

	public void eliminarItem(Item eliminar) {
		ListIterator<Item> objetos = inventario.listIterator();
		boolean encontrado = false;
		do {
			Item item = objetos.next();
			if (item.getId() == eliminar.getId()) {
				objetos.remove();
				encontrado = true;
				if (item.getCantidad() - 1 > 0) {
					objetos.add(new Item(item.getNombre(),
							item.getCantidad() - 1, item.getMultiplicador(),
							item.getId(), item.getTipo(), item.getPrecio()));
				}

			}

		} while (objetos.hasNext() && !encontrado);
	}

	public void comprarItem(Context context, Item item) {
		if (mensaje == null)
			mensaje = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		String mensaje = "No tienes créditos";
		if (this.creditos >= item.getPrecio()) {
			setCreditos(getCreditos()
					- (int) (item.getPrecio() * item.getCantidad()));
			insertarItem(item);
			mensaje = item.getNombre() + " comprado";
			this.mensaje.setText(mensaje);
			this.mensaje.show();
		}

	}

	public ArrayList<Item> getInventario() {
		return inventario;
	}

	public ArrayList<Item> getInventario(int tipo) {
		ArrayList<Item> vObjetos = getInventario();
		ArrayList<Item> vFiltro = new ArrayList<Item>();

		if (tipo == -1) {
			return inventario;

		} else {
			for (int i = 0; i < vObjetos.size(); i++) {
				if (vObjetos.get(i).getTipo() == tipo) {
					vFiltro.add(vObjetos.get(i));
				}
			}
		}

		return vFiltro;
	}

	public void setInventario(ArrayList<Item> inventario) {
		this.inventario = inventario;
	}

	// -----------------------------------------------------------------------------------------------
	// Gestión del estado
	// -----------------------------------------------------------------------------------------------
	public void guardarEstado(final Context context) {
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				SharedPreferences sharedPref = PreferenceManager
						.getDefaultSharedPreferences(context);
				SharedPreferences.Editor editor = sharedPref.edit();

				for (int i = 0; i < Typifications.basic_attNames.length; i++) {
					editor.putInt(Typifications.basic_attNames[i],
							atributos_basicos.get(i).getValor());
				}
				for (int i = 0; i < Typifications.combat_attNames.length; i++) {
					editor.putInt(Typifications.combat_attNames[i],
							atributos_combate.get(i).getValor());
				}

				editor.putInt(context.getString(R.string.experiencia),
						getExperiencia());
				editor.putInt(context.getString(R.string.acciones),
						getNumAcciones());
				editor.putString(context.getString(R.string.nombre),
						getNombre());
				editor.putInt(context.getString(R.string.creditos),
						getCreditos());
				editor.putInt(context.getString(R.string.nivel), getNivel());
				Gson gson = new Gson();
				String json = gson.toJson(getInventario());
				editor.putString(context.getString(R.string.inventario), json);
				editor.commit();

				return true;
			}

		}.execute();
	}

	public void cargarEstado(final Context context) {
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				SharedPreferences sharedPref = PreferenceManager
						.getDefaultSharedPreferences(context);
				atributos_basicos.clear();
				atributos_combate.clear();
				for (int i = 0; i < Typifications.basic_attNames.length; i++) {
					atributos_basicos.add(new Atributo(sharedPref.getInt(
							Typifications.basic_attNames[i],
							Typifications.STANDARD_BASIC_ATRIBUTE),
							Typifications.basic_attNames[i]));
				}
				for (int i = 0; i < Typifications.combat_attNames.length; i++) {
					atributos_combate.add(new Atributo_combate(sharedPref
							.getInt(Typifications.combat_attNames[i],
									Typifications.STANDARD_COMBAT_ATRIBUTE),
							Typifications.combat_attNames[i]));
				}

				setNivel(sharedPref.getInt(context.getString(R.string.nivel),
						Typifications.MIN_BASIC + 1));
				setExperiencia(sharedPref.getInt(
						context.getString(R.string.experiencia),
						Typifications.MIN_BASIC));

				setNumAcciones(sharedPref.getInt(
						context.getString(R.string.acciones), NACCIONDEFAULT));
				setNombre(sharedPref.getString(
						context.getString(R.string.nombre), NOMDEFAULT));
				setCreditos(sharedPref.getInt(
						context.getString(R.string.creditos), NCREDITOSDEFAULT));

				Gson gson = new Gson();
				String json = sharedPref.getString(
						context.getString(R.string.inventario), null);
				ArrayList<Item> inventario = null;
				if (json == null) {
					inventario = new ArrayList<Item>();
				} else {
					Type type = new TypeToken<ArrayList<Item>>() {
					}.getType();
					inventario = gson.fromJson(json, type);
				}
				setInventario(inventario);

				return true;
			}

		}.execute();
	}

	public void secuelasCombate(int registroVida) {

		// Disminuye el peso
		cambiaBasico(Typifications.basic_att.Peso.ordinal(), 1, -1);
		// Aumenta el hambre
		cambiaBasico(Typifications.basic_att.Hambre.ordinal(), 1, 1);
		// Aumenta el cansancio
		cambiaBasico(Typifications.basic_att.Cansancio.ordinal(), 2, 1);
		// Disminuye la felicidad
		cambiaBasico(Typifications.basic_att.Felicidad.ordinal(), 2, -1);

			
		setBasic_att(Typifications.basic_att.Vida.ordinal(),registroVida/nivel);




	}

	// -----------------------------------------------------------------------------------------------

	// XXX: Lo que pasa después del combate
	public int get_commit_XP(Invasor v, Context context) {
		int xp = 0;
		if (this.nivel > v.getNivel()) {
			xp = (this.nivel + 1) * 25;
		}
		if (this.nivel == v.getNivel()) {
			xp = (this.nivel + 1) * 15;
		}
		if (this.nivel < v.getNivel()) {
			xp = (this.nivel + 1) * 10;
		}
		if (Combat.ganador == Combat.ganador_values.INVASOR.ordinal())
			xp = xp / 2;
		experiencia = experiencia + xp;
		checkLvlUp(context);
		return xp;
	}

	public void checkLvlUp(Context context) {
		if (mensaje == null)
			mensaje = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		if (Typifications.lvl_req[this.nivel] != -1
				&& this.experiencia >= Typifications.lvl_req[this.nivel]) {
			nivel++;
			this.mensaje.setText("Felicidades, ¡has subido de nivel!");
			this.mensaje.show();

			// Te cambian los atributos
			for (int i = 0; i < Typifications.combat_attNames.length; i++) {
				this.setCombat_att(i, Typifications.Att_x_level[nivel] / 2
						+ Combat.dX(Typifications.Att_x_level[nivel] / 2));
			}
		}

	}

	public int get_commit_CombatCredits(Invasor v, int resultado) {
		int credits;
		credits = v.getNivel() + Combat.dX(4);
		if (resultado == Combat.ganador_values.INVASOR.ordinal()) {
			credits = credits / 2;

		}
		this.creditos = this.creditos + credits;
		return credits;
	}
}
