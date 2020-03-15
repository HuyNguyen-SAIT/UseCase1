/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dataaccess.CategoryDB;
import dataaccess.ItemDB;
import dataaccess.UserDB;
import database.HomeInventoryDBException;
import domain.Category;
import domain.Item;
import domain.User;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.UserService;

/**
 *
 * @author 794458
 */
public class InventoryServlet extends HttpServlet {

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
//            out.println("<title>Servlet InventoryServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet InventoryServlet at " + request.getContextPath() + "</h1>");
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
        UserDB udb = new UserDB();
        //ItemDB idb = new ItemDB();
        UserService uc = new UserService();
        User loggedIn = null;
        HttpSession session = request.getSession();
        try {
            loggedIn = uc.get((String)session.getAttribute("username"));
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Item> itemList = loggedIn.getItemList();
        request.setAttribute("itemList", itemList);
        
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
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
        
        ItemDB idb = new ItemDB();
        CategoryDB cdb = new CategoryDB();
        UserDB udb = new UserDB();
        User loggedIn = null;
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
        String action = request.getParameter("action");
        try {
            loggedIn = udb.getUser(username);
        } catch (HomeInventoryDBException ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        if(action.equals("delete"))
        {
            int selectedID = Integer.parseInt(request.getParameter("selectedItem"));
            try {
                Item deletedItem = idb.getItem(selectedID);
                //deletedItem.setOwner(loggedIn);
                idb.delete(deletedItem);
                request.setAttribute("invalidItem", "Deleted successfully!");
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("invalidItem", "Failed to delete!");
            }
        }
        else
        {
       
        double itemPrice =0;
            String categoryName = request.getParameter("type");
            int categoryID = Integer.parseInt(categoryName);
            String itemName = request.getParameter("itemAddName");
            String itemPriceString = request.getParameter("itemAddPrice");
            try
            {
            itemPrice = Double.parseDouble(itemPriceString);
            }
            catch(NumberFormatException a)
            {
                return;
            }
            Category category;
            category = cdb.findCategory(categoryID);
            Item newItem = new Item(0, itemName,category, itemPrice);
            newItem.setOwner(loggedIn);
            try {
                idb.insert(newItem);
                request.setAttribute("invalidItem", "Added successfully!");
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("invalidItem", "Failed to add!");
            }
            
        }
   
//        UserDB newUDB = new UserDB();
//        User newUser= null;
//        try {
//            newUser = newUDB.getUser(username);
//        } catch (HomeInventoryDBException ex) {
//            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        List<Item> itemList;
//        itemList = (List<Item>)newUser.getItemList();
//        request.setAttribute("itemList", itemList);
//        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        doGet(request, response);
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
