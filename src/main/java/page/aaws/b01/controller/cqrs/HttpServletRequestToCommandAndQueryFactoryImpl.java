package page.aaws.b01.controller.cqrs;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerMapping;
import page.aaws.b01.controller.cqrs.query.GetTodoQueryImpl;
import page.aaws.b01.controller.cqrs.query.GetTodosByPageQueryImpl;
import page.aaws.b01.cqrs.CommandAndQueryFactory;
import page.aaws.b01.controller.cqrs.command.*;
import page.aaws.b01.dto.PageRequestDto;
import page.aaws.b01.dto.TodoDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

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
        } else if (requiredType.equals(DeleteTodoCommandImpl.class)) {
            Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            return (T) new DeleteTodoCommandImpl(Long.valueOf(map.get("id")));
        } else if (requiredType.equals(GetTodoQueryImpl.class)) {
            Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            return (T) new GetTodoQueryImpl(Long.valueOf(map.get("id")));
        } else if (requiredType.equals(GetTodosByPageQueryImpl.class)) {
            return (T) new GetTodosByPageQueryImpl(
                    PageRequestDto.builder()
                            .number(request.getParameter("number") == null ? PageRequestDto.DEFAULT_NUMBER : Integer.valueOf(request.getParameter("number")))
                            .size(request.getParameter("size") == null ? PageRequestDto.DEFAULT_SIZE : Integer.valueOf(request.getParameter("size")))
                            .types(request.getParameterValues("types"))
                            .keyword(request.getParameter("keyword"))
                            .done(request.getParameter("done") == null ? null : Boolean.valueOf(request.getParameter("done")))
                            .periodStartedAt(request.getParameter("periodStartedAt") == null ? null : LocalDateTime.parse(request.getParameter("periodStartedAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                            .periodEndedAt(request.getParameter("periodEndedAt") == null ? null : LocalDateTime.parse(request.getParameter("periodEndedAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                            .build()
            );
        }
        return null;
    }
}
