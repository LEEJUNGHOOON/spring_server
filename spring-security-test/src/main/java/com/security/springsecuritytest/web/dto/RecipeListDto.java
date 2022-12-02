package com.security.springsecuritytest.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeListDto {

    private String Name;
    private String imgsrc;
    @Builder
    public RecipeListDto(String Name, String imgsrc){
        this.Name = Name;
        this.imgsrc = imgsrc;
    }
}
