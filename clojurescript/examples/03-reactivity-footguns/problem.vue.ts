import { defineComponent, reactive, ref, computed } from 'vue';

export default defineComponent({
  name: 'ReactivityFootguns',
  setup() {
    const state = reactive({
      count: 0,
      user: { name: 'Alice', age: 25 }
    });

    // Problem: destructuring breaks reactivity
    const { count } = state;
    const countRef = ref(count); // This captures the initial value only!

    // Problem: destructuring nested objects
    const { user } = state;
    const userRef = ref(user); // Loses reactivity to user changes

    // Problem: ref vs reactive confusion
    const countAsRef = ref(0);
    const countAsReactive = reactive({ value: 0 });

    const increment = () => {
      state.count++;
      countAsRef.value++;
      countAsReactive.value++;
    };

    const updateUser = () => {
      state.user.name = 'Bob';
      state.user.age = 30;
    };

    // Computed works correctly
    const computedCount = computed(() => state.count);

    return {
      state,
      countRef,
      userRef,
      countAsRef,
      countAsReactive,
      computedCount,
      increment,
      updateUser,
    };
  },
  template: `
    <div>
      <h3>Vue Reactivity Footguns</h3>
      <p>Reactive state count: {{ state.count }}</p>
      <p>Destructured count (broken): {{ countRef }} - won't update!</p>
      <p>Computed count (works): {{ computedCount }}</p>
      <p>User name: {{ state.user.name }}</p>
      <p>Destructured user name (broken): {{ userRef.name }} - won't update!</p>
      <p>Ref count: {{ countAsRef }}</p>
      <p>Reactive count: {{ countAsReactive.value }}</p>
      <button @click="increment">Increment All Counts</button>
      <button @click="updateUser">Update User</button>
    </div>
  `,
});