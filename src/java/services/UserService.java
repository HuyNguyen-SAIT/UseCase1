package services;

import dataaccess.UserDB;
import domain.User;
import java.util.List;

public class UserService {

    private UserDB userDB;

    public UserService() {
        userDB = new UserDB();
    }

    public User get(String username) throws Exception {
        return userDB.getUser(username);
    }

    public List<User> getAll() throws Exception {
        return userDB.getAll();
    }

    public int update(String username, String password, String firstname, String lastname, String email) throws Exception {
        User user = get(username);
        user.setPassword(password);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        return userDB.update(user);
    }
    public int update(String username, String password, String firstname, String lastname, String email, boolean isActive) throws Exception {
        User user = get(username);
        //user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setActive(isActive);
        return userDB.update(user);
    }
    
    public int update(String username, String password, String firstname, String lastname, String email, boolean isActive, boolean isAdmin) throws Exception {
        User user = get(username);
        //user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setActive(isActive);
        user.setIsAdmin(isAdmin);
        return userDB.update(user);
    }

    public int delete(User user) throws Exception {
        
        // do not allow the admin to be deleted
        int delete;
        delete= userDB.delete(user);
        return delete;
    }

    public int insert(String username, String password, String firstname, String lastname, String email) throws Exception {
        List<User>userList = userDB.getAll();
        for(User user: userList)
        {
            if(user.getUsername().equals(username))
            {
                return 0;
            }
        }
        User user = new User(username, password);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setActive(true);
        user.setIsAdmin(false);
        return userDB.insert(user);
    }
    public int insert(User insertUser) throws Exception {
        List<User>userList = userDB.getAll();
        for(User user: userList)
        {
            if(user.getUsername().equals(insertUser.getUsername()))
            {
                return 0;
            }
        }
        
        return userDB.insert(insertUser);
    }
}
