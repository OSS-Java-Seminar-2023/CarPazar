package hr.carpazar.models;

import jakarta.persistence.*;
import lombok.*;
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
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chatId;

    @Column(name = "message_content", nullable = false, columnDefinition = "text")
    private String messageContent;

    @Column(name = "message_datetime", nullable = false)
    private LocalDateTime messageDatetime;

}
