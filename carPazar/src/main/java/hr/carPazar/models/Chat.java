package hr.carPazar.models;
import lombok.Getter;
import lombok.Setter;
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
    @JoinColumn(name = "listing_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Listing listingId;

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User buyerId;

}