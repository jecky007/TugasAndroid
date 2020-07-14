package com.example.splashscreen.model;

public class Book {
    private int id;
    private int harga;
    private int tahun;
    private String judul;
    private String penerbit;
    private String penulis;
    private String thumb;
    private String message;
    private boolean success;

    public Book() {
    }

    public Book(int id, int harga, int tahun, String judul, String penerbit, String penulis, String thumb, String message, boolean success) {
        this.id = id;
        this.harga = harga;
        this.tahun = tahun;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.thumb = thumb;
        this.message = message;
        this.success = success;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
