package ge.tbc.testautomation;

import ge.tbc.testautomation.data.BookDataProvider;
import ge.tbc.testautomation.data.Constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BooksApiTests {
    private RequestSpecification spec;

    @BeforeClass
    public void beforeClass() {
        spec = new RequestSpecBuilder()
                .setBaseUri(BOOK_STORE_URI)
                .build();
    }

    @Test(dataProvider = "bookData", dataProviderClass = BookDataProvider.class)
    public void getSingleBook(String isbn, String author) {
            given(spec)
                    .when()
                    .basePath(BOOKS_PATH)
                    .queryParam(ISBN_PARAM, isbn)
                    .get(BOOK_ENDPOINT)
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .body(TITLE_KEY, notNullValue(),
                            ISBN_KEY, notNullValue(),
                            PUBLISH_DATE_KEY, notNullValue(),
                            PAGES_KEY, notNullValue())
                    .body(AUTHOR_KEY, equalTo(author));
    }
    @Test
    public void DeleteBookTest() {
        given(spec)
                .when()
                .basePath(BOOKS_PATH)
                .delete(BOOK_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(401)
                .body(MESSAGE_KEY, equalTo(USER_NOT_AUTHORIZED_MESSAGE));
    }
    @Test
    public void GetAllBooksTest() {
        given()
                .spec(spec)
                .when()
                .basePath(Constants.BOOKS_PATH)
                .get(BOOKS_ENDPOINT)
                .then()
                .statusCode(200)
                .assertThat()
                .body(BOOKS_PAGES_PATH, everyItem(lessThan(1000)))
                .body(FIRST_BOOK_AUTHOR_PATH, equalTo(AUTHOR_RICHARD_SILVERMAN),
                        SECOND_BOOK_AUTHOR_PATH, equalTo(AUTHOR_ADDY_OSMANI))
                .log().all();
    }
}
