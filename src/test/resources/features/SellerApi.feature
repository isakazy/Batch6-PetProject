Feature: test seller api


  @VerifySingleSellerEmail 
    Scenario: Get single seller and verify seller first name is not empty
    Given user hits get single seller api with "/api/myaccount/sellers/4710"
    Then verify seller first name is not empty



  @verifySellerEmailNotEmpty @regression
  Scenario: Get all sellers, verify seller email is not empty
    Given user hits get all seller api with "/api/myaccount/sellers"
    Then verify seller id is not equal 0


    @getModifySellerEmail @regression
    Scenario: get single seller, modify the email and verify email was modified
      Given user hits get single seller api with "/api/myaccount/sellers/4710"
      Then verify seller email is not empty
      And hit put api with "/api/myaccount/sellers/4710"
      Then verify user email is as expected


  @ArchiveSeller @regression
  Scenario: get as seller, archive a seller and verify the seller was archived
    Given user hit get a single seller api with "/api/myaccount/sellers/5766"
    And user hits archive the seller with "/api/myaccount/sellers/archive/unarchive"
    Then hit get all sellers and verify the seller was archived "/api/myaccount/sellers"


  @CreateGetDeleteSeller 
  Scenario: Create a seller, verify seller was created and delete the same seller
    Given user hits create seller api with "/api/myaccount/sellers/"
    Then  verify user id is generated
    And get single seller api is hit with "/api/myaccount/sellers/"
    Then verify seller name is not empty
    And verify seller email is not empty
    Then delete seller api is hit with "/api/myaccount/sellers/"
    Then get all sellers api is hit with "/api/myaccount/sellers"
    Then verify deleted seller is not present in the list
