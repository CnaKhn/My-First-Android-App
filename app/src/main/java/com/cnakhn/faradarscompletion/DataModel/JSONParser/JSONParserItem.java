package com.cnakhn.faradarscompletion.DataModel.JSONParser;

public class JSONParserItem {

    private String jsonImageUrl;
    private String jsonTitle;
    private int jsonLikes;

    public JSONParserItem(String jsonImageUrl, String jsonTitle, int jsonLikes) {
        this.jsonImageUrl = jsonImageUrl;
        this.jsonTitle = jsonTitle;
        this.jsonLikes = jsonLikes;
    }

    public String getJsonImageUrl() {
        return jsonImageUrl;
    }

    public String getJsonTitle() {
        return jsonTitle;
    }

    public int getJsonLikes() {
        return jsonLikes;
    }
}
