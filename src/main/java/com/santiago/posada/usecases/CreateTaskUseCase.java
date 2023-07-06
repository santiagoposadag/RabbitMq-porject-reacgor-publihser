package com.santiago.posada.usecases;

import com.santiago.posada.adapters.RabbitMqEventPublisher;
import com.santiago.posada.repository.ToDoRepository;
import com.santiago.posada.repository.model.ToDo;
import com.santiago.posada.usecases.businessExceptions.LengthOfTaskException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class CreateTaskUseCase implements Function<ToDo, Mono<ToDo>> {

    private final ToDoRepository toDoRepository;
    private final RabbitMqEventPublisher publisher;

    public CreateTaskUseCase(ToDoRepository toDoRepository, RabbitMqEventPublisher publisher) {
        this.toDoRepository = toDoRepository;
        this.publisher = publisher;
    }

    @Override
    public Mono<ToDo> apply(ToDo toDo) {

        return Mono.just(toDo)
                .flatMap(todo -> {
                    if(todo.getTask().length() < 10){
                        return Mono.error(new LengthOfTaskException("El mensaje de la tarea a realizar es muy corto"));
                    }
                    return Mono.just(todo);
                }).flatMap(todo -> toDoRepository.save(toDo).onErrorMap(error -> new InternalError(error.getMessage())))
                .map(todo -> {
                    publisher.publishTaskCreated(todo).subscribe();
                    return todo;
                });
    }
}
