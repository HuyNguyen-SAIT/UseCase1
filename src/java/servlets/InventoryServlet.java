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
import services.InventoryService;
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
       
        
        UserDB udb = new UserDB();
        ItemDB idb = new ItemDB();
        CategoryDB cdb = new CategoryDB();
        
        HttpSession session = request.getSession();
        
        User loggedIn = new User();
        List<Item> itemList = null;
        List<Category> categories=null;
        
        try {
            loggedIn = udb.getUser((String)session.getAttribute("username"));
            if(loggedIn.getActive()==true)
            {
                request.setAttribute("selectedA", "selected");
                request.setAttribute("selectedI", "");
            }
            else
            {
                request.setAttribute("selectedI", "selected");
                request.setAttribute("selectedA", "");
            }
            itemList = idb.getAll(loggedIn);
            categories = cdb.getAll();
            //itemList = loggedIn.getItemList();
        } catch (HomeInventoryDBException ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("selectedUser", loggedIn);
        request.setAttribute("categories", categories);
        request.setAttribute("itemList", itemList);
        request.setAttribute("addorsave", "Add");
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
  //  }
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
        
        InventoryService ic = new InventoryService();
        UserService uc = new UserService();
        
        User loggedIn = null;
        List<Item> items = null;
        List<Category> categories;
        
        String selectID;
        int selectedID;
        
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
            int result =0;
            selectID = request.getParameter("selectedItem");
            selectedID = Integer.parseInt(selectID);
            try {
                //List<Item> items = loggedIn.getItemList();
                Item deletedItem = idb.getItem(selectedID);
                //items.remove(deletedItem);
                ic.itemDeleteFilter(loggedIn,deletedItem);
                
                //items = idb.getAll(loggedIn);
                //udb.setUserList(loggedIn, items);
                //loggedIn.setItemList(items);
                //List<Item> itemlist = loggedIn.getItemList();
                request.setAttribute("invalidItem", "Deleted successfully!");
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("invalidItem", "Failed to delete!");
            }
        }
        else
            if(action.equals("view"))
            {
                selectID = request.getParameter("selectedItem");
                selectedID = Integer.parseInt(selectID);
                categories = cdb.getAll();
            try {
                Item item = idb.getItem(selectedID);
                request.setAttribute("chosenItem", item);
                request.setAttribute("selected", item.getCategory());
                
                request.setAttribute("addorsave", "Save");
                
                try {
            items = idb.getAll(loggedIn);
            //itemList = loggedIn.getItemList();
        } catch (HomeInventoryDBException ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("selectedUser", loggedIn);
        request.setAttribute("categories", categories);
        request.setAttribute("itemList", items);
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        else
                if(action.equals("Save"))
                {
                    selectID = request.getParameter("selectedItem");
                    selectedID = Integer.parseInt(selectID);
                    String newName = request.getParameter("itemAddName");
                    String newPriceS = request.getParameter("itemAddPrice");
                    String newCategoryName = request.getParameter("type");
                    int newCategoryID = Integer.parseInt(newCategoryName);
                    Category newCate = cdb.findCategory(newCategoryID);
                    Double newPrice = Double.parseDouble(newPriceS);
            try {
                ic.saveItem(selectedID, newName, newPrice, newCate);
                request.setAttribute("invalidItem", "Updated successfully!");
                
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
                }     
        else
                    if(action.equals("Edit"))
                    {
                
                String password = request.getParameter("password");
                String fname = request.getParameter("firstname");
                String lname = request.getParameter("lastname");
                String email = request.getParameter("email");
                String active = request.getParameter("isactive");
                boolean isActive;
                if(active.equals("Active"))
                {
                    isActive = true;
                }
                else
                {
                    isActive = false;
                }
            try {
                uc.update(username, password, fname, lname, email, isActive);
                request.setAttribute("invalidItem", "Updated successfully!");
                
                
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("invalidItem", "Failed to update!");
            }
         }
                
         else{
       
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
                ic.insertItemFilter(loggedIn,newItem);
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("invalidItem", "Added successfully!");
            
        }

//        User newLoggedIn = new User();
//        try {
//            newLoggedIn = udb.getUser(username);
//        } catch (HomeInventoryDBException ex) {
//            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//       request.setAttribute("itemList", newLoggedIn.getItemList());
//       
//      getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        
    
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
