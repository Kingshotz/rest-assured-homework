package ge.tbc.testautomation;

import ge.tbc.testautomation.data.BookDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static ge.tbc.testautomation.data.Constants.BOOKS_PATH;
import static ge.tbc.testautomation.data.Constants.BOOK_STORE_UI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BooksApiTests {
    @BeforeClass
    public void beforeClass() {
        RestAssured.baseURI = BOOK_STORE_UI;
    }

    @Test(dataProvider = "bookData", dataProviderClass = BookDataProvider.class)
    public void getSingleBook(String isbn, String author) {
            given()
                    .when()
                    .basePath(BOOKS_PATH)
                    .queryParam("ISBN", isbn)
                    .get("/Book")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .body("title", notNullValue(),
                            "isbn", notNullValue(),
                            "publish_date", notNullValue(),
                            "pages", notNullValue())
                    .body("author", equalTo(author));
    }
    @Test
    public void DeleteBookTest() {
        given()
                .when()
                .basePath(BOOKS_PATH)
                .delete("/Book")
                .then()
                .assertThat()
                .statusCode(401)
                .body("message", equalTo("User not authorized!"));
    }
}
