package com.example.mankeu;

public class Transaksi {
    private String id_transaksi;
    private String tanggal_transaksi;
    private String categori_transaksi;
    private String label_transaksi;
    private String jumlah;

    public Transaksi(String id_transaksi, String tanggal_transaksi, String categori_transaksi, String label_transaksi, String jumlah){
        this.id_transaksi = id_transaksi;
        this.tanggal_transaksi = tanggal_transaksi;
        this.categori_transaksi = categori_transaksi;
        this.label_transaksi = label_transaksi;
        this.jumlah = jumlah;
    }
    public String getId_transaksi() {
        return id_transaksi;
    }
    public String getTanggal_transaksi() {
        return tanggal_transaksi;
    }
    public String getLabel_transaksi() {
        return label_transaksi;
    }
    public String getjumlah() {
        return jumlah;
    }
    public String getcategori_transaksi() {
        return categori_transaksi;
    }
}
