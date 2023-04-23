package com.security.springsecuritytest.domain.recipeReview;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface RecipeReviewRepo extends JpaRepository<RecipeReview,String>{
    @Query(value = "select recipeReview from RecipeReview recipeReview where recipeReview.UID = ?1 and recipeReview.recipeList.id = ?2")
    Optional<RecipeReview> findByUID(String UID,int recipeId);
}
