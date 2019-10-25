package it.unimib.disco.lta.bct.bctjavaeclipse.ui.ava.configuration;

import java.awt.Window;

import it.unimib.disco.lta.ava.engine.configuration.AvaConfiguration;
import it.unimib.disco.lta.ava.engine.configuration.AvaConfigurationFactory;
import it.unimib.disco.lta.ava.engine.configuration.ThresholdType;
import it.unimib.disco.lta.ava.graphs.TransitionDirection;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AvaSettingsDialog  {

	private Text stepsToPerform;
	private Shell shell;
	private Text basicPatternTText;
	private Text compositePatternTText;
	private Text interPatternTText;
	private Text maxBasicInterpretationsText;
	private Text maxCompositeInterpretationsText;
	private Text maxInterInterpretationsText;
	private Text expectedSubSequencesLimitText;
	private Text expectedSequencesLimitText;
	private AvaConfiguration avaConfiguration;
	private Text stepsToPerformBackward;
	private Text maxPatternInterpretationsText;

	
	protected Control createContents(Composite parent){
		Composite content = new Composite(parent,SWT.BORDER);
		GridLayout layout = new GridLayout(2,true);
		
		content.setLayout(layout);
		
		Label l = new Label(content,SWT.NONE);
		l.setText("Steps to perform forward: ");
		
		stepsToPerform = new Text(content,SWT.SINGLE);
		stepsToPerform.setText("3");
		stepsToPerform.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		stepsToPerform.setTextLimit(20);
		
		l = new Label(content,SWT.NONE);
		l.setText("Steps to perform backward: ");
		
		stepsToPerformBackward = new Text(content,SWT.SINGLE);
		stepsToPerformBackward.setText("3");
		stepsToPerformBackward.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		l = new Label(content,SWT.NONE);
		l.setText("Max interpretations for each pattern: ");
		
		maxPatternInterpretationsText = new Text(content,SWT.SINGLE);
		maxPatternInterpretationsText.setText("-1");
		maxPatternInterpretationsText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		l = new Label(content,SWT.NONE);
		l.setText("Max basic interpretations: ");
		
		maxBasicInterpretationsText = new Text(content,SWT.SINGLE);
		maxBasicInterpretationsText.setText("-1");
		maxBasicInterpretationsText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		l = new Label(content,SWT.NONE);
		l.setText("Max composite interpretations: ");
		
		maxCompositeInterpretationsText = new Text(content,SWT.SINGLE);
		maxCompositeInterpretationsText.setText("-1");
		maxCompositeInterpretationsText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		l = new Label(content,SWT.NONE);
		l.setText("Max inter-component interpretations: ");
		
		maxInterInterpretationsText = new Text(content,SWT.SINGLE);
		maxInterInterpretationsText.setText("-1");
		maxInterInterpretationsText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		
		l = new Label(content,SWT.NONE);
		l.setText("Basic pattern threshold: ");
		
		basicPatternTText = new Text(content,SWT.SINGLE);
		basicPatternTText.setText("0.5");
		basicPatternTText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		
		l = new Label(content,SWT.NONE);
		l.setText("Composite pattern threshold: ");
		
		compositePatternTText = new Text(content,SWT.SINGLE);
		compositePatternTText.setText("0.5");
		compositePatternTText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		l = new Label(content,SWT.NONE);
		l.setText("Inter Component pattern threshold: ");
		
		interPatternTText = new Text(content,SWT.SINGLE);
		interPatternTText.setText("0.5");
		interPatternTText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		l = new Label(content,SWT.NONE);
		l.setText("Max Expected Sequences");
		
		expectedSequencesLimitText = new Text(content,SWT.SINGLE);
		expectedSequencesLimitText.setText("10");
		expectedSequencesLimitText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		l = new Label(content,SWT.NONE);
		l.setText("Max Expected SubSequences");
		
		expectedSubSequencesLimitText = new Text(content,SWT.SINGLE);
		expectedSubSequencesLimitText.setText("10");
		expectedSubSequencesLimitText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		
		
		Button ok= new Button(content, SWT.PUSH);  
		ok.setText("&OK");
		ok.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				createAvaConfiguration();
				shell.close();
			}
			
		});
		
		Button cancel = new Button(content, SWT.PUSH);  
		cancel.setText("&Cancel");
		cancel.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}			
		});

		content.pack();
		return content;
	}
	
	public Text getMaxPatternInterpretationsText() {
		return maxPatternInterpretationsText;
	}

	public void setMaxPatternInterpretationsText(Text maxPatternInterpretationsText) {
		this.maxPatternInterpretationsText = maxPatternInterpretationsText;
	}

	public AvaConfiguration open(){
		shell.open();
		runEventLoop(shell);
		
		return avaConfiguration;
	}
	
	private void runEventLoop(Shell loopShell) {

		//Use the display provided by the shell if possible
		Display display;
		if (shell == null) {
			display = Display.getCurrent();
		} else {
			display = loopShell.getDisplay();
		}

		while (loopShell != null && !loopShell.isDisposed()) {
			try {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			} catch (Throwable e) {
				
			}
		}
		display.update();
	}
	
	

	public AvaSettingsDialog(Shell parentShell) {
		shell = new Shell(parentShell.getDisplay(),SWT.DIALOG_TRIM);
		createContents(shell);
		shell.pack();
		//shell.pack();
		// TODO Auto-generated constructor stub
	}

	
	public void createAvaConfiguration(){
		
		AvaConfiguration conf = AvaConfigurationFactory.createDefaultAvaConfiguration(3);
		
		conf.setStepsToPerform(TransitionDirection.forward,Integer.valueOf(stepsToPerform.getText()));
		conf.setStepsToPerform(TransitionDirection.backward,Integer.valueOf(stepsToPerformBackward.getText()));
		
		
		conf.setThreshold(ThresholdType.singlePatternScore, Double.valueOf(basicPatternTText.getText()).floatValue());
		conf.setThreshold(ThresholdType.compositePatternScore, Double.valueOf(compositePatternTText.getText()).floatValue());
		conf.setThreshold(ThresholdType.interComponentPatternScore, Double.valueOf(interPatternTText.getText()).floatValue());
		
		conf.setBasicInterpretationsForViolationLimit(Integer.valueOf(maxBasicInterpretationsText.getText()));
		conf.setCompositeInterpretationsForViolationLimit(Integer.valueOf(maxCompositeInterpretationsText.getText()));
		conf.setInterComponentInterpretationsForViolationLimit(Integer.valueOf(maxInterInterpretationsText.getText()));
		
		conf.setExpectedSequencesLimit(Integer.valueOf(expectedSequencesLimitText.getText()));
		conf.setExpectedSubSequencesLimit(Integer.valueOf(expectedSubSequencesLimitText.getText()));
		
		conf.setMaxInterpretationsPerPattern(Integer.valueOf(maxPatternInterpretationsText.getText()));
		
		avaConfiguration = conf; 
	}

	public AvaConfiguration getAvaConfiguration() {
		return avaConfiguration;
	}
	
	public void loadAvaConfiguration( AvaConfiguration conf ){
		stepsToPerform.setText(String.valueOf(conf.getStepsToPerform(TransitionDirection.forward)));
		stepsToPerformBackward.setText(String.valueOf(conf.getStepsToPerform(TransitionDirection.forward)));
		
		basicPatternTText.setText(String.valueOf(conf.getThreshold(ThresholdType.singlePatternScore)));
		compositePatternTText.setText(String.valueOf(conf.getThreshold(ThresholdType.compositePatternScore)));
		interPatternTText.setText(String.valueOf(conf.getThreshold(ThresholdType.interComponentPatternScore)));
		
		maxBasicInterpretationsText.setText(String.valueOf(conf.getBasicInterpretationsForViolationLimit()));
		maxCompositeInterpretationsText.setText(String.valueOf(conf.getCompositeInterpretationsForViolationLimit()));
		maxInterInterpretationsText.setText(String.valueOf(conf.getInterComponentInterpretationsForViolationLimit()));
		
		expectedSequencesLimitText.setText(String.valueOf(conf.getExpectedSequencesLimit()));
		expectedSubSequencesLimitText.setText(String.valueOf(conf.getExpectedSubSequencesLimit()));
		
		maxPatternInterpretationsText.setText(String.valueOf(conf.getMaxInterpretationsPerPattern()));
	}

}
