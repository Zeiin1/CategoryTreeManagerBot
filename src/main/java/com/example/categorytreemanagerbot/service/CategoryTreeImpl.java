package com.example.categorytreemanagerbot.service;

import com.example.categorytreemanagerbot.entity.CategoryTree;
import com.example.categorytreemanagerbot.entity.CategoryTreeRequest;
import com.example.categorytreemanagerbot.repository.CategoryTreeRepository;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CategoryTreeImpl implements CategoryTreeService  {
    @Autowired
    private CategoryTreeRepository categoryTreeRepository;


    @Override
    public String save(CategoryTreeRequest categoryTreeRequest) {
        String parent = categoryTreeRequest.getParentName();
        String child = categoryTreeRequest.getName();

        if(child==null)
        {
            if(categoryTreeRepository.findAll().size()==0) //проверка сучествует ли главный корень
            {
                CategoryTree categoryTree = new CategoryTree();
                categoryTree.setName(parent);
                categoryTreeRepository.save(categoryTree);
                return "Главный корень создан успешно!";
            }
            else return "Главный корень уже существует! Вы можете создавать дочерние элементы";
            //создаем корень
        }else if(child!=null && parent!=null)
        {
            CategoryTree parentLeaf = categoryTreeRepository.findByName(parent);
            CategoryTree currNameIs = categoryTreeRepository.findByName(child);
            if(parentLeaf!=null && currNameIs==null)
            {
                CategoryTree newCategoryTree = new CategoryTree();
                newCategoryTree.setName(child);
                newCategoryTree.setParentId(parentLeaf.getId());
                categoryTreeRepository.save(newCategoryTree);
                return "Дочерний элемент создан успешно!";
            }
            else if(parentLeaf==null)
                return "Родитель с названием: "+parent + " не нашлось!";
            else return "Элемент с названием: "+child+" уже существует в системе!";
            //создаем дочерний


        }
        else
            return "Укажите название!";


    }

    @Override
    public ArrayList getAllCategory() {
        return (ArrayList) categoryTreeRepository.findAll();
    }

    @Override
    public CategoryTree getRootParent() {
        return categoryTreeRepository.getRootTree();
    }

    @Override
    public String deleteCategoryTree(CategoryTreeRequest categoryTreeRequest) {
        CategoryTree categoryTree = categoryTreeRepository.findByName(categoryTreeRequest.getName());
        if(categoryTree == null)
            return "Элемент с названием :"+categoryTreeRequest.getName()+", не существует в системе!";
        else
        {
            //удаляем дочерние элементы


            deleteChildren(categoryTree);
            return "Элемент и все его дочерние элементы были успешно удалены!";
        }

    }

    public void deleteChildren(CategoryTree categoryTree)
    {
        Collection<CategoryTree> categoryTrees = categoryTreeRepository.findAllByParentId(categoryTree.getId());
        if(categoryTrees.size()!=0)
        {
            for (CategoryTree childTree : categoryTrees)
            {
                deleteChildren(childTree);
            }
        }
        categoryTreeRepository.delete(categoryTree);
    }
}
