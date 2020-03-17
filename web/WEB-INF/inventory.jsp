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
        <title>Inventory</title>
    </head>
    <body>
        <table>
            <tr>
                <td>
                    <a href="admin">Admin</a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="inventory">Inventory</a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="login?logout">Logout</a>
                </td>
            </tr>
        </table>
        <h1>Home Inventory</h1>
        
        
        
        <h2>Inventory for ${first} ${last}</h2>
        
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
        <table border="3">
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
                    <input type="submit" value="Delete"> 
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="selectedItem" value="${item.itemID}"> 
                    </form>
                </td>
            </tr>
                </tbody>
            </c:forEach>
   </table>
    

    
    <h2>Add Item</h2>
 <form method="post" action="inventory">
        <table>
            <tr>
            <td>
                     
  Category:<select name="type">
  <c:forEach var="catg" items="${categories}">   
  <option value="${catg.categoryID}">${catg.categoryName}</option>
  </c:forEach>
  </select> 
                     
            </td>
            </tr>
            <tr>
                <td>
                    Item name: <input type="text" required name="itemAddName">
                </td>
            </tr>

            <tr>
                <td>
                    Price: <input type="number" required name="itemAddPrice" value="0" min="0" step="0.01">
                </td>
                
            </tr>   
            <tr>
                <td>
                    <input type="submit" name="action" value="Save"><br/>
                    
                </td>
            </tr>
        </table> 
 </form>
    <font color="red" size="4">
    <p>${invalidItem}</p>
    </font>
    </body>
</html>
