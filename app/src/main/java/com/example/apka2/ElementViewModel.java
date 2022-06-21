package com.example.apka2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ElementViewModel extends AndroidViewModel {

    private  ElementRespository mRespository;
    private  LiveData<List<Element>> mAllElements;

    public ElementViewModel(@NonNull Application application) {
        super(application);
        mRespository = new ElementRespository(application);
        mAllElements = mRespository.getAllElements();
    }
    LiveData<List<Element>> getmAllElements(){
        return mAllElements;
    }

    public void deleteAllElements(){
        mRespository.deleteAll();
    }
    public void insert(Element element){
        mRespository.insert(element);
    }
    public void update(Element element){
        mRespository.update(element);
    }
    public void delete(Element element){
        mRespository.delete(element);
    }
}
