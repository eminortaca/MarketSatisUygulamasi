package model;
//                         Ürün bilgilerini tutan sınıf
public class Product {
    private final String id;
    private String name;
    private double price;
    private int stock;
    private String category;

    public Product(String id, String name, double price, int stock, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Stok azaltma
    public boolean reduceStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
            return true;
        }
        return false;
    }

    // Stok artırma
    public void addStock(int quantity) {
        stock += quantity;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%.2f TL) - Stok: %d", id, name, price, stock);
    }
}
