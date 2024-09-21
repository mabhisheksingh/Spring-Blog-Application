package com.blog.utils.constants;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    private final String gender;
    Gender(String gender){
        this.gender=gender;
    }

    public String getValidGender(String gender){
        for(Gender validGender: Gender.values()){
            if(validGender.getGender().equalsIgnoreCase(gender)){
                return validGender.getGender();
            }
        }
        throw new IllegalArgumentException("Invalid gender: "+gender);
    }

}
