package hr.carPazar.carPazar.models;

import javax.persistence.*;
import lombok.Data;

import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "listing")
@Data
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "listing_datetime", nullable = false)
    private LocalDateTime listingDatetime;

    @Column(name = "title", nullable = false, length = 64)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "is_sponsored", nullable = false)
    private Boolean isSponsored;

    @Column(name = "is_sold", nullable = false)
    private Boolean isSold;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Specification specification;

}
