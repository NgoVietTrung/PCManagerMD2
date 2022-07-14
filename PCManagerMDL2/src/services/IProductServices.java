package services;

import model.Product;

import java.util.List;

public interface IProductServices {
    List<Product> getProduct();
    void add(Product newProduct);
    Product getbyId(long id);
    boolean existbyId(long id);
    void removebyId(long id);
}
