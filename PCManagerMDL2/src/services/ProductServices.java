package services;

import model.Product;
import utils.CSVUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductServices implements IProductServices{
    public static ArrayList<Product> products = new ArrayList<>();
    private final String path = "data\\product.csv";

    public ArrayList<Product> getProduct() {
        List<String> records = CSVUtils.read(path);
        for (String record : records) {
            products.add(new Product(record));
        }
        return products;

    }

    @Override
    public void add(Product newProduct) {
        products.add(newProduct);
        update();
    }

    @Override
    public Product getbyId(long id) {
        for (Product product : products) {
            if (id == product.getId()) {
                return product;
            }
        }
        return null;
    }

    public void update() {
        CSVUtils.write(path, products);
    }

    @Override
    public boolean existbyId(long id) {
        for (Product product : products) {
            if (id == product.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removebyId(long id) {
        int index = findIndexById(id);
        products.remove(index);
        update();
    }

    public int findIndexById(long id) {
        for (int i = 0; i < products.size(); i++) {
            if (id == products.get(i).getId()) {
                return i;
            }
        }
        return -1;
    }

    public boolean existByName(String name) {
        for (Product product : products) {
            if (name.equals(product.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static String addCharacters(long price) {
        String patternTienTe = ",###₫";
        DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
        return formatTienTe.format(price);
    }

    public void productsList() {
        System.out.println("----------------------------- Product List ------------------------------");
        System.out.printf("%-69s\n", "");
        System.out.printf("        %-16s %-15s %-15s %-12s\n", "ID", "Product name", "Quantity", "Price");
        System.out.printf("%-69s\n", "");

        for (Product product : products) {
            System.out.printf("        %-16s %-15s %-17s %-10s\n", product.getId(), product.getName(), product.getQuantity(), addCharacters(product.getPrice()));
        }
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------------------");
    }
}
