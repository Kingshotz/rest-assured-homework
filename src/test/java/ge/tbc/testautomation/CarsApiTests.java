package ge.tbc.testautomation;

import ge.tbc.testautomation.data.Constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static ge.tbc.testautomation.data.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CarsApiTests {
    private RequestSpecification spec;
    private JsonPath jsonPath;

    @BeforeClass
    public void beforeClass() {
        spec = new RequestSpecBuilder()
                .setBaseUri(Constants.DRIVERS_URI)
                .build();
        jsonPath = given()
                .spec(spec)
                .basePath(Constants.DRIVERS_PATH)
                .queryParam(LIMIT_PARAM, 40)
                .when()
                .get()
                .then()
                .extract().jsonPath()
                .setRootPath(MR_DATA_ROOT_PATH);
    }

    @Test
    public void validateSeriesAndSeason(){
        assertThat(jsonPath.getString(SERIES_PATH), equalTo(SERIES_F1));
        assertThat(jsonPath.getString(DRIVER_TABLE_SEASON_PATH), equalTo(SEASON_2025));
    }

    @Test
    public void validateTotalNumberOfDrivers() {
        assertThat(Integer.parseInt(jsonPath.get(TOTAL_PATH)), equalTo(jsonPath.getList(DRIVER_TABLE_DRIVERS_PATH).size()));
    }

    @Test
    public void validateFullNameFirstBefore1990() {
        String dateOfBirth = DATE_1990_01_01;
        Map<String, String> driver = jsonPath
                .param(DATE_OF_BIRTH_KEY, dateOfBirth)
                .get(DRIVER_BORN_BEFORE_DATE_QUERY);
        String FullName = toFullName(driver);
        assertThat(FullName, equalTo(DRIVER_FERNANDO_ALONSO));
    }

    @Test
    public void validateNumberOfDriversAfter2000() {
        String dateOfBirth = DATE_2000_01_01;
        List<Map<String, String>> drivers = jsonPath
                .param(DATE_OF_BIRTH_KEY, dateOfBirth)
                .getList(DRIVERS_BORN_AFTER_DATE_QUERY);
        assertThat(drivers.size(), greaterThan(8));
    }

    @Test
    public void balidateNumberOfFrenchs() {
        String nationality = NATIONALITY_FRENCH;
        List<Map<String, String>> french = jsonPath
                .param(NATIONALITY_KEY, nationality)
                .getList(DRIVERS_BY_NATIONALITY_QUERY);
        assertThat(french.size(), equalTo(3));
    }

    @Test
    public void familyNameStartsWithAOrBMoreThanFive() {
        String nationality = NATIONALITY_FRENCH;
        List<Map<String,String>> drivers = jsonPath
                .getList(DRIVERS_FAMILY_NAME_STARTS_WITH_A_OR_B_QUERY);
        assertThat(drivers.size(), greaterThan(5));
    }

    @Test
    public void permanentNumberBelow10OrLongFamilyNameCountIsAtLeast5() {
        List<Map<String, String>> drivers = jsonPath.getList(DRIVERS_LOW_NUMBER_OR_LONG_FAMILY_NAME_QUERY);

        List<String> fullNames = drivers.stream().map(this::toFullName).toList();
        fullNames.forEach(System.out::println);

        assertThat(fullNames.size(), greaterThanOrEqualTo(5));
    }

    @Test
    public void testByNationality() {
        assertThat(getDriversByNationality(NATIONALITY_BRITISH), hasItem(containsString(DRIVER_FIRST_NAME_GEORGE)));
        assertThat(getDriversByNationality(NATIONALITY_BRAZILIAN), hasSize(greaterThan(0)));
        assertThat(getDriversByNationality(NATIONALITY_FRENCH), hasItem(DRIVER_PIERRE_GASLY));
    }


    private List<String> getDriversByNationality(String nationality) {
        List<Map<String, String>> drivers = jsonPath
                .param(NATIONALITY_KEY, nationality)
                .getList(DRIVERS_BY_NATIONALITY_QUERY);

        return drivers.stream()
                .map(this::toFullName)
                .toList();
    }

    private String toFullName(Map<String, String> driver){
        return driver.get(GIVEN_NAME_KEY) + " " + driver.get(FAMILY_NAME_KEY);
    }
}
