package model;

import java.util.ArrayList;
import java.util.List;

//                                                       Alışveriş sepeti sınıfı
public class ShoppingCart {
    private final List<CartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    // Sepete ürün ekle
    public void addProduct(Product product, int quantity) {
        // Ürün zaten sepette mi kontrol et
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.addQuantity(quantity);
                return;
            }
        }
        // Yeni ürün ekle
        items.add(new CartItem(product, quantity));
    }

    // Sepetten ürün çıkar
    public boolean removeProduct(String productId) {
        return items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    // Sepeti temizle
    public void clear() {
        items.clear();
    }

    // Toplam fiyatı hesapla
    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    // Sepetteki ürünleri getir
    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    // Sepet boş mu?
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Sepetteki ürün sayısı
    public int getItemCount() {
        return items.size();
    }
}
