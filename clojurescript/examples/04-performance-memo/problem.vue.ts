import { defineComponent, ref, computed, watchEffect } from 'vue';

export default defineComponent({
  name: 'PerformanceMemoProblem',
  setup() {
    const count = ref(0);
    const data = ref([1, 2, 3, 4, 5]);

    // Problem: computed vs methods confusion, overhead on reactivity
    const doubledData = computed(() => {
      console.log('Doubling data...'); // Logs on every access if not careful
      return data.value.map(x => x * 2);
    });

    const sum = computed(() => {
      console.log('Calculating sum...');
      return data.value.reduce((a, b) => a + b, 0);
    });

    // Manual effect for title update
    watchEffect(() => {
      document.title = `Count: ${count.value}, Sum: ${sum.value}`;
    });

    const increment = () => count.value++;
    const addNumber = () => data.value.push(Math.floor(Math.random() * 10));

    return {
      count,
      data,
      doubledData,
      sum,
      increment,
      addNumber,
    };
  },
  template: `
    <div>
      <h3>Vue Performance Memo Problem</h3>
      <p>Count: {{ count }}</p>
      <p>Doubled data: {{ doubledData.join(', ') }}</p>
      <p>Sum: {{ sum }}</p>
      <button @click="increment">Increment</button>
      <button @click="addNumber">Add Random Number</button>
      <p>Check console - computations run on every reactivity trigger</p>
    </div>
  `,
});