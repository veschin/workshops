import { defineComponent, ref, watch, watchEffect, computed } from 'vue';

export default defineComponent({
  name: 'UseEffectHell',
  setup() {
    const count = ref(0);
    const multiplier = ref(2);

    // Problem: watch vs watchEffect confusion
    // watchEffect runs immediately and tracks all reactive dependencies
    watchEffect(() => {
      console.log('watchEffect: count * multiplier =', count.value * multiplier.value);
      // This runs on every change, including initial render
    });

    // watch requires explicit dependency specification
    watch(count, (newCount) => {
      console.log('watch count:', newCount);
      // Only runs when count changes, but misses multiplier changes
    });

    // Problem: computed vs watch confusion
    const product = computed(() => count.value * multiplier.value);

    // Manual effect-like behavior
    const updateTitle = () => {
      document.title = `Count: ${product.value}`;
    };

    // Need to call manually or use watchEffect
    watchEffect(updateTitle);

    const increment = () => {
      count.value++;
    };

    return {
      count,
      multiplier,
      product,
      increment,
    };
  },
  template: `
    <div>
      <h3>Vue useEffect/watch Hell</h3>
      <p>Count: {{ count }}</p>
      <p>Multiplier: {{ multiplier }}</p>
      <p>Product: {{ product }}</p>
      <button @click="increment">Increment Count</button>
      <button @click="multiplier++">Increase Multiplier</button>
      <p>Check console for watch vs watchEffect behavior</p>
    </div>
  `,
});