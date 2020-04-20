<%-- 
    Document   : inventory
    Created on : 13-Mar-2020, 10:42:27 AM
    Author     : 794458
--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel='stylesheet' type='text/css' href="js/style.css"/>
        <title>Inventory</title>
    </head>
    <body>
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
        <p class="red">${invalidItem}</p>
        <h1>Home Inventory</h1>
        <h2>Inventory for ${selectedUser.firstName} ${selectedUser.lastName}</h2>
        
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
        <table class="table1">
            <thead>
            <tr>
                
            <th>
                Category
            </th>
            <th>
                Name
            </th>
            <th>
                Price
            </th>
            <th>
                Edit
            </th>
            <th>
                Delete
            </th>
            </tr>
            </thead>
            <c:forEach var="item" items="${itemList}" >
                <tbody>
            <tr>
                <td> 
                    ${item.category.categoryName}
                </td>
                
                <td>
                    ${item.itemName}
                </td>
                
                <td>
                    $${item.price}
                </td>
                <td>
                    <form action="inventory" method="POST">
                    <input type="submit" value="Edit"> 
                    <input type="hidden" name="action" value="view">
                    <input type="hidden" name="selectedItem" value="${item.itemID}"> 
                    </form>
                </td>
                <td>
                    <form action="inventory" method="POST">
                    <input type="submit" value="Delete"> 
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="selectedItem" value="${item.itemID}"> 
                    </form>
                </td>
            </tr>
                </tbody>
            </c:forEach>
   </table>
    <c:if test="${deleted!=null}">
        <form action="inventory" method="POST">
            <input type="submit" value="Undo delete"> 
            <input type="hidden" name="action" value="undo">
        </form>
    </c:if>

    
    <h2>${addorsave} Item</h2>
 <form method="post" action="inventory">
        <table>
            <tr>
            <td>
                     
             Category:
                     
            </td>
            <td>
            <select name="type">
               
                <c:forEach var="catg" items="${categories}">   
                    <option value="${catg.categoryID}" name="${catg.categoryName}" >${catg.categoryName}</option>
                </c:forEach>
            </select> 
            </td>
            </tr>
            <tr>
                <td>
                    Item name: 
                </td>
                <td>
                    <input type="text" required name="itemAddName" value="${chosenItem.itemName}">
                </td>
            </tr>

            <tr>
                <td>
                    Price: 
                </td>
                <td>
                    <input type="number" required name="itemAddPrice" min="0" step="0.01" value="${chosenItem.price}">
                </td>
                
            </tr>   
            <tr>
                <td colspan="2">
                    <input type="submit" name="action" value="${addorsave}"><br/>
                    <input type="hidden" name="selectedItem" value="${chosenItem.itemID}"> 
                </td>
            </tr>
        </table> 
 </form>
  
    <h2>Edit your information</h2>
        <form action="inventory" method="POST">
            <table>
                <tr>
                    <td>
                        Status:
                    </td>
                    <td>
                        <select name="isactive">
                            <option value="Active" ${selectedA}>Active</option>
                            <option value="Inactive" ${selectedI}>Inactive</option>
                        </select>
                    </td>
                </tr> 
                <tr>
                    <td>
                       Username: 
                    </td>
                    <td>
                        <input class="dark" readonly type="text" required name="username" value="${selectedUser.username}" ${readonly} id="username">
                    </td>
                </tr>
                <tr>
                    <td>
                        Password:
                    </td>
                    <td>
                        <input type="password" required name="password" value="${selectedUser.password}" id="password">
                    </td>
                </tr>
                <tr>
                    <td>
                        First name:
                    </td>
                    <td>
                        <input type="text" required name="firstname" value="${selectedUser.firstName}" id="firstname">
                    </td>
                </tr>
                <tr>
                    <td>
                        Last name:
                    </td>
                    <td>
                        <input type="text" required name="lastname" value="${selectedUser.lastName}" id="lastname">
                    </td>
                </tr>
                <tr>
                    <td>
                        Email:
                    </td>
                    <td>
                        <input type="email" required name="email" value="${selectedUser.email}" id="email">
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="hidden" name="action" value="Edit">
                        <input type="submit" value="Save">
                    </td>
                </tr>
            </table>
            </form>
    </body>
</html>
