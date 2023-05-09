package com.security.springsecuritytest.domain.recipeIngredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepo extends JpaRepository<Recipeingredient, Integer>{

    //jpql을 이용해 쿼리를 보내 해당하는 id값을 가진 data들만 retrieve
    @Query(value = "select recipeIngredient from Recipeingredient recipeIngredient where recipeIngredient.recipeList.id = ?1")
    List<Recipeingredient> findByRecipeLList(int id);
}
