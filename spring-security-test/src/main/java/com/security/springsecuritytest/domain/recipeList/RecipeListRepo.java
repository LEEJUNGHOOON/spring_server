package com.security.springsecuritytest.domain.recipeList;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeListRepo extends JpaRepository<RecipeList, Integer>{

}
