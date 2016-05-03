package com.model;




public class Atributo_combate extends Atributo {
	
	
	public Atributo_combate(){
		super();
	}
	public Atributo_combate(int val, String nom){
		super(val,nom);
	}
	
	@Override
	public boolean inBounds(int valor){
		boolean resultado = false;
		if(valor>=Typifications.MIN_COMBAT && valor<=Typifications.MAX_COMBAT)
			resultado = true;
		return resultado;
	}
	@Override
	public int correctedAtt(int val){
		int att_corregido = -1;
		if(val>Typifications.MAX_COMBAT)
			att_corregido = Typifications.MAX_COMBAT;
		if(val<Typifications.MIN_COMBAT)
			att_corregido = Typifications.MIN_COMBAT;
		return att_corregido;
	}
}
