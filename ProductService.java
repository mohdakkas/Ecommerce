package com.ecom.Ecommerce.service;



import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecom.Ecommerce.model.Product;


public interface ProductService {

    public Product saveProduct(Product product);
    
    public List<Product> getAllProducts();

    public Boolean deleteProduct(Integer id);

    public Product getProductById(Integer id);

    public Product updateProduct(Product product, MultipartFile image);

}
