/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.CategoryDB;
import database.HomeInventoryDBException;
import domain.Category;
import java.util.List;

/**
 *
 * @author 794458
 */
public class CategoryService {
    
    private final CategoryDB cdb;
    
    public CategoryService()
    {
        cdb = new CategoryDB();
    }
    
    public int insertCategory(String name) throws HomeInventoryDBException
    {
        List<Category> cateList = cdb.getAll();
        for(Category ca: cateList)
        {
        if(ca.getCategoryName().equals(name))
        {
            return 0;
        }
        }
        Category category = new Category(name);
        return cdb.insert(category);
    }
    public Category getCategory(String name) throws HomeInventoryDBException
    {
       
        return cdb.findCategory(name);
    }
    public Category getCategory(int id) throws HomeInventoryDBException
    {
       
        return cdb.findCategory(id);
    }
    public List<Category> getAll() throws HomeInventoryDBException
    {
        
        return cdb.getAll();
    }
    public int updateCategory(String old,String newCate) throws HomeInventoryDBException
    {
        Category cate = cdb.findCategory(old);
        if(newCate.equals(old))
        {
            return 1;
        }
        else
        {
        List<Category> allCate = cdb.getAll();
        for(Category ca: allCate)
        {
            if(newCate.equals(ca.getCategoryName()))
            {
                return 0;
            }
        }
        cate.setCategoryName(newCate);
        return cdb.update(cate);
        }
    }
}
