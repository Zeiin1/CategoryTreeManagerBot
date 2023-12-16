package com.example.categorytreemanagerbot.service;

import com.example.categorytreemanagerbot.entity.CategoryTree;
import com.example.categorytreemanagerbot.entity.CategoryTreeRequest;
import org.hibernate.mapping.List;

import java.util.ArrayList;

public interface CategoryTreeService {
    String save(CategoryTreeRequest categoryTreeRequest);

    ArrayList getAllCategory();

    CategoryTree getRootParent();

    String deleteCategoryTree(CategoryTreeRequest categoryTreeRequest);
}
