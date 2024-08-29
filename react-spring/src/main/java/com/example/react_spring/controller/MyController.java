package com.example.react_spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.react_spring.model.TodoItem;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class MyController {
    private static List<TodoItem> items = new ArrayList<>();

    static {        
        items.add(new TodoItem(0, "Watch the sunset"));
        items.add(new TodoItem(1, "Read Venkatesananda's Supreme Yoga"));
        items.add(new TodoItem(2, "Watch the mind"));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoItem>> getTodos(){
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<TodoItem> getTodo(@PathVariable Long id) {
        TodoItem todo = items.stream()
            .filter(item -> item.getId().equals(id))
            .findFirst()
            .orElse(null);

        if (todo != null) {
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    //Create a  new TODO item
    @PostMapping("/todos")
    public ResponseEntity<TodoItem> createTodo(@RequestBody TodoItem newTodo) {
        // Generate a unique ID (simple approach for this example)
        Integer nextId = items.stream().mapToInt(TodoItem::getId).max().orElse(0) + 1;
        newTodo.setId(nextId);
        return new ResponseEntity<>(newTodo, HttpStatus.CREATED);
    }

    // Update (toggle completion) a TODO item
    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoItem> updateTodoCompleted(@PathVariable Integer id) {
        System.out.println("BEGIN update: " + id);
        Optional<TodoItem> optionalTodo = items.stream().filter(item -> item.getId().equals(id)).findFirst();
        if (optionalTodo.isPresent()) {
            optionalTodo.get().setCompleted(!optionalTodo.get().isCompleted());
            return new ResponseEntity<>(optionalTodo.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a TODO item
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Integer id) {
        System.out.println("BEGIN delete: " + id);
        Optional<TodoItem> optionalTodo = items.stream().filter(item -> item.getId().equals(id)).findFirst();
        System.out.println(optionalTodo);
        if (optionalTodo.isPresent()) {
            items.removeIf(item ->  item.getId().equals(optionalTodo.get().getId()));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}