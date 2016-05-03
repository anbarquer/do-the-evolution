package com.model;

import java.util.ArrayList;
import java.util.Random;

import com.cloudmonsters.R;

import android.content.Context;
import android.graphics.drawable.Drawable;

public final class Randomix {

	private static ArrayList<Drawable> avatars = null;
	public static ArrayList<Drawable> avatarsSelec = null;

	private static void prepareAvatars(Context context) {

		avatars = new ArrayList<Drawable>();
		avatars.add(context.getResources().getDrawable(R.drawable.avatar_1));
		avatars.add(context.getResources().getDrawable(R.drawable.avatar_2));
		avatars.add(context.getResources().getDrawable(R.drawable.avatar_3));
		avatars.add(context.getResources().getDrawable(R.drawable.avatar_4));
		avatars.add(context.getResources().getDrawable(R.drawable.avatar_5));
		avatars.add(context.getResources().getDrawable(R.drawable.avatar_6));
		avatars.add(context.getResources().getDrawable(R.drawable.avatar_7));
		avatars.add(context.getResources().getDrawable(R.drawable.avatar_8));
		avatars.add(context.getResources().getDrawable(R.drawable.avatar_9));
		avatars.add(context.getResources().getDrawable(R.drawable.avatar_10));

	}

	public static Invasor RandInvader(int level, Context context) {

		if (avatars == null)
			prepareAvatars(context);

		int fuerza, destreza, resistencia, vida, nivel;
		fuerza = Typifications.Att_x_level[level] / 2 / 2
				/ +Combat.dX(Typifications.Att_x_level[level] / 2);
		destreza = Typifications.Att_x_level[level] / 2
				+ Combat.dX(Typifications.Att_x_level[level] / 2);
		resistencia = Typifications.Att_x_level[level] / 2
				+ Combat.dX(Typifications.Att_x_level[level] / 2);
		vida = Typifications.Att_x_level[level] / 2
				+ Combat.dX(Typifications.Att_x_level[level] / 2)*5;
		nivel = (int) (Math.random() * ((level + 2) - (level - 1))) + level - 1;
		Invasor invader = new Invasor(fuerza, destreza, resistencia, vida,
				nivel);

		Random rand = new Random();
		int randomNum = rand.nextInt(avatars.size() - 0);
		
		
		if(avatarsSelec == null)
			avatarsSelec = new ArrayList<Drawable>();
		
		avatarsSelec.add(avatars.get(randomNum));
		return invader;
	}

}
