package pizaini.bootcamp.persegipanjang;

public class PersegiPanjang {
    private int panjang;
    private int lebar;
    
    public PersegiPanjang(int panjang, int lebar){
        this.panjang = panjang;
        this.lebar = lebar;
    }
    
    public double hitungLuas(){
        double luas = this.lebar * this.panjang;
        return luas;
    }
    
    public double hitungKeliling(){
        double keliling = 2 * (this.panjang + this.lebar);
        return keliling;
    }
}
