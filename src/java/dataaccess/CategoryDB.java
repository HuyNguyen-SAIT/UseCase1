/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import database.DBUtil;
import database.HomeInventoryDBException;
import domain.Category;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author 794458
 */
public class CategoryDB {

public Category findCategory(int categoryID)
{
    Category category = null;
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    try {
    category = em.find(Category.class, categoryID);
    return category;                
        } finally {
            em.close();
        }
}

public Category findCategory(String categoryName)
{
    Category category = null;
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    List<Category> caList;
    try {
    caList = em.createNamedQuery("Category.findAll", Category.class).getResultList();
    for(Category ca: caList)
    {
        if(ca.getCategoryName().equals(categoryName))
        {
            category = ca;
            break;
        }
    }
    return category;                
    } finally {
            em.close();
        }
}

public int insert(Category category) throws HomeInventoryDBException
{
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    EntityTransaction trans = em.getTransaction();
        try {
         
            trans.begin();
            em.persist(category);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(CategoryDB.class.getName()).log(Level.SEVERE, "Cannot insert " + category.toString(), ex);
            throw new HomeInventoryDBException("Error inserting user");
        } finally {
            em.close();
        }
}
public int update(Category cate) throws HomeInventoryDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(cate);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(CategoryDB.class.getName()).log(Level.SEVERE, "Cannot update " + cate.toString(), ex);
            throw new HomeInventoryDBException("Error updating category");
        } finally {
            em.close();
        }
    }

public List<Category> getAll()
{
    List<Category> categories = null;
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    
    try {
        categories = em.createNamedQuery("Category.findAll", Category.class).getResultList();
    return categories;                
        } finally {
            em.close();
        }
}



}
