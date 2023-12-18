package com.example.categorytreemanagerbot.repository;

import com.example.categorytreemanagerbot.entity.CategoryTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CategoryTreeRepository extends JpaRepository<CategoryTree,Long> {

    CategoryTree findByName(String name);
    @Query("select tree from CategoryTree tree where tree.parentId is null")
    CategoryTree getRootTree();

    Collection<CategoryTree> findAllByParentId(Long parentId);
}
