package com.security.springsecuritytest.domain.recipeDetail;

import com.security.springsecuritytest.domain.recipeList.RecipeList;

import lombok.*;

import jakarta.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Table(name = "recipeCooking")
public class Recipedetail {

    @Id
    @Column
    private int idx;//index값
    @Column
    private String cooking_order;//해당레시피를 조리 하는 순서 ex)면을 삶는다
    @Column
    private int cooking_order_no;// 해당 레시피 조리 순서 ex)
    @ManyToOne(targetEntity = RecipeList.class)//전체 레시피리스트 에서 id값을 왜래키로 참조
    @JoinColumn(name="total_list_ID")
    private RecipeList recipeList;

    @Builder
    public Recipedetail(int idx, String cooking_order, int cooking_order_no, RecipeList recipeList){
        this.idx = idx;
        this.cooking_order = cooking_order;
        this.cooking_order_no = cooking_order_no;
        this.recipeList = recipeList;
    }

}
