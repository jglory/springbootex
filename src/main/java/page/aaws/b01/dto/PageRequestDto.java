package page.aaws.b01.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto {
    public static final int DEFAULT_NUMBER = 0;
    public static final int DEFAULT_SIZE = 10;

    @Builder.Default
    private int number = DEFAULT_NUMBER;

    @Builder.Default
    private int size = DEFAULT_SIZE;

    @Builder.Default
    private String[] types = {};

    @Builder.Default
    private String keyword = "";

    private Boolean done;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime periodStartedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime periodEndedAt;
}
