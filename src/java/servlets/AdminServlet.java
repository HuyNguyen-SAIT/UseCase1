/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dataaccess.ItemDB;
import dataaccess.UserDB;
import database.HomeInventoryDBException;
import domain.User;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.UserService;

/**
 *
 * @author 794458
 */
public class AdminServlet extends HttpServlet {

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
//            out.println("<title>Servlet AdminServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet AdminServlet at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        UserService uc = new UserService();
        //UserDB udb = new UserDB();
        List<User> userList = null;
        try {
            userList = uc.getAll();
        
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("userList", userList);
        request.setAttribute("addorsave", "Add");
        
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
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
        UserService uc = new UserService();
        String action = request.getParameter("action");
        UserDB udb = new UserDB();
        ItemDB idb = new ItemDB();
        User selectedUser = null ;
        if(action.equals("view"))
        {
            
            String selectUser = request.getParameter("selectedUser");
            try {
                selectedUser = udb.getUser(selectUser);
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("selectedUser", selectedUser);
            
            
        //UserDB udb = new UserDB();
        List<User> userList = null;
        try {
            userList = uc.getAll();
            
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("userList", userList);
        request.setAttribute("addorsave", "Save");
        request.setAttribute("readonly", "readonly");
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        }
        else
            if(action.equals("delete"))
            {
            String selectUser = request.getParameter("selectedUser");
            int deleted;
            try {
                selectedUser = udb.getUser(selectUser);
                selectedUser.setItemList(idb.getAll(selectedUser));
                deleted = udb.delete(selectedUser);
                if(deleted == 0)
                {
                    request.setAttribute("message", "Cannot delete admins!");
                }
                else
                {
                    request.setAttribute("message", "Delete successfully!");
                }
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Failed to delete!");
            }
                
                request.setAttribute("selectedUser", null);
                //request.setAttribute("addorsave", "Add");
                doGet(request,response);
            
            }
        else
                if(action.equals("Add"))
            {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String fname = request.getParameter("firstname");
                String lname = request.getParameter("lastname");
                String email = request.getParameter("email");
            try {
                uc.insert(username, password, fname, lname,email);
                request.setAttribute("message", "Added successfully!");
            
                
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Failed to add, username already exists!");
            }
            doGet(request,response);
            }
        else
                {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String fname = request.getParameter("firstname");
                String lname = request.getParameter("lastname");
                String email = request.getParameter("email");
            try {
                uc.update(username, password, fname, lname, email);
                request.setAttribute("message", "Updated successfully!");
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Failed to update!");
            }
                    doGet(request,response);
                }
        
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
