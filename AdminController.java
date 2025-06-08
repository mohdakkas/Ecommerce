package com.ecom.Ecommerce.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.ecom.Ecommerce.model.Category;
import com.ecom.Ecommerce.model.Product;
import com.ecom.Ecommerce.model.UserDtls;
import com.ecom.Ecommerce.service.CategoryService;
import com.ecom.Ecommerce.service.ProductService;
import com.ecom.Ecommerce.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/Admin")
public class AdminController {


    @Autowired
  private UserService userService;



    private CategoryService categoryService;

 
    @Autowired
    private ProductService productService;


    

    // Constructor Injection
    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
        @GetMapping("/")
        public String index(){
            return"admin/index";
    
        }

        
       @GetMapping("/loadAddProduct")
       public String loadAddProduct() {
		
		return "admin/add_product";
        }
      
        
    
        @GetMapping("/category")
        public String category( Model m){
            m.addAttribute("categorys", categoryService.getAllCategories());
            return"admin/category";
    
        }
    
   


  
    



        @PostMapping("/saveCategory")
        public String saveCategory(@ModelAttribute Category category, 
                                   @RequestParam("file") MultipartFile file, 
                                   HttpSession session) {
            try {
                // Validate file
                if (file == null || file.isEmpty()) {
                    session.setAttribute("errorMsg", "File is empty");
                    return "redirect:/Admin/category";
                }
        
                // Set the image name
                String imageName = file.getOriginalFilename();
                category.setImageName(imageName);
        
                // Check if category already exists
                boolean existCategory = categoryService.existCategory(category.getName());
        
                if (existCategory) {
                    session.setAttribute("errorMsg", "Category Name already exists");
                } else {
                    Category savedCategory = categoryService.saveCategory(category);
        
                    if (ObjectUtils.isEmpty(savedCategory)) {
                        session.setAttribute("errorMsg", "Not saved! Internal server error");
                    } else {
                        // Define external upload directory
                        java.io.File saveFile = new java.io.File("uploads/category_img");
                        if (!saveFile.exists()) {
                            saveFile.mkdirs(); // Create directory if not exists
                        }
        
                        // Save the file
                        java.nio.file.Path path = Paths.get(saveFile.getAbsolutePath(), file.getOriginalFilename());
                        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        
                        session.setAttribute("succMsg", "Saved successfully");
                    }
                }
            } catch (Exception e) {
                session.setAttribute("errorMsg", "Error saving file: " + e.getMessage());
                e.printStackTrace();
            }
        
            return "redirect:/Admin/category";
        }
        

@GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable int id, HttpSession session) {
        Boolean deleteCategory = categoryService.deleteCategory(id);

        if (deleteCategory) {
            session.setAttribute("succMsg", "Category deleted successfully");
        } else {
            session.setAttribute("errorMsg", "Something went wrong");
        }

        return "redirect:/Admin/category";
    }


    @GetMapping("/loadEditCategory/{id}")
	public String loadEditCategory(@PathVariable int id, Model m) {
		m.addAttribute("category", categoryService.getCategoryById(id));
		return "Admin/edit_category";
	}

    @PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		Category oldCategory = categoryService.getCategoryById(category.getId());
		String imageName = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();

		if (!ObjectUtils.isEmpty(category)) {

			oldCategory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());
			oldCategory.setImageName(imageName);
		}

		Category updateCategory = categoryService.saveCategory(oldCategory);

		if (!ObjectUtils.isEmpty(updateCategory)) {

			if (!file.isEmpty()) {
				java.io.File saveFile = new ClassPathResource("static/img").getFile();

                java.nio.file.Path path = Paths.get(saveFile.getAbsolutePath(), "category_img", file.getOriginalFilename());

                // Save file
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			session.setAttribute("succMsg", "Category update success");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/Admin/loadEditCategory/" + category.getId();
	}

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product, 
                              @RequestParam("file") MultipartFile image,
                              HttpSession session) throws IOException {
    
        // Define the folder where images will be saved
        String uploadDir = "src/main/resources/static/img/product_img";
    
        // Ensure the directory exists
        java.io.File directory = new java.io.File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    
        // Get the filename
        String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
        product.setImage(imageName);
    
        // Save product to the database
        Product savedProduct = productService.saveProduct(product);
    
        if (!ObjectUtils.isEmpty(savedProduct)) {
            // Save image file
            java.nio.file.Path path = Paths.get(uploadDir, imageName);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    
            session.setAttribute("succMsg", "Product Saved Successfully");
        } else {
            session.setAttribute("errorMsg", "Something went wrong on the server");
        }
    
        return "redirect:/Admin/loadAddProduct";
    }
   

    @GetMapping("/products")
	public String loadViewProduct(Model m) {
		m.addAttribute("products", productService.getAllProducts());
		return "admin/products";
	}


    @GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id, HttpSession session) {
		Boolean deleteProduct = productService.deleteProduct(id);
		if (deleteProduct) {
			session.setAttribute("succMsg", "Product delete success");
		} else {
			session.setAttribute("errorMsg", "Something wrong on server");
		}
		return "redirect:/Admin/products";
	}
   


    @GetMapping("/editProduct/{id}")
	public String editProduct(@PathVariable("id") int id, Model m) {
		m.addAttribute("product", productService.getProductById(id));
		m.addAttribute("categories", categoryService.getAllCategories());
		return "Admin/edit_product";
	}

    @PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
			HttpSession session, Model m) {

		Product updateProduct = productService.updateProduct(product, image);
		if (!ObjectUtils.isEmpty(updateProduct)) {
			session.setAttribute("succMsg", "Product update success");
		} else {
			session.setAttribute("errorMsg", "Something wrong on server");
		}

		return "redirect:/Admin/editProduct/" + product.getId();
	}


    @GetMapping("/Users")
	public String getAllUsers(Model m) {
		
        List<UserDtls> users = userService.getUsers("ROLE_USER");
		m.addAttribute("users", users);
		return "/Admin/Users";
	}



    @GetMapping("/updateSts")
	public String updateUserAccountStatus(@RequestParam Boolean status, @RequestParam Integer id, HttpSession session) {
		Boolean f = userService.updateAccountStatus(id, status);
		if (f) {
			session.setAttribute("succMsg", "Account Status Updated");
		} else {
			session.setAttribute("errorMsg", "Something wrong on server");
		}
		return "redirect:/Admin/users";
	}









}
    
    
    










