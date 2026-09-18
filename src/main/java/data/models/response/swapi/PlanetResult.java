package data.models.response.swapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlanetResult {

    private PlanetProperties properties;

    @JsonProperty("_id")
    private String id;

    private String description;
    private String uid;

    @JsonProperty("__v")
    private int version;
}
