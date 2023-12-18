package com.example.categorytreemanagerbot.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryTreeStructure {
    //тут мы рисуем само дерево
    private Map<Long, List<CategoryTree>> treeMap;
    String parentName;
    public CategoryTreeStructure(String parentName) {
        treeMap = new HashMap<>();
        this.parentName = parentName;
    }

    public void addToTree(CategoryTree category) {
        treeMap.computeIfAbsent(category.getParentId(), k -> new ArrayList<>()).add(category);
    }

    public String printTree(Long parentId, int level) {
        StringBuilder stringBuilder = new StringBuilder();
        if (treeMap.containsKey(parentId)) {

            stringBuilder.append("  ".repeat(level)); // Adjust spacing according to level
            stringBuilder.append("|_ ").append(parentName).append("\n");
            level = level + 2;
            stringBuilder.append(drawTree(parentId, level + 2));


        }
        return stringBuilder.toString();
    }
    public StringBuilder drawTree(Long id,int level)
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (treeMap.containsKey(id)) {

            for (CategoryTree category : treeMap.get(id)) {
                stringBuilder.append("  ".repeat(level));
                stringBuilder.append("|_ ").append(category.getName()).append("\n");
                stringBuilder.append(drawTree(category.getId(), level + 2));

            }
        }
        return stringBuilder;
    }


}