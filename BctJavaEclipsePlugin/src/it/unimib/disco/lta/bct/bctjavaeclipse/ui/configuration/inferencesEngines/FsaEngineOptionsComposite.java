package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.inferencesEngines;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.DigitVerifyListener;

import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * this is the composite for Fsa Engine Options
 * 
 * @author Terragni Valerio
 *
 */
public class FsaEngineOptionsComposite extends Composite{
	private Properties loadedOptions;
	public Combo loggerCombo;
	public Combo levelCombo;
	public Combo enableMinimizationCombo;
	public Text minTrustLenText;
	public Text logFileText;
	public Button logFileButton;
	public Label logfileLabel;
	
	public FsaEngineOptionsComposite(Composite parent, int style) { // create content
		super(parent, style);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		this.setLayout(layout);		
		
		Label loggerComboLabel = new Label(this,SWT.NULL);
		loggerComboLabel.setText("Logger : ");

		loggerCombo = new Combo(this, SWT.READ_ONLY);
		loggerCombo.add("console");
		loggerCombo.add("file");

		loggerCombo.addSelectionListener(new SelectionAdapter() {//click button for open fileDialog
			public void widgetSelected(SelectionEvent event) {
				controlFileConsole();

			}
		});
		
		{
			GridData gd = new GridData();
			gd.grabExcessHorizontalSpace = true;
			gd.minimumWidth = 100;
			loggerCombo.setLayoutData(gd);
		}

		Label levelComboLabel = new Label(this,SWT.NULL);
		levelComboLabel.setText("Verbosity level :");


		levelCombo = new Combo(this, SWT.READ_ONLY);

		String[] levelLabel = new String[]{"0", "1", "2", "3","4"};   
		for(int i=0; i<levelLabel.length; i++)
			levelCombo.add(levelLabel[i]);  

		{
			GridData gd = new GridData();
			gd.grabExcessHorizontalSpace = true;
			gd.minimumWidth = 100;
			levelCombo.setLayoutData(gd);
		}
		
		//
		//Log file selection widgets
		//
		
		logfileLabel = new Label(this,SWT.NULL);
		logfileLabel.setText("Logfile :");
		
		Composite logFileComposite = new Composite(this,SWT.NONE);
		{
			GridData gd = new GridData();
			gd.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
		}
		RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
		logFileComposite.setLayout(rowLayout);
		rowLayout.marginBottom = 0;
		rowLayout.marginLeft = 0;
		rowLayout.marginRight = 0;
		rowLayout.marginWidth = 0;
		rowLayout.marginHeight = 0;
		rowLayout.marginTop = 0;
		
		
		logFileText = new Text(logFileComposite, SWT.BORDER);
		RowData ld = new RowData();
		ld.width = 200;
		logFileText.setLayoutData(ld);

		logFileButton = new Button(logFileComposite,SWT.NULL);
		logFileButton.setText("Browse");
		
		logFileButton.addSelectionListener(new SelectionAdapter() {//click button for open fileDialog
			public void widgetSelected(SelectionEvent event) {
				Shell shell = new Shell();
				FileDialog fileDialog = new FileDialog(shell);
				String dir = fileDialog.open();
				if(dir != null) {
					logFileText.setText(dir);
				}
			}
		}); 
		
		
		Label enableMinimizationLabel = new Label(this,SWT.NULL);	 	
		enableMinimizationLabel.setText("Minimization : ");  

		enableMinimizationCombo = new Combo(this, SWT.READ_ONLY);
		enableMinimizationCombo.add("none");
		enableMinimizationCombo.add("end");
		enableMinimizationCombo.add("step");
		{
			GridData gd = new GridData();
			gd.grabExcessHorizontalSpace = true;
			gd.minimumWidth = 100;
			enableMinimizationCombo.setLayoutData(gd);
		}
		

		Label minTrustLenLabel = new Label(this,SWT.NULL);	 	
		minTrustLenLabel.setText("Min Trust Len : ");  

		minTrustLenText = new Text(this, SWT.BORDER);
		{
			GridData gd = new GridData();
			gd.grabExcessHorizontalSpace = true;
			gd.minimumWidth = 100;
			minTrustLenText.setLayoutData(gd);
		}
		
		minTrustLenText.addVerifyListener(new DigitVerifyListener());
		

		
		this.pack(); 
	}
	/**
	 * fill the composite field with inferenceEnginesOptions Properties
	 * 
	 * @param inferenceEnginesOptions
	 */
	public void load( Properties inferenceEnginesOptions ){
		loadedOptions = inferenceEnginesOptions;

		String logger = (String) loadedOptions.get("logger");
		if ( logger != null ){
		loggerCombo.select(loggerCombo.indexOf(logger));
		controlFileConsole();
		}
		String level = (String) loadedOptions.get("level");
		if ( level != null ){
		levelCombo.select(levelCombo.indexOf(level));
		}
		String enableMinimization = (String) loadedOptions.get("enableMinimization");
		if ( enableMinimization != null ){
		enableMinimizationCombo.select(enableMinimizationCombo.indexOf(enableMinimization));		
		}
		minTrustLenText.setText((String)loadedOptions.get("minTrustLen"));


	}
/** 
 * if loggerCombo select is "file", the field for the path must be enabled
 * 
 */
	private void controlFileConsole() {
		if(loggerCombo.getSelectionIndex()>-1 && loggerCombo.getItem(loggerCombo.getSelectionIndex()).equals("file")){
			logFileText.setEnabled(true);	
			logFileButton.setEnabled(true);
			logfileLabel.setEnabled(true);
		}else
		{   
			logFileText.setText("");
			logFileText.setEnabled(false);
			logFileButton.setEnabled(false);
			logfileLabel.setEnabled(false);
		}
	}

	/**
	 * This method returns a Properties object containing the invariant generator options as set by the user
	 * 
	 * @return properties defined
	 */
	public Properties getUserDefinedOptions() {
		Properties p = new Properties();
		if ( loadedOptions != null ){
			p.putAll(loadedOptions);
		}
		//here set the options with the values selected by the user
		p.setProperty( "logger", loggerCombo.getItem(loggerCombo.getSelectionIndex()) );
		p.setProperty( "level", levelCombo.getItem(levelCombo.getSelectionIndex()));
		p.setProperty( "enableMinimization", enableMinimizationCombo.getItem(enableMinimizationCombo.getSelectionIndex()));
		p.setProperty( "minTrustLen",minTrustLenText.getText());


		return p;
	}
}