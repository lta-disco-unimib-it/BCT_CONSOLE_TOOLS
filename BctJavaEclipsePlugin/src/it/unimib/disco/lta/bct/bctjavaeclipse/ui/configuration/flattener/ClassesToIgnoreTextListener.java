package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.flattener;


import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.flattener.ObjectFlattenerPage.TableRow;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class ClassesToIgnoreTextListener implements Listener {

	

	private TableRow tableRow;
	private Text classToIgnoreText;

	public ClassesToIgnoreTextListener(TableRow row, Text classToIgnoreText) {
		this.tableRow = row;
		this.classToIgnoreText = classToIgnoreText;
	}

	public void handleEvent(Event event) {
		
		//tableRow.setContent(classToIgnoreText.getText());
		
	}

}
