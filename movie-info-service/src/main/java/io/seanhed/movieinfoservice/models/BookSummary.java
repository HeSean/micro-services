package io.seanhed.movieinfoservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSummary {
	
	private String kind;
	private String totalItems;
	private Object[] items;

}
