package com.example.samodejnecestitke;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "MojiKontakti.db";   
	public static final String CONTACTS_TABLE_NAME = "kontakti";
	public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_IMEINPRIIMEK ="ime_in_priimek";
    public static final String CONTACTS_COLUMN_DANROJSTVA ="dan_rojstva";
    public static final String CONTACTS_COLUMN_MESECROJSTVA ="mesec_rojstva";
    public static final String CONTACTS_COLUMN_LETOROJSTVA ="leto_rojstva";
    public static final String CONTACTS_COLUMN_TELEFONSKASTEVILKA ="telefonska_stevilka";
    public static final String CONTACTS_COLUMN_VERA ="vera";
    public static final String CONTACTS_COLUMN_SPOROCILOZAROJSTNIDAN ="sporocilo_za_rojstni_dan";
    public static final String CONTACTS_COLUMN_SPOROCILOZABOZIC ="sporocilo_za_bozic";
    
    
    public DBHelper(Context context)
    {
       super(context, DATABASE_NAME , null, 1);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
       // TODO Auto-generated method stub
       db.execSQL(
       "CREATE TABLE IF NOT EXISTS "+CONTACTS_TABLE_NAME+"("+CONTACTS_COLUMN_ID+" INTEGER NOT NULL PRIMARY KEY, ime_in_priimek VARCHAR(30), dan_rojstva TINYINT, mesec_rojstva TINYINT, leto_rojstva TINYINT, telefonska_stevilka VARCHAR(20), vera VARCHAR(30), sporocilo_za_rojstni_dan TEXT, sporocilo_za_bozic TEXT)");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // TODO Auto-generated method stub
       db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
       onCreate(db);
    }
    
    public boolean insertContact(Integer id, String imeInPriimek, int danRojstva, int mesecRojstva, int letoRojstva, String telefonskaStevilka, String vera, String sporociloZaRojstniDan, String sporociloZaBozic)
    {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_ID, id);
       contentValues.put(CONTACTS_COLUMN_IMEINPRIIMEK, imeInPriimek);
       contentValues.put(CONTACTS_COLUMN_DANROJSTVA, danRojstva);
        contentValues.put(CONTACTS_COLUMN_MESECROJSTVA, mesecRojstva);
        contentValues.put(CONTACTS_COLUMN_LETOROJSTVA, letoRojstva);
       contentValues.put(CONTACTS_COLUMN_TELEFONSKASTEVILKA, telefonskaStevilka);
       contentValues.put(CONTACTS_COLUMN_VERA, vera);
       contentValues.put(CONTACTS_COLUMN_SPOROCILOZAROJSTNIDAN, sporociloZaRojstniDan);
       contentValues.put(CONTACTS_COLUMN_SPOROCILOZABOZIC, sporociloZaBozic);
       db.insert(CONTACTS_TABLE_NAME, null, contentValues);
       return true;
    }
    
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM "+CONTACTS_TABLE_NAME+" WHERE id="+id+"", null );
        return res;
     }
    
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
     }
    
    public boolean updateContact(Integer id, String imeInPriimek, int danRojstva, int mesecRojstva, int letoRojstva, String telefonskaStevilka, String vera, String sporociloZaRojstniDan, String sporociloZaBozic)
    {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put(CONTACTS_COLUMN_IMEINPRIIMEK, imeInPriimek);
       contentValues.put(CONTACTS_COLUMN_DANROJSTVA, danRojstva);
       contentValues.put(CONTACTS_COLUMN_MESECROJSTVA, mesecRojstva);
       contentValues.put(CONTACTS_COLUMN_LETOROJSTVA, letoRojstva);
       contentValues.put(CONTACTS_COLUMN_TELEFONSKASTEVILKA, telefonskaStevilka);
       contentValues.put(CONTACTS_COLUMN_VERA, vera);
       contentValues.put(CONTACTS_COLUMN_SPOROCILOZAROJSTNIDAN, sporociloZaRojstniDan);
       contentValues.put(CONTACTS_COLUMN_SPOROCILOZABOZIC, sporociloZaBozic);
       db.update(CONTACTS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
       return true;
    }
    
    public Integer deleteContact (Integer id)
    {
       SQLiteDatabase db = this.getWritableDatabase();
       return db.delete(CONTACTS_TABLE_NAME,
       "id = ? ",
       new String[] { Integer.toString(id) });
    }
    
    public ArrayList<Contact> getAllContacts()
    {
        ArrayList<Contact> array_list = new ArrayList<Contact>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + CONTACTS_TABLE_NAME;
        Cursor cursor =  db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setDay(cursor.getInt(2));
                contact.setMonth(cursor.getInt(3));
                contact.setYear(cursor.getInt(4));
                contact.setPhoneNumber(cursor.getInt(5));
                contact.setReligion(cursor.getString(6));
                contact.setBirthdayMessage(cursor.getString(7));
                contact.setChristmasMessage(cursor.getString(8));
                // Adding contact to list
                array_list.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return array_list;



        /*int id[] = new int[res.getCount()];
        int velikostBaze = id.length;
       res.moveToFirst();
       while(res.isAfterLast() == false){
       array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_IMEINPRIIMEK)));
       res.moveToNext();
       }
        return array_list;*/
    }
    
    /*public int getId(String imeInPriimek){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id FROM  kontakti WHERE id=0";
        Cursor mCursor = db.rawQuery(query, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor.getInt(mCursor.getColumnIndex("_id"));
    }*/
    
}
