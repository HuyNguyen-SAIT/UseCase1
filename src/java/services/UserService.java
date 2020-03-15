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

    public int delete(User user) throws Exception {
        
        // do not allow the admin to be deleted
        int delete;
        if (user.getUsername().substring(0,5).equalsIgnoreCase("admin")) {
            return 0;
        }
        delete= userDB.delete(user);
         return delete;
    }

    public int insert(String username, String password, String firstname, String lastname, String email) throws Exception {
        User user = new User(username, password);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        return userDB.insert(user);
    }
}
