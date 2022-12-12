package com.security.springsecuritytest.domain.recipeDetail;


import com.security.springsecuritytest.domain.recipeIngredient.Recipeingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipedetailRepo extends JpaRepository<Recipedetail, Integer>{

    @Query(value = "select recipeDetail from Recipedetail recipeDetail where recipeDetail.recipeList.id = ?1")
    List<Recipedetail> findByRecipeLList(int id);
}
