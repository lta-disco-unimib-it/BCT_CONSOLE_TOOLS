/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.cprover.perspectives;

import org.cprover.views.ClaimsView;
import org.cprover.views.LoopsView;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;


/**
 *  This class is meant to serve as an example for how various contributions 
 *  are made to a perspective. Note that some of the extension point id's are
 *  referred to as API constants while others are hardcoded and may be subject 
 *  to change. 
 */

/**
 * Class: VerifyPerspective
 *
 * Purpose: Organize all views around the editor area of CProver Eclipse Plugin 
 */

public class VerifyPerspective implements IPerspectiveFactory {

    public static final String PERSPECTIVE_ID = "org.cprover.plugin.perspectives.VerifyPerspective";
	
	private IPageLayout factory;

	public VerifyPerspective() {
		super();
	}

	public void createInitialLayout(IPageLayout factory) {
		this.factory = factory;

		addViews();
		addPerspectiveShortcuts();
		addViewShortcuts();
	}
	
	/**
	 * Function: addViews
	 * 
	 * Purpose: add views to Verify perspective; two views on topRight are defined for CProver Plugin.
	 */

	private void addViews() {
		// Creates the overall folder layout. 
		// Note that each new Folder uses a percentage of the remaining EditorArea.
		IFolderLayout bottom =
			factory.createFolder(
				"bottom", //NON-NLS-1
				IPageLayout.BOTTOM,
				0.8f,
				factory.getEditorArea());
		bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
//		bottom.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);

//		IFolderLayout middle = factory.createFolder("middle", IPageLayout.BOTTOM, 0.7f, factory.getEditorArea());
//		middle.addView(ClaimsView.ID_CLAIMS_VIEW);
//		middle.addView("org.cprover.plugin.views.SampleView");
		
		IFolderLayout topLeft =
			factory.createFolder(
					"topLeft", //NON-NLS-1
					IPageLayout.TOP,
					0.4f,
					factory.getEditorArea());
		topLeft.addView(IDebugUIConstants.ID_DEBUG_VIEW);
		
		IFolderLayout topRight =
			factory.createFolder(
					"topRight", //NON-NLS-1
					IPageLayout.RIGHT,
					0.5f,
					"topLeft");
		topRight.addView(ClaimsView.ID_CLAIMS_VIEW);
		topRight.addView(LoopsView.ID_LOOPS_VIEW);
		
		IFolderLayout right =
			factory.createFolder(
					"middleRight", //NON-NLS-1
					IPageLayout.RIGHT,
					0.7f,
					factory.getEditorArea());
		right.addView(IDebugUIConstants.ID_VARIABLE_VIEW);
		right.addView(IPageLayout.ID_OUTLINE);		

//		factory.addFastView("org.eclipse.team.sync.views.SynchronizeView", 0.50f); //NON-NLS-1
	}

	private void addPerspectiveShortcuts() {
		factory.addPerspectiveShortcut("org.eclipse.ui.resourcePerspective"); //NON-NLS-1
	}

	private void addViewShortcuts() {
		factory.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
		factory.addShowViewShortcut(IDebugUIConstants.ID_VARIABLE_VIEW);
		factory.addShowViewShortcut(IDebugUIConstants.ID_DEBUG_VIEW);
		factory.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		factory.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		factory.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
//		factory.addShowViewShortcut("org.eclipse.pde.runtime.LogView");
	}

}
