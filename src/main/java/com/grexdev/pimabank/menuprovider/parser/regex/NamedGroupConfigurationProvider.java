package com.grexdev.pimabank.menuprovider.parser.regex;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

@Slf4j
public class NamedGroupConfigurationProvider {
    
    private static final String UNNAMED_GROUP_PREFFIX = "TAG_";
    
    public Map<String, String> getNamedRegexpGroups(String resourceName) throws IOException, ConfigurationException {
        try (InputStream inputStream = loadResourceFromClasspath(resourceName)) {
            PropertiesConfiguration.setDefaultListDelimiter(';');
            PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
            propertiesConfiguration.load(inputStream, "UTF-8");
                      
            Iterator<String> keyIterator = propertiesConfiguration.getKeys();
            Map<String, String> namedGroups = new HashMap<String, String>();
            
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                String value = propertiesConfiguration.getString(key);
                log.debug("key={}, value = >>>{}<<<", key, value);
                
                if (key.startsWith(UNNAMED_GROUP_PREFFIX) == false) {
                    namedGroups.put(key, value);
                }
            }
            
            return namedGroups;
        }
    }
    
    private InputStream loadResourceFromClasspath(String resource) {
        ClassLoader classloader = this.getClass().getClassLoader();
        return classloader.getResourceAsStream(resource);
    }

}
