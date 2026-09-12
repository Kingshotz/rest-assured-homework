package data.models.request.petstore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private String status;
    private Boolean complete;
    private Long petId;
    private Integer quantity;
    private LocalDate shipDate;
    private Long id;
}
