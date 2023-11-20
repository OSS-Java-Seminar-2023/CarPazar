package hr.carPazar.models;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Data
public class Message {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "chat_id", nullable = false)
    private UUID chatId;

    @Column(name = "message_content", nullable = false, columnDefinition = "text")
    private String messageContent;

    @Column(name = "message_datetime", nullable = false)
    private LocalDateTime messageDatetime;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Chat chat;

}
