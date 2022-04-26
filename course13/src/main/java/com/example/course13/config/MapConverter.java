package com.example.course13.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author oldFlag
 * @since 2022/4/25
 */

@Component
@ConfigurationPropertiesBinding
public class MapConverter implements Converter<String, Map<String, String>> {

    @Override
    public Map<String, String> convert(String source) {
        String[] split = source.split(",");

        if (split.length == 0) {
            throw new RuntimeException("at least one master node is needed");
        }

        if (split.length % 2 != 0) {
            throw new RuntimeException("address syntax error, e.g. master,jdbc:mysql://xxx");
        }

        final Map<String, String> map = new HashMap<>();

        for (int i = 0; i < split.length; i += 2) {
            map.put(split[i], split[i + 1]);
        }

        return Collections.unmodifiableMap(map);
    }
}

