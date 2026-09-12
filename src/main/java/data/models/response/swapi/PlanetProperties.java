package data.models.response.swapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlanetProperties {

    private LocalDateTime created;
    private LocalDateTime edited;

    private String climate;

    @JsonProperty("surface_water")
    private String surfaceWater;

    private String name;
    private String diameter;

    @JsonProperty("rotation_period")
    private String rotationPeriod;

    private String terrain;
    private String gravity;

    @JsonProperty("orbital_period")
    private String orbitalPeriod;

    private String population;
    private String url;
}
