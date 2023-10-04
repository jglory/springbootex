package page.aaws.b01.controller.transformer;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class DeleteTodoFailTransformerImpl extends FailTransformerImpl {
    public DeleteTodoFailTransformerImpl(HttpStatusCode httpStatusCode, Exception exception) {
        super(httpStatusCode, exception);
    }

    @Override
    public ResponseEntity<?> process() {
        Map<String, String> response = new HashMap<>();
        response.put("result", "fail");
        response.put("message", "해당하는 일정 정보를 찾을 수 없습니다.");

        return new ResponseEntity<>(response, this.getHttpStatusCode());
    }
}
