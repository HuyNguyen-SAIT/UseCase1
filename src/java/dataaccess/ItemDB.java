/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import database.DBUtil;
import database.HomeInventoryDBException;
import domain.Item;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author 794458
 */
public class ItemDB {

    public int delete(Item item)throws HomeInventoryDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.remove(em.merge(item));
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot delete " + item.toString(), ex);
            throw new HomeInventoryDBException("Error deleting item");
        } finally {
            em.close();
        }
    }    
    
    public List<Item> getAll(User owner) throws HomeInventoryDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Item> items = em.createNamedQuery("Item.findAll", Item.class).getResultList();
            ArrayList<Item> userItems= new ArrayList<>();
            for(int i=0; i<items.size();i++)
            {
                if(items.get(i).getOwner().getUsername().equals(owner.getUsername()))
                {
                   userItems.add(items.get(i));
                }
            }
            return userItems;                
        } finally {
            em.close();
        }
    }
    
    public Item getItem(int itemID) throws HomeInventoryDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Item item;
        try {
            //items = em.createNamedQuery("Item.findByItemID",  Item.class).getResultList();
            item = em.find(Item.class, itemID);
            return item;                
        } finally {
            em.close();
        }
    }
    
    
    
    public int insert(Item item) throws HomeInventoryDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            //Role role = em.find(Role.class, 2);  // 2 is for regular user
            //user.setRole(role);
            trans.begin();
            em.persist(item);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot insert " + item.toString(), ex);
            throw new HomeInventoryDBException("Error inserting item");
        } finally {
            em.close();
        }
    }
}
