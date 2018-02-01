package com.abhi.feedxmlparsing.model;

/**
 * Created by abhijeet on 1/2/18.
 */

public class Entry {

    public final String title;
    public final String link;
    public final String summary;

    public Entry(String title, String summary, String link) {
        this.title = title;
        this.summary = summary;
        this.link = link;
    }
}



