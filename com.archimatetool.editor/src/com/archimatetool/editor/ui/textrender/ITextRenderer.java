/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.ui.textrender;

import com.archimatetool.model.IArchimateModelObject;

/**
 * Interface for a text control renderer
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public interface ITextRenderer {
    
    String modelPrefix = "model";
    String viewPrefix = "view";
    String modelFolderPrefix = "mfolder";
    String viewFolderPrefix = "vfolder";
    
    String corePrefixes = "mfolder|vfolder|model|view";
    String sourceConnectionPrefixes = "connection:source|triggering:source|access:source|specialization:source|composition:source|assignment:source|aggregation:source|realization:source|serving:source|influence:source|flow:source|association:source";
    String targetConnectionPrefixes = "connection:target|triggering:target|access:target|specialization:target|composition:target|assignment:target|aggregation:target|realization:target|serving:target|influence:target|flow:target|association:target";
    String allPrefixes = corePrefixes + "|" + sourceConnectionPrefixes + "|" + targetConnectionPrefixes;
    
    /**
     * @param object The object whose text should be rendered
     * @param text The text that should be rendered
     * @return The result of the text rendering
     */
    String render(IArchimateModelObject object, String text);
}