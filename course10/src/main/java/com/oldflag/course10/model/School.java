package com.oldflag.course10.model;

import lombok.Data;

import java.util.List;

/**
 * @author oldFlag
 * @since 2022/4/1
 */
@Data
public class School {

    List<Klass> classes;

    String collageName;


}
