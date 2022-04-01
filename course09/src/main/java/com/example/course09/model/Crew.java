package com.example.course09.model;

import org.springframework.stereotype.Component;

/**
 * @author oldFlag
 * @since 2022/4/1
 */
@Component
public class Crew {

    private String name;
    private Integer sex;
    private String species;

    public Crew() {
    }

    public Crew(String name, Integer sex, String species) {
        this.name = name;
        this.sex = sex;
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
