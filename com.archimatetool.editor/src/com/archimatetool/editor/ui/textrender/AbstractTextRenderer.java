/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.ui.textrender;

import com.archimatetool.model.IArchimateModelObject;
import com.archimatetool.model.IDiagramModel;
import com.archimatetool.model.IDiagramModelArchimateComponent;
import com.archimatetool.model.IDiagramModelComponent;
import com.archimatetool.model.IDiagramModelNote;
import com.archimatetool.model.IFolder;

/**
 * Name renderer
 * 
 * @author Phillip Beauvoir
 */
public abstract class AbstractTextRenderer implements ITextRenderer {
    
    /**
     * Get the actual object that this represents
     * If it's an IDiagramModelArchimateComponent return the referenced IArchimateConcept
     * @param object The object
     * @return object itself or the IArchimateConcept
     */
    protected IArchimateModelObject getActualObject(IArchimateModelObject object) {
        return object instanceof IDiagramModelArchimateComponent ? ((IDiagramModelArchimateComponent)object).getArchimateConcept() : object;
    }
    
    /**
     * Get the object referred to by prefix
     * @param object The object
     * @param prefix The prefix
     * @return the referenced object
     */
    protected IArchimateModelObject getObjectFromPrefix(IArchimateModelObject object, String prefix) {
        // Model
        if(modelPrefix.equals(prefix)) {
            return object.getArchimateModel();
        }

        // View - object is an IDiagramModelComponent so return IDiagramModel
        if(viewPrefix.equals(prefix) && object instanceof IDiagramModelComponent) {
            return ((IDiagramModelComponent)object).getDiagramModel();
        }
        
        // Linked object from a Note
        if(linkedPrefix.equals(prefix) && object instanceof IDiagramModelNote) {
            IDiagramModelNote note = (IDiagramModelNote)object;
            IArchimateModelObject other = null;
            
            // Note has at least one source connection...
            if(!note.getSourceConnections().isEmpty()) {
                other = note.getSourceConnections().get(0).getTarget();
            }
            // Or Note has at least one target connection...
            else if(!note.getTargetConnections().isEmpty()) {
                other = note.getTargetConnections().get(0).getSource();
            }
            
            return getActualObject(other);
        }
            
        IArchimateModelObject actualObject = getActualObject(object);

        // Model Folder
        if(modelFolderPrefix.equals(prefix) && actualObject.eContainer() instanceof IFolder) { // Has a folder parent
            return (IFolder)actualObject.eContainer();
        }
        
        // View Folder
        if(viewFolderPrefix.equals(prefix) && object instanceof IDiagramModelComponent) {
            IDiagramModel dm = ((IDiagramModelComponent)object).getDiagramModel();
            return dm != null ? (IArchimateModelObject)dm.eContainer() : null; // folder parent of IDiagramModel
        }
        
        return actualObject;
    }

}