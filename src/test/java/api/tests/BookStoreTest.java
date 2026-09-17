package api.tests;

import api.client.BookStoreApi;
import data.Constants;
import data.models.response.BooksItem;
import data.models.response.GetBooksResponse;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookStoreTest {
    BookStoreApi bookStoreApi;

    @BeforeClass
    public void beforeClass() {
        bookStoreApi = new BookStoreApi();
    }

    @Test
    public void getAllBooks() {
        Response res =bookStoreApi.getAllBooks()
                .then()
                .statusCode(200)
                .extract()
                .response();
        GetBooksResponse response = res.as(GetBooksResponse.class);
        List<BooksItem> books = response.getBooks();
        assertThat(books.stream().allMatch(
                book-> book.getPages() < 1000),
                equalTo(true));

        List<String> lastTwoAuthor = books.stream()
                .map(BooksItem::getAuthor)
                .skip(books.size() - 2)
                .collect(Collectors.toList());

        assertThat(lastTwoAuthor, hasItems(Constants.LAST_AUTHOR, Constants.BEFORE_LAST_AUTHOR));
    }
}
