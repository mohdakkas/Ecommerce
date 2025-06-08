

package com.ecom.Ecommerce.service;

import java.util.List;
import com.ecom.Ecommerce.model.Category;

public interface CategoryService {

    Category saveCategory(Category category);

    Boolean existCategory(String name);

    List<Category> getAllCategories();

    public Boolean deleteCategory(int id);

    
    
    public Category getCategoryById(int id);
    
    public List<Category> getAllActiveCategory();

   
}

