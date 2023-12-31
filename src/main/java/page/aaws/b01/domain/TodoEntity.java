package page.aaws.b01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import lombok.*;

import org.hibernate.annotations.GenericGenerator;

import page.aaws.b01.util.SnowflakeIdGenerator;

@Entity(name = "todo")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class TodoEntity extends BaseEntity {
    @Id
    @GenericGenerator(name = "snowflake", type = SnowflakeIdGenerator.class)
    @GeneratedValue(generator = "snowflake")
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
