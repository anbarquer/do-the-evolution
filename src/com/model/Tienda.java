package com.model;

import java.util.ArrayList;
import java.util.Random;

public class Tienda {

	private ArrayList<Item> objetos = new ArrayList<Item>();
	private int id = 0;

	private static class SingletonHelper {
		private static final Tienda INSTANCE = new Tienda();
	}

	public static Tienda getTienda() {
		return SingletonHelper.INSTANCE;
	}

	private Tienda() {
		String[] nombresComida = { "Plátano", "Yogulado", "Bocadillo",
				"Lentejas", "Filete" };
		String[] nombresLimpia = { "Jabón", "Gel", "Estropajo",
				"Ducha a presión", "Limpieza total" };
		String[] nombresEjercicio = { "Andar", "Trotar", "Pesas", "Bicicleta",
				"Triatlón" };
		String[] nombresDormir = { "Cabezada", "Siesta", "Dormir",
				"Sueño reparador", "Descanso total" };
		String[] nombresJuego = { "Bolindres", "Peonza", "Pelota", "Cometa",
				"Play 4" };
		String[] nombresPociones = { "Jeringuilla", "Ampolla", "Vial",
				"Probeta", "Frasco" };

		int[] multiplicadores = { 1, 2, 3, 4, 5 };
		setVector(nombresComida, multiplicadores, Item.COMIDA);
		setVector(nombresDormir, multiplicadores, Item.DORMIR);
		setVector(nombresEjercicio, multiplicadores, Item.EJERCICIO);
		setVector(nombresLimpia, multiplicadores, Item.LAVAR);
		setVector(nombresJuego, multiplicadores, Item.JUEGO);
		setVector(nombresPociones, multiplicadores, Item.POCION);

	}

	private void setVector(String[] vector, int[] mult, int tipo) {
		for (int i = 0; i < vector.length; i++) {
			getObjetos()
					.add(new Item(vector[i], 1, mult[i], id, tipo, mult[i]));
			id++;
		}
	}

	public ArrayList<Item> getObjetos() {
		return objetos;
	}

	public int getId() {
		return id;
	}

	public Item getRandomItem() {
	    Random rand = new Random();
	    int randomNum = rand.nextInt(id - 0);
		return objetos.get(randomNum);

	}
}
