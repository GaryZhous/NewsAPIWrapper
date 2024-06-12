# Java Maven Wrapper for News API
[![](https://jitpack.io/v/GaryZhous/NewsAPIWrapper.svg)](https://jitpack.io/#GaryZhous/NewsAPIWrapper)

There's already an existing wrapper in Java, but that's for Gradle users. So I made this Maven wrapper. Check out the amazing News API here!
[News API official website](https://newsapi.org/)

Add the following dependencies to your pom.xml file to use my code!
```
	<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
	</repositories>
	<dependency>
		<groupId>com.github.GaryZhous</groupId>
		<artifactId>NewsAPIWrapper</artifactId>
		<version>v1.0.1</version>
	</dependency>
```
### How to use it?
```Java
//endpoint 1 Everything
NewsApi newsApi = new NewsApi("your_api_key");
NewsResponse response = newsApi.getNews(the_thing_you_want_to_know, start_time_stamp, end_time_stamp, sort_by, page_size, page_num);
```
The first API endpoint can be accessed through creating a NewsApi object by passing your api key. The call function "getNews" with parameters (except for the first one, the other params are optional. If you don't want your query results be based on them, please replace them with "null")
