package com.security.springsecuritytest.domain.recipeList;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Table(name = "totalList")
public class RecipeList {

    @Id
    @Column
    private int ID;
    @Column
    private String Name;
    @Column
    private String imgsrc;
    @Column
    private String recipe_tag;

    @Builder
    public RecipeList(int ID, String Name, String imgsrc, String recipe_tag){
        this.ID = ID;
        this.Name = Name;
        this.imgsrc = imgsrc;
        this.recipe_tag = recipe_tag;
    }
}
