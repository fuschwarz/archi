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
    String connectionPrefixes = "connection|triggering|access|specialization|composition|assignment|aggregation|realization|serving|influence|flow|association";
    String allPrefixes = corePrefixes + "|" + connectionPrefixes;
    
    /**
     * @param object The object whose text should be rendered
     * @param text The text that should be rendered
     * @return The result of the text rendering
     */
    String render(IArchimateModelObject object, String text);
}