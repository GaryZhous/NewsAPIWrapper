# Java Maven Wrapper for News API
[![](https://jitpack.io/v/GaryZhous/NewsAPIWrapper.svg)](https://jitpack.io/#GaryZhous/NewsAPIWrapper)

There's already an existing wrapper in Java, but that's for Gradle users. So I made this Maven wrapper. Check out the amazing News API here!
[News API official website](https://newsapi.org/)

Add the following dependencies to your pom.xml file to use my code through maven!
```xml
	<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
	</repositories>
	<dependency>
		<groupId>com.github.GaryZhous</groupId>
		<artifactId>NewsAPIWrapper</artifactId>
		<version>v1.0.2</version> <!-- or v1.0.1 -->
	</dependency>
```
### How to use it?
```Java
//endpoint 1 Everything
NewsApi newsApi = new NewsApi("your_api_key");
//default getNews
NewsResponse response = newsApi.getNews(query);
//advanced getNews
NewsResponse response = newsApi.getNews(query, search_in_title_description_or_content, domains, excluded_domains, start_time_stamp, end_time_stamp, language, sort_by, page_size, page_num);
```
The first API endpoint can be accessed by creating a NewsApi object by passing your API key. The call function "getNews" with parameters (except for the first one, the other params are optional. If you don't want your query results to be based on them, please replace them with "null")
for the "query" param, here's the pattern: Keywords or phrases to search for in the article title and body. Users can enclose phrases in quotes for exact matches, prepend a plus symbol (+) to ensure certain words or phrases appear, use a minus symbol (-) to exclude words, and combine keywords like AND, OR, NOT for complex queries. These elements can be grouped with parentheses for further specificity. The entire search query must be URL-encoded and is limited to 500 characters.
```Java
//endpoint 2 top headlines
NewsResponse response = newsApi.getTopHeadlines(country, sources, query, page_size, page_num);
```
Similar here, country, sources, query, page_size, and page_num are all optional parameters, you can leave them to "null".
```Java
//endpoint 3 News Sources
SourceResponse response = newsApi.getNewsSources(category, country, language);
```
For the third endpoint, you can fetch information about news sources (e.g. CNN & BBC) by passing proper parameters to the "getNewsSources" function. You can also leave parameters category, country, and language to "null" since they are optional.
### Then...
you can either use the getters of the response classes or pass those response objects back to "newsApi"'s member functions to access values like "Author", "Articles", and "Content".
```Java
//example here
NewsApi newsApi = new NewsApi("your_api_key_here");
SourceResponse response = newsApi.getNewsSources("technology", "en", "us");

if (response != null && response.getSources() != null) {
	response.getSources().forEach(source -> 
	System.out.println(source.getName() + " - " + source.getDescription()));
}
```
