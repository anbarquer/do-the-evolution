package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import com.cloudmonsters.R;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Invasor implements Serializable {
	private static final long serialVersionUID = 1L;
	private int fuerza;
	private int destreza;
	private int resistencia;
	private int vida;
	private int nivel;
	private String nombre;
	private Drawable avatar;

	private ArrayList<Drawable> avatarsMonstruo = null;

	private void prepareAvatarsMonstruo(Context context) {
		avatarsMonstruo = new ArrayList<Drawable>();
		avatarsMonstruo.add(context.getResources().getDrawable(
				R.drawable.avatar_m0));
		avatarsMonstruo.add(context.getResources().getDrawable(
				R.drawable.avatar_m11));
		avatarsMonstruo.add(context.getResources().getDrawable(
				R.drawable.avatar_m12));
		avatarsMonstruo.add(context.getResources().getDrawable(
				R.drawable.avatar_m13));
	}

	public Invasor() {
		fuerza = 0;
		destreza = 0;
		resistencia = 0;
		vida = 0;
		nivel = 0;
		nombre = Typifications.Inv_name[Combat
				.dX(Typifications.Inv_name.length) - 1];

	}

	public String getNombre() {
		return nombre;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public Invasor(int ifuerza, int idestreza, int iresistencia, int ivida) {
		fuerza = ifuerza;
		destreza = idestreza;
		resistencia = iresistencia;
		vida = ivida;
		nombre = Typifications.Inv_name[Combat
				.dX(Typifications.Inv_name.length) - 1];

	}

	public Invasor(int ifuerza, int idestreza, int iresistencia, int ivida,
			int inivel) {
		fuerza = ifuerza;
		destreza = idestreza;
		resistencia = iresistencia;
		vida = ivida;
		nivel = inivel;
		nombre = Typifications.Inv_name[Combat
				.dX(Typifications.Inv_name.length) - 1];

	}

	public Invasor(Monstruo monster, Context context) {
		fuerza = monster.getCombat_att(0);
		destreza = monster.getCombat_att(1);
		resistencia = monster.getCombat_att(2);
		vida = monster.getBasic_att(3) * monster.getNivel();
		nombre = monster.getNombre();
		nivel = monster.getNivel();

		if (avatarsMonstruo == null)
			prepareAvatarsMonstruo(context);

		switch (nivel) {
		case 1:
			avatar = avatarsMonstruo.get(0);
			break;
		case 2:
			avatar = avatarsMonstruo.get(1);
			break;
		case 3:
			avatar = avatarsMonstruo.get(2);
			break;
		default:
			avatar = avatarsMonstruo.get(3);
			break;
		}

	}

	public int getFuerza() {
		return fuerza;

	}

	public int getDestreza() {
		return destreza;

	}

	public int getResistencia() {
		return resistencia;
	}

	public int getVida() {
		return vida;
	}

	public void setFuerza(int nFuerza) {
		fuerza = nFuerza;
	}

	public void setDestreza(int ndestreza) {
		destreza = ndestreza;
	}

	public void setResistencia(int nres) {
		resistencia = nres;
	}

	public void setVida(int nvida) {
		vida = nvida;

	}

	public void setAvatar(Drawable avatar) {
		this.avatar = avatar;
	}

	public Drawable getAvatar() {
		return this.avatar;
	}

}