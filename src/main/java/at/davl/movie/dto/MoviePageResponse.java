package at.davl.movie.dto;

import java.util.List;

/**
 *
 * @param movieDtos     -> list of source from DB
 * @param pageNumber    -> current page number
 * @param pageSize      -> how many elements will be on the page
 * @param totalElements -> total elements
 * @param totalPages    -> total pages
 * @param isLast        -> if it is last page
 *
 */

public record MoviePageResponse(List<MovieDto> movieDtos,
                                Integer pageNumber,
                                Integer pageSize,
                                long totalElements,
                                int totalPages,
                                boolean isLast
                                ) {



}
