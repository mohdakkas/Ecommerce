// package com.ecom.Ecommerce.service.impl;

// import java.util.List;
// import java.util.Locale.Category;
// import org.springframework.beans.factory.annotation.Autowired;

// import com.ecom.Ecommerce.repository.CategoryRepository;
// import com.ecom.Ecommerce.service.CategoryService;

// public class CategoryServiceImpl  implements CategoryService{

//     @Autowired

//     private CategoryRepository CategoryRepository;

//     @Override
//     public Category saveCategory(Category category) {
        
//         throw new UnsupportedOperationException("Unimplemented method 'saveCategory'");
//     }

//     @Override
//     public List<Category> getAllCategory() {
       
//         throw new UnsupportedOperationException("Unimplemented method 'getAllCategory'");
//     }

//     @Override
//     public Boolean  existCategory(String name) {
        
//         throw new UnsupportedOperationException("Unimplemented method 'exitscategory'");
//     }

//     @Override
//     public Category saveCategory(com.ecom.Ecommerce.model.Category category) {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'saveCategory'");
//     }
    
// }




package com.ecom.Ecommerce.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ecom.Ecommerce.model.Category;
import com.ecom.Ecommerce.repository.CategoryRepository;
import com.ecom.Ecommerce.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Boolean existCategory(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    

   
    @Override
    public Boolean deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElse(null);

		if (!ObjectUtils.isEmpty(category)) {
			categoryRepository.delete(category);
			return true;
		}
		return false;
    }

   

    @Override
    public Category getCategoryById(int id) {
		Category category = categoryRepository.findById(id).orElse(null);
		return category;
	}

    @Override
    public List<Category> getAllActiveCategory() {
    
        categoryRepository.findByIsActiveTrue();

     return null;   

   
}










}


