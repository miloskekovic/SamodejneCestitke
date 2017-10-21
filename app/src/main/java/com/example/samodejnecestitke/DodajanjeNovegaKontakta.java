package com.example.samodejnecestitke;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DodajanjeNovegaKontakta extends Activity {
	DBHelper mydb;
	EditText ime_in_priimek;
	EditText dan_rojstva, mesec_rojstva, leto_rojstva;
	EditText telefonska_stevilka;
	Spinner vera;
	EditText sporocilo_za_rojstni_dan;
	EditText sporocilo_za_bozic;
	Button bs;
	Button bu;
	Button bi;
	int id_To_Update = 0;
	Bundle extras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dodajanje_novega_kontakta);
		final List<String> list=new ArrayList<String>();
        list.add("Pravoslavni");
        list.add("Katolik");
        vera = (Spinner) findViewById(R.id.spinnerVera);
        ArrayAdapter<String> adp1=new ArrayAdapter<String>(this, R.layout.my_spinner_item_list,list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vera.setAdapter(adp1);
        vera.setSelection(0);
		ime_in_priimek = (EditText) findViewById(R.id.editTextImeInPriimek);
		dan_rojstva = (EditText) findViewById(R.id.editTextDanRojstva);
		dan_rojstva.setGravity(Gravity.CENTER);
		dan_rojstva.addTextChangedListener(new CustomTextWatcher(dan_rojstva));
		mesec_rojstva = (EditText) findViewById(R.id.editTextMesecRojstva);
		mesec_rojstva.setGravity(Gravity.CENTER);
		mesec_rojstva.addTextChangedListener(new CustomTextWatcher(mesec_rojstva));
		leto_rojstva = (EditText) findViewById(R.id.editTextLetoRojstva);
		leto_rojstva.setGravity(Gravity.CENTER);
		leto_rojstva.addTextChangedListener(new CustomTextWatcher(leto_rojstva));
		telefonska_stevilka = (EditText) findViewById(R.id.editTextTelefonskaStevilka);
		/*telefonska_stevilka.addTextChangedListener(new TextWatcher(){
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
//***  Use the below to lines ****
				if (telefonska_stevilka.getText().toString().startsWith("0") )
					telefonska_stevilka.setText("");
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void afterTextChanged(Editable s){}
		});*/
		sporocilo_za_rojstni_dan = (EditText) findViewById(R.id.editTextSporociloZaRojstniDan);
		sporocilo_za_bozic = (EditText) findViewById(R.id.editTextSporociloZaBozic);
		bs = (Button)findViewById(R.id.buttonShraniKontakt);
		bu = (Button)findViewById(R.id.buttonUrediKontakt);
		bi = (Button)findViewById(R.id.buttonIzbrisiKontakt);
		extras = getIntent().getExtras();
		if(extras !=null) {//urejamo obstojeci kontakt
			urejanjeKontakta();
		}else{//dodajamo novi kontakt
			shranjevanjeKontakta();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}




	public boolean izbrisiKontakt(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	      builder.setMessage(R.string.izbrisi)
	     .setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int id) {
	            mydb.deleteContact(id_To_Update);
	            Toast.makeText(getApplicationContext(), getResources().getString(R.string.kontaktIzbrisan), Toast.LENGTH_SHORT).show();
	            Intent intent = new Intent(getApplicationContext(),com.example.samodejnecestitke.KontaktiActivity.class);
	            startActivity(intent);
	         }
	      })
	     .setNegativeButton(R.string.ne, new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int id) {
	            // User cancelled the dialog
	         }
	      });
	      AlertDialog d = builder.create();
	      d.setTitle("Ali ste prepricani?");
	      d.show();

	      return true;
	}
	
	public boolean shranjevanjeKontakta(){
		ime_in_priimek.setEnabled(true);
	  	ime_in_priimek.setFocusableInTouchMode(true);
	  	ime_in_priimek.setClickable(true);

	  	dan_rojstva.setEnabled(true);
	  	dan_rojstva.setFocusableInTouchMode(true);
	  	dan_rojstva.setClickable(true);

	  	mesec_rojstva.setEnabled(true);
	  	mesec_rojstva.setFocusableInTouchMode(true);
	  	mesec_rojstva.setClickable(true);

	  	leto_rojstva.setEnabled(true);
	  	leto_rojstva.setFocusableInTouchMode(true);
	  	leto_rojstva.setClickable(true);
	      
	  	telefonska_stevilka.setEnabled(true);
	  	telefonska_stevilka.setFocusableInTouchMode(true);
	  	telefonska_stevilka.setClickable(true);
          
	  	vera.setEnabled(true);
	  	vera.setFocusable(false);
	  	vera.setClickable(true);

	  	sporocilo_za_rojstni_dan.setEnabled(true);
	  	sporocilo_za_rojstni_dan.setFocusableInTouchMode(true);
	  	sporocilo_za_rojstni_dan.setClickable(true);

	  	sporocilo_za_bozic.setEnabled(true);
	  	sporocilo_za_bozic.setFocusableInTouchMode(true);
	  	sporocilo_za_bozic.setClickable(true);

		bs.setVisibility(View.VISIBLE);
		bu.setVisibility(View.INVISIBLE);
		bi.setVisibility(View.INVISIBLE);
		bs.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
                if (preveriCeSoVneseniVsiPotrebniPodatki()==true) {
                    if (extras != null) {
                        spremeniKontaktIzBaze();
                    } else {
                        dodajKontaktVBazo();
                    }
                    Intent intent = new Intent(getApplicationContext(), com.example.samodejnecestitke.MainActivity.class);
                    startActivity(intent);
                }
			}
		});
	  	return true;
	}

	public void dodajKontaktVBazo(){
		mydb = new DBHelper(this);
		if (mydb.insertContact(mydb.numberOfRows(), ime_in_priimek.getText().toString(), Integer.parseInt(dan_rojstva.getText().toString()), Integer.parseInt(mesec_rojstva.getText().toString()), Integer.parseInt(leto_rojstva.getText().toString()), telefonska_stevilka.getText().toString(), vera.getSelectedItem().toString(), sporocilo_za_rojstni_dan.getText().toString(), sporocilo_za_bozic.getText().toString())) {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.uspesnoDodajanjeKontakta), Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.neuspesnoDodajanjeAliSpreminjanje), Toast.LENGTH_SHORT).show();
		}
	}

	public void spremeniKontaktIzBaze(){
		mydb = new DBHelper(this);
		if(mydb.updateContact(id_To_Update,ime_in_priimek.getText().toString(), Integer.parseInt(dan_rojstva.getText().toString()), Integer.parseInt(mesec_rojstva.getText().toString()), Integer.parseInt(leto_rojstva.getText().toString()), telefonska_stevilka.getText().toString(), vera.getSelectedItem().toString(),sporocilo_za_rojstni_dan.getText().toString(), sporocilo_za_bozic.getText().toString())){
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.uspesnoSpreminjanjeKontakta), Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getApplicationContext(),com.example.samodejnecestitke.MainActivity.class);
			startActivity(intent);
		}else{
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.neuspesnoDodajanjeAliSpreminjanje), Toast.LENGTH_SHORT).show();
		}
	}

	public void pokaziKontakt(){
		mydb = new DBHelper(this);
		int zaporednaStevilkaKontaktaKiGaZelimoSpremeniti = extras.getInt("id");
		Cursor rs=mydb.getData(zaporednaStevilkaKontaktaKiGaZelimoSpremeniti);
		id_To_Update=zaporednaStevilkaKontaktaKiGaZelimoSpremeniti;
		rs.moveToFirst();
		String ip=rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_IMEINPRIIMEK));
		Integer dr=rs.getInt(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_DANROJSTVA));
		Integer mr=rs.getInt(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_MESECROJSTVA));
		Integer lr=rs.getInt(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_LETOROJSTVA));
		String ts=rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_TELEFONSKASTEVILKA));
		String ve=rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_VERA));
		String szrd=rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_SPOROCILOZAROJSTNIDAN));
		String szb=rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_SPOROCILOZABOZIC));
		if(!rs.isClosed()){
			rs.close();
		}
		ime_in_priimek.setText((CharSequence)ip);
		ime_in_priimek.setFocusable(false);
		ime_in_priimek.setClickable(false);

		dan_rojstva.setText(dr.toString());
		dan_rojstva.setFocusable(false);
		dan_rojstva.setClickable(false);

		mesec_rojstva.setText(mr.toString());
		mesec_rojstva.setFocusable(false);
		mesec_rojstva.setClickable(false);

		leto_rojstva.setText(lr.toString());
		leto_rojstva.setFocusable(false);
		leto_rojstva.setClickable(false);

		telefonska_stevilka.setText(ts.toString());
		telefonska_stevilka.setFocusable(false);
		telefonska_stevilka.setClickable(false);

		if(ve.equals("Pravoslavni")){
			vera.setSelection(0);
		}else{
			vera.setSelection(1);
		}
		vera.setFocusable(false);
		vera.setClickable(false);

		sporocilo_za_rojstni_dan.setText(szrd);
		sporocilo_za_rojstni_dan.setFocusable(false);
		sporocilo_za_rojstni_dan.setClickable(false);

		sporocilo_za_bozic.setText(szb);
		sporocilo_za_bozic.setFocusable(false);
		sporocilo_za_bozic.setClickable(false);

		bs.setVisibility(View.INVISIBLE);
		bu.setVisibility(View.VISIBLE);
		bi.setVisibility(View.VISIBLE);
	}

	public boolean urejanjeKontakta(){
		pokaziKontakt();
		bu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				shranjevanjeKontakta();
			}
		});
		bi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				izbrisiKontakt();
			}
		});
		return true;
	}


	private class CustomTextWatcher implements TextWatcher {
		private EditText mEditText;

		public CustomTextWatcher(EditText e) {
			mEditText = e;
		}

		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {
			String imeEditTextaKiGaUporabnikTrenutnoSpreminja = vrniImeEditTexta(mEditText);
			switch (imeEditTextaKiGaUporabnikTrenutnoSpreminja){
				case "editTextDanRojstva":
					if(dan_rojstva.getText().length()>0) {
						int num = Integer.parseInt(dan_rojstva.getText().toString());
						if (num<1 || num>31)
						{
							Toast.makeText(DodajanjeNovegaKontakta.this,getApplicationContext().getString(R.string.narobeVnesenDanRojstva),Toast.LENGTH_SHORT).show();
							dan_rojstva.setText("");
						}
					}
				break;
				case "editTextMesecRojstva":
					if(mesec_rojstva.getText().length()>0) {
						int num = Integer.parseInt(mesec_rojstva.getText().toString());
						if (num<1 || num>12)
						{
							Toast.makeText(DodajanjeNovegaKontakta.this,getApplicationContext().getString(R.string.narobeVnesenMessecRojstva),Toast.LENGTH_SHORT).show();
							mesec_rojstva.setText("");
						}
					}
				break;
				case "editTextLetoRojstva":
					if(leto_rojstva.getText().length()>0) {
						int num = Integer.parseInt(leto_rojstva.getText().toString());
						int trenutnoLeto = Calendar.getInstance().get(Calendar.YEAR);
						String letoString = String.valueOf(trenutnoLeto);
						int tis = Character.digit(letoString.charAt(0), 10);
						int stot = Character.digit(letoString.charAt(1), 10);
						int des = Character.digit(letoString.charAt(2), 10);
						int eni = Character.digit(letoString.charAt(3), 10);
						int steviloVnesenihCifer = Integer.parseInt(String.valueOf(s.length()));
						Log.w("steviloVnesenihCifer", steviloVnesenihCifer+"");
						//Log.w("tis", tis+"");
						Log.w("Vneseno leto rojstva", num+"");
						if ((steviloVnesenihCifer==1 && (num<1 || num>tis)) || ((steviloVnesenihCifer==2) && (num<19 || num>2*10+stot)) || (steviloVnesenihCifer==3 && (num<190 || num>2*100+des)) || (steviloVnesenihCifer==4 && (num<1900 || (num>trenutnoLeto))) || steviloVnesenihCifer>4){
							String pripravljenString = getApplicationContext().getString(R.string.narobeVnesenoLetoRojstva)+" "+trenutnoLeto+"!";
							Toast.makeText(DodajanjeNovegaKontakta.this, pripravljenString, Toast.LENGTH_SHORT).show();
							leto_rojstva.setText("");
						}
					}
				break;
			}
			//Toast.makeText(DodajanjeNovegaKontakta.this, vrniImeEditTexta(idEditTexta), Toast.LENGTH_SHORT).show();
		}

		public void afterTextChanged(Editable s) {
		}
	}

	public String vrniImeEditTexta(EditText idEditTexta){
		String editTextString = idEditTexta+"";
		editTextString = editTextString.substring(editTextString.indexOf(":id/")+4, editTextString.length()-1);
		return editTextString;
	}

	public boolean preveriCeSoVneseniVsiPotrebniPodatki(){
		if(TextUtils.isEmpty(ime_in_priimek.getText().toString())){
			ime_in_priimek.setError(getApplicationContext().getString(R.string.praznoImeInPriimek));
			return false;
		}else if(TextUtils.isEmpty(dan_rojstva.getText().toString())){
			dan_rojstva.setError(getApplicationContext().getString(R.string.prazenDanRojstva));
			return false;
		}else if(TextUtils.isEmpty(mesec_rojstva.getText().toString())){
			mesec_rojstva.setError(getApplicationContext().getString(R.string.prazenMesecRojstva));
			return false;
		}else if(TextUtils.getTrimmedLength(leto_rojstva.getText().toString())!=4){
			leto_rojstva.setError(getApplicationContext().getString(R.string.nepopolnoLetoRojstva));
			return false;
		}else if(TextUtils.isEmpty(telefonska_stevilka.getText().toString())){
			telefonska_stevilka.setError(getApplicationContext().getString(R.string.praznaTelefonskaStevilka));
			return false;
		}else if(TextUtils.isEmpty(sporocilo_za_rojstni_dan.getText().toString())){
			sporocilo_za_rojstni_dan.setError(getApplicationContext().getString(R.string.praznoSporociloZaRojstniDan));
			return false;
		}else if(TextUtils.isEmpty(sporocilo_za_bozic.getText().toString())){
			sporocilo_za_bozic.setError(getApplicationContext().getString(R.string.praznoSporociloZaBozic));
			return false;
		}else{
			return true;
		}
    }
	
}
