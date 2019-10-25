package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentsConfigurationComposite;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (bctc).
 */
 
public class BCTComponentsConfigurationWizardPage extends WizardPage implements BCTObserver {
	//private Text containerText;

	////private Text fileText;

	private ISelection selection;

	private ComponentsConfigurationComposite componentConfigurationComposite;

	private ComponentsConfiguration ccToLoad;

	private boolean initialized;

	private boolean componentsRequired = false;

	

//	public void setContainer( IFolder folder ){
//		containerText.setText(folder.getFullPath().toString());
//	}
	
	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public BCTComponentsConfigurationWizardPage(ISelection selection) {
		super("wizardPage");
		
		setTitle("BCT Components Definition");
		setDescription("This wizard creates a new file with *.bctcc extension.");
		this.selection = selection;
		
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		
		componentConfigurationComposite = new ComponentsConfigurationComposite(container,SWT.NONE);
		
		componentConfigurationComposite.addBCTObserver(this);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		componentConfigurationComposite.setLayoutData(gd);


//		Label label = new Label(container, SWT.NULL);
//		label.setText("&Load from:");
//
//		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
//		gd = new GridData(GridData.FILL_HORIZONTAL);
//		containerText.setLayoutData(gd);
//		containerText.setEditable(true);
//		containerText.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent e) {
//				dialogChanged();
//			}
//		});
//
//		Button button = new Button(container, SWT.PUSH);
//		button.setText("Browse...");
//		button.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				handleBrowse();
//			}
//		});
				

				
		
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if ( ccToLoad != null ){
			internalLoad(ccToLoad);
			ccToLoad = null;
		}
//		if (selection != null && selection.isEmpty() == false
//				&& selection instanceof IStructuredSelection) {
//			IStructuredSelection ssel = (IStructuredSelection) selection;
//			if (ssel.size() > 1)
//				return;
//			Object obj = ssel.getFirstElement();
//			if (obj instanceof IResource) {
//				IContainer container;
//				if (obj instanceof IContainer)
//					container = (IContainer) obj;
//				else
//					container = ((IResource) obj).getParent();
//				containerText.setText(container.getFullPath().toString());
//			}
//		}
//		
//		
//		
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

//	private void handleBrowse() {
//		
//		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
////		SelectionDialog dialog = new ContainerSelectionDialog(
////				getShell(), ResourcesPlugin.getWorkspace().getRoot(), true, 
////				"Select new file container");
//		String result;
//		if ( ( result = dialog.open() ) != null) {
//			containerText.setText(result);
//		}
//		
//	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		
		
		StringBuffer messageB = new StringBuffer();
		
		if ( componentsRequired  && componentConfigurationComposite.getComponents().size() == 0 ){
			messageB.append("At least one component must be defined\n");
		}
		
		if ( messageB.length() == 0){
			updateStatus(null);
		} else {
			updateStatus(messageB.toString());
		}
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	public ComponentsConfigurationComposite getComponentConfigurationComposite(){
		return componentConfigurationComposite;
	}

	private void componentConfigurationChanged() {
		dialogChanged();
	}

	public void setConfigurationName(String configurationName) {
		componentConfigurationComposite.setConfigurationName(configurationName);
	}

	public ComponentsConfiguration createComponentsConfiguration() {
		return componentConfigurationComposite.createComponentsConfiguration();
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		componentConfigurationChanged();
	}

	public void load(ComponentsConfiguration cc) {
		if ( ! initialized ){
			ccToLoad = cc;
		} else {
			internalLoad(cc);
		}
		
	}

	private void internalLoad(ComponentsConfiguration cc) {
		try {
			componentConfigurationComposite.load(cc);
		} catch (ComponentManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getInstance().log(e);
		}
	}

	public boolean isComponentsRequired() {
		return componentsRequired;
	}

	public void setComponentsRequired(boolean componentsRequired) {
		this.componentsRequired = componentsRequired;
	}
}