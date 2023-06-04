package com.security.springsecuritytest.domain.recipeList;


import com.security.springsecuritytest.domain.recipeIngredient.Recipeingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeListRepo extends JpaRepository<RecipeList, Integer>{
    @Query(value = "select recipeList from RecipeList recipeList where recipeList.recipe_tag like %:tag%")//tag을 가진 data들만 가져옴
    List<RecipeList> findByTag(String tag);
}
