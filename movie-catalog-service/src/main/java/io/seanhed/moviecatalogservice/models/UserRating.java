package io.seanhed.moviecatalogservice.models;

import java.util.List;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Data;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {

	private String userId;
	private List<Rating> userRatings;
}
