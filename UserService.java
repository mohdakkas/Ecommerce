package com.ecom.Ecommerce.service;

import java.util.List;

import com.ecom.Ecommerce.model.UserDtls;

public interface UserService {

    


   


    public UserDtls saveUser(UserDtls user);

    public List<UserDtls> getUserDtls(String role );

    public List<UserDtls> getUsers(String string);

    public Boolean updateAccountStatus(Integer id, Boolean status);

    public void increaseFailedAttempt(UserDtls user);

	public void userAccountLock(UserDtls user);

    public boolean unlockAccountTimeExpired(UserDtls user);

	public void resetAttempt(int userId);
}
