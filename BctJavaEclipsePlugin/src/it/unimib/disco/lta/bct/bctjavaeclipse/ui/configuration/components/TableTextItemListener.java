package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class TableTextItemListener implements Listener {

	private Text text;
	private TableItem item;
	private int column;
	private CCombo combo;
	private String rule;
	
	/**
	 * 
	 * @param text
	 * @param item
	 */
	public TableTextItemListener(Text text, TableItem item,int column)
	{
		this.text=text;
		this.item=item;
		this.column=column;
	}
	public TableTextItemListener(CCombo combo, TableItem item,int column)
	{
		this.combo=combo;
		this.item=item;
		this.column=column;

	}
	
	public void handleEvent(Event event) {

		//System.out.println("Item "+text.getText());

			//set items text, it is necessary in order to retrieve the filled values
//		if(column!=3)
//		{
//			item.setText(column,text.getText());
//		}
//		else
//		{
//			item.setText(column,combo.getText());
//		}	
	}

}
