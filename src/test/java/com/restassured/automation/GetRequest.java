package com.restassured.automation;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetRequest {

    @Test
    void getWeatherDetails()
    {
        //Specify BaseURI
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

        //Request Object
        RequestSpecification httpRequest = RestAssured.given();

        //Response Object
        Response response = httpRequest.request(Method.GET, "/Hyderabad");

        //print response in console window
        String responseBody = response.getBody().asString();
        System.out.println("Response body is : " + responseBody);

        //Validation
        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("Status code is : " +statusCode);
        Assert.assertEquals(statusCode,200);

        //Status line validation
        String statusLine = response.getStatusLine();
        System.out.println("Status line is : " +statusLine);
        Assert.assertEquals(statusLine,"HTTP/1.1 200 OK");

    }
}
