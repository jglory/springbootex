package page.aaws.b01.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto {
    @Builder.Default
    private int number = 1;

    @Builder.Default
    private int size = 10;

    @Builder.Default
    private String[] types = {};

    @Builder.Default
    private String keyword = "";

    private Boolean done;

    private LocalDateTime periodStartedAt;

    private LocalDateTime periodEndedAt;
}
