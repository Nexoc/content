package at.davl.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * in application.yml should be changed email and password
 * in EmailService in line 20 should be added email
 */


@SpringBootApplication
public class MovieApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieApiApplication.class, args);
	}

}

/*
### Post http://localhost:8080/api/v1/movie/add-movie
file: file.png
movieDto : {
    "movieId" : 1,
    "title" : "Avengers: Infinity War",
    "director" : "Russo Brothers",
    "studio" : "Marvel Studio",
    "movieCast" : ["RDJ", "Chris Evan", "Scarlett Johansson"],
    "releaseYear" : 2018,
    "poster" : "some.png",
    "posterUrl" : "some_url"
  }

body -> row
 {
    "name" : "marat",
    "email" : "davl@gmail.com",
    "username" : "nexoc",
    "password" : "12345"
}

https://github.com/SudarshanShahCode/movieAPI-backend-YT/tree/master/src/main/java/com/movieflix

 */
