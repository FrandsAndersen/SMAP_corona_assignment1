package com.example.coronaStats;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_EDIT = 101;

    private TextView tvCountryName;
    private TextView tvCases;
    private TextView tvDeaths;
    private TextView tvUserRating;
    private TextView tvUserNote;
    private ImageView imgFlag;
    private Button btnBack;
    private Button btnEdit;

    private DetailsActivityViewModel _detailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Setup ViewModel
        _detailsViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(DetailsActivityViewModel.class);
        _detailsViewModel.getCountryData().observe(this, new Observer<CountryModel>() {
            @Override
            public void onChanged(CountryModel countryModel) {
                updateCountryData(countryModel);
            }
        });
        setupUI();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goEdit();
            }
        });
    }

    private void updateCountryData(CountryModel countryModel) {
        tvCountryName.setText(countryModel.getCountryName());
        tvCases.setText(countryModel.getNbrOfCases());
        tvDeaths.setText(countryModel.getNbrOfDeaths());
        tvUserRating.setText(countryModel.getUserRating());
        tvUserNote.setText(countryModel.getUserNote());
        imgFlag.setImageResource(countryModel.getImgRessource());
    }

    private void goEdit() {
        Gson gson = new Gson();
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(Constants.COUNTRYDATA,gson.toJson(_detailsViewModel.getCountryData().getValue()));
        startActivityForResult(intent,REQUEST_CODE_EDIT);
    }

    private void goBack() {
        CountryModel countryModel = _detailsViewModel.getCountryData().getValue();

        Intent resultData = new Intent();
        Gson gson = new Gson();
        resultData.putExtra(Constants.COUNTRYDATA,gson.toJson(countryModel));
        setResult(RESULT_OK,resultData);
        finish();
    }

    private void setupUI() {
        tvCountryName = findViewById(R.id.tvCountryName);
        tvCases = findViewById(R.id.tvCases);
        tvDeaths = findViewById(R.id.tvDeaths);
        tvUserRating = findViewById(R.id.tvUserRating);
        tvUserNote = findViewById(R.id.tvUserNote);
        imgFlag = findViewById(R.id.imgFlag);

        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);

        if(_detailsViewModel.getCountryData().getValue() == null){
            Intent data = getIntent();
            Gson gson = new Gson();
            CountryModel countryModel = gson.fromJson(data.getStringExtra(Constants.COUNTRYDATA),CountryModel.class);
            _detailsViewModel.setCountryData(countryModel);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_EDIT){
            if(resultCode == RESULT_OK){
                Gson gson = new Gson();

                CountryModel countryModel = gson.fromJson(data.getStringExtra(Constants.COUNTRYDATA),CountryModel.class);
                _detailsViewModel.setCountryData(countryModel);
            }
        }
    }
}