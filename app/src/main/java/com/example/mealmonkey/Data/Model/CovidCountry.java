package com.example.mealmonkey.Data.Model;

import com.google.gson.annotations.SerializedName;

public class CovidCountry {

    @SerializedName("country")
    private String Country;

    @SerializedName("cases")
    private String TotalCases;

    @SerializedName("todayCases")
    private String TodayCases;

    @SerializedName("deaths")
    private String TotalDeaths;

    @SerializedName("todayDeaths")
    private String TodayDeaths;

    @SerializedName("recovered")
    private String TotalRecovered;

    @SerializedName("active")
    private String NowActive;

    @SerializedName("critical")
    private String NowCritical;

    @SerializedName("casesPerOneMillion")
    private String CaseParOneMillion;

    @SerializedName("deathsPerOneMillion")
    private String DeathsPerOneMillion;

    @SerializedName("totalTests")
    private String TotalTests;

    @SerializedName("testsPerOneMillion")
    private String TestsPerOneMillion;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getTotalCases() {
        return TotalCases;
    }

    public void setTotalCases(String totalCases) {
        TotalCases = totalCases;
    }

    public String getTodayCases() {
        return TodayCases;
    }

    public void setTodayCases(String todayCases) {
        TodayCases = todayCases;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public String getTodayDeaths() {
        return TodayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        TodayDeaths = todayDeaths;
    }

    public String getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        TotalRecovered = totalRecovered;
    }

    public String getNowActive() {
        return NowActive;
    }

    public void setNowActive(String nowActive) {
        NowActive = nowActive;
    }

    public String getNowCritical() {
        return NowCritical;
    }

    public void setNowCritical(String nowCritical) {
        NowCritical = nowCritical;
    }

    public String getCaseParOneMillion() {
        return CaseParOneMillion;
    }

    public void setCaseParOneMillion(String caseParOneMillion) {
        CaseParOneMillion = caseParOneMillion;
    }

    public String getDeathsPerOneMillion() {
        return DeathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(String deathsPerOneMillion) {
        DeathsPerOneMillion = deathsPerOneMillion;
    }

    public String getTotalTests() {
        return TotalTests;
    }

    public void setTotalTests(String totalTests) {
        TotalTests = totalTests;
    }

    public String getTestsPerOneMillion() {
        return TestsPerOneMillion;
    }

    public void setTestsPerOneMillion(String testsPerOneMillion) {
        TestsPerOneMillion = testsPerOneMillion;
    }
}
