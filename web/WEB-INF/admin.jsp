<%-- 
    Document   : admin
    Created on : 13-Mar-2020, 10:42:37 AM
    Author     : 794458
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' type='text/css' href="js/style.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="js/register.js"></script>
        <title>Administrator</title>
    </head>
    <body>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <table>
            <tr>
                <td>
                    <a href="admin">| Admin |</a>
                </td>
           
                <td>
                    <a href="inventory">Inventory |</a>
                </td>
           
                <td>
                    <a href="login?logout">Logout |</a>
                </td>
            </tr>
        </table>
         <p class="red">${message}</p>
        <h1>Welcome ${adminName.firstName} ${adminName.lastName}</h1>
        
        <h2>Manage Users:</h2>
        
        
        <table class="table1">
            <tr>
                <th>
                    Status
                </th>
                <th>
                    Username
                </th>
                <th>
                    First Name
                </th>
                <th>
                    Last Name
                </th>
                <th>
                    Delete
                </th>
                <th>
                    Edit
                </th>
             
            </tr>
            <c:forEach var="user" items="${userList}" >
                <tbody class="table1">
            <tr>
                <c:if test="${user.active==true}">
                 <td class="greenA">
                    <!--<p class="green">-Active-</p>-->
                 </td>
                </c:if>
                <c:if test="${user.active==false}">
                 <td class="redI">        
                  <!--<p class="red">-Inactive-</p>-->
                 </td>
                </c:if>
                
                <td> 
                    ${user.username}
                </td>
                
                <td>
                    ${user.firstName}
                </td>
                
                <td>
                    ${user.lastName}
                </td>
                
                <td>
                    <form action="admin" method="POST">
                        <input type="submit" value="Delete" >
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="selectedUser" value="${user.username}">  
                    </form>
                </td>
                
                <td>
                    <form action="admin" method="POST">
                    <input type="submit" value="Edit">
                    <input type="hidden" name="action" value="view">
                    <input type="hidden" name="selectedUser" value="${user.username}">  
                    </form>
                </td>
                <c:if test="${user.isAdmin==false}">
                    <td>
                    <form action="admin" method="POST">
                    <input type="submit" value="Promote">
                    <input type="hidden" name="action" value="promote">
                    <input type="hidden" name="selectedUser" value="${user.username}">  
                    </form>
                    </td>
                </c:if>
                    <c:if test="${user.isAdmin==true}">
                    <td>
                    <form action="admin" method="POST">
                    <input type="submit" value="Demote">
                    <input type="hidden" name="action" value="demote">
                    <input type="hidden" name="selectedUser" value="${user.username}">  
                    </form>
                    </td>
                    </c:if>
            </tr>
        </tbody>
                 </c:forEach>
        </table>
        
        <h2>Item Search:</h2>
        <table>
            <tr>
            <td>
            Item name: 
            </td>
            <td>
            <input type="text" id="itemSearch"><br/>
            </td>
            </tr>
        </table>
        <span id="searchResult">-Result will display here-</span>
        
        <h2>View/Modify Category:</h2>
        
   
        <form method="POST" action="admin">
            <table>
                <tr>
                    <td>
                        <select id="chosenCate">
                            <option value="" disabled hidden selected>
                                Select a category
                            </option>
                          <c:forEach var="cate" items="${allCate}">
              
                            <option value="${cate.categoryName}">${cate.categoryName}</option> 
                   
                          </c:forEach>
                        </select>
                    </td>
                    <td>
                         <input type="text" id="modifyCateName" required name="modifyCateName" value="${caName}">
                    </td>
                    <td>
                         <input type="submit" value="Save">
                         <input type="hidden" name="action" value="saveCategory">
                         <input id="modify" type="hidden" name="selectedCate" value="${caName}">
                    </td>
            </table>
        </form>
        
       <h2>Add Category:</h2>
       <form action="admin" method="POST">
       <table>
           <tr>
               <td>
                   Category name: 
               </td>
               <td>
                   <input type="text" id="categoryName" required name="cateName" value="${caName2}">
               </td>
               <td>
                   <input type="submit" value="Add">
                   <input type="hidden" name="action" value="addCategory">
               </td>
           </tr>
               
                   
       </table>
               <span class="red" id="error_message"></span>
       </form>
        <h2>${addorsave} User:</h2>
        
            <form action="admin" method="POST">
                <table>
                    <tr>
                        <td>
                            Status:
                        </td>
                        <td>
                            <select name="isactive">
                               <option name="Active" ${selectedA}>Active</option>
                               <option name="Inactive" ${selectedI}>Inactive</option>
                            </select>
                        </td>
                    </tr>
                    
                    <tr>
                        <td>
                            Username:
                        </td>
                        <td>
                            <input type="text" required name="username" value="${selectUser.username}" ${readonly} id="username">
                        </td>
                    </tr>
                    <tr>
                        <td>
                           Password: 
                        </td>
                        <td>
                            <input type="password" required name="password" value="${selectUser.password}" id="password">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            First name:
                        </td>
                        <td>
                            <input type="text" required name="firstname" value="${selectUser.firstName}" id="firstname">
                        </td>
                    </tr>
                    <tr>
                        <td>
                             Last name:
                        </td>
                        <td>
                            <input type="text" required name="lastname" value="${selectUser.lastName}" id="lastname">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Email:
                        </td>
                        <td>
                             <input type="email" required name="email" value="${selectUser.email}" id="email">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="hidden" name="action" value="${addorsave}">
                            <input type="submit" value="${addorsave}">
                        </td>
                    </tr>
      
                </table>
            </form>
            
    </body>
</html>
