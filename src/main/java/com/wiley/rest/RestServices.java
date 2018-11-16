package com.wiley.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class RestServices {

    private final String REST_URL;


    public RestServices (String url){
        this.REST_URL = url;
    }

    public Response sendGetRequest(String requestPart){
        return RestAssured.get(REST_URL+requestPart);
    }

    public int getResponseStatus (String requestPart){
        return sendGetRequest(requestPart).getStatusCode();
    }

    public byte[] getImage(String requestPart){
        return sendGetRequest(requestPart).getBody().asByteArray();
    }
}
