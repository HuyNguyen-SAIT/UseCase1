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
            <th>
                Delete
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
                    <input type="submit" value="Delete"> 
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="selectedItem" value="${item.itemID}"> 
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
  <option value="1">Kitchen</option>
  <option value="2">Bathroom</option>
  <option value="3">Living room</option>
  <option value="4">Basement</option>
  <option value="5">Bedroom</option>
  <option value="6">Garage</option>
  <option value="7">Office</option>
  <option value="8">Utility room</option>
  <option value="9">Storage</option>
  <option value="10">Other</option>
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
                    ${invalidItem}
                </td>
            </tr>
        </table> 
 </form>
    </body>
</html>
