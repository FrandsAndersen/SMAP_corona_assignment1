package com.example.coronaStats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DecimalFormat;

public class EditActivity extends AppCompatActivity {

    private TextView tvCountryName;
    private TextView tvUserRating;
    private EditText eTxtUserNote;
    private SeekBar sbRatingBar;
    private ImageView imgFlag;
    private Button btnCancel;
    private Button btnOk;

    private EditActivityViewModel _editViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Setup ViewModel
        _editViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(EditActivityViewModel.class);

        _editViewModel.getCountryData().observe(this, new Observer<CountryModel>() {
            @Override
            public void onChanged(CountryModel countryModel) {
                updateCountryData(countryModel);
            }
        });

        setupUI();
    }

    private void updateCountryData(CountryModel countryModel) {
        tvCountryName.setText(countryModel.getCountryName());
        tvUserRating.setText(countryModel.getUserRating());
        eTxtUserNote.setText(countryModel.getUserNote());
        imgFlag.setImageResource(countryModel.getImgRessource());
    }

    private void setupListeners() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCancel();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goOk();
            }
        });

        sbRatingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateSlideNumber(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateSlideNumber(int progress) {
        CountryModel countryModel = _editViewModel.getCountryData().getValue();
        countryModel.setUserRating(convertValueToDouble(progress));
        _editViewModel.setCountryData(countryModel);
    }

    private void goOk() {
        CountryModel countryModel = _editViewModel.getCountryData().getValue();
        countryModel.setUserNote(eTxtUserNote.getText().toString());

        Intent resultData = new Intent();
        Gson gson = new Gson();
        resultData.putExtra(Constants.COUNTRYDATA,gson.toJson(countryModel));
        setResult(RESULT_OK,resultData);

        finish();
    }

    private void goCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public String convertValueToDouble(int value){
        double doubleVal = 0.0;
        doubleVal = .1f * value;
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(doubleVal);
    }

    private void setupUI() {
        tvCountryName = findViewById(R.id.tvCountryName);
        tvUserRating = findViewById(R.id.tvUserRating);
        eTxtUserNote = findViewById(R.id.eTxtUserNote);
        sbRatingBar = findViewById(R.id.sbSeekBar);
        sbRatingBar.setMax(100);

        imgFlag = findViewById(R.id.imgFlag);
        btnCancel = findViewById(R.id.btnCancel);
        btnOk = findViewById(R.id.btnOk);

        if(_editViewModel.getCountryData().getValue() == null){
            Intent data = getIntent();
            Gson gson = new Gson();
            CountryModel countryModel = gson.fromJson(data.getStringExtra(Constants.COUNTRYDATA),CountryModel.class);
            _editViewModel.setCountryData(countryModel);
        }
        if (!_editViewModel.getCountryData().getValue().getUserRating().equals("")) {
            float f = Float.parseFloat(_editViewModel.getCountryData().getValue().getUserRating());
            int progress = (int)(f*10);
            sbRatingBar.setProgress(progress);
        }

        setupListeners();
    }
}