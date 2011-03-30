/*******************************************************************************
 * Copyright (c) 2010 Bolton University, UK.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 *******************************************************************************/
package uk.ac.bolton.archimate.editor.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.LabelRetargetAction;
import org.eclipse.ui.actions.RetargetAction;




/**
 * Action Factory 
 * 
 * @author Phillip Beauvoir
 */
public final class ArchimateEditorActionFactory {
    
    /**
     * Standard Delete Action with icon and key binding
     */
    public static class DELETE_ACTION extends Action {
        public DELETE_ACTION(String text) {
            ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
            setText(text);
            setActionDefinitionId("org.eclipse.ui.edit.delete"); // Ensures key binding is displayed //$NON-NLS-1$
            setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
            setDisabledImageDescriptor(
                    sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
        }
    }
    
    /**
     * Provide our own Delete Action in order to use a LabelRetargetAction
     */
    public static final ActionFactory DELETE = new ActionFactory("delete", //$NON-NLS-1$
            IWorkbenchCommandConstants.EDIT_DELETE) {
        
        @Override
        public IWorkbenchAction create(IWorkbenchWindow window) {
            if(window == null) {
                throw new IllegalArgumentException();
            }
            LabelRetargetAction action = new LabelRetargetAction(getId(), "&Delete");
            action.setToolTipText("Delete");
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            action.enableAccelerator(false);
            // window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.DELETE_RETARGET_ACTION);
            ISharedImages sharedImages = window.getWorkbench().getSharedImages();
            action.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
            action.setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
            return action;
        }
    };

    /**
     * Provide our own Rename Action in order to use a LabelRetargetAction and not have name of "Rename..."
     */
    public static final ActionFactory RENAME = new ActionFactory("rename", //$NON-NLS-1$
            IWorkbenchCommandConstants.FILE_RENAME) {
       
        @Override
        public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            LabelRetargetAction action = new LabelRetargetAction(getId(), "Rena&me");
            action.setToolTipText("Rename");
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    /**
     * A Retargetable Action to Open a Diagram
     */
    public static final ActionFactory OPEN_DIAGRAM = new ActionFactory("open_diagram", //$NON-NLS-1$
                                    "uk.ac.bolton.archimate.editor.action.openDiagram") {

        @Override
        public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(), "Open &View");
            window.getPartService().addPartListener(action);
            // Don't do this unless registering a key binding in plugin.xml
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    /**
     * A Retargetable Action to Close a Model
     */
    public static final ActionFactory CLOSE_MODEL = new ActionFactory("close_model", //$NON-NLS-1$
                                    "uk.ac.bolton.archimate.editor.action.closeModel") {

        @Override
        public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(), "Close Model");
            window.getPartService().addPartListener(action);
            // Don't do this unless registering a key binding in plugin.xml
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    /**
     * A Retargetable Action to Save Model
     */
    public static final ActionFactory SAVE_MODEL = new ActionFactory("save_model", //$NON-NLS-1$
                                IWorkbenchCommandConstants.FILE_SAVE) {
        
        @Override
        public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new SaveAction(window);
            action.setId(getId());
            return action;
        }
    };
    
    /**
     * A Retargetable Action to Save As
     */
    public static final ActionFactory SAVE_AS = new ActionFactory("save_as", //$NON-NLS-1$
                                IWorkbenchCommandConstants.FILE_SAVE_AS) {
        
        @Override
        public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new SaveAsAction(window);
            action.setId(getId());
            return action;
        }
    };
    
    /**
     * A Retargetable Action to Save As Template
     */
    public static final ActionFactory SAVE_AS_TEMPLATE = new ActionFactory("save_as_template", //$NON-NLS-1$
                                "uk.ac.bolton.archimate.editor.action.saveAsTemplate") {
        
        @Override
        public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new SaveAsTemplateAction(window);
            action.setId(getId());
            return action;
        }
    };
    
    /**
     * A Retargetable Action to Duplicate
     */
    public static final ActionFactory DUPLICATE = new ActionFactory("duplicate", //$NON-NLS-1$
                                "uk.ac.bolton.archimate.editor.action.duplicate") {
        
        @Override
        public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            LabelRetargetAction action = new LabelRetargetAction(getId(), "Duplicate");
            window.getPartService().addPartListener(action);
            // Don't do this unless registering a key binding in plugin.xml
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

}
