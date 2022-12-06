package com.security.springsecuritytest.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeListDto {

    private int ID;
    private String Name;
    private String imgsrc;
    @Builder
    public RecipeListDto(int ID,String Name, String imgsrc){
        this.ID = ID;
        this.Name = Name;
        this.imgsrc = imgsrc;
    }
}
