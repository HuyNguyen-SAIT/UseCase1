/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import database.DBUtil;
import database.HomeInventoryDBException;
import domain.Category;
import domain.Item;
import domain.User;
import java.util.List;
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
