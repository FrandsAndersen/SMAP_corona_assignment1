package com.example.coronaStats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailsActivityViewModel extends ViewModel {
    private MutableLiveData<CountryModel> _country;

    public LiveData<CountryModel> getCountryData() {
        if(_country == null){
            _country = new MutableLiveData<CountryModel>();
        }
        return _country;
    }

    public void setCountryData(CountryModel country){
        _country.setValue(country);
    }
}
