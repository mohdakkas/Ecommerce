package com.ecom.Ecommerce.controller;



// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;

// import java.nio.file.Path;
// import java.nio.file.StandardCopyOption;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.util.ObjectUtils;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;

// import com.ecom.Ecommerce.model.Product;
// import com.ecom.Ecommerce.model.UserDtls;
// import com.ecom.Ecommerce.service.UserService;

// import jakarta.servlet.http.HttpSession;


// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path; 
// import java.nio.file.Paths;
// import java.nio.file.StandardCopyOption;

// import org.springframework.core.io.ClassPathResource;
// import org.springframework.web.multipart.MultipartFile;




import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.Ecommerce.model.Product;
import com.ecom.Ecommerce.model.UserDtls;
import com.ecom.Ecommerce.service.UserService;

import jakarta.servlet.http.HttpSession;




@Controller
public class HomeController {

    @Autowired
	private UserService userService;

    @GetMapping("/")
    public String Index(){
        return "index";

    }   


    @GetMapping("/login")
    public String login(){
        return "login";

    }


    @GetMapping("/register")
    public String register(){
        return "register";

    }

    @GetMapping("/base")
    public String base(){
        return "base";

    }

    @GetMapping("/product")
    public String product(){
        return "product";

    }

    

    @GetMapping("/view_products")
	public String view_products() {
		return "view_products";
	}

@GetMapping("/product_details")
public String productDetails(@RequestParam Integer id, Model model) {
    // Get product data (replace with your actual logic)
    Product product = new Product();
    product.setId(id);
    product.setTitle("Sample Product");
    // Set other properties...
    
    model.addAttribute("product", product);
    return "product_details";
}

@PostMapping("/saveUser")
public String saveUser(@ModelAttribute UserDtls user, 
                       @RequestParam("img") MultipartFile file, 
                       HttpSession session) throws IOException {

    // Set default profile image if no file is uploaded
    String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
    user.setProfileImage(imageName);

    // Save user to database
    UserDtls savedUser = userService.saveUser(user);

    if (!ObjectUtils.isEmpty(savedUser)) {
        if (!file.isEmpty()) {
            // Define the path where images should be stored
            String uploadDir = "src/main/resources/static/img/profile_img/";

            
            Files.createDirectories(Paths.get(uploadDir));

            // Save the file
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File saved at: " + path);
        }

        session.setAttribute("succMsg", "Register successfully");
    } else {
        session.setAttribute("errorMsg", "Something went wrong on the server"); 
    }

    return "redirect:/register";
}







}
