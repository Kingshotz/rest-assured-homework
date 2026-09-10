package ge.tbc.testautomation;

import ge.tbc.testautomation.data.Constants;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OpenLibraryApiTests {
    private RequestSpecification spec;

    @BeforeClass
    public void beforeClass() {
        spec = new RequestSpecBuilder()
                .setBaseUri(Constants.OPEN_LIBRARY_URI)
                .build();
    }

    @Test
    public void SearchTest() {
        given(spec)
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