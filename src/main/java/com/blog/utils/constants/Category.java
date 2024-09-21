package com.blog.utils.constants;

public enum Category {
  SPORTS("sport"),
  TECHNOLOGY("technology"),
  POLITICS("politics"),
  ENTERTAINMENT("Entertainment"),
  TRAVEL("travel"),
  FASHION("fashion"),
  HEALTH("health"),
  BUSINESS("business"),
  SCIENCE("science"),
  OTHER("other");

  private String name;

  Category(String name) {
    this.name = name;
  }

  public String getCategory() {
    return name;
  }

  public String getValidGender(String category) {
    for (Category validGender : Category.values()) {
      if (validGender.getCategory().equalsIgnoreCase(category)) {
        return validGender.getCategory();
      }
    }
    throw new IllegalArgumentException("Invalid category: " + category);
  }
}
