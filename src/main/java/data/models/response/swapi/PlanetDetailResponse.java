package data.models.response.swapi;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PlanetDetailResponse(
        String message,
        PlanetResult result,
        String apiVersion,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        LocalDateTime timestamp,
        PlanetSupport support
) {
}
