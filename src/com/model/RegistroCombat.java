package com.model;

public class RegistroCombat {

	private String suceso;
	private int vidaMonstruo;
	private int vidaInvasor;
	/*
	 * true - monstruo false - invasor
	 */
	private boolean quienPega;
	private int fin = 0;

	public RegistroCombat() {
		suceso = "";
		vidaMonstruo = 0;
		vidaInvasor = 0;
		quienPega = true;
	}

	public RegistroCombat(String suceso, int vidaMonstruo, int vidaInvasor) {
		this.suceso = suceso;
		this.vidaMonstruo = vidaMonstruo;
		this.vidaInvasor = vidaInvasor;
	}

	public RegistroCombat(String suceso, int vidaMonstruo, int vidaInvasor,
			boolean quienpega) {
		this.suceso = suceso;
		this.vidaMonstruo = vidaMonstruo;
		this.vidaInvasor = vidaInvasor;
		this.quienPega = quienpega;
	}

	public String getSuceso() {
		return suceso;
	}

	public void setSuceso(String suceso) {
		this.suceso = suceso;
	}

	public int getVidaMonstruo() {
		return vidaMonstruo;
	}

	public void setVidaMonstruo(int vidaMonstruo) {
		this.vidaMonstruo = vidaMonstruo;
	}

	public int getVidaInvasor() {
		return vidaInvasor;
	}

	public void setVidaInvasor(int vidaInvasor) {
		this.vidaInvasor = vidaInvasor;
	}

	public boolean getQuienPega() {
		return quienPega;
	}

	public void setQuienPega(boolean quienPega) {
		this.quienPega = quienPega;
	}

	public int getFin() {
		return fin;
	}

	public void setFin(int fin) {
		this.fin = fin;
	}
	
	
}
