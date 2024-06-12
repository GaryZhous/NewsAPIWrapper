package com.example.newsapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Gary Zhou
 * @since v1.0.0
*/
public class NewsApi {
    private String apiKey;

    public NewsApi(String key) {
        this.apiKey = key;
    }

    /**
     * Fetches news from the News API with various optional parameters.
     * @param query the search query
     * @param from the start date for the news articles (optional)
     * @param to the end date for the news articles (optional)
     * @param sortBy method of sorting the news articles (optional)
     * @param pageSize number of results per page (optional)
     * @param page page number of the results (optional)
     * @return a NewsResponse object containing the fetched news articles
     */
    public NewsResponse getNews(String query, String from, String to, String sortBy, Integer pageSize, Integer page) {
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            StringBuilder urlString = new StringBuilder("https://newsapi.org/v2/everything?q=" + encodedQuery);

            // Add optional parameters only if they are provided
            if (from != null && !from.isEmpty()) {
                urlString.append("&from=").append(from);
            }
            if (to != null && !to.isEmpty()) {
                urlString.append("&to=").append(to);
            }
            if (sortBy != null && !sortBy.isEmpty()) {
                urlString.append("&sortBy=").append(sortBy);
            }
            if (pageSize != null) {
                urlString.append("&pageSize=").append(pageSize);
            }
            if (page != null) {
                urlString.append("&page=").append(page);
            }

            urlString.append("&apiKey=").append(apiKey);
            URL url = new URL(urlString.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            System.out.println("Sending request to URL : " + url);
            System.out.println("Response Code : " + con.getResponseCode());

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            con.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.toString(), NewsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fetches top headlines with optional parameters.
     * @param country The 2-letter ISO 3166-1 code of the country (optional)
     * @param category The category of the news (optional)
     * @param sources Comma-separated string of identifiers for the news sources (optional)
     * @param query Keywords or phrases to search for in the article (optional)
     * @param pageSize Number of results per page (optional)
     * @param page Page number of the results (optional)
     * @return a NewsResponse object containing the fetched news articles
     */
    public NewsResponse getTopHeadlines(String country, String category, String sources, String query, Integer pageSize, Integer page) {
        try {
            StringBuilder urlBuilder = new StringBuilder("https://newsapi.org/v2/top-headlines?apiKey=" + apiKey);
            
            if (country != null && !country.isEmpty()) {
                urlBuilder.append("&country=").append(country);
            }
            if (category != null && !category.isEmpty()) {
                urlBuilder.append("&category=").append(category);
            }
            if (sources != null && !sources.isEmpty()) {
                urlBuilder.append("&sources=").append(sources);
            }
            if (query != null && !query.isEmpty()) {
                urlBuilder.append("&q=").append(URLEncoder.encode(query, "UTF-8"));
            }
            if (pageSize != null) {
                urlBuilder.append("&pageSize=").append(pageSize);
            }
            if (page != null) {
                urlBuilder.append("&page=").append(page);
            }

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("Sending request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            con.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.toString(), NewsResponse.class);
        } catch (Exception e) {
            System.err.println("Exception occurred while fetching top headlines: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fetches the available news sources from the News API with optional filtering.
     * @param category The category of the news (optional)
     * @param language The language of the news (optional)
     * @param country The country of the news (optional)
     * @return a SourceResponse object containing a list of news sources
     */
    public SourceResponse getNewsSources(String category, String language, String country) {
        try {
            StringBuilder urlBuilder = new StringBuilder("https://newsapi.org/v2/top-headlines/sources?apiKey=" + apiKey);

            if (category != null && !category.isEmpty()) {
                urlBuilder.append("&category=").append(URLEncoder.encode(category, "UTF-8"));
            }
            if (language != null && !language.isEmpty()) {
                urlBuilder.append("&language=").append(URLEncoder.encode(language, "UTF-8"));
            }
            if (country != null && !country.isEmpty()) {
                urlBuilder.append("&country=").append(URLEncoder.encode(country, "UTF-8"));
            }

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("Sending request to URL: " + url);
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            con.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.toString(), SourceResponse.class);
        } catch (Exception e) {
            System.err.println("Exception occurred while fetching news sources: " + e.getMessage());
            return null;
        }
    }

    // Utility methods to extract specific information from the NewsResponse
    public int getTotalResults(NewsResponse response){
        return response.getTotalResults();
    }

    public List<String> getArticleTitles(NewsResponse response) {
        return response.getArticles().stream().map(Article::getTitle).collect(Collectors.toList());
    }

    public List<String> getArticleContents(NewsResponse response) {
        return response.getArticles().stream().map(Article::getContent).collect(Collectors.toList());
    }

    public List<String> getArticleAuthors(NewsResponse response) {
        return response.getArticles().stream().map(Article::getAuthor).collect(Collectors.toList());
    }

    public List<String> getArticleURLs(NewsResponse response) {
        return response.getArticles().stream().map(Article::getUrl).collect(Collectors.toList());
    }

    public List<String> getArticleSources(NewsResponse response) {
        return response.getArticles().stream().map(article -> article.getSource().getName()).collect(Collectors.toList());
    }

    public List<String> getArticleDescriptions(NewsResponse response) {
        return response.getArticles().stream().map(Article::getDescription).collect(Collectors.toList());
    }

    // Modified utility methods to include a limit on the number of articles returned
    public List<String> getArticleTitles(NewsResponse response, int limit) {
        return response.getArticles().stream()
                       .map(Article::getTitle)
                       .limit(limit)
                       .collect(Collectors.toList());
    }

    public List<String> getArticleContents(NewsResponse response, int limit) {
        return response.getArticles().stream()
                       .map(Article::getContent)
                       .limit(limit)
                       .collect(Collectors.toList());
    }

    public List<String> getArticleAuthors(NewsResponse response, int limit) {
        return response.getArticles().stream()
                       .map(Article::getAuthor)
                       .limit(limit)
                       .collect(Collectors.toList());
    }

    public List<String> getArticleURLs(NewsResponse response, int limit) {
        return response.getArticles().stream()
                       .map(Article::getUrl)
                       .limit(limit)
                       .collect(Collectors.toList());
    }

    // Utility methods to fetch details from SourceResponse
    public List<String> getArticleSources(NewsResponse response, int limit) {
        return response.getArticles().stream()
                       .map(article -> article.getSource().getName())
                       .limit(limit)
                       .collect(Collectors.toList());
    }

    public List<String> getArticleDescriptions(NewsResponse response, int limit) {
        return response.getArticles().stream()
                       .map(Article::getDescription)
                       .limit(limit)
                       .collect(Collectors.toList());
    }

    public List<String> getSourceIds(SourceResponse response) {
        return response.getSources().stream().map(NewsSource::getId).collect(Collectors.toList());
    }

    public List<String> getSourceNames(SourceResponse response) {
        return response.getSources().stream().map(NewsSource::getName).collect(Collectors.toList());
    }

    public List<String> getSourceDescriptions(SourceResponse response) {
        return response.getSources().stream().map(NewsSource::getDescription).collect(Collectors.toList());
    }

    public List<String> getSourceURLs(SourceResponse response) {
        return response.getSources().stream().map(NewsSource::getUrl).collect(Collectors.toList());
    }

    public List<String> getSourceCategories(SourceResponse response) {
        return response.getSources().stream().map(NewsSource::getCategory).collect(Collectors.toList());
    }

    public List<String> getSourceLanguages(SourceResponse response) {
        return response.getSources().stream().map(NewsSource::getLanguage).collect(Collectors.toList());
    }

    public List<String> getSourceCountries(SourceResponse response) {
        return response.getSources().stream().map(NewsSource::getCountry).collect(Collectors.toList());
    }
    // Utility methods to fetch details from SourceResponse with a limit on the number of results
    public List<String> getSourceIds(SourceResponse response, int limit) {
        return response.getSources().stream().map(NewsSource::getId).limit(limit).collect(Collectors.toList());
    }

    public List<String> getSourceNames(SourceResponse response, int limit) {
        return response.getSources().stream().map(NewsSource::getName).limit(limit).collect(Collectors.toList());
    }

    public List<String> getSourceDescriptions(SourceResponse response, int limit) {
        return response.getSources().stream().map(NewsSource::getDescription).limit(limit).collect(Collectors.toList());
    }

    public List<String> getSourceURLs(SourceResponse response, int limit) {
        return response.getSources().stream().map(NewsSource::getUrl).limit(limit).collect(Collectors.toList());
    }

    public List<String> getSourceCategories(SourceResponse response, int limit) {
        return response.getSources().stream().map(NewsSource::getCategory).limit(limit).collect(Collectors.toList());
    }

    public List<String> getSourceLanguages(SourceResponse response, int limit) {
        return response.getSources().stream().map(NewsSource::getLanguage).limit(limit).collect(Collectors.toList());
    }

    public List<String> getSourceCountries(SourceResponse response, int limit) {
        return response.getSources().stream().map(NewsSource::getCountry).limit(limit).collect(Collectors.toList());
    }
}
