package api.tests;

import api.client.SwapiApi;
import data.Constants;
import data.models.response.swapi.PlanetDetailResponse;
import data.models.response.swapi.PlanetsListResponse;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Feature("SWAPI Planets")
public class SwapiTest {

    SwapiApi swapiApi;
    PlanetsListResponse planetsList;
    List<PlanetDetailResponse> planets;

    @BeforeClass
    public void beforeClass() {
        swapiApi = new SwapiApi();

        planetsList = swapiApi.getPlanets()
                .then()
                .statusCode(200)
                .extract()
                .as(PlanetsListResponse.class);

        planets = planetsList.results().stream()
                .map(planet -> swapiApi.getPlanetByUrl(planet.url())
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(PlanetDetailResponse.class))
                .collect(Collectors.toList());
    }

    @Test
    @Story("Planets list response has the expected fields")
    public void planetsListFieldsAreValid() {
        Assert.assertEquals(planetsList.message(), Constants.EXPECTED_MESSAGE);
        Assert.assertEquals(planetsList.results().size(), Constants.EXPECTED_SIZE);
        Assert.assertTrue(planetsList.totalRecords() > 0);
        Assert.assertNotNull(planetsList.timestamp());
        Assert.assertEquals(planetsList.support().contact(), Constants.EXPECTED_SUPPORT_CONTACT);
    }

    @Test
    @Story("Three most recently edited planets are identified")
    public void threeMostRecentlyEditedPlanets() {
        List<PlanetDetailResponse> topThree = planets.stream()
                .sorted(Comparator.comparing((PlanetDetailResponse p) -> p.result().getProperties().getEdited()).reversed())
                .limit(3)
                .collect(Collectors.toList());

        Assert.assertEquals(topThree.size(), 3);

        LocalDateTime previousEdited = null;
        for (PlanetDetailResponse planet : topThree) {
            LocalDateTime edited = planet.result().getProperties().getEdited();
            Assert.assertNotNull(edited);
            if (previousEdited != null) {
                Assert.assertFalse(edited.isAfter(previousEdited));
            }
            previousEdited = edited;
        }
    }

    @Test
    @Story("Planet with the highest rotation period is found")
    public void planetWithHighestRotationPeriod() {
        PlanetDetailResponse topPlanet = planets.stream()
                .max(Comparator.comparingInt(p -> Integer.parseInt(p.result().getProperties().getRotationPeriod())))
                .orElseThrow();

        Assert.assertEquals(topPlanet.result().getProperties().getName(), "Kamino");
        Assert.assertEquals(topPlanet.result().getProperties().getRotationPeriod(), "27");
        Assert.assertEquals(topPlanet.message(), "ok");
        Assert.assertNotNull(topPlanet.result().getUid());
        Assert.assertNotNull(topPlanet.result().getProperties().getUrl());
    }
}
