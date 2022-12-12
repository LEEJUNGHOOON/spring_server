package com.security.springsecuritytest.domain.recipeDetail;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipedetailRepo extends JpaRepository<Recipedetail, Integer>{

}
