package io.seanhed.movieratingsservice.models;

import java.util.List;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Data;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {

	private List<Rating> userRatings;
}
