package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import com.devsuperior.dsmovie.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ScoreControllerRA {

    private Long existingMovieId, nonExistingMovieId;

    private Map<String, Object> putScoreInstance;

    private String clientUsername, clientPassword, adminUsername, adminPassword;
    private String clientToken, adminToken,invalidToken;



    @BeforeEach
    void setUp() throws Exception {
        existingMovieId = 1L;
        nonExistingMovieId = 100L;

        putScoreInstance = new HashMap<>();
        putScoreInstance.put("movieId", nonExistingMovieId);
        putScoreInstance.put("score", 4);

        clientUsername = "alex@gmail.com";
        clientPassword = "123456";
        adminUsername = "maria@gmail.com";
        adminPassword = "123456";

        clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
        adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";
    }


	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(putScoreInstance)
            .when()
                .put("/scores")
            .then()
                .statusCode(404);


	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
        Map<String, Object> missingMovieId = new HashMap<>();
        missingMovieId.put("score", 4.0);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(missingMovieId)
           .when()
                .put("/scores")
           .then()
                .statusCode(422);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
        putScoreInstance.put("score", -2);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(putScoreInstance)
                .when()
                .put("/scores")
                .then()
                .statusCode(422);
	}
}
