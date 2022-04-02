package com.example.course10.config;

import com.example.course10.GsonUtil;
import com.example.course10.model.Klass;
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
public class KlassConverter implements Converter<String, List<Klass>> {

    @Override
    public List<Klass> convert(String source) {
        return GsonUtil.toList(source);
    }
}
