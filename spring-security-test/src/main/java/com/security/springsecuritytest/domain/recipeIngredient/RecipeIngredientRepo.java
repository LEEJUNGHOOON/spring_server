package com.security.springsecuritytest.domain.recipeIngredient;


import com.security.springsecuritytest.domain.user.UserInfo;
import com.security.springsecuritytest.web.dto.RecipeIngredientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RecipeIngredientRepo extends JpaRepository<Recipeingredient, Integer>{

}
