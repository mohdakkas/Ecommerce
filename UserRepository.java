package com.ecom.Ecommerce.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ecom.Ecommerce.model.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {

    public UserDtls findByEmail(String email);

    public UserDtls  findByUsername(String username);

    public List<UserDtls> findByRole(String role);


 

}
