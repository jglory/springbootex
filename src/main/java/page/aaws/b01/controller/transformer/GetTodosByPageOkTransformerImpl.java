package page.aaws.b01.controller.transformer;

import org.springframework.http.ResponseEntity;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.dto.PageDto;

public class GetTodosByPageOkTransformerImpl extends OkTransformer {
    private final PageDto<TodoDto> pageDto;

    public GetTodosByPageOkTransformerImpl(PageDto<TodoDto> pageDto) {
        this.pageDto = pageDto;
    }

    @Override
    public ResponseEntity<?> process() {
        return ResponseEntity.ok(this.pageDto);
    }
}
