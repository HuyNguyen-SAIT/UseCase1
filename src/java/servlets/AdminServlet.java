/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
import services.CategoryService;
import services.InventoryService;
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
        HttpSession session = request.getSession();
        InventoryService is = new InventoryService();
        request.setAttribute("adminName", session.getAttribute("selectedUser"));
        CategoryService cs = new CategoryService(); 
        String action = request.getParameter("action");
        request.setAttribute("addorsave2", "Add");
        request.setAttribute("addorsave", "Add");
        request.setAttribute("doneorview", "Add category");
        if(action==null)
        {
        //request.setAttribute("addorsave2", "Add");
        }
        else
            if(action.equals("NewCate"))
            {
              
              String name = request.getParameter("categoryName");
              Category category =null;
            try {
                category = cs.getCategory(name);
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
                            if(name.equals(""))
                            {
                                
                            }
                            else
                                if(category==null)
                            {
                                response.getWriter().write("-Great!");
                            }
                            else
                            {
                                response.getWriter().write("-Category already existed");
                            }
                            return;
            }
        else
                if(action.equals("EditCategory"))
            {
                String save = request.getParameter("addorsave2");
                String save2 = request.getParameter("doneorview");
                request.setAttribute("addorsave2", save);
                request.setAttribute("doneorview", save2);
            }
        else
                    if(action.equals("checkItem"))
                {
                    int counter=0;
                    String itemName = request.getParameter("itemName");
                    if(itemName.equals(""))
                    {
                       return; 
                    }
                    else
                    {
                    int length = itemName.length();
                    String result="<table class=\"table1\"><tr><th>Item name</th><th>Price($)</th><th>Owner</th></tr>";
            try {
                List<Item> itemList = is.getAll();
                for(Item item:itemList)
                {
                    if(item.getItemName().length()<length)
                    {
                        counter++;
                    }
                    else
                    if(item.getItemName().substring(0,length).equals(itemName))
                    {
                        result+="<tr><td>"+item.getItemName()+"</td><td>"+item.getPrice()+"</td><td>"+item.getOwner().getUsername()+"</td></tr>";
                    }
                    else
                    {
                        counter++;
                    }
                }
                if(counter==itemList.size())
                {
                    result="<p class=\"red\">-No items found</p>";
                }
                else
                {
                    result+="</table>";
                }
                response.getWriter().write(result);
                return;
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
               
            }
                    }
                }
        else
                    {
                        
                    }
        
        UserService uc = new UserService();
        //UserDB udb = new UserDB();
        List<User> userList = null;
        List<Category> cateList = null;
        try {
            userList = uc.getAll();
            cateList = cs.getAll();
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("allCate", cateList);
        request.setAttribute("userList", userList);
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
        HttpSession session = request.getSession();
        UserService uc = new UserService();
        CategoryService cs = new CategoryService();
        String action = request.getParameter("action");
        
        UserDB udb = new UserDB();
        ItemDB idb = new ItemDB();
        
        User selectedUser = null ;
        List<Category>cateList=null;
        List<User> userList = null;
        
        if(action.equals("view"))
        {
            
            String selectUser = request.getParameter("selectedUser");
            try {
                selectedUser = udb.getUser(selectUser);
                if(selectedUser.getActive()==true)
                {
                    request.setAttribute("selectedA", "selected");
                    request.setAttribute("selectedI", "");
                }
                else
                {
                    request.setAttribute("selectedI", "selected");
                    request.setAttribute("selectedA", "");
                }
                session.setAttribute("selectedUsername", selectedUser);
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("selectUser", selectedUser);
            
            
        //UserDB udb = new UserDB();
        
        try {
            userList = uc.getAll();
            cateList = cs.getAll();
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("allCate", cateList);
        request.setAttribute("userList", userList);
        request.setAttribute("addorsave", "Save");
        request.setAttribute("readonly", "readonly class=\"dark\"");
        request.setAttribute("addorsave2", "Add");
        request.setAttribute("doneorview", "Add category");
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
                session.setAttribute("deletedUser", selectedUser);
                deleted = udb.delete(selectedUser);
                if(deleted == 0)
                {
                    request.setAttribute("message", "Cannot delete admins!");
                    request.setAttribute("deleted", null);
                }
                else
                {
                    request.setAttribute("message", "Delete successfully!");
                    request.setAttribute("deleted", "Deleted");
                }
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Failed to delete!");
                
            }
                
                request.setAttribute("selectUser", null);
                //request.setAttribute("addorsave", "Add");
                
                
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
            
            }
        else
                    if(action.equals("Save"))
                {
                
                //String oldUsername =(String) session.getAttribute("username");
                String username = request.getParameter("username");
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
                request.setAttribute("message", "Updated successfully!");
                
                
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Failed to update!");
            }
                    
                }
        else
                        if(action.equals("addCategory"))
                {
                    String name = request.getParameter("cateName");
            try {
                Category cate = cs.getCategory(name);
                if(cate!=null)
                {
                    request.setAttribute("message", "Failed, category already existed"); 
                    request.setAttribute("category", cate);
                }
                else
                {
                    cs.insertCategory(name);
                    request.setAttribute("message", "Added successfully!");
                   
                }
                } catch (HomeInventoryDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
                      
            
        
    }
        else
                            if(action.equals("saveCategory"))
                        {
                            String oldCate = request.getParameter("selectedCate");
                            String newCate = request.getParameter("modifyCateName");
                            if(oldCate == null || oldCate.equals(""))
                            {
                               
                            }
                            
            try {
                int result = cs.updateCategory(oldCate, newCate);
                if(result==1)
                {
                    request.setAttribute("message", "Updated successfully!");
                }
                else
                {
                    request.setAttribute("message", "Failed, category already existed");
                }
                
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
                        }
        else
                                if(action.equals("promote"))
                                {
                                    String promotingUser = request.getParameter("selectedUser");
            try {
                User user = udb.getUser(promotingUser);
                uc.update(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getActive(), true);
                request.setAttribute("message", "Promoted successfully, user "+user.getUsername()+" is now an amin");
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Failed to promote");
            }
                                }
        else
                         if(action.equals("demote"))       {
                                   String demotingUser = request.getParameter("selectedUser");
            try {
                User user = udb.getUser(demotingUser);
                uc.update(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getActive(), false);
                request.setAttribute("message", "Demoted successfully, user "+user.getUsername()+" is now a regular user");
            } catch (HomeInventoryDBException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Failed to demote");
            } 
                                }
        else
                         {
                            User restoredUser =(User) session.getAttribute("deletedUser");
            try {
                uc.insert(restoredUser);
                request.setAttribute("message", "User restored successfully!");
                request.setAttribute("deleted", null);
                request.setAttribute("deletedUser", null);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
                         }
        doGet(request,response);
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
