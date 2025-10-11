import React, { useState, useEffect } from 'react';

interface Todo {
  id: string;
  text: string;
  done: boolean;
}

const TodoApp: React.FC = () => {
  const [todos, setTodos] = useState<Todo[]>([]);
  const [input, setInput] = useState('');
  const [filter, setFilter] = useState<'all' | 'active' | 'completed'>('all');

  // Problem: Manual effect for persistence, potential stale closures
  useEffect(() => {
    const saved = localStorage.getItem('todos');
    if (saved) setTodos(JSON.parse(saved));
  }, []);

  useEffect(() => {
    localStorage.setItem('todos', JSON.stringify(todos));
  }, [todos]); // Missing filter dep if needed elsewhere

  const addTodo = () => {
    if (input.trim()) {
      setTodos(prev => [...prev, { id: Date.now().toString(), text: input, done: false }]);
      setInput('');
    }
  };

  const toggleTodo = (id: string) => {
    setTodos(prev => prev.map(todo => 
      todo.id === id ? { ...todo, done: !todo.done } : todo
    )); // Spread hell for immutability
  };

  const deleteTodo = (id: string) => {
    setTodos(prev => prev.filter(todo => todo.id !== id));
  };

  const filteredTodos = todos.filter(todo => {
    if (filter === 'active') return !todo.done;
    if (filter === 'completed') return todo.done;
    return true;
  });

  // Problem: Props drilling if this were in a larger app
  return (
    <div>
      <h3>React TODO Problem</h3>
      <input 
        value={input} 
        onChange={e => setInput(e.target.value)} 
        onKeyDown={e => e.key === 'Enter' && addTodo()}
        placeholder="Add todo" 
      />
      <ul>
        {filteredTodos.map(todo => (
          <li key={todo.id} className={todo.done ? 'completed' : ''}>
            <input 
              type="checkbox" 
              checked={todo.done} 
              onChange={() => toggleTodo(todo.id)} 
            />
            <span>{todo.text}</span>
            <button onClick={() => deleteTodo(todo.id)}>Delete</button>
          </li>
        ))}
      </ul>
      <div>
        <button onClick={() => setFilter('all')}>All</button>
        <button onClick={() => setFilter('active')}>Active</button>
        <button onClick={() => setFilter('completed')}>Completed</button>
      </div>
      <p>Check console/localStorage for persistence issues on reload</p>
    </div>
  );
};

export default TodoApp;