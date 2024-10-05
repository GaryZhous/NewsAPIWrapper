# Java Wrapper Library for News API
[![](https://jitpack.io/v/GaryZhous/NewsAPIWrapper.svg)](https://jitpack.io/#GaryZhous/NewsAPIWrapper)

There's already an unofficial wrapper in Java, but that's only for Gradle build. So I made this wrapper and hosted it on JitPack which supports both Maven and Gradle. Check out the amazing News API here!
[News API official website](https://newsapi.org/)
### Contribute to this project by submit an issue or pull request!

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
		<version>Tag</version> <!-- v1.0.2 or v1.0.1 -->
	</dependency>
```

Or this in Gradle
```gradle
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
	dependencies {
	        implementation 'com.github.GaryZhous:NewsAPIWrapper:Tag' //v1.0.1 or v1.0.2
	}
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
The first API endpoint can be accessed by creating a NewsApi object by passing your API key. Then, call the function "getNews" with parameters (except for the first one, the other params are optional. If you don't want your query results to be based on them, please replace them with "null"). For the "**query**" param, here's the pattern: Keywords or phrases to search for in the article title and body. Users can enclose phrases in quotes for exact matches, prepend a plus symbol (**+**) to ensure certain words or phrases appear, use a minus symbol (**-**) to exclude words, and combine keywords like **AND, OR, NOT** for complex queries. These elements can be grouped with parentheses for further specificity. The entire search query must be URL-encoded and is limited to **500** characters.
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
You can either use the getters of the response classes or pass those response objects back to "newsApi"'s member functions to access values like "Author", "Articles", and "Content".
```Java
NewsApi newsApi = new NewsApi("your_api_key_here");
//Example 1
SourceResponse response = newsApi.getNewsSources("technology", "en", "us");

if (response != null && response.getSources() != null) {
	response.getSources().forEach(source -> 
	System.out.println(source.getName() + " - " + source.getDescription()));
}

//Example 2
NewsResponse news_response = newsApi.getNews("bitcoin+market");
List<String> ArticleList = newsApi.getArticleTitles(news_response, 5); //get top 5 news articles from the response
ArticleList.forEach(System.out::println);

//Example 3
NewsResponse news_response = newsApi.getNews("apple", null, null, null, null, "2024-08-10", "2024-08-11", null, "popularity", null, null); //a wrapper for GET https://newsapi.org/v2/everything?q=apple&from=2024-08-10&to=2024-08-11&sortBy=popularity&apiKey=your_api_key_here
System.out.prinln(String.format("Total Results: %d", newsApi.getTotalResults(news_response))); //get the number of total results from the query
```
