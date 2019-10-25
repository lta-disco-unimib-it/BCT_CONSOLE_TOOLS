package org.cprover.launch;

import org.cprover.core.CProverPlugin;
import org.cprover.ui.Constants;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.launch.internal.ui.LaunchMessages;
import org.eclipse.cdt.launch.internal.ui.LaunchUIPlugin;
import org.eclipse.cdt.ui.CElementLabelProvider;
import org.eclipse.cdt.ui.ICDTConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.swt.widgets.List;

/**
 * A launch configuration tab that displays and edits project and main type name launch
 * configuration attributes.
 * 
 * Modeled after @see org.eclipse.cdt.launch.ui.CMainTab
 * 
 * @author Gérard Basler
 */
public class VerifyMainTab extends AbstractLaunchConfigurationTab {

	// Project UI widgets
	protected Label fProjLabel;
	protected Text fProjText;
	protected Button fProjButton;
	
	// Main class UI widgets
	protected Label fProgLabel;
	protected List fProgList;
	protected StringBuffer bufStr = new StringBuffer();
	
	protected static final String EMPTY_STRING = ""; //$NON-NLS-1$
	
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		GridLayout topLayout = new GridLayout();
		comp.setLayout(topLayout);
		
		createVerticalSpacer(comp, 1);
		createProjectGroup(comp, 1);
		createExeFileGroup(comp, 1);
	}

	@SuppressWarnings("restriction")
	protected void createProjectGroup(Composite parent, int colSpan) {
		Composite projComp = new Composite(parent, SWT.NONE);
		GridLayout projLayout = new GridLayout();
		projLayout.numColumns = 2;
		projLayout.marginHeight = 0;
		projLayout.marginWidth = 0;
		projComp.setLayout(projLayout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		projComp.setLayoutData(gd);

		fProjLabel = new Label(projComp, SWT.NONE);
		fProjLabel.setText(("&ProjectColon")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = 2;
		fProjLabel.setLayoutData(gd);

		fProjText = new Text(projComp, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fProjText.setLayoutData(gd);
		fProjText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});

		Composite projCompButtons = new Composite(projComp, SWT.NONE);
		GridLayout ButtonsLayout = new GridLayout();
		projLayout.marginHeight = 0;
		projLayout.marginWidth = 0;
		projCompButtons.setLayout(ButtonsLayout);
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_FILL);
		gd.verticalAlignment = SWT.TOP;
		projCompButtons.setLayoutData(gd);

		fProjButton = createPushButton(projCompButtons, ("Browse file"), null); //$NON-NLS-1$
		fProjButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				handleProjectButtonSelected();
				updateLaunchConfigurationDialog();
			}
		});
	}
	
	@SuppressWarnings("restriction")
	protected void createExeFileGroup(Composite parent, int colSpan) {
		Composite mainComp = new Composite(parent, SWT.NONE);
		GridLayout mainLayout = new GridLayout();
		mainLayout.numColumns = 2;
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		mainComp.setLayout(mainLayout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		mainComp.setLayoutData(gd);
		
		fProgLabel = new Label(mainComp, SWT.NONE);
		fProgLabel.setText("Files to verify:"); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = 2;
		fProgLabel.setLayoutData(gd);		
		
		fProgList = new List (mainComp, SWT.DROP_DOWN | SWT.BORDER  | SWT.H_SCROLL | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		fProgList.setLayoutData(gd);

		Composite mainCompButtons = new Composite(mainComp, SWT.NONE);
		GridLayout ButtonsLayout = new GridLayout();
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		mainCompButtons.setLayout(ButtonsLayout);
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_FILL);
		gd.verticalAlignment = SWT.TOP;
		mainCompButtons.setLayoutData(gd);

		// for Browse button
		Button fProgSearchButton;
		fProgSearchButton = createPushButton(mainCompButtons, ("Browse"), null); //$NON-NLS-1$
		fProgSearchButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				handleSearchButtonSelected();
				updateLaunchConfigurationDialog();
			}
		});

		// for Remove button
		final Button fProgRemoveButton;
		fProgRemoveButton = createPushButton(mainCompButtons, "Remove", null); //$NON-NLS-1$
		fProgRemoveButton.setVisible(false);
		fProgRemoveButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				handleRemoveButtonSelected();
				updateLaunchConfigurationDialog();
			}
		});
		
		fProgList.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {				
				String[] selected = fProgList.getSelection();				
				
				//Set Remove Button visible if some item is selected
				if(!selected[0].matches(""))
					fProgRemoveButton.setVisible(true);

			}
			public void widgetDefaultSelected(SelectionEvent event) {
				//String[] selected = fProgList.getSelection();
			}
		});
	}
		
	/**
	 * Show a dialog that lets the user select a project. This in turn provides context for the main
	 * type, allowing the user to key a main type name, or constraining the search for main types to
	 * the specified project.
	 */
	protected void handleProjectButtonSelected() {
		ICProject project = chooseCProject();
		if (project == null) {
			return;
		}

		String projectName = project.getElementName();
		fProjText.setText(projectName);
	}

	/**
	 * Realize a C Project selection dialog and return the first selected project, or null if there
	 * was none.
	 */
	protected ICProject chooseCProject() {
		try {
			ICProject[] projects = CoreModel.getDefault().getCModel().getCProjects();

			ILabelProvider labelProvider = new CElementLabelProvider();
			ElementListSelectionDialog dialog = new ElementListSelectionDialog(getShell(), labelProvider);
			dialog.setTitle(("Select projects")); //$NON-NLS-1$
			dialog.setMessage(("Choose_project_to_constrain_search_for_program")); //$NON-NLS-1$
			dialog.setElements(projects);

			ICProject cProject = getCProject();
			if (cProject != null) {
				dialog.setInitialSelections(new Object[]{cProject});
			}
			if (dialog.open() == Window.OK) {
				return (ICProject)dialog.getFirstResult();
			}
		} catch (CModelException e) {
			LaunchUIPlugin.errorDialog("Launch UI internal error", e); //$NON-NLS-1$			
		}
		return null;
	}
	

	/**
	 * Return the ICProject corresponding to the project name in the project name text field, or
	 * null if the text does not match a project name.
	 */
	protected ICProject getCProject() {
		String projectName = fProjText.getText().trim();
		if (projectName.length() < 1) {
			return null;
		}
		return CoreModel.getDefault().getCModel().getCProject(projectName);
	}

	/**
	 * Show a dialog that lets the user select a project. This in turn provides context for the main
	 * type, allowing the user to key a main type name, or constraining the search for main types to
	 * the specified project.
	 */
	protected void handleSearchButtonSelected() {
		final ICProject cproject = getCProject();
		if (cproject == null) {
			MessageDialog.openInformation(getShell(), ("Project_required"), //$NON-NLS-1$
					("Enter_project_before_browsing_for_program")); //$NON-NLS-1$
			return;
		}
		
		FileDialog fileDialog = new FileDialog(getShell(), SWT.NONE);
		
		if((fProgList.getItemCount() == 1) && (fProgList.getItem(0).matches("")))
			fileDialog.setFileName(EMPTY_STRING);    
		else
			fileDialog.setFileName(fProgList.getItem(fProgList.getItemCount() - 1));

		String text= fileDialog.open();
		if (text != null) {
			if((fProgList.getItemCount() == 1) && (fProgList.getItem(0).matches("")))
				fProgList.remove(0);
			if(fProgList.indexOf(text) == -1){
				fProgList.add(text);
				bufStr.append(text).append(";");
			}
		}
	}

	/**
	 * Remove the selected file in the fProgList.
	 * */
	protected void handleRemoveButtonSelected() {
		final ICProject cproject = getCProject();
		if (cproject == null) {
			//MessageDialog.openInformation(getShell(), LaunchMessages.getString("CMainTab.Project_required"), //$NON-NLS-1$
			//		LaunchMessages.getString("CMainTab.Enter_project_before_browsing_for_program")); //$NON-NLS-1$
			return;
		}
		
		String progToRemove = (fProgList.getSelection())[0];
		if((progToRemove != null) && (!progToRemove.matches(""))) {			
			int itemI = fProgList.indexOf(progToRemove);
			int itemCntBefore = fProgList.getItemCount();
			fProgList.remove(fProgList.indexOf(progToRemove));
			
			if(itemI == itemCntBefore - 1) {
				fProgList.select(itemI - 1);
			}
			else {
				fProgList.select(itemI);
			}

			int start = bufStr.indexOf(progToRemove);
			if(start != -1)
				bufStr.delete(start, start + progToRemove.length() + 1);
			else
				bufStr.delete(0, bufStr.length());
			
			if(fProgList.getItemCount() == 0){
				fProgList.add(EMPTY_STRING);
				bufStr.delete(0, bufStr.length());
			}
		}
		else {
			fProgList.removeAll();
	        fProgList.add(EMPTY_STRING);    
			bufStr.delete(0, bufStr.length());
		}
	}

	public String getName() {
		return "CProverMainTab.Main";
	}

	public void initializeFrom(ILaunchConfiguration configuration) {
		updateProjectFromConfig(configuration);
		updateFilesFromConfig(configuration);
	}

	protected void updateProjectFromConfig(ILaunchConfiguration configuration) {
		String projectName = EMPTY_STRING;
		try {
			projectName = configuration.getAttribute(Constants.ATTR_PROJECT_NAME, EMPTY_STRING);
		} catch (CoreException ce) {
			CProverPlugin.log(ce);
		}
		fProjText.setText(projectName);
	}

	protected void updateFilesFromConfig(ILaunchConfiguration configuration) {
		String programName = EMPTY_STRING;
		try {
			programName = configuration.getAttribute(Constants.ATTR_FILES, EMPTY_STRING);
		} catch (CoreException ce) {
			CProverPlugin.log(ce);
		}

		fProgList.removeAll();
		bufStr.delete(0, bufStr.length());
		
        String[] tmp_nan1 = programName.split(";", 100);        
        for(int i = 0; i < tmp_nan1.length; i++) {
        	//if(tmp_nan1[i].isEmpty() == false)
        	if(tmp_nan1[i].length() != 0)
        	{
        		fProgList.add(tmp_nan1[i]);
        		bufStr.append(tmp_nan1[i]).append(";");
        	}
        }        
        
        if(fProgList.getItemCount() == 0)
        	fProgList.add(EMPTY_STRING);        
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(Constants.ATTR_PROJECT_NAME, fProjText.getText());
		
		configuration.setAttribute(Constants.ATTR_FILES, bufStr.toString());
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// We set empty attributes for project & program so that when one config
		// is
		// compared to another, the existence of empty attributes doesn't cause
		// an
		// incorrect result (the performApply() method can result in empty
		// values
		// for these attributes being set on a config if there is nothing in the
		// corresponding text boxes)
		// plus getContext will use this to base context from if set.
		configuration.setAttribute(Constants.ATTR_PROJECT_NAME, EMPTY_STRING);
		configuration.setAttribute(Constants.ATTR_FILES, EMPTY_STRING);
	}
}
