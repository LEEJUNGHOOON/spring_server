package com.security.springsecuritytest.domain.recipeList;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Table(name = "totalList")
public class RecipeList {

    @Id
    @Column
    private int ID;//레시피 id값
    @Column
    private String Name;//레시피의 이름
    @Column
    private String imgsrc;//이미지 url 주소
    @Column
    private String recipe_tag;// 태그 Ex)#한식,#양식

    @Builder
    public RecipeList(int ID, String Name, String imgsrc, String recipe_tag){
        this.ID = ID;
        this.Name = Name;
        this.imgsrc = imgsrc;
        this.recipe_tag = recipe_tag;
    }
}
