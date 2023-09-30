package page.aaws.b01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "todo")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TodoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String subject;

    @Column(length = 2000, nullable = false)
    private String description;

    @Column()
    private LocalDateTime periodStartedAt;

    @Column()
    private LocalDateTime periodEndedAt;

    @Column()
    private Boolean done;

    public void changeSubject(String subject) {
        this.subject = subject;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changePeriod(LocalDateTime startedAt, LocalDateTime endedAt) {
        this.periodStartedAt = startedAt;
        this.periodEndedAt = endedAt;
    }

    public void hasDone(Boolean done) {
        this.done = done;
    }
}
