package com.example.apka2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table1")
public class Element {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public long Id;

    @NonNull
    @ColumnInfo(name = "producent")
    public String producent;

    @NonNull
    @ColumnInfo(name = "model")
    public String model;

    @NonNull
    @ColumnInfo(name = "wersja")
    public String wersja;

    @NonNull
    @ColumnInfo(name = "strona_www")
    public String strona_www;

    public Element(@NonNull String producent, @NonNull String model, @NonNull String wersja, @NonNull String strona_www) {
        this.producent = producent;
        this.model=model;
        this.wersja=wersja;
        this.strona_www=strona_www;

    }

    public long getId() {
        return Id;
    }

    @NonNull
    public String getProducent() {
        return producent;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public void setProducent(@NonNull String producent) {
        this.producent = producent;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    @NonNull
    public String getWersja() {
        return wersja;
    }

    @NonNull
    public String getStrona_www() {
        return strona_www;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    public void setWersja(@NonNull String wersja) {
        this.wersja = wersja;
    }

    public void setStrona_www(@NonNull String strona_www) {
        this.strona_www = strona_www;
    }
}
