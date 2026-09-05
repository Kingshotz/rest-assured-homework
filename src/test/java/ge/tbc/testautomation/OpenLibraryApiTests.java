package ge.tbc.testautomation;

import ge.tbc.testautomation.data.Constants;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OpenLibraryApiTests {
    @BeforeClass
    public void beforeClass() {
        RestAssured.baseURI = Constants.OPEN_LIBRARY_URI;
    }

    @Test
    public void SearchTest() {
        given()
                .basePath(Constants.OPEN_LIBRARY_SEARCH_PATH)
                .queryParam("q", "Harry Potter")
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("numFound", greaterThan(0))
                .body("docs[0].author_name", hasItem("J. K. Rowling"))
                .body("docs[0].title", equalTo("Harry Potter and the Philosopher's Stone"));
    }
}
