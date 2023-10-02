package page.aaws.b01.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PageDto<T> {
    private int number;

    private int size;

    private long totalElements;

    private List<T> items;

    public int getTotalPages() {
        return Long.valueOf(this.totalElements / this.size + (this.totalElements % this.size > 0 ? 1 : 0)).intValue();
    }
}
