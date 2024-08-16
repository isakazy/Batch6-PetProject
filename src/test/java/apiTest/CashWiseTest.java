package apiTest;
import antities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashWiseAuthorization;
import utilities.Config;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CashWiseTest {

    String email;
    @Test
    public void Authentication(){

        // talk about the body, what is request body, and types of body

        // the code will fail, need to add jackson, after the test create a utility file;
        RequestBody requestBody = new RequestBody();
        String url = "https://backend.cashwise.us/api/myaccount/auth/login";

        requestBody.setEmail("isakazy@gmail.com");
        requestBody.setPassword("isakazyamanbaev");

       Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody).post(url);
       int statusCode = response.statusCode();
       Assert.assertEquals(200, statusCode);
       response.prettyPrint();
       String token = response.jsonPath().getString("jwt_token");
        System.out.println(token);
    }


    // get single selle, talk about jsonPath, talk about verification
    @Test
    public void getSingleSeller(){
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/" + 1212;
        String token = CashWiseAuthorization.getToken();

       Response response = RestAssured.given().auth().oauth2(token).get(url);

       int statusCode = response.statusCode();

       Assert.assertEquals(200, statusCode);

      // response.prettyPrint();

        String email = response.jsonPath().getString("email");
        Assert.assertFalse(email.isEmpty());

        String expectedEnding = ".com";
        Assert.assertTrue(email.endsWith(expectedEnding));

       String expectedName = "cucumberAPi";
       String sellerName = response.jsonPath().getString("seller_name");
       Assert.assertEquals(expectedName, sellerName);

    }



    // next test is about a json path and parameters, run the test via postman first and restAssured
    // get an email by index from the list of responses

    @Test
    public void getAllSellers(){
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseAuthorization.getToken();

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("page", 1 );
        params.put("size", 100 );

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);

        response.prettyPrint();
        String email = response.jsonPath().getString("responses[1].email");
        Assert.assertFalse(email.isEmpty());

    }

    // get an email by id and verify email is not empty

    @Test
    public void VerifyById(){
        String token = CashWiseAuthorization.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 100);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);

        //response.prettyPrint();  look for en expected email first

        String expectedEmail = "chas.kihn@gmail.com";
        String email = response.jsonPath().getString("responses.find {it.id ='4686'}.email");
        Assert.assertEquals(email, expectedEmail );
    }



    // verify all emails are not empty, talk about list again

    @Test
    public void VerifyEmailsNotEmpty(){
        String token = CashWiseAuthorization.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 100);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);

        List<String> emailsInList = response.jsonPath().getList("responses.email"); // show the response list in the json viewer

        for(String email : emailsInList){
            Assert.assertFalse(email.isEmpty());
        }

    }



    // Create a seller and verify the seller ID
    // start here as a warmup
    @Test
    public void CreateSeller() {
        RequestBody requestBody = new RequestBody();

        requestBody.setCompany_name("tableTennis#1 ");
        requestBody.setSeller_name("isakazyBestCoach ");
        requestBody.setEmail("tabe@gmail.com"); // the id has to be unique
        requestBody.setPhone_number("3127795529");
        requestBody.setAddress("9790WestHigginsRoad");

        String token = CashWiseAuthorization.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).post(url);

        int statusCode = response.statusCode();

        Assert.assertEquals(201, statusCode);

        String id = response.jsonPath().getString("seller_id");
        Assert.assertFalse(id.isEmpty());
        String email = response.jsonPath().getString("email");
        Assert.assertFalse(email.isEmpty());

    }


    // Create Seller, Create on PostMan first and talk about Request Body
    @Test
    public void CreateAndGetSeller(){
        RequestBody requestBody = new RequestBody();

        requestBody.setCompany_name("tableTennis#1 ");
        requestBody.setSeller_name("isakazyBestCoach ");
        requestBody.setEmail("tablOe@gmail.com"); // the id has to be unique
        requestBody.setPhone_number("3127795529");
        requestBody.setAddress("9790WestHigginsRoad");

        String token = CashWiseAuthorization.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).post(url);

        int statusCode = response.statusCode();

        Assert.assertEquals(201, statusCode);

        String id = response.jsonPath().getString("seller_id");

        // chain of api hits continue to git get api and verify you actually created a seller

        String getUrl = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/" + id;

        Response response1 = RestAssured.given().auth().oauth2(token).get(getUrl);
        int statusCode2 = response1.statusCode();
        Assert.assertEquals(200 ,statusCode2);


    }









}
