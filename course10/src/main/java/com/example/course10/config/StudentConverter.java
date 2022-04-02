package com.example.course10.config;

import com.example.course10.GsonUtil;
import com.example.course10.model.Student;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author oldFlag
 * @since 2022/4/2
 */

@Component
@ConfigurationPropertiesBinding
public class StudentConverter implements Converter<String, List<Student>> {

    @Override
    public List<Student> convert(String source) {
        return GsonUtil.toList(source);
    }
}
