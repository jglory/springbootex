package page.aaws.b01.controller.transformer;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DeleteTodoFailTransformerImpl extends DeleteTodoFailTransformer {
    @Override
    public ResponseEntity<?> process(Object... data) {
        HttpStatusCode httpStatusCode = (HttpStatusCode) data[1];

        Map<String, String> response = new HashMap<>();
        response.put("result", "fail");
        response.put("message", "해당하는 일정 정보를 찾을 수 없습니다.");

        return new ResponseEntity<>(response, httpStatusCode);
    }
}
