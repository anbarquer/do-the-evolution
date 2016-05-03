package com.model;

import java.util.ArrayList;

import android.util.Log;

public final class Combat {
	public static int ganador;
	public static int iniciativa;
	private static int vida_imon;
	private static int vida_aimon;
	public static ArrayList<RegistroCombat> logCombat = new ArrayList<RegistroCombat>();

	public static enum ganador_values {
		HOST, INVASOR
	};

	public static String combatir(Invasor usr_mon, Invasor ai_mon) {
		Log.i("Combate", "Combate iniciado");
		String resultado = new String();
		vida_imon = usr_mon.getVida();
		vida_aimon = ai_mon.getVida();
		resultado = resultado + ('\n' + Iniciativa(usr_mon, ai_mon));
		resultado = resultado + ('\n' + Combates(usr_mon, ai_mon));
		
		return resultado;
	}

	public static String Combates(Invasor usr_mon, Invasor ai_mon) {
		String resultado = new String();
		while (vida_imon > 0 && vida_aimon > 0) {
			if (iniciativa == 1) {
				resultado = resultado + '\n' + Danio1(usr_mon, ai_mon);
				iniciativa = (iniciativa + 1) % 2;
			} else {
				resultado = resultado + '\n' + Danio2(usr_mon, ai_mon);
				iniciativa = (iniciativa + 1) % 2;
			}

		}
		if (vida_imon > vida_aimon) {
			ganador = ganador_values.HOST.ordinal();
			resultado = resultado + '\n' + "Has ganado";
			
			setEntradaLogFin("Has ganado",vida_imon,0,true);

		} else {
			ganador = ganador_values.INVASOR.ordinal();
			resultado = resultado + '\n' + "Has perdido";
			setEntradaLogFin("Has perdido",0,vida_aimon,false);
		}

		return resultado;
	}

	public static String Danio1(Invasor usr_mon, Invasor ai_mon) {
		String resultado = new String();
		resultado = "";
		int vidaReg = 0;
		int danio = Combat.dX(usr_mon.getFuerza());
		if ((usr_mon.getDestreza() + Combat.d100() > ai_mon.getDestreza()
				+ Combat.d100())) {
			if (usr_mon.getDestreza() + Combat.d100() > 70) {
				danio = danio * 2;
				resultado = resultado
						+ '\n'
						+ "INCREIBLE! HAS REALIZADO UN GOLPE CRÍTICO!!!!! ES CONTRIÑENTE!";
				setEntradaLog(Typifications.CRITIC_HECHO,vida_imon,vida_aimon,true);
					
			}
			if(danio < 0)
				danio = 0;
			vida_aimon = vida_aimon - danio;
			resultado = resultado + '\n' + "El enemigo ha sufrido "
					+ Integer.toString(danio) + " puntos de daño";
			
			vidaReg = vida_aimon;
			if(vida_aimon < 0)
				vidaReg = 0;
			
				setEntradaLog("El enemigo ha sufrido "
					+ Integer.toString(danio) + " puntos de daño",vida_imon,vidaReg,true);
			
		} else {
			resultado = resultado + '\n' + "El enemigo ha esquivado tu ataque";
			setEntradaLog("El enemigo ha esquivado tu ataque",vida_imon,vida_aimon,false);
		}
		return resultado;
	}

	public static String Danio2(Invasor usr_mon, Invasor ai_mon) {
		String resultado = new String();
		resultado = "";
		int vidaReg = 0;
		int danio = Combat.dX(ai_mon.getFuerza());
		if ((usr_mon.getDestreza() + Combat.d100() < ai_mon.getDestreza()
				+ Combat.d100())) {
			if (ai_mon.getDestreza() + Combat.d100() > 70) {
				danio = danio * 2;
				resultado = resultado
						+ '\n'
						+ "INCREIBLE! TE HAN METIDO UN CRITIQUÍN!! ESO VA A DOLER MAÑANA!!";
				setEntradaLog(Typifications.CRITIC_COMIDO,vida_imon,vida_aimon,false);
			}
			if(danio<0)
				danio = 0;
			vida_imon = vida_imon - danio;
			resultado = resultado + '\n' + "El enemigo te ha inflingido "
					+ Integer.toString(danio) + " puntos de daño";
			vidaReg = vida_imon;
			if(vida_imon < 0)
				vidaReg = 0;
			
			setEntradaLog("El enemigo te ha inflingido "
					+ Integer.toString(danio) + " puntos de daño",vidaReg,vida_aimon,false);
			
		} else {
			resultado = resultado + '\n' + "¡Has esquivado el atroz ataque!";
			setEntradaLog("¡Has esquivado el atroz ataque!",vida_imon,vida_aimon,true);
		}
		return resultado;
	}

	public static String Iniciativa(Invasor usr_mon, Invasor ai_mon) {
		int usr_roll = Combat.d100() + usr_mon.getDestreza();
		int ai_roll = Combat.d100() + ai_mon.getDestreza();
		boolean quien = true;
		String c = new String();
		if (usr_roll > ai_roll) {
			c = "Ha ganado la iniciativa tu monstruo";
			
			iniciativa = 1;
		} else {
			iniciativa = 0;
			c = "Ha ganado iniciativa el enemigo";
			quien = false;
		}
		
		setEntradaLog(c,vida_imon,vida_aimon,quien);

		return c;

	}

	public static int d100() {
		return (int) (Math.random() * 100 + 1);
	}

	public static int dX(int caras_del_dado) {
		return (int) (Math.random() * caras_del_dado + 1);
	}

	public static int d10() {
		return (int) (Math.random() * 10 + 1);
	}
	
	private static void setEntradaLog(String suceso, int vidaMon, int vidaAIMon,boolean quien){
		logCombat.add(new RegistroCombat(suceso,vidaMon,vidaAIMon,quien));
	}
	
	private static void setEntradaLogFin(String suceso, int vidaMon, int vidaAIMon,boolean quien){
		RegistroCombat reg = new RegistroCombat(suceso,vidaMon,vidaAIMon,quien);
		reg.setFin(1);
		logCombat.add(reg);
	}
	
	public static ArrayList<RegistroCombat> getLog(){
		return logCombat;
	}
	
	public static void clearLog(){
		logCombat.clear();
	}
}