package com.example.course10.config;

import com.example.course10.model.Klass;
import com.example.course10.model.Student;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @author oldFlag
 * @since 2022/4/2
 */
@ConfigurationProperties(prefix = "school")
@PropertySource("classpath:application.properties")
public class SchoolProperties {

    private List<Student> students;
    private List<Klass> classes;

    private String collageName;

    public List<Student> getStudents() {
        return students;
    }

//    public void setStudents(String students) {
//        this.students = GSON.fromJson(students, new TypeToken<List<Student>>() {
//        }.getType());
//    }

    public List<Klass> getClasses() {
        return classes;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setClasses(List<Klass> classes) {
        this.classes = classes;
    }

//    public void setClasses(String classes) {
//        this.classes = GSON.fromJson(classes, new TypeToken<List<Klass>>() {
//        }.getType());
//    }

    public String getCollageName() {
        return collageName;
    }

    public void setCollageName(String collageName) {
        this.collageName = collageName;
    }

}
