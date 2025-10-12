import{r as s,c as p,a as d}from"../chunks/runtime-dom.esm-bundler-D8fGIWEs.js";const e=s([]);function n(t){e.value.push(t)}function a(t){e.value.splice(t,1)}const o=p(()=>e.value.reduce((t,c)=>t+c.price,0)),i=[{id:1,name:"MacBook Pro",price:2499},{id:2,name:"iPhone 15",price:999},{id:3,name:"AirPods Pro",price:249},{id:4,name:"iPad Air",price:599}],l={setup(){return{cart:e,products:i,addToCart:n,removeFromCart:a,cartTotal:o}},template:`
    <div>
      <Header />
      <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; margin-top: 2rem;">
        <ProductList />
        <Cart />
      </div>
    </div>
  `},m={setup(){return{cart:e,cartTotal:o}},template:`
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
  `},u={setup(){return{products:i,addToCart:n}},template:`
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
  `},v={setup(){return{cart:e,removeFromCart:a,cartTotal:o}},template:`
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
  `},r=d(l);r.component("Header",m);r.component("ProductList",u);r.component("Cart",v);r.mount("#app");
