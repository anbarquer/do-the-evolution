package com.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

	private String nombre;
	private long cantidad;
	private int multiplicador;
	private long id;
	private int tipo;
	private int precio;

	public final static int COMIDA = 0;
	public final static int DORMIR = 1;
	public final static int EJERCICIO = 2;
	public final static int LAVAR = 3;
	public final static int JUEGO = 4;
	public final static int POCION = 5;

	public Item() {
		super();
	}

	public Item(String nombre, long cantidad, int multiplicador, long id,
			int tipo, int precio) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.multiplicador = multiplicador;
		this.id = id;
		this.tipo = tipo;
		this.precio = precio;
	}

	public Item(Parcel in) {
		this.id = in.readLong();
		this.cantidad = in.readLong();
		this.multiplicador = in.readInt();
		this.nombre = in.readString();
		this.tipo = in.readInt();
		this.precio = in.readInt();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public int getMultiplicador() {
		return multiplicador;
	}

	public void setMultiplicador(int multiplicador) {
		this.multiplicador = multiplicador;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeLong(cantidad);
		dest.writeInt(multiplicador);
		dest.writeString(nombre);
		dest.writeInt(tipo);
		dest.writeInt(precio);
	}

	public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {

		public Item createFromParcel(Parcel source) {
			return new Item(source);
		}

		public Item[] newArray(int size) {
			return new Item[size];
		}
	};

}
