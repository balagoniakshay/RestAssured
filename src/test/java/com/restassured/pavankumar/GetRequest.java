package com.restassured.pavankumar;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
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

        //Body Validation : Capture all the values from the response
        Assert.assertTrue(responseBody.contains("Hyderabad"));

        JsonPath jsonPath = response.jsonPath();

        String city = jsonPath.get("City").toString();
        Assert.assertEquals(city,"Hyderabad");

        jsonPath.get("Temperature");
        jsonPath.get("Humidity");
        jsonPath.get("WeatherDescription");
        jsonPath.get("WindSpeed");
        jsonPath.get("WindDirectionDegree");

    }

    @Test
    void googleMapAPI()
    {
        //Specify BaseURI
        RestAssured.baseURI = "https://maps.googleapis.com";

        //Request Object
        RequestSpecification httpRequest = RestAssured.given();

        //Response Object
        Response response = httpRequest.request(Method.GET, "/maps/api/place/nearbysearch/xml?location=-33.8670522,151.1957362&radius=1500&type=supermarket&key=AIzaSyBjGCE3VpLU4lgTqSTDmHmJ2HoELb4Jy1s");

        //print response in console window
        String responseBody = response.getBody().asString();
        System.out.println("Response body is : " + responseBody);

        // Validation:
        // Validating headers

        String contentType = response.header("Content-Type");
        Assert.assertEquals(contentType,"application/xml; charset=UTF-8");

        String contentEncoding = response.header("Content-Encoding");
        Assert.assertEquals(contentEncoding,"gzip");

        //printing all headers

        Headers allHeaders = response.headers();

        for(Header header:allHeaders)
        {
            System.out.println(header.getName() + " : " + header.getValue());
        }
    }

    @Test
    void authenticationAPI()
    {
        //Specify BaseURI
        RestAssured.baseURI = "http://restapi.demoqa.com/authentication/CheckForAuthentication/";

        //Basic Authentication
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("ToolsQA");
        authScheme.setPassword("TestPassword");

        RestAssured.authentication = authScheme;

        //Request Object
        RequestSpecification httpRequest = RestAssured.given();

        //Response Object
        Response response = httpRequest.request(Method.GET, "/");

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

        //Body Validation : Capture all the values from the response
        JsonPath jsonPath = response.jsonPath();

        String faultId = jsonPath.get("FaultId").toString();
        Assert.assertEquals(faultId,"OPERATION_SUCCESS");
        String fault = jsonPath.get("Fault").toString();
        Assert.assertEquals(fault,"Operation completed successfully");
    }

}
