package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.flattener;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CreateModifierDialog extends TitleAreaDialog {

	public CreateModifierDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected Control createDialogArea(Composite parent) {  
		Composite contents = new Composite(parent, SWT.NONE);  
		contents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label maxDepthLabel=new Label(contents, SWT.NULL);
		maxDepthLabel.setText("");
		

		
		return contents;
	}
	
	
}
