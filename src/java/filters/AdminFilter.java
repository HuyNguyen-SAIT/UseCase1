/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import domain.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.UserService;



/**
 *
 * @author 794458
 */
public class AdminFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of generated methods, choose Tools | Templates.
     
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //To change body of generated methods, choose Tools | Templates.
         
        HttpServletRequest r = (HttpServletRequest)request;
        HttpSession session = r.getSession();
        String username =(String) session.getAttribute("username");
        UserService us = new UserService();
        try {
            User user = us.get(username);
            if(user.getIsAdmin()==true)
        {
            chain.doFilter(request, response);
        }
        else
        {
               HttpServletResponse resp = (HttpServletResponse)response; 
               resp.sendRedirect("inventory");
        }
        } catch (Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public void destroy() {
         //To change body of generated methods, choose Tools | Templates.
    }
    
}
