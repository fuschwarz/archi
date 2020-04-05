/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.ui.textrender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.archimatetool.model.IArchimateModelObject;
import com.archimatetool.model.IDocumentable;

/**
 * Documentation renderer
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public class DocumentationRenderer extends AbstractTextRenderer {
    
    private static final Pattern DOCUMENTATION_PATTERN = Pattern.compile("\\$\\{(mfolder:|vfolder:|model:|view:)?documentation}");

    @Override
    public String render(IArchimateModelObject object, String text) {
        Matcher matcher = DOCUMENTATION_PATTERN.matcher(text);
        
        while(matcher.find()) {
            String prefix = matcher.group(1);
            String replacement = "";
            
            // If this object is IDocumentable
            if(getActualObject(object) instanceof IDocumentable) {
                // Get ref object...
                IArchimateModelObject refObject = getObjectFromPrefix(object, prefix);
                // If ref object is IDocumentable
                if(refObject instanceof IDocumentable) {
                    replacement = ((IDocumentable)refObject).getDocumentation();
                }
            }
            
            text = text.replace(matcher.group(), replacement);
        }
        
        return text;
    }
}