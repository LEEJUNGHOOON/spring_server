package com.security.springsecuritytest.domain.recipeIngredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepo extends JpaRepository<Recipeingredient, Integer>{

}
