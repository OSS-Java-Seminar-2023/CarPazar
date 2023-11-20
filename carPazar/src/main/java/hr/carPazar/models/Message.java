package hr.carPazar.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Message {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Chat chatId;

    @Column(name = "message_content", nullable = false, columnDefinition = "text")
    private String messageContent;

    @Column(name = "message_datetime", nullable = false)
    private LocalDateTime messageDatetime;

}
