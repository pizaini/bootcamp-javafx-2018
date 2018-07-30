/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.sort;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Pizaini
 */
public class ContohArray1Controller implements Initializable {
    @FXML
    private Button buttonTambahData;
    @FXML
    private ListView<String> listViewTidakTerurut, listViewTerurut;
    @FXML
    private TextField textNamaBuah;
    @FXML
    private Button buttonUrutkan;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void tambahData(){
        String angka = textNamaBuah.getText();
        //pastikan ada karakter yg diinputkan
        if(angka.trim().length() > 0){
            Integer angkaInt = 0;
            try{
                angkaInt = Integer.parseInt(angka);
            }catch(NumberFormatException ex){
                System.out.println("Maaf, input harus berupa angka : "+ex.getMessage());
            }
            listViewTidakTerurut.getItems().add(angkaInt.toString());
            //clear textfield
            textNamaBuah.clear();
        }
    }
    
    public void hapusData(){
        int indexTerpilih = listViewTidakTerurut.getSelectionModel().getSelectedIndex();
        if(indexTerpilih > -1){
            listViewTidakTerurut.getItems().remove(indexTerpilih);
            listViewTidakTerurut.getSelectionModel().clearSelection();
        }
    }
    
    public void urutkan(){
        //ambil data dari table yang tidak terurut
        ObservableList<String> items = listViewTidakTerurut.getItems();
        //konversi observeable list ke String[]
        String[] arrayString = items.toArray(new String[items.size()]);
      
       //konversi arrays string menjadi array int
       int[] arrayInt2 = Arrays.stream(arrayString).mapToInt(Integer::parseInt).toArray();

       //urutkan dengan bubble sort
        BubbleSort bubbleSort = new BubbleSort(arrayInt2);
        int[] terurutAscending = bubbleSort.menaik();
       //koversi ke string array
       String[] arrayTerurutString = 
               Arrays.stream(terurutAscending).mapToObj(String::valueOf).toArray(String[]::new);
       
       //tampilkan ke table yang sudah terurut
       listViewTerurut.getItems().setAll(arrayTerurutString);
    }
    
    public void urutkanMenurun(){
        //ambil data dari table yang tidak terurut
        ObservableList<String> items = listViewTidakTerurut.getItems();
        //konversi observeable list ke String[]
        String[] arrayString = items.toArray(new String[items.size()]);
       //konversi arrays string menjadi array int
       int[] arrayInt2 = Arrays.stream(arrayString).mapToInt(Integer::parseInt).toArray();
       //urutkan dengan bubble sort
        BubbleSort bubbleSort = new BubbleSort(arrayInt2);
        int[] terurutDescending = bubbleSort.menurun();
       //koversi ke string array
       String[] arrayTerurutString = 
               Arrays.stream(terurutDescending).mapToObj(String::valueOf).toArray(String[]::new);
       //tampilkan ke table yang sudah terurut
       listViewTerurut.getItems().setAll(arrayTerurutString);     
    }
    
}