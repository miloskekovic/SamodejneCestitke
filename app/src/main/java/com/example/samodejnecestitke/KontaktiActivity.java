package com.example.samodejnecestitke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class KontaktiActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.samodejneCestitke.MESSAGE";
    private ArrayList<Long> idsKontaktov = new ArrayList<Long>();
    DBHelper mydb;
    private ArrayList<Contact> vsiKontakti;
    private ListView listViewVsehKontaktov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onResume();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakti);
        mydb = new DBHelper(this);
        vsiKontakti = mydb.getAllContacts();
        Contact contact = new Contact();
        ArrayList<String> samoImenaInPriimkiKontaktov = contact.vrniImenaInPriimkeVsehKontaktov(vsiKontakti);
        Collections.sort(samoImenaInPriimkiKontaktov);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, samoImenaInPriimkiKontaktov);

        //adding it to the list view.
        listViewVsehKontaktov = (ListView) findViewById(R.id.listViewVsiKontakti);
        registerForContextMenu(listViewVsehKontaktov);
        listViewVsehKontaktov.setAdapter(arrayAdapter);
        listViewVsehKontaktov.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
            // TODO Auto-generated method stub
            String ime = listView.getItemAtPosition(position).toString();
            Bundle dataBundle = new Bundle();
            Contact izbraniKontakt = new Contact();
            int idKontakta = izbraniKontakt.getIdByName(vsiKontakti, ime);
            dataBundle.putInt("id", idKontakta);
            Intent intent = new Intent(getApplicationContext(), com.example.samodejnecestitke.DodajanjeNovegaKontakta.class);
            intent.putExtras(dataBundle);
            startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kontakti, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.itemIzprazniBazo:
                //Intent intent = new Intent(getApplicationContext(),com.example.samodejnecestitke.DodajanjeNovegaKontakta.class);
                //intent.putExtras(dataBundle);
                //startActivity(intent);
                getApplicationContext().deleteDatabase("MojiKontakti.db");
                Toast.makeText(getApplicationContext(), "Baza je zdaj prazna", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), com.example.samodejnecestitke.MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.itemPosortirajKontakte:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), com.example.samodejnecestitke.MainActivity.class);
        startActivity(intent);
        return;
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listViewVsiKontakti) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.izbrisi_doloceni_kontakt, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        mydb = new DBHelper(this);
        switch (item.getItemId()) {
            case R.id.itemIzbrisiDoloceniKontakt:
                String ime = listViewVsehKontaktov.getItemAtPosition((int) info.id).toString();
                Contact izbraniKontakt = new Contact();
                int idKontakta = izbraniKontakt.getIdByName(vsiKontakti, ime);
                //String key = ((TextView) info.targetView).getText().toString();
                Log.w("ID kontakta", idKontakta + "");
                mydb.deleteContact(idKontakta);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.kontaktIzbrisan), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), com.example.samodejnecestitke.MainActivity.class);
                startActivity(intent);
                return true;
            /*case R.id.edit:
                // edit stuff here
                return true;
            case R.id.delete:
                // remove stuff here
                return true;*/
            default:
                return super.onContextItemSelected(item);
        }
    }


}
