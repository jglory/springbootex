package page.aaws.b01.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private Long id;

    private String subject;

    private String description;

    private LocalDateTime periodStartedAt;

    private LocalDateTime periodEndedAt;

    private Boolean done = false;
}
