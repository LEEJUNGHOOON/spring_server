package com.security.springsecuritytest.domain.recipeIngredient;

import com.security.springsecuritytest.domain.recipeList.RecipeList;

import lombok.*;

import jakarta.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Table(name = "recipeIngredient")
public class Recipeingredient {

    @Id
    @Column
    private int idx_ing;//index값
    @Column
    private String ingredient_name;//재료의 이름
    @Column
    private String ingredient_Cp;//재료의 양 ex)100g/3개
    @ManyToOne(targetEntity = RecipeList.class)//전체 레시피리스트 에서 id값을 왜래키로 참조
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
