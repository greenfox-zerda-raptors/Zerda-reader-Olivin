package com.greenfox.zerdaReader.domain;

/**
 * Created by zoloe on 2017. 01. 18..
 */
public class TestJson {
    private final long id;
    private final String content;


    public TestJson(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

