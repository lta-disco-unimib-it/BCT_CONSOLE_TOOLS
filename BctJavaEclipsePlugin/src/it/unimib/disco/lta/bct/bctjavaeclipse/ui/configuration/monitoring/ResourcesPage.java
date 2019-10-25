package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring;


import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.DBStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.JavaProjectValidator;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.ProjectValidator;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.SelectionDialog;

public class ResourcesPage extends Composite implements BCTObservable, ModifyListener {

	Button[] button= new Button[2];
	Text resourceName;
	Button fileButton;
	Button dbButton;
	private Text dataDirFileText;
	Text uriText;
	Text userText;
	Text passwordText;
	
	private BCTObservaleIncapsulated observer;
	private Text prjText;
	private boolean automaticallyUpdateDataFolders = false;
	private boolean onlyJavaProjectsAllowed = true;
	
	public boolean areOnlyJavaProjectsAllowed() {
		return onlyJavaProjectsAllowed;
	}

	public void setOnlyJavaProjectsAllowed(boolean onlyJavaProjectsAllowed) {
		this.onlyJavaProjectsAllowed = onlyJavaProjectsAllowed;
	}

	public ResourcesPage(Composite parent)
	{
		
		super(parent,SWT.NONE);
		
		this.observer = new BCTObservaleIncapsulated(this);
		
		GridLayout gl = new GridLayout();
		this.setLayout(gl);
		
		
		Group resourceNameGroup=new Group(this, SWT.NONE);
		resourceNameGroup.setBounds(5, 5, 440, 100);
		resourceNameGroup.setText("Monitoring Configuration");
		
		Label ResourcesNameLabel=new Label(resourceNameGroup, SWT.NULL);
		ResourcesNameLabel.setText("Name:");	
		ResourcesNameLabel.setBounds(10, 20, 75, 30);
	    resourceName = new Text(resourceNameGroup, SWT.SINGLE | SWT.BORDER);
	    resourceName.setBounds(90, 20, 250, 20);
	    resourceName.addModifyListener(this);
	    resourceName.setToolTipText("Name of the Monitoring Configuration.");
	    
	    
	    Label prjLabel=new Label(resourceNameGroup, SWT.NULL);
        prjLabel.setText("Project:");
        prjLabel.setBounds(10, 50, 75, 20);
		prjText = new Text(resourceNameGroup, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		prjText.setEditable(false);
		prjText.setBounds(90, 50, 250, 20);
		prjText.setToolTipText("The name of the java project referenced by this Monitoring Configuration. " +
				"This is not mandatory. A monitoring configuration can be used to monitor/instrument binary code (e.g. java classes, folders and jars)" +
				" belonging to different projects." +
				"The project indicated in this text area is used to automatically perform configuration activities.");
		
		Button button = new Button(resourceNameGroup, SWT.NONE);
		button.setText("Select...");
		button.setBounds(350, 50, 100, 20);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prjBrowse();
			}

		});

	    
	    
        Group fileResource=new Group(this, SWT.NONE);
        fileResource.setBounds(5, 120, 440, 100);
        fileResource.setText("File");
              
        fileButton=new Button(fileResource,SWT.RADIO);
        fileButton.setText("File");
        fileButton.setSelection(true);
        fileButton.setBounds(5,20,60, 25);
        fileButton.setToolTipText("Select this if you want to store BCT data on filesystem.");
        
        Label dataDirFileLabel=new Label(fileResource, SWT.NULL);
        dataDirFileLabel.setText("Data Dir: ");
        dataDirFileLabel.setBounds(5, 50, 75, 20);
		dataDirFileText = new Text(fileResource, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		dataDirFileText.setEditable(false);
		dataDirFileText.setBounds(90, 50, 250, 20);
		dataDirFileText.setToolTipText(ConfigurationMessages.m1);
		
		
		button = new Button(fileResource, SWT.NONE);
		button.setText("Browse...");
		button.setBounds(350, 50, 100, 20);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});

		
		
//
//
		
	    Group dbResources=new Group(this, SWT.NONE);
	    dbResources.setBounds(5, 240, 440, 150);
	    dbResources.setText("DB");
	    dbResources.setEnabled(true);
	    
	    dbButton=new Button(dbResources,SWT.RADIO);
	    dbButton.setText("DB");
	    dbButton.setSelection(false);
	    dbButton.setBounds(5,20,60, 25);
	    dbButton.setToolTipText("Select this if you want to store BCT data on database.");
		    
		Label uriLabel=new Label(dbResources, SWT.NULL);
	    uriLabel.setText("URI: ");
	    uriLabel.setBounds(5, 50, 75, 20);
		uriText= new Text(dbResources, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		uriText.setEditable(false);
		uriText.setBounds(90, 50, 250, 20);
		uriText.addModifyListener(this);
		uriText.setToolTipText("URI of the database to save BCT data to.");
		
		Label userLabel=new Label(dbResources, SWT.NULL);
		userLabel.setText("User: ");
		userLabel.setBounds(5, 80, 75, 20);
		userText = new Text(dbResources, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		userText.setEditable(false);
		userText.setBounds(90, 80, 250, 20);
		userText.addModifyListener(this);
		userText.setToolTipText("Username to connect to the DB.");
		
		Label passwordLabel=new Label(dbResources, SWT.NULL);
		passwordLabel.setText("Password: ");
		passwordLabel.setBounds(5, 110, 75, 20);
		passwordText = new Text(dbResources, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		passwordText.setEditable(false);
		passwordText.setBounds(90, 110, 250, 20);
		passwordText.addModifyListener(this);
		passwordText.setToolTipText("DB password");
		
        //FileButtonListener a =new FileButtonListener(fileButton,dbButton,passwordText,userText,uriText,dataDirFileText);
        fileButton.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				widgetFileButtonSelected();
			}
        });
        
        DbButtonListener b =new DbButtonListener(fileButton,dbButton,passwordText,userText,uriText,dataDirFileText);
        dbButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				widgetDbButtonSelected();
			}
        	
        });
        
        fileButton.setSelection(true);
	}

	private void widgetDbButtonSelected() {
		
  		fileButton.setSelection(false);
  		dbButton.setSelection(true);
  		
  		dataDirFileText.setEditable(false);
  		passwordText.setEditable(true);
		uriText.setEditable(true);
		userText.setEditable(true);
		
		observer.notifyBCTObservers("");
	}
	
	private void widgetFileButtonSelected() {
  		fileButton.setSelection(true);
  		dbButton.setSelection(false);
  		
  		dataDirFileText.setEditable(true);
  		passwordText.setEditable(false);
		uriText.setEditable(false);
		userText.setEditable(false);
		
		observer.notifyBCTObservers("");
	}
	
	
	private void handleBrowse() {
		
		//FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
		SelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), true, 
				"Select new file container");

		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				dataDirFileText.setText(((Path) result[0]).toString());
			}
		}
		observer.notifyBCTObservers(null);
	}
	
	
	public String getResourceName() {
		return resourceName.getText();
	}

	public void setResourceName(String resourceName) {
		this.resourceName.setText( resourceName );
	}

	public String getDataDirFile() {
		return dataDirFileText.getText();
	}

	public void setDataDir(String dest) {
		this.dataDirFileText.setText(dest);
	}

	public String getPassword() {
		return passwordText.getText();
	}

	public void setPasswordText(Text passwordText) {
		this.passwordText = passwordText;
	}

	public String getUriText() {
		return uriText.getText();
	}

	public void setUriText(Text uriText) {
		this.uriText = uriText;
	}

	public String getUser() {
		return userText.getText();
	}

	public void setUserText(Text userText) {
		this.userText = userText;
	}
	
	public boolean dbButtonSelected(){
		return dbButton.getSelection();
	}
	
	public boolean fileButtonSelected(){
		return fileButton.getSelection();
	}

	public boolean ready(){
		if(dbButton.getSelection()==true)
		{
			return !( uriText.getText().length() == 0  || userText.getText().length() == 0 || passwordText.getText().length() == 0 );
		}
		if(fileButton.getSelection()==true)
		{
			return ! ( dataDirFileText.getText().length() == 0);
		}
		return false;
	}
	
	
	public StorageConfiguration createStorageConfiguration() {
		
		StorageConfiguration resource = null;
		if(dbButton.getSelection()==true)
		{
			resource=new DBStorageConfiguration(uriText.getText(),userText.getText(),passwordText.getText());
		}
		if(fileButton.getSelection()==true)
		{
			resource=new FileStorageConfiguration(dataDirFileText.getText());
		}


		return resource;
	}
	
	public void loadStorageConfiguration(StorageConfiguration sc){
		if (sc instanceof DBStorageConfiguration ){
			DBStorageConfiguration dbsc = (DBStorageConfiguration) sc;
			uriText.setText(dbsc.getUri());
			passwordText.setText(dbsc.getPassword());
			userText.setText(dbsc.getUser());
			
			dbButton.setSelection(true);
			
			uriText.setEditable(true);
			userText.setEditable(true);
			passwordText.setEditable(true);
		} else if (sc instanceof FileStorageConfiguration) {
			FileStorageConfiguration fsc = (FileStorageConfiguration) sc;
			dataDirFileText.setText(fsc.getDataDirPath());
			fileButton.setSelection(true);
		}
	}
	
	
	public void addBCTObserver(BCTObserver l) {
		observer.addBCTObserver(l);
	}

	protected void notifyBCTObservers(Object message) {
		observer.notifyBCTObservers(message);
	}


	public void modifyText(ModifyEvent e) {
		observer.notifyBCTObservers(null);
	}


	private void prjBrowse() {
		
		//FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), true, 
				"Select new file container");
		if ( onlyJavaProjectsAllowed ){
		dialog.setValidator(new JavaProjectValidator());
		} else {
			dialog.setValidator(new AllProjectValidator());
		}
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				prjText.setText(((Path) result[0]).toString());
				updateStoringFolders();
			}
		}
		observer.notifyBCTObservers(null);
		
	}

	private void updateStoringFolders() {
		if ( automaticallyUpdateDataFolders ){
			String resourceNameString = resourceName.getText();
			String projectNameString = prjText.getText();
			if ( projectNameString.length() > 0 && resourceNameString.length() > 0 ){
				this.dataDirFileText.setText(projectNameString+"/"+dataDirFileText.getText());
				
			}
		}
	}

	public boolean isAutomaticallyUpdateDataFolders() {
		return automaticallyUpdateDataFolders;
	}

	public void setAutomaticallyUpdateDataFolders(
			boolean automaticallyUpdateStoringFolders) {
		this.automaticallyUpdateDataFolders = automaticallyUpdateStoringFolders;
	}

	public String getReferredProjectName() {
		if ( prjText.getText().isEmpty() ){
			return null;
		}
		return prjText.getText();
	}

	public void setReferredProjectName(String referredProjectName) {
		if ( referredProjectName == null ){
			return;
		}
		prjText.setText(referredProjectName);
	}
	
}
