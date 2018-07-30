/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.sort;

import java.util.Arrays;

/**
 *
 * @author Pizaini
 */
public class BubbleSort {
    private final int[] data;

    public BubbleSort(int[] data) {
        //mengcopy data array
        this.data = Arrays.copyOf(data, data.length);
    }
    
    public int[] menaik(){
        for(int j = 0; j < data.length - 1; j++){
            for(int i = data.length - 1; i > 0; i--){
                    int a = data[i];
                    int b = data[i-1];
                    if(a < b){
                        data[i] = b;
                        data[i-1] = a;
                    }
            }
        }
        return data;
    }
    
    public int[] menurun(){
        for(int j = 0; j < data.length - 1; j++){
            for(int i = data.length - 1; i > 0; i--){
                    int a = data[i];
                    int b = data[i-1];
                    if(a > b){
                        data[i] = b;
                        data[i-1] = a;
                    }
            }
        }
        return data;
    }
}
