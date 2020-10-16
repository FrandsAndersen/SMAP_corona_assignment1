package com.example.coronaStats;

public class CountryModel {
    private String _countryName;
    private String _countryCode;
    private String _userRating;
    private String _userNote;
    private String _nbrOfCases;
    private String _nbrOfDeaths;

    public CountryModel(String countryName, String countryCode, String userRating, String userNote, String nbrOfCases, String nbrOfDeaths) {
        _countryName = countryName;
        _countryCode = countryCode;
        _userRating = userRating;
        _userNote = userNote;
        _nbrOfCases = nbrOfCases;
        _nbrOfDeaths = nbrOfDeaths;
    }

    public String getCountryName() {
        return _countryName;
    }

    public String getUserNote() {
        return _userNote;
    }

    public String getNbrOfCases() {
        return _nbrOfCases;
    }

    public String getNbrOfDeaths() {
        return _nbrOfDeaths;
    }

    public String getUserRating() {
        return _userRating;
    }

    public void setUserRating(String userRating) {
        this._userRating = userRating;
    }

    public void setUserNote(String userNote) {
        this._userNote = userNote;
    }

    public String getCountryCode() {
        return _countryCode;
    }

    public void setCountryCode(String _countryCode) {
        this._countryCode = _countryCode;
    }

    public int getImgRessource() {
        if(_countryCode.equals("CA"))
            return R.drawable.ca;
        else if(_countryCode.equals("DK"))
            return R.drawable.dk;
        else if(_countryCode.equals("CN"))
            return R.drawable.cn;
        else if(_countryCode.equals("DE"))
            return R.drawable.de;
        else if(_countryCode.equals("FI"))
            return R.drawable.fi;
        else if(_countryCode.equals("IN"))
            return R.drawable.in;
        else if(_countryCode.equals("JP"))
            return R.drawable.jp;
        else if(_countryCode.equals("NO"))
            return R.drawable.no;
        else if(_countryCode.equals("RU"))
            return R.drawable.ru;
        else if(_countryCode.equals("SE"))
            return R.drawable.se;
        else if(_countryCode.equals("US"))
            return R.drawable.us;
        return 0;
    }


}
