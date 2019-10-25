/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.editors;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.BctViolationsLogDataComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.ViolationsAnalysisResultComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.wizards.BctViolationsLogAnalysisWizard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.MultiPageEditorPart;

public class BctViolationsLogEditor extends MultiPageEditorPart implements IResourceChangeListener, BCTObserver {

	private boolean dirty;

	private BctViolationsLogDataComposite violationsLogComposite;

	private ViolationsAnalysisResultComposite resultComposite;

	private BctViolationsAnalysisConfiguration violationsAnalysisConfig;

	private List<IFile> filesToOpen;

	private IEditorSite site;

	/**
	 * Creates a multi-page editor example.
	 */
	public BctViolationsLogEditor() {
		super();

		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	private static GridData createFill() {
		GridData gd = new GridData();
		gd.horizontalAlignment = 4;
		gd.grabExcessHorizontalSpace = true;
		gd.verticalAlignment = 4;
		gd.grabExcessVerticalSpace = true;
		return gd;
	}

	/**
	 * Creates page 0 of the multi-page editor, which contains a text editor.
	 */
	void createAnalysisTab() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);

		Composite parent = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		parent.setLayout(layout);

		violationsLogComposite = new BctViolationsLogDataComposite(parent, SWT.NONE, true);

		composite.setLayoutData(createFill());

		Button analyzeButton = new Button(parent, SWT.PUSH);
		analyzeButton.setText("New Violations Log Analysis");
		analyzeButton.setLocation(20, 20);
		analyzeButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				BctViolationsLogAnalysisWizard wizard = new BctViolationsLogAnalysisWizard();
				wizard.setFilesToOpen(filesToOpen);
				WizardDialog dialog = new WizardDialog(getSite().getShell(), wizard);
				dialog.create();
				dialog.open();
			}

		});

		int index = addPage(composite);
		setPageText(index, "Analysis setup");

		composite.setContent(parent);
		composite.setMinSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createAnalysisTab();

		ArrayList<File> files = new ArrayList<File>();
		for (IFile file : filesToOpen) {
			violationsLogComposite.setMonitoringConfiguration(MonitoringConfigurationRegistry.getInstance()
					.getAssociatedMonitoringConfiguration(file));
			files.add(file.getLocation().toFile());
		}

		violationsLogComposite.loadFiles(files);
		violationsLogComposite.setSite(site);
		
	}

	private void setDirty(boolean isDirty) {
		if (dirty != isDirty) {
			dirty = isDirty;
			this.firePropertyChange(PROP_DIRTY);
		}
	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		site.setSelectionProvider(null);
		super.dispose();
	}

	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
	}

	/**
	 * Saves the multi-page editor's document as another file. Also updates the
	 * text for page 0's tab, and updates this multi-page editor's input to
	 * correspond to the nested editor's.
	 */
	public void doSaveAs() {

		SaveAsDialog dialog = new SaveAsDialog(null);
		dialog.create();

		if (dialog.open() == Window.CANCEL) {
			return;
		}

		IPath filePath = dialog.getResult();
		if (filePath == null) {
			return;
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = workspace.getRoot().getFile(filePath);

		save(file);

	}

	private void save(IFile file) {
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		this.site = site;
		filesToOpen = new ArrayList<IFile>();
		if ((editorInput instanceof IFileEditorInput)) {
			IFile fileToOpen = ((IFileEditorInput) editorInput).getFile();
			filesToOpen.add(fileToOpen);
		}
		super.init(site, editorInput);

	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		System.out.println(event.getType());
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IProgressMonitor progressMonitor = new NullProgressMonitor();
					doSave(progressMonitor);
				}
			});
		}
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		if (!dirty) {
			dirty = true;
			this.firePropertyChange(PROP_DIRTY);
		}
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

}
