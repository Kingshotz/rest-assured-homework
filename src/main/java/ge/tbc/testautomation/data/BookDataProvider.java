package ge.tbc.testautomation.data;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;

import java.util.List;

import static ge.tbc.testautomation.data.Constants.BOOKS_PATH;
import static ge.tbc.testautomation.data.Constants.BOOK_STORE_UI;
import static io.restassured.RestAssured.given;

public class BookDataProvider {

    @DataProvider(name = "bookData")
    public static Object[][] bookData() {
        Response response = given()
                .baseUri(BOOK_STORE_UI)
                .basePath(BOOKS_PATH)
                .when()
                .get("/Books")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<String> isbns = jsonPath.getList("books.isbn");
        List<String> authors = jsonPath.getList("books.author");
        int size = isbns.size();
        Object[][] data = new Object[size][2];

        for (int i = 0; i < size; i++) {
            data[i][0] = isbns.get(i);
            data[i][1] = authors.get(i);
        }
        return data;
    }

}
