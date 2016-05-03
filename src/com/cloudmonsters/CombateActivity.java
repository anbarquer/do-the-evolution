package com.cloudmonsters;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class CombateActivity extends ActionBarActivity {

	private final String ENEMIGOS = "enemigos";
	public final static String RESULTADO = "resultado";
	public final static String INVASOR = "invasor";
	public static int delay = 1000;
	public static boolean menuItems = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_combate);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setTitle(getString(R.string.combates));
		listadoInvasores();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.menu_combat, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_rapido).setVisible(menuItems);
		menu.findItem(R.id.action_refresh).setVisible(!menuItems);
		return super.onPrepareOptionsMenu(menu);
	}
	
	public void setFalse(){
		invalidateOptionsMenu();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	   switch (item.getItemId()) {
	      case R.id.action_rapido:
	    	  delay = 0;
	         return true;
	      case R.id.action_refresh:
	    	  refreshInvasores();
	    	  return true;
	      default:
	         return super.onOptionsItemSelected(item);
	   }
	}
				
	private void listadoInvasores() {

		InvasorFragment listadoInvasoresFragment = (InvasorFragment) getFragmentManager()
				.findFragmentByTag(ENEMIGOS);

		if (listadoInvasoresFragment == null) {
			listadoInvasoresFragment = new InvasorFragment();
		}
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, listadoInvasoresFragment, ENEMIGOS)
				.commit();
	}
	
	private void refreshInvasores(){
		InvasorFragment listadoInvasoresFragment = (InvasorFragment) getFragmentManager()
				.findFragmentByTag(ENEMIGOS);

		if (listadoInvasoresFragment == null) {
			listadoInvasoresFragment = new InvasorFragment();
		}
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.detach(listadoInvasoresFragment);
		ft.attach(listadoInvasoresFragment);
		ft.commit();
	}
	
	
	@Override
	public void onBackPressed() {
		
		setResult(RESULT_OK, new Intent());
		finish();
		super.onBackPressed();
	}
	
	
	
}
