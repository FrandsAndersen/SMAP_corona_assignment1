package com.example.coronaStats;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements CountryAdapter.ICountryItemClickedListener{

    public static final int REQUEST_CODE_DETAILS = 101;

    // widgets
    private RecyclerView _rcvCountries;
    private CountryAdapter _adapter;
    private Button btnExit;

    // viewmodel
    private ListActivityViewModel _listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnExit = findViewById(R.id.btnExit);

        // setup recyclerview with adapter and layout manager
        _adapter = new CountryAdapter(this);
        _rcvCountries = findViewById(R.id.rcvCountries);
        _rcvCountries.setLayoutManager(new LinearLayoutManager(this));
        _rcvCountries.setAdapter(_adapter);

        _listViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(ListActivityViewModel.class);

        _listViewModel.getCountryData().observe(this, new Observer<ArrayList<CountryModel>>() {
            @Override
            public void onChanged(ArrayList<CountryModel> countryModels) {
                _adapter.updateCountryList(_listViewModel.getCountryData().getValue());
            }
        });


        if(_listViewModel.getCountryData().getValue() == null){
            ArrayList<CountryModel> ctry = readCountriesFromCsv();
            _listViewModel.setCountryData(ctry);
        }

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goExit();
            }
        });
    }

    private void goExit() {
        finish();
    }

    // The following code is based on this example on how to read a csv-file in java:
    // https://stackoverflow.com/questions/43055661/reading-csv-file-in-android-app/43055945
    private ArrayList<CountryModel> readCountriesFromCsv() {
        ArrayList<CountryModel> cList = new ArrayList<CountryModel>();
        try{

            CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource(R.raw.coronastats)));//Specify asset file name
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                String[] values = nextLine[0].split(";");

                CountryModel cm = new CountryModel(values[0],values[1],"","",values[2],values[3]);
                cList.add(cm);
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }

        return cList;
    }


    @Override
    public void onCountryClicked(int index) {
        CountryModel country = _listViewModel.getCountryData().getValue().get(index);

        Gson gson = new Gson();
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constants.COUNTRYDATA,gson.toJson(country));
        startActivityForResult(intent,REQUEST_CODE_DETAILS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_DETAILS){
            if(resultCode == RESULT_OK){
                Gson gson = new Gson();
                CountryModel countryModel = gson.fromJson(data.getStringExtra(Constants.COUNTRYDATA),CountryModel.class);

                for (CountryModel cm: _listViewModel.getCountryData().getValue()) {
                    if(cm.getCountryCode().equals(countryModel.getCountryCode())){
                        cm.setUserNote(countryModel.getUserNote());
                        cm.setUserRating(countryModel.getUserRating());
                        _listViewModel.setCountryData(_listViewModel.getCountryData().getValue());
                    }
                }
            }
        }
    }
}