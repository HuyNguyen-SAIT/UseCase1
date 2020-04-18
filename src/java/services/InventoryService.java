/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import domain.Item;
import domain.User;
import dataaccess.*;
import database.HomeInventoryDBException;
import domain.Category;
import java.util.List;
/**
 *
 * @author 794458
 */
public class InventoryService {
    private final ItemDB idb;
    
    public InventoryService()
    {
        idb = new ItemDB();
    }
    public int itemDeleteFilter(User user, Item item) throws HomeInventoryDBException
    {
        int counter=0;
        
        List<Item> userItems = idb.getAll(user);
        for(int i=0; i<userItems.size();i++)
        {
              if(item.getItemID() == userItems.get(i).getItemID())
              {
                
              }
              else
              {
                  counter++;
              }
       
        }
        if(counter==userItems.size() || user==null)
        {
            return 0;
        }
        else
        {
            return idb.delete(item);
        }
         
    }

public int insertItemFilter(User user, Item item) throws HomeInventoryDBException
{

if(user == null)
{
    return 0;
}
else
{
    return idb.insert(item);
}

}
public int saveItem(int id,String newName, double newPrice, Category newCate) throws HomeInventoryDBException
{
    
    Item item = idb.getItem(id);
    item.setItemName(newName);
    item.setPrice(newPrice);
    item.setCategory(newCate);
    return idb.save(item);
}

public List<Item> getAll() throws HomeInventoryDBException
{
    return idb.getAll();
}
}
