package com.security.springsecuritytest.domain.recipeIngredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepo extends JpaRepository<Recipeingredient, Integer>{

    @Query(value = "select recipeIngredient from Recipeingredient recipeIngredient where recipeIngredient.recipeList.id = ?1")
    List<Recipeingredient> findByRecipeLList(int id);
}
