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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointVariable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue.ModifiedInfo;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

public class FindDialog extends Dialog {
	
	private Text programPointName, variableName, variableValue;
	private Combo programPointType, variableType, variableModifiedInfo;
	private TextFocusListener focusListener;
	private FindDialogInput dialogInput;
	private TextModifyListener textModifyListener;
	private ComboModifyListener comboModifyListener; 

	private class TextFocusListener extends FocusAdapter {
		@Override
		public void focusGained(FocusEvent e) {
			Widget widget = e.widget;
			if (widget instanceof Text) {
				Text text = (Text) widget;
				text.selectAll();
			}
		}
	}
	
	private class TextModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			Text textField = (Text) e.widget;
			String text = validateString(textField.getText());
			
			if (textField.equals(programPointName)) {
				dialogInput.setProgramPointNameExpression(text);
			} else if (textField.equals(variableName)) {
				dialogInput.setVariableNameExpression(text);
			} else if (textField.equals(variableValue)) {
				dialogInput.setVariableValueExpression(text);
			}
		}
	}
	
	private class ComboModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			Combo combo = (Combo) e.widget;
			String text = combo.getText();
			
			if (text.equals(".*")) {
				if (combo.equals(programPointType)) {
					dialogInput.setProgramPointType(null);
				} else if (combo.equals(variableType)) {
					dialogInput.setVariableType(null);
				} else if (combo.equals(variableModifiedInfo)) {
					dialogInput.setVariableModifiedInfo(null);
				}
			} else {
				if (combo.equals(programPointType)) {
					dialogInput.setProgramPointType(ProgramPoint.Type.valueOf(text));
				} else if (combo.equals(variableType)) {
					dialogInput.setVariableType(ProgramPointVariable.Type.valueOf(text));
				} else if (combo.equals(variableModifiedInfo)) {
					dialogInput.setVariableModifiedInfo(ModifiedInfo.valueOf(text));
				}
			}
		}	
	}
	
	public FindDialog(Shell parentShell, FindDialogInput dialogInput) {
		super(parentShell);
		focusListener = new TextFocusListener();
		textModifyListener = new TextModifyListener();
		comboModifyListener = new ComboModifyListener();
		this.dialogInput = dialogInput;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Find Program Points");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		
		new Label(composite, SWT.NONE).setText("Find criteria. Empty fields will be ignored.");
		createProgramPointGroup(composite);
		createVariableGroup(composite);
		
		return composite;
	}
	
	private void createProgramPointGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText("Program point");
		group.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, true, 2, 1));
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		group.setLayout(gridLayout);
		
		new Label(group, SWT.NONE).setText("Name:");
		programPointName = new Text(group, SWT.SEARCH | SWT.SINGLE);
		programPointName.setText("regular expression here");
		programPointName.addFocusListener(focusListener);
		programPointName.setFocus();
		programPointName.addModifyListener(textModifyListener);
		
		new Label(group, SWT.NONE).setText("Type:");		
		programPointType = new Combo(group, SWT.READ_ONLY);
		programPointType.add(".*");
		for(ProgramPoint.Type ppType : ProgramPoint.Type.values()) {
			programPointType.add(ppType.toString());
		}
		programPointType.addModifyListener(comboModifyListener);
	}

	private void createVariableGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText("Variable");
		group.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, true, 2, 1));
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		group.setLayout(gridLayout);
		
		new Label(group, SWT.NONE).setText("Name:");
		variableName = new Text(group, SWT.SEARCH | SWT.SINGLE);
		variableName.setText("regular expression here");
		variableName.addFocusListener(focusListener);
		variableName.addModifyListener(textModifyListener);
		
		new Label(group, SWT.NONE).setText("Type:");		
		variableType = new Combo(group, SWT.READ_ONLY);
		variableType.add(".*");
		for(ProgramPointVariable.Type varType : ProgramPointVariable.Type.values()) {
			variableType.add(varType.toString());
		}
		variableType.addModifyListener(comboModifyListener);
		
		new Label(group, SWT.NONE).setText("Value:");
		variableValue = new Text(group, SWT.SEARCH | SWT.SINGLE);
		variableValue.setText("regular expression here");
		variableValue.addFocusListener(focusListener);
		variableValue.addModifyListener(textModifyListener);
		
		new Label(group, SWT.NONE).setText("Modified Info:");		
		variableModifiedInfo = new Combo(group, SWT.READ_ONLY);
		variableModifiedInfo.add(".*");
		for(ModifiedInfo info : ModifiedInfo.values()) {
			variableModifiedInfo.add(info.toString());
		}
		variableModifiedInfo.addModifyListener(comboModifyListener);
	}

	private String validateString(String string) {
		if(string.isEmpty())
			return null;
		return string;
	}
}
