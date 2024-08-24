package steps;

import antities.RequestBody;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import utilities.ApiRunner;

import java.util.HashMap;
import java.util.Map;

public class SellerApiSteps {
    String email;
    Faker faker = new Faker();
    int id;
    int idForArchive;


// singe seller verify name is not empty
    @Given("user hits get single seller api with {string}")
    public void user_hits_get_single_seller_api_with(String endPoint) {
        ApiRunner.runGet(endPoint);
    }


    @Then("verify seller first name is not empty")
    public void verify_seller_first_name_is_not_empty() {
        String firstName = ApiRunner.getCustomResponse().getSeller_name();
        Assert.assertFalse(firstName.isEmpty());
    }





// get all sellers verify seller id is not equal to 0
    @Given("user hits get all seller api with {string}")
    public void user_hits_get_all_seller_api_with(String endPoint) {


       Map<String, Object> params = new HashMap<>();
       params.put("page", 1 );
       params.put("size", 110);
       params.put("isArchived", false);

      ApiRunner.runGet(endPoint, params);

    }



    @Then("verify seller id is not equal {int}")
    public void verify_seller_id_is_not_equal(int testAgainst  ) {
        int  size = ApiRunner.getCustomResponse().getResponses().size();
        for(int i = 0; i < size; i ++ ){
            int sellerId = ApiRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            Assert.assertNotEquals(testAgainst, sellerId);
        }
    }





    //modify seller email
    @Then("verify seller email is not empty")
    public void verify_seller_email_is_not_empty() {
      String email = ApiRunner.getCustomResponse().getEmail();
      Assert.assertFalse(email.isEmpty());
    }

    @Then("hit put api with {string}")
    public void hit_put_api_with(String endPoint) {



        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("whateverName");
        requestBody.setSeller_name("newName");
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number("3127795529");
        requestBody.setAddress("2239 North Western ave");

        email = ApiRunner.getCustomResponse().getEmail();

    }

    @Then("verify user email is as expected")
    public void verify_user_email_is_as_expected() {
        Assert.assertEquals(email, ApiRunner.getCustomResponse().getEmail() );

    }



    // create a seller verify seller was created and delete a seller

    @Given("user hits create seller api with {string}")
    public void user_hits_create_seller_api_with(String endPoint) {

        RequestBody requestBody = new RequestBody();

        requestBody.setCompany_name("iskazy INC");
        requestBody.setSeller_name("Iskazy Amanbaev");
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number("3127795529");
        requestBody.setAddress("2239 North Western Ave ");

        ApiRunner.runPost(endPoint, requestBody);

    }

    @Then("verify user id is generated")
    public void verify_user_id_is_generated() {
        id =  ApiRunner.getCustomResponse().getSeller_id();
        Assert.assertNotEquals(0 , id);


    }

    @Then("get single seller api is hit with {string}")
    public void get_single_seller_api_is_hit_with(String endpoint) {
        ApiRunner.runGet(endpoint + id);

    }

    @Then("verify seller name is not empty")
    public void verify_seller_name_is_not_empty() {

        String name = ApiRunner.getCustomResponse().getSeller_name();
        Assert.assertFalse(name.isEmpty());

    }

    @Then("delete seller api is hit with {string}")
    public void delete_seller_api_is_hit_with(String deleteEndPoint) {

    ApiRunner.runDELETE(deleteEndPoint + id);

    }

    @Then("get all sellers api is hit with {string}")
    public void get_all_sellers_api_is_hit_with(String endPoint) {

        Map<String, Object> params = new HashMap<>();
        params.put("page", 1 );
        params.put("size", 110);
        params.put("isArchived", false);
        ApiRunner.runGet(endPoint, params);


    }

    @Then("verify deleted seller is not present in the list")
    public void verify_deleted_seller_is_not_present_in_the_list() {

        boolean isPresent = true;

        for(int i = 0; i < ApiRunner.getCustomResponse().getResponses().size(); i ++ ){
            int sellerId = ApiRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            if(sellerId != id ){
                isPresent = false;
                break;
            }
        }
        Assert.assertFalse(isPresent);
    }


    @Given("user hit get a single seller api with {string}")
    public void user_hit_get_a_single_seller_api_with(String endPoint ) {
        ApiRunner.runGet(endPoint);
        idForArchive = ApiRunner.getCustomResponse().getSeller_id();

    }
    @Given("user hits archive the seller with {string}")
    public void user_hits_archive_the_seller_with(String EndPoint) {
        Map<String, Object> params = new HashMap<>();

        params.put("sellersIdsForArchive", idForArchive);
        params.put("archive", true);
        ApiRunner.runPost(EndPoint, params);


    }
    @Then("hit get all sellers and verify the seller was archived {string}")
    public void hit_get_all_sellers_and_verify_the_seller_was_archived(String endPoint) {

        Map<String, Object> params = new HashMap<>();

        params.put("page", 1 );
        params.put("size", 10);
        params.put("isArchived", true);

        ApiRunner.runGet(endPoint, params);

        boolean isPresent = false;
        int size = ApiRunner.getCustomResponse().getResponses().size();

        for(int i = 0; i < size; i ++ ){
            int  sellerId = ApiRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            if(sellerId == idForArchive){
                isPresent = true;
                break;
            }
        }
        Assert.assertTrue(isPresent);


    }




}


