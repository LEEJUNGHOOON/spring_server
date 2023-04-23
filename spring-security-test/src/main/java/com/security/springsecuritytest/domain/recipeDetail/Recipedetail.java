package com.security.springsecuritytest.domain.recipeDetail;

import com.security.springsecuritytest.domain.recipeList.RecipeList;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Table(name = "recipeCooking")
public class Recipedetail {

    @Id
    @Column
    private int idx;
    @Column
    private String cooking_order;
    @Column
    private int cooking_order_no;
    @ManyToOne(targetEntity = RecipeList.class)
    @JoinColumn(name="total_list_ID")
    private RecipeList recipeList;

    @Builder
    public Recipedetail(int idx, String cooking_order, int cooking_order_no, RecipeList recipeList){
        this.idx = idx;
        this.cooking_order = cooking_order;
        this.cooking_order_no = cooking_order_no;
        this.recipeList = recipeList;
    }
//    @Override
//    public String toString(){
//        return "{"+"idx="+idx+", cooking_order="
//    }
}
