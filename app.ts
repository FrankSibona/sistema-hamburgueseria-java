interface CartItem {
  id: string;
  name: string;
  ingredients: string;
  price: number;
  quantity: number;
  image: string;
  tag: string;
}

const initialItems: CartItem[] = [
  {
    id: 'brutal-01',
    name: 'La Brutal',
    ingredients: 'Doble carne, cheddar, cebolla asada y salsa bruta.',
    price: 8.5,
    quantity: 1,
    image: 'images/bruta-burger.png',
    tag: 'Top seller',
  },
  {
    id: 'rocki-02',
    name: 'Rocki Stack',
    ingredients: 'Papas crunch, bacon y salsa de la casa.',
    price: 10.2,
    quantity: 2,
    image: 'images/combo-stack.png',
    tag: 'Combo',
  },
  {
    id: 'chill-03',
    name: 'Bebida Chill',
    ingredients: 'Limonada de hierbabuena con hielo fino.',
    price: 3.2,
    quantity: 1,
    image: 'images/drink-chill.png',
    tag: 'Refresco',
  },
];

const cartItemsEl = document.querySelector<HTMLDivElement>('#cart-items');
const emptyStateEl = document.querySelector<HTMLElement>('#empty-state');
const subtotalValueEl = document.querySelector<HTMLElement>('#subtotal-value');
const shippingValueEl = document.querySelector<HTMLElement>('#shipping-value');
const serviceValueEl = document.querySelector<HTMLElement>('#service-value');
const totalValueEl = document.querySelector<HTMLElement>('#total-value');
const emptyCartBtn = document.querySelector<HTMLButtonElement>('#empty-cart-btn');
const restoreCartBtn = document.querySelector<HTMLButtonElement>('#restore-cart-btn');

let cartItems = [...initialItems];

function formatCurrency(value: number): string {
  return new Intl.NumberFormat('es-AR', {
    style: 'currency',
    currency: 'ARS',
  }).format(value);
}

function renderCart(): void {
  if (!cartItemsEl || !subtotalValueEl || !shippingValueEl || !serviceValueEl || !totalValueEl) {
    return;
  }

  if (cartItems.length === 0) {
    cartItemsEl.innerHTML = '';
    emptyStateEl?.classList.remove('hidden');
  } else {
    emptyStateEl?.classList.add('hidden');
  }

  cartItemsEl.innerHTML = cartItems
    .map(
      (item) => `
        <li class="cart-item" data-id="${item.id}">
          <figure class="product-thumb">
            <img src="${item.image}" alt="${item.name}" />
          </figure>
          <div class="item-copy">
            <div class="item-head">
              <h3>${item.name}</h3>
              <button class="remove-btn" data-action="remove" data-id="${item.id}" type="button" aria-label="Eliminar ${item.name}">✕</button>
            </div>
            <p>${item.ingredients}</p>
            <div class="item-meta">
              <span class="meta-pill">${item.tag}</span>
              <span class="meta-pill">${item.quantity} unidad/es</span>
            </div>
            <div class="item-pricing">
              <span>Precio unitario</span>
              <strong>${formatCurrency(item.price)}</strong>
              <small>Subtotal: ${formatCurrency(item.price * item.quantity)}</small>
            </div>
          </div>
          <div class="qty-wrap">
            <div class="qty-controls">
              <button class="qty-btn" data-action="decrease" data-id="${item.id}" type="button">−</button>
              <span class="qty-value">${item.quantity}</span>
              <button class="qty-btn" data-action="increase" data-id="${item.id}" type="button">+</button>
            </div>
            <span class="price-tag">${formatCurrency(item.price * item.quantity)}</span>
          </div>
        </li>
      `,
    )
    .join('');

  updateSummary();
}

function updateSummary(): void {
  const subtotal = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  const shipping = subtotal > 0 ? 4.5 : 0;
  const service = subtotal > 0 ? 2 : 0;
  const total = subtotal + shipping + service;

  subtotalValueEl!.textContent = formatCurrency(subtotal);
  shippingValueEl!.textContent = formatCurrency(shipping);
  serviceValueEl!.textContent = formatCurrency(service);
  totalValueEl!.textContent = formatCurrency(total);
}

function changeQuantity(id: string, delta: number): void {
  cartItems = cartItems
    .map((item) => (item.id === id ? { ...item, quantity: Math.max(0, item.quantity + delta) } : item))
    .filter((item) => item.quantity > 0);
  renderCart();
}

function removeItem(id: string): void {
  cartItems = cartItems.filter((item) => item.id !== id);
  renderCart();
}

cartItemsEl?.addEventListener('click', (event) => {
  const target = event.target as HTMLElement;
  const action = target.dataset.action;
  const id = target.dataset.id;

  if (!action || !id) {
    return;
  }

  if (action === 'increase') changeQuantity(id, 1);
  if (action === 'decrease') changeQuantity(id, -1);
  if (action === 'remove') removeItem(id);
});

emptyCartBtn?.addEventListener('click', () => {
  cartItems = [];
  renderCart();
});

restoreCartBtn?.addEventListener('click', () => {
  cartItems = [...initialItems];
  renderCart();
});

renderCart();
