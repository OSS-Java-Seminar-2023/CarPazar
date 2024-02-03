package hr.carpazar.dtos;

import hr.carpazar.models.Specification;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ListingDto {
    private LocalDateTime localDateTime;
    private String title;
    private String description;
    private Long price;
    private Boolean isSponsored;
    private Boolean isSold;
    private Specification specifications;
    private String id;
}
