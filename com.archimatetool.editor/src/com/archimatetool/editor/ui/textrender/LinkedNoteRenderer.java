/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.ui.textrender;

import com.archimatetool.model.IArchimateModelObject;
import com.archimatetool.model.IDiagramModelNote;
import com.archimatetool.model.IDocumentable;

/**
 * Linked Note renderer
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public class LinkedNoteRenderer extends AbstractTextRenderer {
    
    public static final String LINKED_DOCUMENTATION = "${linkeddoc}";
    
    @Override
    public String render(IArchimateModelObject object, String text) {
        if(object instanceof IDiagramModelNote) {
            IDiagramModelNote note = (IDiagramModelNote)object;
            
            if(text.contains(LINKED_DOCUMENTATION)) {
                String replacement = "";
                IArchimateModelObject other = null;
                
                // Note has at least one source connection...
                if(!note.getSourceConnections().isEmpty()) {
                    other = note.getSourceConnections().get(0).getTarget();
                }
                // Or Note has at least one target connection...
                else if(!note.getTargetConnections().isEmpty()) {
                    other = note.getTargetConnections().get(0).getSource();
                }
                
                // Use the other's documentation
                other = getActualObject(other);
                
                if(other instanceof IDocumentable) {
                    replacement = ((IDocumentable)other).getDocumentation();
                }

                text = text.replace(LINKED_DOCUMENTATION, replacement);
            }
        }
        
        return text;
    }
    
}