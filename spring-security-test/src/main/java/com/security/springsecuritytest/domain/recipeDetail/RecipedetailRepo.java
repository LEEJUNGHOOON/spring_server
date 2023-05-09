package com.security.springsecuritytest.domain.recipeDetail;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipedetailRepo extends JpaRepository<Recipedetail, Integer>{
    //jpql을 이용해 쿼리를 보내 해당하는 id값을 가진 data들만 retrieve
    @Query(value = "select recipeDetail from Recipedetail recipeDetail where recipeDetail.recipeList.id = ?1")
    List<Recipedetail> findByRecipeLList(int id);
}
