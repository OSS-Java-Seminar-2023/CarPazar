package hr.carPazar.models;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "chat")
@Data
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
