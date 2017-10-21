package com.example.samodejnecestitke;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milos_000 on 17.10.2017..
 */

public class Contact {

    //private variables
    int id;
    String ime_in_priimek;
    int dan_rojstva;
    int mesec_rojstva;
    int leto_rojstva;
    int telefonska_stevilka;
    String vera;
    String sporocilo_za_rojstni_dan;
    String sporocilo_za_bozic;

    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String imeInPriimek, int danRojstva, int mesecRojstva, int letoRojstva, int telefonskaStevilka, String vera, String sporociloZaRojstniDan, String sporociloZaBozic){
        this.id = id;
        this.ime_in_priimek = imeInPriimek;
        this.dan_rojstva = danRojstva;
        this.mesec_rojstva = mesecRojstva;
        this.leto_rojstva = letoRojstva;
        this.telefonska_stevilka = telefonskaStevilka;
        this.vera = vera;
        this.sporocilo_za_rojstni_dan = sporociloZaRojstniDan;
        this.sporocilo_za_bozic = sporociloZaBozic;
    }


    // getting ID
    public int getID(){
        return this.id;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }

    public int getIdByName(ArrayList<Contact> vsiKontakti, String iskaniKontakt){
        int iskaniID = 0;
        for(Contact kontakt:vsiKontakti){
            if(kontakt.getName().equals(iskaniKontakt)){
                iskaniID=kontakt.getID();
                break;
            }
        }
        return iskaniID;
    }

    // getting name
    public String getName(){
        return this.ime_in_priimek;
    }

    // setting name
    public void setName(String name){
        this.ime_in_priimek = name;
    }

    // getting day of birth
    public int getDay(){
        return this.dan_rojstva;
    }

    // setting day of birth
    public void setDay(int day){
        this.dan_rojstva = day;
    }

    // getting month of birth
    public int getMonth(){
        return this.mesec_rojstva;
    }

    // setting month of birth
    public void setMonth(int month){
        this.mesec_rojstva = month;
    }

    // getting year of birth
    public int getYear(){
        return this.leto_rojstva;
    }

    // setting year of birth
    public void setYear(int year){
        this.leto_rojstva = year;
    }


    // getting phone number
    public int getPhoneNumber(){
        return this.telefonska_stevilka;
    }

    // setting phone number
    public void setPhoneNumber(int phone_number){
        this.telefonska_stevilka = phone_number;
    }

    // getting religion
    public String getReligion(){
        return this.vera;
    }

    // setting religion
    public void setReligion(String religion){
        this.vera = religion;
    }

    // getting birthdayMessage
    public String getBirthdayMessage(){
        return this.sporocilo_za_rojstni_dan;
    }

    // setting birthdayMessage
    public void setBirthdayMessage(String birthdayMessage){
        this.sporocilo_za_rojstni_dan = birthdayMessage;
    }

    // getting christmasMessage
    public String getChristmasMessage(){
        return this.sporocilo_za_bozic;
    }

    // setting christmasMessage
    public void setChristmasMessage(String christmasMessage){
        this.sporocilo_za_bozic = christmasMessage;
    }

    public ArrayList<String> vrniImenaInPriimkeVsehKontaktov(List<Contact> vsiKontakti){
        ArrayList<String> imenaKontaktov = new ArrayList<String>();
        for(Contact kontakt:vsiKontakti){
            imenaKontaktov.add(kontakt.getName());
        }
        return imenaKontaktov;
    }
}
