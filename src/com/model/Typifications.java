package com.model;

public final class Typifications {
	public final static String[] basic_attNames = { "Hambre", "Peso",
			"Somnolencia", "Vida", "Felicidad", "Suciedad", "Cansancio" };
	public final static String[] combat_attNames = { "Fuerza", "Destreza",
			"Resistencia" };
	public final static int[] time_affect = { 30, 40, 15, 100, 100, 20, 30 };
	public final static int[] affect_val = { 1, -1, 1, -1, -1, 1, 1 };
	public final static String Inv_name[] = { "Gizamluke",
			"Politoxicómano nervioso", "Star Criminal",
			"Mamarracho influenciable", "UniPr0n", "Dark Anor-Mal",
			"Dark Eidous", "Juanfra Casado", "Cretino anormal",
			"Enemigo aleatorio por defecto", "HAL 9000", "Dalek",
			"Nullpointer Excelsior", "Combat Wombat", "Strankoff Potrovich",
			"Putin", "Cocacolo", "Ekiekiekitapang", "3 licencias de Sublime",
			"Bilbo Brokovitch", "Janival", "Pretzel con mayonesa",
			"Manolo Escobar", "El Fary", "Ben Kenobi", "ZARABorg" };

	public final static String HAS_GANADO = "Has ganado";
	public final static String HAS_PERDIDO = "Has perdido";
	public final static String CRITIC_HECHO = "¡Increibla-bla! ¡Has realizado un crítico! ¡Es contriñente!";
	public final static String CRITIC_COMIDO = "¡O mai cat! ¡Te has comido un critiquín! ¡Eso va a doler mañana!";

	public final static int max_time_affect = 5000;

	public final static int MAX_BASIC = 10;
	public final static int MIN_BASIC = 0;
	public final static int MAX_COMBAT = 100;
	public final static int MIN_COMBAT = 0;
	public final static int MIN = 0;
	public final static int MAX_INVASORES = 10;

	public final static int Acciones_x_tiempo = 10;
	public final static int Tiempo_para_acciones = 30;
	
	public final static int STANDARD_BASIC_ATRIBUTE = MAX_BASIC / 2;
	public final static int STANDARD_COMBAT_ATRIBUTE = 7;

	public final static int Att_x_level[] = { 5, 7, 10, 13, 15, 17 };
	public final static int COSTE_COMBAT = 3;

	// Enum chachi para saber donde esta cada atributo :D
	// XXX: Como usar enum como un index :
	// Typifications.basic_att.Hambre.ordinal();
	public static enum basic_att {
		Hambre, Peso, Somnolencia, Vida, Felicidad, Suciedad, Cansancio
	}

	public static enum combat_att {
		Fuerza, Destreza, Resistencia
	}

	public static int lvl_req[] = { 0, 100, 200, 300, 500, 1000, -1 };

	// Acciones que da cada vez y cada cuanto para el service
	public static int actions_per_time = 10;
	public static int time_actions = 30;

}
