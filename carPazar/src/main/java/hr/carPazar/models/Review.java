package hr.carPazar.models;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;


@Entity
@Table(name = "review")
@Data
public class Review {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "review", nullable = false, columnDefinition = "text")
    private String review;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "rating", nullable = false)
    private Integer rating;



}
