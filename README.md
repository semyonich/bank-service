## Bank Service API
This project is an implementation of simple bank REST API, which allows to create users, 
accounts in different currencies for users and provides money transfer between any two 
accounts. Only ADMIN can create users, accounts and access information about all users and accounts.
User has access to his accounts and money transfers. If currency of destination account differs from the source account - amount 
will be converted according actual
currency exchange rate, which is obtained from side [API](https://api.exchangerate.host).
You can test this app through Swagger UI, 
which is available at `http://localhost:8080/swagger-ui/`. 
All input data is being validated.
These features are realized with:
- MySQL (DB layer)
- Java (Service layer)
- Spring Boot 
- Spring Security (Authentication/Authorization) with JWT
- Swagger

## How to use it
Steps 2-7 can be skipped.<br>
__1__.  Download and install the 
  [JDK](https://www.oracle.com/java/technologies/javase-downloads.html "Download JDK"),
[Git](https://git-scm.com/downloads) and [Maven](https://maven.apache.org/download.cgi) . 
Clone this project with command `git clone https://github.com/semyonich/bank-service.git` <br>
__2__. Download and install [MySQL Server](https://dev.mysql.com/downloads/ "Download MySQL").<br>
__3__. Create `payment_portal` schema in your DB and user without password.<br>
__4__. Download and install IDE with Spring support. 
  For example [IntelliJ IDEA](https://www.jetbrains.com/ru-ru/idea/download/#section=windows) .<br>
__5__. Open project in your IDE.<br>
__6__. Setup values in the file "application.properties" :<br>
 + spring.datasource.url: jdbc:mysql://*"your_DB_URL:port"*/payment_portal 
 + spring.datasource.username: *"your_DB_username"* <br>
 + application.admin.username: *ADMIN_PHONE_NUMBER* 
 + application.admin.password: *ADMIN_PASSWORD* <br>

__7__. Run a project.<br>
__8__. Or you can skip steps 2-7 and install [Docker](https://www.docker.com/products/docker-desktop) .
 Enter project dir, which contains `docker-compose.yml` and run command: `docker-compose up` <br>
__9__. Project now is available on `http://localhost:8080`. You can send requests using [Postman](https://www.postman.com/downloads/ "Download Postman"). <br>
__10__. As apllication is secured with Spring Security and JWT authentication, 
  at first you need to get JWT with Postman. Send __POST__ request with body<br> `{
"username": "ADMIN_PHONE_NUMBER",
"password": "ADMIN_PASSWORD"
}` to __URL__ `http://localhost:8080/auth`. <br>
`Content-Type` in Headers should be `application/json`. You will get response <br>
`{"username":"ADMIN_PHONE_NUMBER","token":YOUR_TOKEN}`.<br>
## Ways to test :
+ If you use Postman to test API, on Authorization tab you should choose `OAuth2.0`, insert this token in field 
  `Access Token` of every of your requests and add 
  Header Prefix `Bearer_`.
+ If you use Swagger to test API, on the Swagger UI page tap button `Authorize` and
  enter your token with Header Prefix in format
`Bearer_YOUR_TOKEN`. Now you can send requests and get responses with Swagger.
