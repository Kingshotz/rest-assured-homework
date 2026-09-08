package ge.tbc.testautomation.data;

public class Constants {

    // Base URIs and base paths
    public static final String
            BOOK_STORE_URI = "https://bookstore.toolsqa.com",
            BOOKS_PATH = "/BookStore/v1",
            PET_STORE_URI = "https://petstore.swagger.io",
            PET_STORE_PATH = "/v2/store",
            PET_PATH = "/v2/pet",
            PET_USER_PATH = "/v2/user",
            OPEN_LIBRARY_URI = "https://openlibrary.org",
            OPEN_LIBRARY_SEARCH_PATH = "/search.json",
            DRIVERS_URI = "https://api.jolpi.ca",
            DRIVERS_PATH = "/ergast/f1/2025/drivers/";

    // Endpoints
    public static final String
            BOOK_ENDPOINT = "/Book",
            BOOKS_ENDPOINT = "/Books",
            ORDER_ENDPOINT = "/order",
            LOGIN_ENDPOINT = "/login",
            PET_BY_ID_ENDPOINT = "/{petId}",
            FIND_BY_STATUS_ENDPOINT = "/findByStatus",
            ID_ENDPOINT = "/{id}";

    // Request parameter names
    public static final String
            ISBN_PARAM = "ISBN",
            LIMIT_PARAM = "limit",
            QUERY_PARAM = "q",
            USERNAME_PARAM = "username",
            PASSWORD_PARAM = "password";

    // Headers
    public static final String
            ACCEPT_HEADER = "accept",
            APPLICATION_JSON = "application/json";

    // JSON keys and paths
    public static final String
            ID_KEY = "id",
            NAME_KEY = "name",
            STATUS_KEY = "status",
            CATEGORY_KEY = "category",
            PHOTO_URLS_KEY = "photoUrls",
            TAGS_KEY = "tags",
            PET_ID_KEY = "petId",
            QUANTITY_KEY = "quantity",
            SHIP_DATE_KEY = "shipDate",
            COMPLETE_KEY = "complete",
            CODE_KEY = "code",
            TYPE_KEY = "type",
            MESSAGE_KEY = "message",
            TITLE_KEY = "title",
            ISBN_KEY = "isbn",
            PUBLISH_DATE_KEY = "publish_date",
            PAGES_KEY = "pages",
            AUTHOR_KEY = "author",
            GIVEN_NAME_KEY = "givenName",
            FAMILY_NAME_KEY = "familyName",
            NATIONALITY_KEY = "nationality",
            DATE_OF_BIRTH_KEY = "dateOfBirth",
            BOOKS_PAGES_PATH = "books.pages",
            FIRST_BOOK_AUTHOR_PATH = "books[0].author",
            SECOND_BOOK_AUTHOR_PATH = "books[1].author",
            NUM_FOUND_PATH = "numFound",
            FIRST_DOC_AUTHOR_NAME_PATH = "docs[0].author_name",
            FIRST_DOC_TITLE_PATH = "docs[0].title",
            SERIES_PATH = "series",
            DRIVER_TABLE_SEASON_PATH = "DriverTable.season",
            TOTAL_PATH = "total",
            DRIVER_TABLE_DRIVERS_PATH = "DriverTable.Drivers",
            MR_DATA_ROOT_PATH = "MRData";

    // Groovy / GPath queries
    public static final String
            FIND_BY_ID_QUERY = "find {it.id == id}",
            DRIVER_BORN_BEFORE_DATE_QUERY = """
                    DriverTable.Drivers.find {
                        it.dateOfBirth != null &&
                        it.dateOfBirth < dateOfBirth
                    }
                    """,
            DRIVERS_BORN_AFTER_DATE_QUERY = """
                    DriverTable.Drivers.findAll {
                        it.dateOfBirth != null &&
                        it.dateOfBirth > dateOfBirth
                    }
                    """,
            DRIVERS_BY_NATIONALITY_QUERY = """
                    DriverTable.Drivers.findAll {
                        it.nationality != null &&
                        it.nationality == nationality
                    }
                    """,
            DRIVERS_FAMILY_NAME_STARTS_WITH_A_OR_B_QUERY = """
                    DriverTable.Drivers.findAll{
                    it.familyName.startsWith('A') || it.familyName.startsWith('B')
                    }
                    """,
            DRIVERS_LOW_NUMBER_OR_LONG_FAMILY_NAME_QUERY =
                    "DriverTable.Drivers.findAll { " +
                            "(it.permanentNumber != null && it.permanentNumber.toInteger() < 10) " +
                            "|| it.familyName.length() > 7 }";

    // Expected / test data values
    public static final String
            ORDER_STATUS_PLACED = "placed",
            PET_NAME_DOGGIE = "doggie",
            PET_STATUS_AVAILABLE = "available",
            PET_STATUS_PENDING = "pending",
            PET_STATUS_SOLD = "sold",
            LOGIN_USERNAME = "username",
            LOGIN_PASSWORD = "password",
            USER_NOT_AUTHORIZED_MESSAGE = "User not authorized!",
            AUTHOR_RICHARD_SILVERMAN = "Richard E. Silverman",
            AUTHOR_ADDY_OSMANI = "Addy Osmani",
            SEARCH_QUERY_HARRY_POTTER = "Harry Potter",
            AUTHOR_JK_ROWLING = "J. K. Rowling",
            BOOK_TITLE_PHILOSOPHERS_STONE = "Harry Potter and the Philosopher's Stone",
            SERIES_F1 = "f1",
            SEASON_2025 = "2025",
            DRIVER_FERNANDO_ALONSO = "Fernando Alonso",
            DRIVER_PIERRE_GASLY = "Pierre Gasly",
            DRIVER_FIRST_NAME_GEORGE = "George",
            NATIONALITY_FRENCH = "French",
            NATIONALITY_BRITISH = "British",
            NATIONALITY_BRAZILIAN = "Brazilian",
            DATE_1990_01_01 = "1990-01-01",
            DATE_2000_01_01 = "2000-01-01";

    // Regex and messages
    public static final String
            THIRTEEN_DIGIT_NUMBER_REGEX = "\\d{13}",
            SESSION_NUMBER_NOT_FOUND_MESSAGE = "Message did not contain a 10-digit number: ",
            EXTRACTED_SESSION_NUMBER_MESSAGE = "Extracted session number: ",
            PET_NOT_FOUND_IN_STATUS_MESSAGE = "Posted pet was not found in findByStatus response";

}
