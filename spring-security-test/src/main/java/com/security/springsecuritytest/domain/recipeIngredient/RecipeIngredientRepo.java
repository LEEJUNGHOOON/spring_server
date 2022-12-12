package com.security.springsecuritytest.domain.recipeIngredient;


import com.security.springsecuritytest.domain.recipeList.RecipeList;
import com.security.springsecuritytest.domain.user.UserInfo;
import com.security.springsecuritytest.web.dto.RecipeIngredientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public interface RecipeIngredientRepo extends JpaRepository<Recipeingredient, Integer>{
    @Query(value = "select recipeIngredient from Recipeingredient recipeIngredient where recipeIngredient.recipeList.id = ?1")
    List<Recipeingredient> findByRecipeLList(Integer rlid);
}
