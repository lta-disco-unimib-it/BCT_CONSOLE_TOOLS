package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import java.util.Comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class ProcessActionsTestComposite extends Composite {

	private static class PATComparator implements Comparator<String> {
		boolean reverse = true;
		
		public int compare(String o1, String o2) {
			int res = o1.compareTo(o2);
			if ( reverse ){
				return - res;
			}
			return res;
		}
			
		
		
		public void reverse(){
			boolean reverse = true;
		}
	}
	private PATComparator pcomparator = new PATComparator();
	private PATComparator acomparator = new PATComparator();
	private PATComparator tcomparator = new PATComparator();
	private SashForm sash;
	private Table processTable;

	public ProcessActionsTestComposite(Composite parent, int style) {
		super(parent, style);
		
		
		sash = new SashForm(this, SWT.VERTICAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		createProcessContent();
	}

	private void createProcessContent() {
	    processTable = new Table(sash, SWT.NONE);
	    processTable.setHeaderVisible(true);
	    processTable.setLinesVisible(true);

	    // Create each of the columns, adding an event
	    // listener that will set the appropriate fields
	    // into the comparator and then call the fillTable
	    // helper method
	    TableColumn[] columns = new TableColumn[3];
	    columns[0] = new TableColumn(processTable , SWT.NONE);
	    columns[0].setText("");
//	    columns[0].addSelectionListener(new SelectionAdapter() {
//	      public void widgetSelected(SelectionEvent event) {
//	        
//	        pcomparator.reverse();
//	        fillTable(table);
//	      }
//	    });

	    columns[1] = new TableColumn(processTable, SWT.NONE);
	    columns[1].setText("Process");
	    columns[1].addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        //comparator.setColumn(PlayerComparator.LAST_NAME);
	        pcomparator.reverse();
	        fillTable(processTable);
	      }
	    });
	}

	
	  private void fillTable(Table table) {
		    // Turn off drawing to avoid flicker
		    table.setRedraw(false);

		    // We remove all the table entries, sort our
		    // rows, then add the entries
		    table.removeAll();
//		    Collections.sort(players, comparator);
//		    for (Iterator itr = players.iterator(); itr.hasNext();) {
//		      Player player = (Player) itr.next();
//		      TableItem item = new TableItem(table, SWT.NONE);
//		      int c = 0;
//		      item.setText(c++, player.getFirstName());
//		      item.setText(c++, player.getLastName());
//		      item.setText(c++, String.valueOf(player.getBattingAverage()));
//		    }
	  }
}
