package com.example.todos.todo;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("api/todos")
public class TodoController {

    private TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Todo> getTodo(@PathVariable Long id) {
        return todoRepository.findById(id);
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        todoRepository.save(todo);
        return todo;
    }

    @PutMapping("/{id}")
    public Todo editTodo(@RequestBody Todo todo, @PathVariable Long id) {
        Todo existingTodo = new Todo();
        try {
            existingTodo = todoRepository.findById(id).orElse(null);
            if (existingTodo != null) {
                existingTodo.setDescription(todo.getDescription());
                existingTodo.setComplete(todo.isComplete());
                existingTodo.setTitle(todo.getTitle());
                todoRepository.save(existingTodo);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            existingTodo = todoRepository.save(todo);
        }
        return existingTodo;
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
