package utilities;

import antities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CashWiseAuthorization {

    public static  String getToken(){
        String url = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBody requestBody = new RequestBody();
        requestBody.setEmail("isakazy@gmail.com");
        requestBody.setPassword("isakazyamanbaev");

        Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody).post(url);
        return response.jsonPath().getString("jwt_token");


    }
}
