package com.example.mini_projet;

public class DataClass {
    private String dataTitle;
    private String dataDescription;
    private String dataPrice;
    private String dataImage;

    public DataClass(String dataTitle, String dataDescription, String dataPrice, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDescription = dataDescription;
        this.dataPrice = dataPrice;
        this.dataImage = dataImage;
    }

    public DataClass()
    {

    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public void setDataDescription(String dataDescription) {
        this.dataDescription = dataDescription;
    }

    public String getDataPrice() {
        return dataPrice;
    }

    public void setDataPrice(String dataPrice) {
        this.dataPrice = dataPrice;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDescription() {
        return dataDescription;
    }



    public String getDataImage() {
        return dataImage;
    }
}
