package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class IoModelsFindDialog extends Dialog {

	private String regex;
	private boolean enter = true, exit = true;
	
	public IoModelsFindDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout layout = (GridLayout) composite.getLayout();
		layout.numColumns = 2;

		Label label = new Label(composite, SWT.NONE);
		label.setText("Search items by regular expression:");
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));

		final Text regexField = new Text(composite, SWT.SINGLE | SWT.BORDER);
		regexField.setText("Regular Expression");
		regexField.selectAll();
		regexField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				regex = regexField.getText();
			}
		});
		regexField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		Group group = new Group(composite, SWT.NONE);
		group.setText("IO Invariants");
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		group.setLayout(new FillLayout());

		final Button enterButton = new Button(group, SWT.CHECK);
		enterButton.setText("Enter");
		enterButton.setSelection(true);
		enterButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enter = enterButton.getSelection();
			}
		});

		final Button exitButton = new Button(group, SWT.CHECK);
		exitButton.setText("Exit");
		exitButton.setSelection(true);
		exitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				exit = exitButton.getSelection();
			}
		});

		return composite;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Find");
	}

	public String getInputText() {
		return regex;
	}

	public boolean findEnterInvariants() {
		return enter;
	}

	public boolean findExitInvariants() {
		return exit;
	}
}