/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.ui.textrender;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.archimatetool.model.IArchimateModelObject;
import com.archimatetool.model.IProperties;
import com.archimatetool.model.IProperty;

/**
 * Properties renderer
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public class PropertiesRenderer extends AbstractTextRenderer {
    
    private static final String PROPERTIES = "${properties}";
    private static final String PROPERTIES_VALUES = "${propertiesvalues}";
    
    private static final Pattern FILTERED_PROPERTIES_WITH_SEPARATOR = Pattern.compile("\\$\\{properties:([^:]*):([^\\}]+)\\}");
    private static final Pattern PROPERTY_VALUE = Pattern.compile("\\$" + allPrefixesGroup + "\\{property:(.*)\\}");

    /**
     * Keep a list of Properties visited to check for circular recursion
     */
    private Set<IProperty> visitedProperties = new HashSet<IProperty>();

    @Override
    public String render(IArchimateModelObject object, String text) {
        text = renderPropertyValue(object, text);
        
        IArchimateModelObject actualObject = getActualObject(object);
        if(actualObject instanceof IProperties) {
            text = renderPropertiesList((IProperties)actualObject, text);
            text = renderPropertiesValues((IProperties)actualObject, text);
            text = renderPropertiesValuesCustomList((IProperties)actualObject, text);
        }
        
        return text;
    }
    
    private String renderPropertiesValuesCustomList(IProperties object, String text) {
    	Matcher matcher = FILTERED_PROPERTIES_WITH_SEPARATOR.matcher(text);
    	
        while(matcher.find()) {
        	String separator = matcher.group(1);
        	String key = matcher.group(2);
        	String s = "";
            
            for(IProperty property : object.getProperties()) {
                if(property.getKey().equals(key)) {
                	if(!s.isEmpty()) {
                		s += separator;
                	}
                	
                	s += property.getValue();
                }
            }
            
            text = text.replace(matcher.group(), s);
        }
        
        return text;
    }

    private String renderPropertyValue(IArchimateModelObject object, String text) {
        Matcher matcher = PROPERTY_VALUE.matcher(text);
        
        while(matcher.find()) {
            String prefix = matcher.group(1);
            String key = matcher.group(2);
            String propertyValue = "";
            
            /*
             * Nested expression that refers to another object.
             * Because this renderer is last in the series of registered renderers,
             * all other expressions will have been rendered by this point
             * except for a nested property value expression. So we recurse this method.
             */
            if(key.startsWith("$")) {
                key = renderPropertyValue(object, key);
                // If we don't render in a given order then we would need to call this instead
                // key = TextRenderer.getDefault().render(object, key);
            }
            
            IArchimateModelObject refObject = getObjectFromPrefix(object, prefix);
            if(refObject instanceof IProperties) {
                IProperty property = getProperty((IProperties)refObject, key);
                if(property != null) {
                    // Ensure we do not have a circular recursion
                    if(visitedProperties.contains(property)) {
                        propertyValue = "***[Label Expression Error] Circular Reference***";
                    }
                    else {
                        visitedProperties.add(property);
                        propertyValue = property.getValue();
                    }
                }
            }
            
            // Property value is an expression
            if(propertyValue.startsWith("$")) {
                propertyValue = TextRenderer.getDefault().render(object, propertyValue);
            }
            
            text = text.replace(matcher.group(), propertyValue);
            
            // Clear this here
            visitedProperties.clear();
        }

        return text;
    }
    
    // List all properties like key: value
    private String renderPropertiesList(IProperties object, String text) {
        return text.replace(PROPERTIES, getAllProperties(object, true));
    }
    
    // List all properties' values
    private String renderPropertiesValues(IProperties object, String text) {
        return text.replace(PROPERTIES_VALUES, getAllProperties(object, false));
    }

    private String getAllProperties(IProperties object, boolean full) {
        String s = "";
        
        for(int i = 0; i < object.getProperties().size(); i++) {
            IProperty property = object.getProperties().get(i);
            
            if(full) {
                s += property.getKey() + ": ";
            }

            s += property.getValue();
            
            if(i < object.getProperties().size() - 1) {
                s += "\n";
            }
        }
        
        return s;
    }
    
    private IProperty getProperty(IProperties object, String key) {
        for(IProperty property : object.getProperties()) {
            if(property.getKey().equals(key)) {
                return property;
            }
        }
        
        return null;
    }
}