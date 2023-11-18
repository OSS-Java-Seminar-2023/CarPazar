package hr.carPazar.carPazar.models;

import javax.persistence.*;
import lombok.Data;

import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

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
