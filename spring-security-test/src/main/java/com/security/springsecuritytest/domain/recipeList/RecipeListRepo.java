package com.security.springsecuritytest.domain.recipeList;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeListRepo extends JpaRepository<RecipeList, Integer>{

}
