<%-- 
    Document   : inventory
    Created on : 13-Mar-2020, 10:42:27 AM
    Author     : 794458
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory</title>
    </head>
    <body>
        <h1>Home Inventory</h1>
        <h2>Inventory for ${first} ${last}</h2>
        
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <form action="inventory" method="POST">
    <c:forEach var="item" items="${itemList}" >
       
        <table>
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
            </tr>
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
                    <input type="hidden" value="${item.itemName}" >
                    
                    <input type="button" value="Delete">  
                </td>
            </tr>
      
   </table>
    
</c:forEach>
    </form>
    <h2>Add Item</h2>
 <form method="post" action="inventory">
        <table>
            <tr>
                <td>
                    Category:<select name="type">
  <option name="kitchen">Kitchen</option>
  <option name="living room">Living Room</option>
  <option name="garage">Garage</option>
  <option name="bedroom">Bedroom</option>
</select> 
                </td>
            </tr>
            <tr>
                <td>
                    Item name: <input type="text" name="itemAddName" value="">
                </td>
            </tr>

            <tr>
                <td>
                    Price: <input type="text" name="itemAddPrice" value="">
                </td>
                
            </tr>   
            <tr>
                <td>
                    <input type="submit" value="Add"><br/>
                    ${invalidItem}
                </td>
            </tr>
        </table> 
 </form>
    </body>
</html>
