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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.properties;



import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class BCTJavaProjectPropertyPage extends PropertyPage {

	private static final String PATH_TITLE = "BCT default MC";
	private static final String OWNER_TITLE = "&Owner:";
	private static final String BCT_DEFAULT_MC = "it.unimib.disco.lta.eclipse.fsa";
	private static final String DEFAULT_MC = "";

	private static final int TEXT_FIELD_WIDTH = 50;

	private Text ownerText;
	private Text pathValueText;
	
	/**
	 * Constructor for SamplePropertyPage.
	 */
	public BCTJavaProjectPropertyPage() {
		super();
	}

	private void addFirstSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		//Label for path field
		Label pathLabel = new Label(composite, SWT.NONE);
		pathLabel.setText(PATH_TITLE);

		Button b = new Button(composite,SWT.NONE);
		b.setText("Select...");
		b.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				//FileSelectionDialog dialog = new FileSelectionDialog();
				// TODO Auto-generated method stub
				throw new sun.reflect.generics.reflectiveObjects.NotImplementedException();
			}

			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				throw new sun.reflect.generics.reflectiveObjects.NotImplementedException();
			}
			
		});
		
		// Path text field
		pathValueText = new Text(composite, SWT.WRAP );
		pathValueText.setText((getResource()).getFullPath().toString());
	}

	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	private void addSecondSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		// Label for owner field
		Label ownerLabel = new Label(composite, SWT.NONE);
		ownerLabel.setText(OWNER_TITLE);

		// Owner text field
		ownerText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		ownerText.setLayoutData(gd);

		// Populate owner text field
		try {
			String owner =
				((IResource) getResource()).getPersistentProperty(
					new QualifiedName("", BCT_DEFAULT_MC));
			ownerText.setText((owner != null) ? owner : DEFAULT_MC);
		} catch (CoreException e) {
			ownerText.setText(DEFAULT_MC);
		}
	}

	private IResource getResource() {
		IJavaProject prj = (IJavaProject) getElement();
		try {
			System.out.println("CR "+prj.getCorrespondingResource());
			System.out.println("R"+prj.getResource());
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prj.getResource();
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addFirstSection(composite);
		
		return composite;
	}

	private Composite createDefaultComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	protected void performDefaults() {
		// Populate the owner text field with the default value
		pathValueText.setText(DEFAULT_MC);
	}
	
	public boolean performOk() {
		// store the value in the owner text field
		try {
			
			getResource().setPersistentProperty(
				new QualifiedName("", BCT_DEFAULT_MC),
				pathValueText.getText());
		} catch (CoreException e) {
			return false;
		}
		return true;
	}

}