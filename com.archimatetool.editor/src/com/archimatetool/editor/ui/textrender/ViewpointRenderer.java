/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.ui.textrender;

import com.archimatetool.model.IArchimateDiagramModel;
import com.archimatetool.model.IArchimateModelObject;
import com.archimatetool.model.viewpoints.ViewpointManager;

/**
 * Viewpoint renderer
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public class ViewpointRenderer implements ITextRenderer {
    
    public static final String VIEWPOINT = "${viewpoint}";

    @Override
    public String render(IArchimateModelObject object, String text) {
        String vpName = "";
        
        if(object instanceof IArchimateDiagramModel) {
            vpName = ViewpointManager.INSTANCE.getViewpoint(((IArchimateDiagramModel)object).getViewpoint()).getName();
        }
        
        return text.replace(VIEWPOINT, vpName);
    }
}