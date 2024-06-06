package at.davl.main.controllers;


import at.davl.main.dto.MovieDto;
import at.davl.main.dto.MoviePageResponse;
import at.davl.main.service.MovieService;
import at.davl.main.exceptions.EmptyFileException;
import at.davl.main.utils.AppConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@PreAuthorize("hasAnyAuthority('ADMIN')") // @EnableMethodSecurity in SecurityConfiguration
@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')") // @EnableMethodSecurity in SecurityConfiguration
    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String movieDto) throws IOException, EmptyFileException {

        if (file.isEmpty()) {
            throw new EmptyFileException("File is empty. Please send another file");
        }
        MovieDto convertedDto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(convertedDto, file), HttpStatus.CREATED);
    }

    private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
        // mapper (we need it for a map value to the MovieDto class)
        ObjectMapper objectMapper = new ObjectMapper();
        // it mapped movie to MovieDto
        MovieDto movieDto = objectMapper.readValue(movieDtoObj, MovieDto.class);
        return movieDto;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')") // @EnableMethodSecurity in SecurityConfiguration
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId){
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')") // @EnableMethodSecurity in SecurityConfiguration
    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMoviesHandler() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')") // @EnableMethodSecurity in SecurityConfiguration
    @PutMapping("/update/{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer movieId,
                                                       @RequestPart MultipartFile file,
                                                       @RequestPart String movieDtoObj) throws IOException {

        if (file.isEmpty()) file = null;
        MovieDto movieDto = convertToMovieDto(movieDtoObj);
        return ResponseEntity.ok(movieService.updateMovie(movieId, movieDto, file));
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')") // @EnableMethodSecurity in SecurityConfiguration
    @GetMapping("/allMoviesPage")
    public ResponseEntity<MoviePageResponse> getAllMoviesWithPaginationHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_Number, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
            ) {
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber, pageSize));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')") // @EnableMethodSecurity in SecurityConfiguration
    @GetMapping("/allMoviesPageSort")
    public ResponseEntity<MoviePageResponse> getAllMoviesWithPaginationHandlerAndSort(
            @RequestParam(defaultValue = AppConstants.PAGE_Number, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir
    ) {
        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber, pageSize, sortBy, dir));
    }
}

