/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.HomeInventoryDBException;
import dataaccess.UserDB;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import domain.User;
import services.AccountService;
import services.GmailService;

/**
 *
 * @author 794458
 */
public class ResetPasswordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ResetPasswordServet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ResetPasswordServet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //sprocessRequest(request, response);
        String action = request.getParameter("action");
        if(action.equals("forget"))
        {
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        }
        else
        {
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //processRequest(request, response);
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        UserDB udb = new UserDB();
        AccountService ac = new AccountService();
        User user = null;
        if(action.equals("recoveryemail"))
        {
                String toEmail = request.getParameter("resetEmail");
                
        try {
            List<User> allUsers = udb.getAll();
            for(User a: allUsers)
            {
                if(a.getEmail().equals(toEmail))
                {
                    if(a.getResetPasswordUUID()!= null)
                    {
                    user = a;
                    session.setAttribute("username", user.getUsername());
                    break;
                    }
                    else
                    {
                        request.setAttribute("errorMessage", "Can only change password once!");
                        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                    }
                }
            }
            if(user != null)
            {
                
                String subject = "Reset password";
                String template = getServletContext().getRealPath("/WEB-INF") + "/emailtemplates/login.html";
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("username", user.getUsername());
                tags.put("link", ac.resetPassword(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/reset?action=new"));
                GmailService.sendMail(toEmail, subject, template, tags);
                request.setAttribute("errorMessage", "Password recovery email sent successfully!");
            }
            else
            {
                request.setAttribute("error", "Wrong email!");
                getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            }
        }catch (HomeInventoryDBException ex) {
            Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }      
        //Mail mail = new Mail();
        //Session session = new Session();
        //mail.sendHTMLEmail("abc@gmail.com", "123password", toEmail, "HEllO WORLD", "<h1>Hello world</h1>");
        //Message message = new MimeMessage(session);      
        //Mail mail = new Mail();
        //Session session = new Session();
        //mail.sendHTMLEmail("abc@gmail.com", "123password", toEmail, "HEllO WORLD", "<h1>Hello world</h1>");
        //Message message = new MimeMessage(session);
        
        }
        else
        {
            String newPassword = request.getParameter("newPassword");
            
            try {
                user = udb.getUser((String)session.getAttribute("username"));
                ac.changePassword(user.getResetPasswordUUID(), newPassword);
                
                request.setAttribute("errorMessage", "Password updated successfully!");
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
