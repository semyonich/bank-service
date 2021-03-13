## Bank Service API
This project is an implementation of simple bank REST API, which allows to create users, 
accounts in different currencies for users and provides money transfer between any two 
accounts. Only ADMIN can create users, accounts and access information about all users and accounts.
User has access to his accounts and money transfers. If currency of destination account differs from the source account - amount 
will be converted according actual
currency exchange rate, which is obtained from side [API](https://api.exchangerate.host).
You can test this app through Swagger UI, 
which is available at http://localhost:8080/swagger-ui/. 
All input data is being validated.
These features are realized with:
- MySQL (DB layer)
- Java (Service layer)
- Spring Boot 
- Spring Security (Authentication/Authorization) with JWT
- Swagger  <br>

## How to use it
- Download and install the 
  [JDK](https://www.oracle.com/java/technologies/javase-downloads.html "Download JDK"). <br>
- Download and install [MySQL Server](https://dev.mysql.com/downloads/ "Download MySQL").<br>
- Create `payment_portal` schema in your DB and user without password.
- Download and install IDE with Spring support. 
  For example [IntelliJ IDEA](https://www.jetbrains.com/ru-ru/idea/download/#section=windows).
- Open project in your IDE.
- You can send requests using [Postman](https://www.postman.com/downloads/ "Download Postman").
- Setup values in the file "application.properties" :<br>
    + spring.datasource.url: jdbc:mysql://*"your_DB_URL:port"*/payment_portal
    + spring.datasource.username: *"your_DB_username"* <br>
    + application.admin.username: *ADMIN_PHONE_NUMBER* 
    + application.admin.password: *ADMIN_PASSWORD*
- Run a project
- As apllication is secured with Spring Security and JWT authentication, 
  at first you need to get JWT with Postman. Send __POST__ request with body<br> `{
"username": "ADMIN_PHONE_NUMBER",
"password": "ADMIN_PASSWORD"
}`.<br>
`Content-Type` in Headers should be `application/json`. You will get response <br>
`{"username":"ADMIN_PHONE_NUMBER","token":YOUR_TOKEN}`.<br>
## Ways to test :
+ If you use Postman to test API, on Authorization tab you should choose `OAuth2.0`, insert this token in field 
  `Access Token` of every of your requests and add 
  Header Prefix `Bearer_`.
+ If you use Swagger to test API, on the Swagger UI page tap button `Authorize` and
  enter your token with Header Prefix in format
`Bearer_YOUR_TOKEN`. Now you can send requests and get responses with Swagger.
