/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.tablemahasiswa;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Pizaini
 */
public class Mahasiswa {

    private final StringProperty nama = new SimpleStringProperty(this, "nama");
    private final StringProperty nim = new SimpleStringProperty(this, "nim");
    private final IntegerProperty umur = new SimpleIntegerProperty(this, "umur");
    
    public Mahasiswa() {
    }
    
    public Mahasiswa(String nama, String nim, Integer umur) {
        this.nama.set(nama);
        this.nim.set(nim);
        this.umur.set(umur);
    }

    public int getUmum() {
        return umur.get();
    }

    public void setUmum(int value) {
        umur.set(value);
    }

    public IntegerProperty umumProperty() {
        return umur;
    }
    
    
    public String getNim() {
        return nim.get();
    }

    public void setNim(String value) {
        nim.set(value);
    }

    public StringProperty nimProperty() {
        return nim;
    }
    

    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
    }

    
    
}
