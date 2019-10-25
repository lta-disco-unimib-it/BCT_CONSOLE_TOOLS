
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.inferencesEngines;

import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Composite for KBehavior Options
 * 
 *
 * @author Terragni Valerio
 *
 */
public class KBehaviorOptionsComposite extends FsaEngineOptionsComposite  {

	public Button cutoffSearchCheckButton;
	public Text maxTrustLenText;
	public Button stepSaveCheckButton;

	public KBehaviorOptionsComposite(Composite parent, int style) { // create component
		super(parent, style);

		Label maxTrustLenLabel = new Label(this,SWT.NULL);	 	
		maxTrustLenLabel.setText("Max trust Len : ");
		maxTrustLenText = new Text(this, SWT.BORDER);
		{
			GridData gd = new GridData();
			gd.grabExcessHorizontalSpace = true;
			gd.minimumWidth = 100;
			maxTrustLenText.setLayoutData(gd);
		}
		
		

		Label cutoffSearchLabel = new Label(this,SWT.NULL);
		cutoffSearchLabel.setText("Cut off search:");

		cutoffSearchCheckButton = new Button(this, SWT.CHECK);

		Label stepSaveLabel = new Label(this,SWT.NULL);
		stepSaveLabel.setText("Save at each step:");

		stepSaveCheckButton = new Button(this, SWT.CHECK);

	}
	/**
	 * fill the composite fields with inferenceEnginesOptions Properties
	 * 
	 * @param inferenceEnginesOptions
	 */

	public void load( Properties invariantGeneratorOptions ){

		super.load(invariantGeneratorOptions);


		if (invariantGeneratorOptions.get("cutOffSearch").equals("true")){
			cutoffSearchCheckButton.setSelection(true);
		}else{
			cutoffSearchCheckButton.setSelection(false);
		}

		maxTrustLenText.setText((String)invariantGeneratorOptions.get("maxTrustLen"));

		if ( "true".equals(invariantGeneratorOptions.get("stepSave"))){
			stepSaveCheckButton.setSelection(true);
		}else{
			stepSaveCheckButton.setSelection(false);
		}

	}

	/**
	 * This method returns a Properties object containing the invariant generator options as set by the user
	 * 
	
	 * @return properties defined
	 */
	@Override
	public Properties getUserDefinedOptions() {
		Properties res = super.getUserDefinedOptions();

		res.setProperty("maxTrustLen", maxTrustLenText.getText());

		res.setProperty("cutOffSearch", cutoffSearchCheckButton.getSelection() ? "true" : "false" );
		res.setProperty("stepSave", stepSaveCheckButton.getSelection() ? "true" : "false" );

		return res;
	}

}
