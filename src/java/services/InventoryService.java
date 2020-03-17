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
import java.util.List;
/**
 *
 * @author 794458
 */
public class InventoryService {
    public int itemDeleteFilter(User user, Item item) throws HomeInventoryDBException
    {
        int counter=0;
        ItemDB idb = new ItemDB();
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
        if(counter==userItems.size())
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
ItemDB idb = new ItemDB();
if(user == null)
{
    return 0;
}
else
{
    return idb.insert(item);
}

}
}
