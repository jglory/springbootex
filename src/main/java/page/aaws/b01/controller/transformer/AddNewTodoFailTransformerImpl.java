package page.aaws.b01.controller.transformer;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class AddNewTodoFailTransformerImpl extends AddNewTodoFailTransformer {
    @Override
    public ResponseEntity<?> process(Object... data) {
        Exception exception = (Exception) data[0];
        HttpStatusCode httpStatusCode = (HttpStatusCode) data[1];

        Map<String, String> response = new HashMap<>();
        response.put("result", "fail");
        response.put("message", "입력 정보가 잘못 되었습니다. " + exception.getMessage());

        return new ResponseEntity<>(response, httpStatusCode);
    }
}
