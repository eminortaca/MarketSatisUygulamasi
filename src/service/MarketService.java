package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Product;

//                                             Market işlemlerini yöneten servis sınıfı
public class MarketService {
    private final List<Product> products;
    private int nextId;

    public MarketService() {
        this.products = new ArrayList<>();
        this.nextId = 1;
        loadSampleProducts();
    }

    // Örnek ürünleri yükle
    private void loadSampleProducts() {
        addProduct(new Product("P001", "Ekmek", 5.0, 50, "Temel Gıda"));
        addProduct(new Product("P002", "Süt", 25.0, 30, "Süt Ürünleri"));
        addProduct(new Product("P003", "Yumurta", 45.0, 40, "Temel Gıda"));
        addProduct(new Product("P004", "Peynir", 150.0, 20, "Süt Ürünleri"));
        addProduct(new Product("P005", "Domates", 35.0, 100, "Sebze"));
        addProduct(new Product("P006", "Salatalık", 20.0, 80, "Sebze"));
        addProduct(new Product("P007", "Elma", 40.0, 60, "Meyve"));
        addProduct(new Product("P008", "Muz", 30.0, 70, "Meyve"));
        addProduct(new Product("P009", "Patlıcan", 60.0, 60, "Sebze"));
    }

    // Ürün ekle
    public void addProduct(Product product) {
        products.add(product);
    }

    // Yeni ürün oluştur
    public Product createProduct(String name, double price, int stock, String category) {
        String id = "P" + String.format("%03d", nextId++);
        Product product = new Product(id, name, price, stock, category);
        products.add(product);
        return product;
    }

    // Ürün sil
    public boolean removeProduct(String productId) {
        return products.removeIf(p -> p.getId().equals(productId));
    }

    // Ürün güncelle
    public boolean updateProduct(String productId, String name, double price, int stock, String category) {
        Optional<Product> productOpt = findProductById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setCategory(category);
            return true;
        }
        return false;
    }

    // Tüm ürünleri getir
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    // ID'ye göre ürün bul
    public Optional<Product> findProductById(String productId) {
        return products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();
    }

    // İsme göre ürün ara
    public List<Product> searchProducts(String searchTerm) {
        String lowerSearch = searchTerm.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerSearch) ||
                           p.getCategory().toLowerCase().contains(lowerSearch))
                .toList();
    }

    // Kategoriye göre ürünleri getir
    public List<Product> getProductsByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equals(category))
                .toList();
    }

    // Tüm kategorileri getir
    public List<String> getAllCategories() {
        return products.stream()
                .map(Product::getCategory)
                .distinct()
                .sorted()
                .toList();
    }
}
