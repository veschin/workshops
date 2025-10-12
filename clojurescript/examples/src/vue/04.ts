import { createApp, ref, computed } from 'vue';

// Типы
interface Product {
  id: number;
  name: string;
  price: number;
}

// Глобальное состояние корзины (реактивное)
const cart = ref<Product[]>([]);

// Функции для работы с корзиной
function addToCart(product: Product) {
  cart.value.push(product);
}

function removeFromCart(index: number) {
  cart.value.splice(index, 1);
}

const cartTotal = computed(() => {
  return cart.value.reduce((sum, item) => sum + item.price, 0);
});

// Доступные товары
const products: Product[] = [
  { id: 1, name: 'MacBook Pro', price: 2499 },
  { id: 2, name: 'iPhone 15', price: 999 },
  { id: 3, name: 'AirPods Pro', price: 249 },
  { id: 4, name: 'iPad Air', price: 599 },
];

// Vue приложение
const App = {
  setup() {
    return {
      cart,
      products,
      addToCart,
      removeFromCart,
      cartTotal
    };
  },
  template: `
    <div>
      <Header />
      <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; margin-top: 2rem;">
        <ProductList />
        <Cart />
      </div>
    </div>
  `
};

// Header компонент
const Header = {
  setup() {
    return {
      cart,
      cartTotal
    };
  },
  template: `
    <header>
      <h2>Интернет-магазин</h2>
      <div>
        <span>Корзина: </span>
        <span class="badge">{{ cart.length }}</span>
        <span> товаров</span>
        <span v-if="cart.length > 0" 
              style="margin-left: 1rem; color: var(--success-color); font-weight: bold;">
          \${{ cartTotal }}
        </span>
      </div>
    </header>
  `
};

// ProductList компонент
const ProductList = {
  setup() {
    return {
      products,
      addToCart
    };
  },
  template: `
    <section>
      <h3>Каталог товаров</h3>
      <ul>
        <li v-for="product in products" 
            :key="product.id"
            style="display: flex; justify-content: space-between; align-items: center;">
          <div>
            <strong>{{ product.name }}</strong>
            <span style="margin-left: 1rem; color: #6b7280;">
              \${{ product.price }}
            </span>
          </div>
          <button @click="addToCart(product)">
            Добавить в корзину
          </button>
        </li>
      </ul>
    </section>
  `
};

// Cart компонент
const Cart = {
  setup() {
    return {
      cart,
      removeFromCart,
      cartTotal
    };
  },
  template: `
    <section>
      <h3>Корзина покупок</h3>
      <div v-if="cart.length === 0">
        <p>Корзина пуста</p>
      </div>
      <div v-else>
        <ul>
          <li v-for="(item, index) in cart" 
              :key="index"
              style="display: flex; justify-content: space-between; align-items: center;">
            <div>
              <strong>{{ item.name }}</strong>
              <span style="margin-left: 1rem; color: #6b7280;">
                \${{ item.price }}
              </span>
            </div>
            <button @click="removeFromCart(index)"
                    style="background: var(--danger-color);">
              Удалить
            </button>
          </li>
        </ul>
        <div style="margin-top: 1rem; padding-top: 1rem; border-top: 2px solid var(--border-color); 
                    font-size: 1.25rem; font-weight: bold;">
          Итого: \${{ cartTotal }}
        </div>
      </div>
    </section>
  `
};

// Регистрация компонентов
const app = createApp(App);
app.component('Header', Header);
app.component('ProductList', ProductList);
app.component('Cart', Cart);

// Монтирование приложения
app.mount('#app');