package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ActionsMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.ComponentsDefinitionComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentManagerException;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import util.componentsDeclaration.Component;

public class ActionsMonitoringPage extends Composite implements BCTObservable, BCTObserver {


	private ComponentsDefinitionComposite componentsComposite;
	private Button monitorActionsButton;
	private Group configurationBox;

	public ActionsMonitoringPage(Composite _parent, int swt) {
		super(_parent,swt);
		

		GridLayout l = new GridLayout();
        l.numColumns = 2;
        this.setLayout(l);
        
		Label smashAggregationLabel=new Label(this, SWT.NULL);
		smashAggregationLabel.setText("Monitor actions");
		
		
		monitorActionsButton = new Button(this,SWT.CHECK);
		
		
		monitorActionsButton.setToolTipText("Check thix box if BCT should consider " +
				"certain methods as indicators of user actions." +
				"\nThis is useful to filter failures on the basis of user actions." +
				"\nIn a MVC architecture controller methods cold be used as user actions indicator." +
				"\nExceptions thrown by action methods are considered failure indicators and are automatically tracked.");
		
		monitorActionsButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				observable.notifyBCTObservers(null);
				if ( monitorActionsButton.getSelection() ){
					configurationBox.setEnabled(true);
					componentsComposite.setEnabled(true);
				} else {
					configurationBox.setEnabled(false);
					componentsComposite.setEnabled(false);
				}
			}
			
		});
        
        
		configurationBox = new Group(this, SWT.BEGINNING);
		
		
		GridLayout cBLayout = new GridLayout();
	    cBLayout.numColumns = 2;
	    configurationBox.setLayout(cBLayout);
	    
		GridData cbLD = new GridData(GridData.FILL_HORIZONTAL);
		cbLD.horizontalSpan = 2;
		cbLD.grabExcessHorizontalSpace = true;
		configurationBox.setLayoutData(cbLD);
		
		configurationBox.setText("Actions definition");
	    
		configurationBox.setToolTipText("Use the actions definition toolbox below to specify the methods that identify user actions.");
	    
		componentsComposite = new ComponentsDefinitionComposite(configurationBox, SWT.NULL, "Actions:", "Action name:");
		
	    componentsComposite.addBCTObserver(this);
        componentsComposite.setToolTipText(
        		"Use the actions definition toolbox to specify the methods " +
        		"that identify user actions, e.g. controller methods.");
	    
        componentsComposite.setLayoutData(cbLD);
	    
	    configurationBox.setEnabled(false);
	    
	    //configurationBox.pack();
	}

	private BCTObservaleIncapsulated observable = new BCTObservaleIncapsulated(this);

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}



	public void load(ActionsMonitoringOptions monitoringOptions) {
		if ( monitoringOptions == null )
			return;
		
		if ( monitoringOptions.isMonitorActions() ){
			monitorActionsButton.setSelection(true);
			configurationBox.setEnabled(true);
		}
		
		 List<Component> actions = monitoringOptions.getActionsGroupsToMonitor();
		if ( actions != null ){
			try {
				componentsComposite.load(actions);
			} catch (ComponentManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ActionsMonitoringOptions createMonitoringOptions(){
		ActionsMonitoringOptions mo = new ActionsMonitoringOptions();
		
		//add actions/tests
		mo.setMonitorActions(monitorActionsButton.getSelection());
		
		List<Component> list = new ArrayList<Component>();
		list.addAll(componentsComposite.getComponents());
		mo.setActionsGroupsToMonitor(list);
		
		return mo;
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		observable.notifyBCTObservers(message);
	}
}
