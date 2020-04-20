/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.HomeInventoryDBException;
import dataaccess.UserDB;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domain.User;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import services.AccountService;
import services.GmailService;
import services.UserService;

/**
 *
 * @author awarsyle
 */
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action != null && action.equals("checkUsername")) {
            String username = request.getParameter("username");

            UserService us = new UserService();
            User user;
            try {
                user = us.get(username);

                // handle the reseponse by hardcoding values
                // the response is not a whole page, just some text
                if(username.equals(""))
                {
                    
                }
                else
                    if (user == null) {
                    response.getWriter().write("OK!");
                } else {
                    response.getWriter().write("Username already existed");
                }
            } catch (Exception ex) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            return; // stop execution of servlet
        }

        getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService ac = new AccountService();
        UserDB db = new UserDB();
        UserService us = new UserService();
        User user = null;
        String username = request.getParameter("username");
        try {
            user = us.get(username);
        } catch (Exception ex) {
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String password = request.getParameter("password");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String passwordcheck = request.getParameter("passwordcheck");
        if(!password.equalsIgnoreCase(passwordcheck))
        {
            request.setAttribute("error","Passwords don't match. Please try again");
            //request.setAttribute("usernameReg", username);
            
            //getServletContext().getRequestDispatcher("/WEB-INF/registrations.jsp").forward(request, response);
        }
        else
            if(user!=null)
            {
             request.setAttribute("error","Fail, username already existed!");
                
            }
        else
        {
            try {
                HttpSession session = request.getSession();
                us.insert(username, password, fname, lname,email);
                //response.getWriter().write("Successful!");
                String subject = "Activate account";
                String template = getServletContext().getRealPath("/WEB-INF") + "/emailtemplates/welcome1.html";
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", fname);
                tags.put("lastname", lname);
                tags.put("username", username);
                tags.put("link", ac.welcome(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/reset?action=newuser"));
                GmailService.sendMail(email, subject, template, tags);
                session.setAttribute("unactivatedUser",username);
                request.setAttribute("errorMessage", "Done! Check your mailbox for account activation");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        //request.setAttribute("errorMessage", "Username already existed!");
        request.setAttribute("usernameReg", username);
        request.setAttribute("fnameReg", fname);
        request.setAttribute("lnameReg", lname);
        request.setAttribute("emailReg", email);
                    
        getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
    }

}

