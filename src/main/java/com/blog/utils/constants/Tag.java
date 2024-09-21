package com.blog.utils.constants;

public enum Tag{
    SPORTS("sports"),
    TECHNOLOGY("technology"),
    HEALTH("health"),
    TRAVEL("travel"),
    FOOD("food"),
    FASHION("fashion"),
    POLITICS("politics"),
    ENTERTAINMENT("entertainment"),
    BUSINESS("business"),
    SCIENCE(""),
    EDUCATION("education"),
    OTHER("other");

    private final String tagName;
    Tag(String tagName){
        this.tagName = this.name().toLowerCase();
    }
    public String getTagName(){
        return this.tagName;
    }
    public static String getValidTag(String tag){
        for(Tag validTag: Tag.values()){
            if(validTag.getTagName().equalsIgnoreCase(tag)){
                return validTag.getTagName();
            }
        }
        throw new IllegalArgumentException("Invalid tag: "+tag);
    }
}
