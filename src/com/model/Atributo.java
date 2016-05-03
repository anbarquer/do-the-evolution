package com.model;

public class Atributo {
	private int valor;
	private String nombre;
	
	public Atributo(){
		valor = 0;
		nombre = "Error_in_att";
	}
	public Atributo(int val, String nom){
		valor = val;
		nombre = nom;
	}
	
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		if(inBounds(valor))
			this.valor = valor;
		else
			this.valor = correctedAtt(valor);
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void incValor(int incremento){
		if(inBounds(valor+incremento))
			valor = valor + incremento;
		else
			setValor(correctedAtt(valor+incremento));
	}
	public boolean inBounds(int valor){
		boolean resultado = false;
		if(valor>=Typifications.MIN_BASIC && valor<=Typifications.MAX_BASIC)
			resultado = true;
		return resultado;
	}
	public int correctedAtt(int val){
		int att_corregido = -1;
		if(val>Typifications.MAX_BASIC)
			att_corregido = Typifications.MAX_BASIC;
		if(val<Typifications.MIN_BASIC)
			att_corregido = Typifications.MIN_BASIC;
		return att_corregido;
	}
}

