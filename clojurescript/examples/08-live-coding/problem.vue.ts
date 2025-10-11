import { defineComponent, ref, reactive, computed, watch } from 'vue';

interface Todo {
  id: string;
  text: string;
  done: boolean;
}

export default defineComponent({
  name: 'TodoAppProblem',
  setup() {
    const input = ref('');
    const todos = ref<Todo[]>([]);
    const filterVal = ref<'all' | 'active' | 'completed'>('all');

    // Problem: Reactivity breaks if destructuring refs
    const { value: inputVal } = input; // This loses reactivity!

    // Manual persistence
    watch(todos, (newTodos) => {
      localStorage.setItem('todos', JSON.stringify(newTodos));
    }, { deep: true });

    // Load on mount
    const loadTodos = () => {
      const saved = localStorage.getItem('todos');
      if (saved) todos.value = JSON.parse(saved);
    };
    loadTodos();

    const addTodo = () => {
      if (input.value.trim()) {
        todos.value.push({ id: Date.now().toString(), text: input.value, done: false });
        input.value = '';
      }
    };

    const toggleTodo = (id: string) => {
      todos.value = todos.value.map(todo => 
        todo.id === id ? { ...todo, done: !todo.done } : todo
      ); // Manual immutability
    };

    const deleteTodo = (id: string) => {
      todos.value = todos.value.filter(todo => todo.id !== id);
    };

    const filteredTodos = computed(() => {
      return todos.value.filter(todo => {
        if (filterVal.value === 'active') return !todo.done;
        if (filterVal.value === 'completed') return todo.done;
        return true;
      });
    });

    // Problem: watch vs computed confusion, deep watch overhead
    watch(filteredTodos, (newFiltered) => {
      console.log('Filtered todos changed:', newFiltered.length);
    });

    return {
      input,
      todos,
      filterVal,
      filteredTodos,
      addTodo,
      toggleTodo,
      deleteTodo,
    };
  },
  template: `
    <div>
      <h3>Vue TODO Problem</h3>
      <input 
        v-model="input" 
        @keyup.enter="addTodo"
        placeholder="Add todo" 
      />
      <ul>
        <li v-for="todo in filteredTodos" :key="todo.id" :class="{ completed: todo.done }">
          <input type="checkbox" v-model="todo.done" @change="toggleTodo(todo.id)" />
          <span>{{ todo.text }}</span>
          <button @click="deleteTodo(todo.id)">Delete</button>
        </li>
      </ul>
      <div>
        <button @click="filterVal = 'all'">All</button>
        <button @click="filterVal = 'active'">Active</button>
        <button @click="filterVal = 'completed'">Completed</button>
      </div>
      <p>Destructuring inputVal won't update UI! Check localStorage on reload.</p>
    </div>
  `,
});