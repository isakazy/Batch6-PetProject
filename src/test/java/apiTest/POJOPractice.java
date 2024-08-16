package apiTest;
import antities.CustomResponse;
import antities.RequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashWiseAuthorization;
import utilities.Config;
import java.util.HashMap;
import java.util.Map;



public class POJOPractice {

    //worm up with post create a category
   // run the test in api level and verify it was actually created in the ui lever
    @Test
    public void CreteCategory(){
        String token = CashWiseAuthorization.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/categories";

        RequestBody requestBody = new RequestBody();

        requestBody.setCategory_description("This is Unique description ");
        requestBody.setCategory_title("this is a unique product ");
        requestBody.setFlag(false);

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).post(url);

        int status = response.statusCode();

        Assert.assertEquals(201, status);
        // verify in the ui level
        // try to store the category id and hit get category api. this will fail since we do not follow jsonPath and pojo is not ready yet
        // demonstrate the challenge
    }


    // the below test is verify if we actually created a title in the api level
    @Test
    public void CreateCategoryWithPojo() throws JsonProcessingException {
        String token = CashWiseAuthorization.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/categories";

        RequestBody requestBody = new RequestBody();

        requestBody.setCategory_description("UniqueTest");
        requestBody.setCategory_title("this is a unique test");
        requestBody.setFlag(false);

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).post(url);

        int statusCode = response.statusCode();
        Assert.assertEquals(201, statusCode);

        // Start mapping with an anticipated response. demonstrate the expected response in the documentation

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse =  mapper.readValue(response.asString(), CustomResponse.class);

        int id = customResponse.getCategory_id();
        System.out.println(id);


         // you do not need a new token you can be still going with an old token
        String url2 = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/categories/" + id;

        Response response1 = RestAssured.given().auth().oauth2(token).get(url2);
        int status2 = response1. statusCode();
        Assert.assertEquals(200 ,status2);

        String categoryTitle = customResponse.getCategory_title();
        Assert.assertEquals("this is a unique test", categoryTitle);
    }




    // now we can talk about lombok and install it in the project and remove all the getter and setters



    // create a seller and verify with a pojo approach
    @Test
    public void CreateSeller() throws JsonProcessingException {
        Faker faker = new Faker();
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseAuthorization.getToken();

        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("trucking Company");
        requestBody.setSeller_name("seller is me");
        requestBody.setPhone_number("3127795529");
        requestBody.setEmail(faker.internet().emailAddress());

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBody).post(url);

        int status = response.statusCode();
        Assert.assertEquals(201, status);

        ObjectMapper objectMapper = new ObjectMapper();
        CustomResponse customResponse = objectMapper.readValue(response.asString(), CustomResponse.class);

        String expectedSellerName = "seller is me";

        Assert.assertEquals(expectedSellerName, customResponse.getSeller_name());
    }





    // create seller - > store the seller id -> hit get single seller api and verify status is 200
    @Test
    public void CreateGetSeller() throws JsonProcessingException {
        Faker faker = new Faker();
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseAuthorization.getToken();

        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("car rentals");
        requestBody.setSeller_name("i lend lexus");
        requestBody.setPhone_number("3127795529");
        requestBody.setEmail(faker.internet().emailAddress());

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).post(url);

        ObjectMapper objectMapper = new ObjectMapper();
        CustomResponse customResponse = objectMapper.readValue(response.asString(), CustomResponse.class);


        int sellerID = customResponse.getSeller_id();
        System.out.println(sellerID);

        String getSellerUrl = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/" + sellerID;

        Response response1 = RestAssured.given().auth().oauth2(token).get(getSellerUrl);

        int statusCode = response1.statusCode();
        Assert.assertEquals(200, statusCode);
    }




    @Test
    public void CreateManySellers() {
        String token = CashWiseAuthorization.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/";
        Faker faker = new Faker();

        for (int i = 0; i < 15; i++) {
            RequestBody requestBody = new RequestBody();

            requestBody.setCompany_name(faker.company().name());
            requestBody.setSeller_name(faker.name().firstName());
            requestBody.setEmail(faker.internet().emailAddress());
            requestBody.setAddress(faker.address().fullAddress());
            requestBody.setPhone_number(faker.phoneNumber().phoneNumber());

            Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                    .body(requestBody).post(url);

            int status = response.statusCode();
            Assert.assertEquals(201, status);
        }
    }




    // here i am having a list of sellers and looping through them in pojo. i created responses list in custom respose and maping the respose  to it
    @Test
    public void GetAllSellers() throws JsonProcessingException {
        String token = CashWiseAuthorization.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 40);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        int size = customResponse.getResponses().size();

        for (int i = 0; i < size; i++) {
            String email = customResponse.getResponses().get(i).getEmail();
            Assert.assertFalse(email.isEmpty());
        }
    }




    }
