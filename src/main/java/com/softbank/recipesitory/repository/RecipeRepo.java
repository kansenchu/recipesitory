package com.softbank.recipesitory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softbank.recipesitory.dao.RecipeDao;

public interface RecipeRepo extends JpaRepository<RecipeDao, Long> {

}
