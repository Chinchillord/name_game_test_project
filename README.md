# Overview

This project provides access to an API which can be used to build an instance of the Name Game!

When the client makes a POST call to the /game endpoint (with no request body or query parameters), a new game will be created and persisted in an Amazon AWS RDS instance and the newly generated id of the game will be returned. You can then append this id as the gameID query parameter for a GET call to the same (/game) endpoint. The result will be a json object that includes the users score (correctGuesses and incorrectGuesses). Subsequent POST calls to /game should include the Game json object in the request body and a guessID query parameter (populated based on the player's selection). Since the Game object is persisted in the database, the client can store the ID and the user can come back to the game any time they want!

POST to /game with no body or parameters returns ID

![Alt text](/name_game_screenshots/new_ss_1.png?raw=true)

GET to /game with gameID provided in POST request

![Alt text](/name_game_screenshots/new_ss_2.png?raw=true)

POST to /game with request body received in the above GET request. Also provide a guessID based on which picture the player selects.
Returns ID for statelessness. To populate the request body for the the POST, I recommend copy/pasting from the GET /game response.

![Alt text](/name_game_screenshots/new_ss_4.png?raw=true)

GET to /game again shows updated score!

![Alt text](/name_game_screenshots/new_ss_5.png?raw=true)


# Architecture

I Chose Amazon AWS RDS and Postgresql for persisting the data. AWS is known for reliability and I wanted to use a relational database to enforce a strict data architecture for scalability. There are three tables in the database:

1. tree_employees: Table for WillowTree Employees
2. headshot: Table for the employees' headshot subobjects; has bidirectional one-to-one relationship with employee object by shared ids.
3. game: Table for storing game instances; uniderectionally related to employee objects.

The API Framework that I chose for development is Spring Boot. Spring follows a MVC model with a service layer and a Data Access Object. In the case of this project, the Game object represents our View. The API is entirely stateless; every call will either return a response to the client or will update the RDS instance. The endpoints are as follows:

1. GET /game?gameID=<string>
  Returns Game object that matches the selected ID
  
2. POST /game?isMattMode<boolean = false>; Request Body: <Game Object?>
  If request includes body, updates game object. isMattMode flag sets the game to "Matt Mode" where only employees named Matt or Matthew will be      included.

3. GET /employee?employeeID<string>
  Returns WillowTree Employee that matches the queried ID.

4. POST /employee Body: EmployeeHeadshotWrapper Object (example in Postman request)

Persistence is handled by Hibernate. Hibernate creates persistence and automates SQL queries based on the design of the data models. More detail about the relationships can be seen above.

![Alt text](/name_game_screenshots/ss_seven.png?raw=true)

Testing is handled using the Spring-provided @DataJPATest, which instantiates a local h2 database and an entity manager, which is autowired into the tests. Test design is focused on integration, rather than strict unit tests, since most of the logic in the project is in coercing together the different layers of data.

Error handling is very straightforward. I am using the default ResponseEntityExceptionHandler along with the @ControllerAdvice annotation, which will cause errors thrown to be associated to a valid HTTP error code i.e. 400 for bad requests and 500 for database errors.

To run the project, simply clone this repo, navigate to the root folder of the project in the terminal and execute `./gradlew bootRun`

Similarly to run the tests, execute `./gradlew test`

Postman collection is included to test the general play flow as outlined in the description and screenshots above. 
