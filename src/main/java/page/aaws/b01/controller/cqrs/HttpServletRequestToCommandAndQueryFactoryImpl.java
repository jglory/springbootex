package page.aaws.b01.controller.cqrs;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import page.aaws.b01.cqrs.CommandAndQuery;
import page.aaws.b01.cqrs.CommandAndQueryFactory;
import page.aaws.b01.controller.cqrs.command.*;
import page.aaws.b01.dto.TodoDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

@Component
public class HttpServletRequestToCommandAndQueryFactoryImpl implements CommandAndQueryFactory {
    @Override
    public <T> T create(Object input, Class<T> requiredType) {
        HttpServletRequest request = (HttpServletRequest) input;

        if (requiredType.equals(AddNewTodoCommandImpl.class)) {
            TodoDto todoDto = new TodoDto();

            try {
                String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
                JSONParser jsonParser = new JSONParser(body);
                LinkedHashMap<String, Object> json = jsonParser.object();

                todoDto.setId((Long)json.get("id"));
                todoDto.setSubject((String)json.get("subject"));
                todoDto.setDescription((String)json.get("description"));
                todoDto.setPeriodStartedAt(
                        json.get("periodStartedAt") == null ? null : LocalDateTime.parse((String)json.get("periodStartedAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                );
                todoDto.setPeriodStartedAt(
                        json.get("setPeriodEndedAt") == null ? null : LocalDateTime.parse((String)json.get("setPeriodEndedAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                );
                todoDto.setDone((Boolean)json.get("done"));
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }

            return (T) new AddNewTodoCommandImpl(todoDto);
        }
        return null;
    }
}