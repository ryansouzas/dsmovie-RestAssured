package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieControllerRA {

    private String movieTitle;

    @BeforeEach
    void setUp(){
        baseURI = "http://localhost:8080";

        movieTitle = "Venom";
    }

	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
        given()
                .get("/movies")
        .then()
                .statusCode(200)
                .body("content.id[0]", is(1));
	}
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {
        given()
                .get("/movies?title={movietitle}", movieTitle)
        .then()
                .statusCode(200)
                .body("content.id[0]", is(2))
                .body("content.title[0]", equalTo("Venom: Tempo de Carnificina"))
                .body("content.score[0]", is(3.3F));
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {		
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {	
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {		
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
	}
}
