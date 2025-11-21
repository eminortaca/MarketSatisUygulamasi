package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.CartItem;
import model.Product;
import model.ShoppingCart;
import service.MarketService;

/**
 * Market Satış Uygulaması Ana GUI Sınıfı
 */
public class MarketGUI extends JFrame {
    private final MarketService marketService;
    private final ShoppingCart cart;
    
    // Ana bileşenler
    private JTable productTable;
    private JTable cartTable;
    private DefaultTableModel productTableModel;
    private DefaultTableModel cartTableModel;
    private JLabel totalLabel;
    private JTextField searchField;
    private JComboBox<String> categoryCombo;

    public MarketGUI() {
        marketService = new MarketService();
        cart = new ShoppingCart();
        
        initializeUI();
        loadProducts();
        updateCategories();
        setVisible(true);
    }

    private void initializeUI() {
        setTitle("Market Satış Uygulaması");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Ana layout
        setLayout(new BorderLayout(10, 10));
        
        // Üst panel - Başlık
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Orta panel - Ana içerik
        add(createMainPanel(), BorderLayout.CENTER);
        
        // Alt panel - Sepet ve toplam
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("🛒 MARKET SATIŞ SİSTEMİ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Sol panel - Ürünler
        mainPanel.add(createProductPanel());
        
        // Sağ panel - Ürün yönetimi
        mainPanel.add(createManagementPanel());
        
        return mainPanel;
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Ürünler",
            0, 0, new Font("Arial", Font.BOLD, 14)
        ));
        
        // Arama ve filtre paneli
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        JButton searchButton = new JButton("🔍 Ara");
        searchButton.addActionListener(e -> searchProducts());
        
        categoryCombo = new JComboBox<>();
        categoryCombo.addItem("Tüm Kategoriler");
        categoryCombo.addActionListener(e -> filterByCategory());
        
        searchPanel.add(new JLabel("Ara:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(new JLabel("Kategori:"));
        searchPanel.add(categoryCombo);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Ürün tablosu
        String[] columnNames = {"ID", "Ürün Adı", "Fiyat (TL)", "Stok", "Kategori"};
        productTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(productTableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setRowHeight(25);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(productTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Sepete ekle butonu
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addToCartButton = new JButton("➕ Sepete Ekle");
        addToCartButton.setFont(new Font("Arial", Font.BOLD, 14));
        addToCartButton.setBackground(new Color(46, 204, 113));
        addToCartButton.setForeground(Color.BLACK);
        addToCartButton.setFocusPainted(false);
        addToCartButton.addActionListener(e -> addToCart());
        buttonPanel.add(addToCartButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Ürün Yönetimi",
            0, 0, new Font("Arial", Font.BOLD, 14)
        ));
        
        // Form paneli
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField categoryField = new JTextField();
        
        formPanel.add(new JLabel("Ürün Adı:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Fiyat (TL):"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Stok:"));
        formPanel.add(stockField);
        formPanel.add(new JLabel("Kategori:"));
        formPanel.add(categoryField);
        
        panel.add(formPanel, BorderLayout.NORTH);
        
        // Buton paneli
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton addButton = new JButton("➕ Yeni Ürün Ekle");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int stock = Integer.parseInt(stockField.getText().trim());
                String category = categoryField.getText().trim();
                
                if (name.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                marketService.createProduct(name, price, stock, category);
                loadProducts();
                updateCategories();
                
                // Alanları temizle
                nameField.setText("");
                priceField.setText("");
                stockField.setText("");
                categoryField.setText("");
                
                JOptionPane.showMessageDialog(this, "Ürün başarıyla eklendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli sayılar girin!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton updateButton = new JButton("✏️ Seçili Ürünü Güncelle");
        updateButton.setBackground(new Color(241, 196, 15));
        updateButton.setForeground(Color.BLACK);
        updateButton.setFont(new Font("Arial", Font.BOLD, 12));
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen güncellenecek ürünü seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                String id = (String) productTableModel.getValueAt(selectedRow, 0);
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int stock = Integer.parseInt(stockField.getText().trim());
                String category = categoryField.getText().trim();
                
                if (name.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                marketService.updateProduct(id, name, price, stock, category);
                loadProducts();
                updateCategories();
                
                JOptionPane.showMessageDialog(this, "Ürün başarıyla güncellendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli sayılar girin!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton deleteButton = new JButton("🗑️ Seçili Ürünü Sil");
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek ürünü seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, "Bu ürünü silmek istediğinizden emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String id = (String) productTableModel.getValueAt(selectedRow, 0);
                marketService.removeProduct(id);
                loadProducts();
                updateCategories();
                
                JOptionPane.showMessageDialog(this, "Ürün başarıyla silindi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        // Seçili ürünü form alanlarına doldur
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    nameField.setText((String) productTableModel.getValueAt(selectedRow, 1));
                    priceField.setText(productTableModel.getValueAt(selectedRow, 2).toString());
                    stockField.setText(productTableModel.getValueAt(selectedRow, 3).toString());
                    categoryField.setText((String) productTableModel.getValueAt(selectedRow, 4));
                }
            }
        });
        
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Sepet tablosu
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Sepet",
            0, 0, new Font("Arial", Font.BOLD, 14)
        ));
        
        String[] cartColumns = {"Ürün", "Adet", "Birim Fiyat", "Toplam"};
        cartTableModel = new DefaultTableModel(cartColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(cartTableModel);
        cartTable.setRowHeight(25);
        cartTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane cartScroll = new JScrollPane(cartTable);
        cartScroll.setPreferredSize(new Dimension(0, 150));
        cartPanel.add(cartScroll, BorderLayout.CENTER);
        
        // Sepet butonları
        JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton removeFromCartButton = new JButton("🗑️ Sepetten Çıkar");
        removeFromCartButton.addActionListener(e -> removeFromCart());
        JButton clearCartButton = new JButton("🧹 Sepeti Temizle");
        clearCartButton.addActionListener(e -> clearCart());
        
        cartButtonPanel.add(removeFromCartButton);
        cartButtonPanel.add(clearCartButton);
        cartPanel.add(cartButtonPanel, BorderLayout.SOUTH);
        
        panel.add(cartPanel, BorderLayout.CENTER);
        
        // Toplam ve satış paneli
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        totalLabel = new JLabel("TOPLAM: 0.00 TL");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalLabel.setForeground(new Color(52, 152, 219));
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JButton completeButton = new JButton("💳 Satışı Tamamla");
        completeButton.setFont(new Font("Arial", Font.BOLD, 16));
        completeButton.setBackground(new Color(46, 204, 113));
        completeButton.setForeground(Color.BLACK);
        completeButton.setFocusPainted(false);
        completeButton.setPreferredSize(new Dimension(200, 50));
        completeButton.addActionListener(e -> completeSale());
        
        totalPanel.add(totalLabel, BorderLayout.CENTER);
        totalPanel.add(completeButton, BorderLayout.EAST);
        
        panel.add(totalPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private void loadProducts() {
        productTableModel.setRowCount(0);
        for (Product product : marketService.getAllProducts()) {
            productTableModel.addRow(new Object[]{
                product.getId(),
                product.getName(),
                String.format("%.2f", product.getPrice()),
                product.getStock(),
                product.getCategory()
            });
        }
    }

    private void updateCategories() {
        String selected = (String) categoryCombo.getSelectedItem();
        categoryCombo.removeAllItems();
        categoryCombo.addItem("Tüm Kategoriler");
        for (String category : marketService.getAllCategories()) {
            categoryCombo.addItem(category);
        }
        if (selected != null) {
            categoryCombo.setSelectedItem(selected);
        }
    }

    private void searchProducts() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadProducts();
            return;
        }
        
        productTableModel.setRowCount(0);
        for (Product product : marketService.searchProducts(searchTerm)) {
            productTableModel.addRow(new Object[]{
                product.getId(),
                product.getName(),
                String.format("%.2f", product.getPrice()),
                product.getStock(),
                product.getCategory()
            });
        }
    }

    private void filterByCategory() {
        String category = (String) categoryCombo.getSelectedItem();
        if (category == null || category.equals("Tüm Kategoriler")) {
            loadProducts();
            return;
        }
        
        productTableModel.setRowCount(0);
        for (Product product : marketService.getProductsByCategory(category)) {
            productTableModel.addRow(new Object[]{
                product.getId(),
                product.getName(),
                String.format("%.2f", product.getPrice()),
                product.getStock(),
                product.getCategory()
            });
        }
    }

    private void addToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen sepete eklenecek ürünü seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String productId = (String) productTableModel.getValueAt(selectedRow, 0);
        Product product = marketService.findProductById(productId).orElse(null);
        
        if (product == null) {
            JOptionPane.showMessageDialog(this, "Ürün bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (product.getStock() <= 0) {
            JOptionPane.showMessageDialog(this, "Bu ürün stokta yok!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String quantityStr = JOptionPane.showInputDialog(this, "Kaç adet eklemek istiyorsunuz?", "1");
        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            return;
        }
        
        try {
            int quantity = Integer.parseInt(quantityStr.trim());
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Geçerli bir adet girin!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (quantity > product.getStock()) {
                JOptionPane.showMessageDialog(this, "Stokta yeterli ürün yok! Mevcut stok: " + product.getStock(), "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            cart.addProduct(product, quantity);
            updateCartTable();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçerli bir sayı girin!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeFromCart() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen sepetten çıkarılacak ürünü seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        CartItem item = cart.getItems().get(selectedRow);
        cart.removeProduct(item.getProduct().getId());
        updateCartTable();
    }

    private void clearCart() {
        if (cart.isEmpty()) {
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Sepeti temizlemek istediğinizden emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            cart.clear();
            updateCartTable();
        }
    }

    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        for (CartItem item : cart.getItems()) {
            cartTableModel.addRow(new Object[]{
                item.getProduct().getName(),
                item.getQuantity(),
                String.format("%.2f TL", item.getProduct().getPrice()),
                String.format("%.2f TL", item.getTotalPrice())
            });
        }
        totalLabel.setText(String.format("TOPLAM: %.2f TL", cart.getTotalPrice()));
    }

    private void completeSale() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sepet boş!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Stokları güncelle
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            product.reduceStock(item.getQuantity());
        }
        
        double total = cart.getTotalPrice();
        
        // Satış tamamlandı mesajı
        JOptionPane.showMessageDialog(this,
            String.format("Satış başarıyla tamamlandı!\n\nToplam Tutar: %.2f TL\n\nTeşekkür ederiz!", total),
            "Satış Tamamlandı",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Sepeti temizle ve ürün listesini güncelle
        cart.clear();
        updateCartTable();
        loadProducts();
    }

    public static void main(String[] args) {
        // Look and Feel ayarla
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Look and Feel ayarlanamadı: " + e.getMessage());
        }
        
        // GUI'yi başlat
        SwingUtilities.invokeLater(() -> new MarketGUI());
    }
}
