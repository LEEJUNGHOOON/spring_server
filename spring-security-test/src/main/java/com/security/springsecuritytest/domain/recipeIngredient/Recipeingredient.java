package com.security.springsecuritytest.domain.recipeIngredient;

import com.security.springsecuritytest.domain.recipeList.RecipeList;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Table(name = "recipeIngredient")
public class Recipeingredient {

    @Id
    @Column
    private int idx_ing;
    @Column
    private String ingredient_name;
    @Column
    private String ingredient_Cp;
    @ManyToOne(targetEntity = RecipeList.class)
    @JoinColumn(name="total_list_ID")
    private RecipeList recipeList;

    @Builder
    public Recipeingredient(int idx_ing,String ingredient_name,String ingredient_Cp,RecipeList recipeList){
        this.idx_ing = idx_ing;
        this.ingredient_name = ingredient_name;
        this.ingredient_Cp = ingredient_Cp;
        this.recipeList = recipeList;
    }
}
