package com.example.newsapi;

import java.util.List;

public class SourceResponse {
    private String status;
    private List<NewsSource> sources;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NewsSource> getSources() {
        return sources;
    }

    public void setSources(List<NewsSource> sources) {
        this.sources = sources;
    }
}