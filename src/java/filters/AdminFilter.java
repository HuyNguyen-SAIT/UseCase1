/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



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
        try
        {
            username = username.substring(0,5);
        }
        catch(Exception a)
        {
            a.printStackTrace();
        }
        if(username.equals("admin"))
        {
            chain.doFilter(request, response);
        }
        else
        {
            
            
               HttpServletResponse resp = (HttpServletResponse)response; 
               resp.sendRedirect("inventory");
           
        
        }
    }

    @Override
    public void destroy() {
         //To change body of generated methods, choose Tools | Templates.
    }
    
}
