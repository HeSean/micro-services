package io.seanhed.moviecatalogservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.seanhed.moviecatalogservice.models.CatalogItem;
import io.seanhed.moviecatalogservice.models.Movie;
import io.seanhed.moviecatalogservice.models.Rating;

@Service
@RequestMapping("/info")
public class MovieInfo {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(
			fallbackMethod = "getFallbackCatalogItem",
//			threadPoolKey = "movieInfoPool",
//			threadPoolProperties = {
//					@HystrixProperty(name = "coreSize", value = "20"),
//					@HystrixProperty(name = "maxQueueSize", value = "10")},
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")},
			commandKey="cmdKey")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(), movie.getMovieId(), rating.getRating());
	}

	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("fallback", "", rating.getRating());
	}
}
