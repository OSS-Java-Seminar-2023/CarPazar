package hr.carpazar.models;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

@Setter
@Getter
@Entity
public class Chat {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "listing_id", referencedColumnName = "id")
    private hr.carpazar.models.Listing listingId;

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    private User buyerId;

}