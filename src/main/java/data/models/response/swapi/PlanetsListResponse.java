package data.models.response.swapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record PlanetsListResponse(
        String message,
        @JsonProperty("total_records") int totalRecords,
        @JsonProperty("total_pages") int totalPages,
        Object previous,
        String next,
        List<PlanetSummary> results,
        String apiVersion,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        LocalDateTime timestamp,
        PlanetSupport support
) {
}
