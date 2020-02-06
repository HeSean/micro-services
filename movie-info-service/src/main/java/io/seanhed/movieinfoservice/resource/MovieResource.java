package io.seanhed.movieinfoservice.resource;

import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionMessage.ItemsBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.seanhed.movieinfoservice.models.BookSummary;
import io.seanhed.movieinfoservice.models.Movie;

@RestController
@RequestMapping("/movies")
public class MovieResource {

	private String googleBooksApi = "https://www.googleapis.com/books/v1/volumes?q=";
	private String author = "sean+hed";

	@RequestMapping("/{movieId}")
	public Movie getMovie(@PathVariable("movieId") String movieId) {
		RestTemplate restTemplate = new RestTemplate();
		BookSummary bookSummary = restTemplate.getForObject(googleBooksApi + author, BookSummary.class);

		System.out.println(bookSummary.getItems()[0]);

		Object items = bookSummary.getItems()[0];
		String title = items.toString().substring(140, 182);
		return new Movie(bookSummary.getTotalItems(), title);
	}
}
