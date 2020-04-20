/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.UserDB;
import database.HomeInventoryDBException;
import domain.User;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author awarsyle
 */
public class AccountService {

    public User login(String username, String password) {
        try {
            UserDB userDB = new UserDB();
            User user = userDB.getUser(username);

            if (user.getPassword().equals(password) && user.getActive()==true) {
                return user;
            }
        } catch (HomeInventoryDBException e) {

        }

        return null;
    }
public String resetPassword(String url)
    {
        String uuid = UUID.randomUUID().toString();
        String link = url + "&uuid=" + uuid;
        return link;
    }
public String welcome(String url)
    {
        String uuid = UUID.randomUUID().toString();
        String link = url + "&uuid=" + uuid;
        return link;
    }
    public boolean changePassword(String uuid, String password) {
        UserService us = new UserService();
        UserDB udb = new UserDB();
        try {
            User user = udb.getByUUID(uuid);
            if(user == null)
            {
               return false; 
            }
            else
            {   
            user.setPassword(password);
            user.setResetPasswordUUID(null);
            
            udb.update(user);
            return true;
            }
        } catch (HomeInventoryDBException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int activateAccount(User user) throws HomeInventoryDBException {
         //To change body of generated methods, choose Tools | Templates.
         UserDB udb = new UserDB();
         user.setActive(true);
         return udb.update(user);
    }
}
