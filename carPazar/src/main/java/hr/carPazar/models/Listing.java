package hr.carPazar.models;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;


import java.time.LocalDateTime;

@Entity
@Table(name = "listing")
@Data
public class Listing {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User userId;

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

    @OneToOne(mappedBy = "listing", cascade = CascadeType.ALL)
    private Specification specifications;

}
