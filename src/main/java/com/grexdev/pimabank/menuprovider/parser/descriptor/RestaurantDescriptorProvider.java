package com.grexdev.pimabank.menuprovider.parser.descriptor;

import java.io.InputStream;

import com.grexdev.pimabank.menuprovider.exception.MenuProviderException;
import com.thoughtworks.xstream.XStream;

public class RestaurantDescriptorProvider {

    private XStream xstream;

    public RestaurantDescriptorProvider() {
        xstream = new XStream();
        xstream.processAnnotations(RestaurantDescriptor.class);
    }

    public RestaurantDescriptor getRestaurantDescriptor(String descriptorFile) throws MenuProviderException {
        try (InputStream inputStream = loadResourceFromClasspath(descriptorFile)) {
            return (RestaurantDescriptor) xstream.fromXML(inputStream);
            
        } catch (Exception e) {
            throw new MenuProviderException("Error during retriving descriptor from file " + descriptorFile, e);
        }
    }

    private InputStream loadResourceFromClasspath(String resource) {
        ClassLoader classloader = this.getClass().getClassLoader();
        return classloader.getResourceAsStream(resource);
    }

}
