import React, { createContext, useContext, useState, ReactNode } from 'react';
import { createRoot } from 'react-dom/client';

// Типы
interface Product {
  id: number;
  name: string;
  price: number;
}

interface CartContextType {
  cart: Product[];
  addToCart: (product: Product) => void;
  removeFromCart: (index: number) => void;
  cartTotal: () => number;
}

// Context для глобального состояния (аналог prop drilling)
const CartContext = createContext<CartContextType | undefined>(undefined);

// Hook для доступа к корзине
function useCart() {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error('useCart must be used within CartProvider');
  }
  return context;
}

// Provider для управления состоянием корзины
function CartProvider({ children }: { children: ReactNode }) {
  const [cart, setCart] = useState<Product[]>([]);

  const addToCart = (product: Product) => {
    setCart(prev => [...prev, product]);
  };

  const removeFromCart = (index: number) => {
    setCart(prev => prev.filter((_, i) => i !== index));
  };

  const cartTotal = () => {
    return cart.reduce((sum, item) => sum + item.price, 0);
  };

  return (
    <CartContext.Provider value={{ cart, addToCart, removeFromCart, cartTotal }}>
      {children}
    </CartContext.Provider>
  );
}

// Доступные товары
const products: Product[] = [
  { id: 1, name: 'MacBook Pro', price: 2499 },
  { id: 2, name: 'iPhone 15', price: 999 },
  { id: 3, name: 'AirPods Pro', price: 249 },
  { id: 4, name: 'iPad Air', price: 599 },
];

// Header с счётчиком
function Header() {
  const { cart, cartTotal } = useCart();

  return (
    <header>
      <h2>Интернет-магазин</h2>
      <div>
        <span>Корзина: </span>
        <span className="badge">{cart.length}</span>
        <span> товаров</span>
        {cart.length > 0 && (
          <span style={{
            marginLeft: '1rem',
            color: 'var(--success-color)',
            fontWeight: 'bold'
          }}>
            ${cartTotal()}
          </span>
        )}
      </div>
    </header>
  );
}

// Список товаров
function ProductList() {
  const { addToCart } = useCart();

  return (
    <section>
      <h3>Каталог товаров</h3>
      <ul>
        {products.map(product => (
          <li
            key={product.id}
            style={{
              display: 'flex',
              justifyContent: 'space-between',
              alignItems: 'center'
            }}
          >
            <div>
              <strong>{product.name}</strong>
              <span style={{ marginLeft: '1rem', color: '#6b7280' }}>
                ${product.price}
              </span>
            </div>
            <button onClick={() => addToCart(product)}>
              Добавить в корзину
            </button>
          </li>
        ))}
      </ul>
    </section>
  );
}

// Корзина
function Cart() {
  const { cart, removeFromCart, cartTotal } = useCart();

  if (cart.length === 0) {
    return (
      <section>
        <h3>Корзина покупок</h3>
        <p>Корзина пуста</p>
      </section>
    );
  }

  return (
    <section>
      <h3>Корзина покупок</h3>
      <div>
        <ul>
          {cart.map((item, index) => (
            <li
              key={index}
              style={{
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center'
              }}
            >
              <div>
                <strong>{item.name}</strong>
                <span style={{ marginLeft: '1rem', color: '#6b7280' }}>
                  ${item.price}
                </span>
              </div>
              <button
                onClick={() => removeFromCart(index)}
                style={{ background: 'var(--danger-color)' }}
              >
                Удалить
              </button>
            </li>
          ))}
        </ul>
        <div style={{
          marginTop: '1rem',
          paddingTop: '1rem',
          borderTop: '2px solid var(--border-color)',
          fontSize: '1.25rem',
          fontWeight: 'bold'
        }}>
          Итого: ${cartTotal()}
        </div>
      </div>
    </section>
  );
}

// Главный компонент
function App() {
  return (
    <CartProvider>
      <div>
        <Header />
        <div style={{
          display: 'grid',
          gridTemplateColumns: '1fr 1fr',
          gap: '2rem',
          marginTop: '2rem'
        }}>
          <ProductList />
          <Cart />
        </div>
      </div>
    </CartProvider>
  );
}

// Инициализация
const root = createRoot(document.getElementById('app')!);
root.render(<App />);
