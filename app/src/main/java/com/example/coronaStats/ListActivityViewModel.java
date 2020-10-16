package com.example.coronaStats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ListActivityViewModel extends ViewModel {
    private MutableLiveData<ArrayList<CountryModel>> _countries;

    public LiveData<ArrayList<CountryModel>> getCountryData() {
        if(_countries == null){
            _countries = new MutableLiveData<ArrayList<CountryModel>>();
        }
        return _countries;
    }

    public void setCountryData(ArrayList<CountryModel> countries){
        _countries.setValue(countries);
    }

}
