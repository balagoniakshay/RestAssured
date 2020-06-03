package com.restassured.pavankumar;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class postRequest {

    @Test
    void RegistrationSuccessful()
    {
        //Specify base URI
        RestAssured.baseURI = "http://restapi.demoqa.com/customer";

        //Request Object
        RequestSpecification httpRequest = RestAssured.given();

        //Request Payload sending along with the POST request
        JSONObject requestParams = new JSONObject();

        requestParams.put("FirstName","Akshay");
        requestParams.put("LastName","Balagoni");
        requestParams.put("UserName","akshaybalagoni");
        requestParams.put("Password","akshaybalagoni");
        requestParams.put("Email","akshaybalagoni@gmail.com");

        httpRequest.header("Content-Type","application/json");

        httpRequest.body(requestParams.toJSONString());

        //Response Object
        Response response = httpRequest.request(Method.POST,"/register");

        String responseBody = response.getBody().asString();
        System.out.println("Response Body is : " +responseBody);

        //Status code validation
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,201);

        //Success code validation
        String successCode = response.jsonPath().get("SuccessCode");
        Assert.assertEquals(successCode,"OPERATION_SUCCESS");

    }
}
