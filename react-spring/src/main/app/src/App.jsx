import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const [todos, setTodos] = useState([]);

  // Fetch todos on component mount
  useEffect(() => {
    fetch('http://localhost:8080/todos')
      .then(response => response.json())
      .then(data => setTodos(data))
      .catch(error => console.error(error));
  }, []);

  // Function to add a new TODO item
  const addTodo = (description) => {
    fetch('http://localhost:8080/todos', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json'},
      body: JSON.stringify({description}),
    })
    .then(response => response.json())
    .then(newTodo => setTodos([...todos, newTodo]))
    .catch(error => console.error(error));
  };

  // Toggle completion
  const toggleTodoComplete = (id) => {
    const updatedTodos = todos.map(todo => {
      if (todo.id === id) {
        return { ...todo, completed: !todo.completed};
      }
      return todo;
    });
    setTodos(updatedTodos);

    // Update completion
    fetch(`http://localhost:8080/todos/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json'},
      body: JSON.stringify({ completed: !todos.find(todo => todo.id === id).completed }),
    })
    .catch(error => console.error(error));
  }

  return (
    <div className='App'>
      <header className="App-header"><h1>My TODO App</h1></header>
        <input id='todo-input' type="text" placeholder="Add to TODO" />
        <button onClick={(e) => addTodo(document.getElementById('todo-input').value)}>Add TODO</button>
        <ul>
          {todos.map(todo => (
            <li key={todo.id}>
              <input type="checkbox" checked={todo.completed} onChange={() => toggleTodoComplete(todo.id)} />
                      {todo.description}
        <button onClick={() => deleteTodo(todo.id)}>🗑</button>
            </li>
          ))}
        </ul>
    </div> 
  )
}

export default App
