package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration;

import org.eclipse.swt.widgets.Table;

public class GUIComponent{

	String text;
	Table table;
	String componentName;
	
	public GUIComponent(String text, Table table) {
		this.text=text;
		this.table=table;
	}
	
	public String getNameC()
	{
		componentName=text;
		return componentName;
	}
	
	public String getPackage(int i)
	{
		
		return table.getItem(i).getText(0);
	}
	
	
}
