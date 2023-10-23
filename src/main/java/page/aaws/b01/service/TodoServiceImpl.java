package page.aaws.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import page.aaws.b01.domain.TodoEntity;
import page.aaws.b01.dto.PageDto;
import page.aaws.b01.dto.PageRequestDto;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.repository.TodoRepository;

import java.util.NoSuchElementException;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final ModelMapper modelMapper;

    private final TodoRepository todoRepository;

    @Override
    public Long addNewTodo(TodoDto dto) {
        return todoRepository.save(modelMapper.map(dto, TodoEntity.class)).getId();
    }

    @Override
    public TodoDto getTodo(Long id) {
        return this.modelMapper.map(todoRepository.findById(id).orElseThrow(), TodoDto.class);
    }

    @Override
    public PageDto<TodoDto> getTodosByPage(PageRequestDto pageRequestDto) {
        Page<TodoEntity> page = todoRepository.getTodosByPage(pageRequestDto, PageRequest.of(pageRequestDto.getNumber(), pageRequestDto.getSize(), Sort.by("createdAt").descending()));

        return PageDto.<TodoDto>builder()
                .number(pageRequestDto.getNumber())
                .size(pageRequestDto.getSize())
                .totalElements(page.getTotalElements())
                .items(page.getContent().stream().map(item -> this.modelMapper.map(item, TodoDto.class)).toList())
                .build();
    }

    @Override
    public TodoDto updateTodo(TodoDto dto) {
        TodoEntity todoEntity = todoRepository.findById(dto.getId()).orElseThrow();
        todoEntity.changeSubject(dto.getSubject());
        todoEntity.changeDescription(dto.getDescription());
        todoEntity.changePeriod(dto.getPeriodStartedAt(), dto.getPeriodEndedAt());
        todoEntity.hasDone(true);
        todoRepository.save(todoEntity);
        return dto;
    }

    @Override
    public Long deleteTodo(Long id) throws NoSuchElementException {
        this.todoRepository.findById(id).orElseThrow();
        todoRepository.deleteById(id);
        return id;
    }
}
