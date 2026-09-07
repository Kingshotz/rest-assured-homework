package ge.tbc.testautomation;

import ge.tbc.testautomation.data.BookDataProvider;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.BOOKS_PATH;
import static ge.tbc.testautomation.data.Constants.BOOK_STORE_URI;
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
        given(spec)
                .when()
                .basePath(BOOKS_PATH)
                .delete("/Book")
                .then()
                .assertThat()
                .statusCode(401)
                .body("message", equalTo("User not authorized!"));
    }
}
