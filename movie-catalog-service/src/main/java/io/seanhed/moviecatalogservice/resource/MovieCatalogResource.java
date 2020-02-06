package io.seanhed.moviecatalogservice.resource;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

import io.seanhed.moviecatalogservice.models.CatalogItem;
import io.seanhed.moviecatalogservice.models.Movie;
import io.seanhed.moviecatalogservice.models.Rating;
import io.seanhed.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder builder;

	@Autowired
	MovieInfo movieInfo;

	@Autowired
	UserRatingInfo userRatingInfo;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId) {

		UserRating ratings = userRatingInfo.getUserRating(userId);

		return ratings.getUserRatings().stream().map(rating ->
		// for each movie ID, call movie-info-service and get details
		movieInfo.getCatalogItem(rating)).collect(Collectors.toList());
	}



}

// alternate WebClient way

// Movie movie = builder.build().get().uri("http://localhost:8082/movie/" +
// rating.getMovieId()).retrieve()
// .bodyToMono(Movie.class).block();