package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.flattener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public abstract class OptionsTableRow<T extends OptionsTableRowContent> {
	
	private TableItem item;
	protected OptionsTable parentTable;
	private int cellNumber;

	public class TextCellModifyListener implements ModifyListener {

		private int cellNumber;
		private Text text;

		public TextCellModifyListener(int cellNumber, Text text){
			this.cellNumber = cellNumber;
			this.text = text;
		}
		
		public void modifyText(ModifyEvent e) {
			cellModified(cellNumber,text.getText());
		}
		
	}

	public class ComboCellModifyListener implements ModifyListener {

		private int cellNumber;
		private CCombo combo;

		public ComboCellModifyListener(int cellNumber, CCombo combo){
			this.cellNumber = cellNumber;
			this.combo = combo;
		}
		
		public void modifyText(ModifyEvent e) {
			cellModified(cellNumber,combo.getText());
		}
		
	}

	
	public class OptionsTableTraverseListener implements TraverseListener {
		public void keyTraversed(TraverseEvent e) {
			System.out.println("EventT :"+e.keyCode);
			if ( e.keyCode == org.eclipse.swt.SWT.TRAVERSE_TAB_NEXT ){
				System.out.println("EventT next:"+e.keyCode);
			} else if ( e.keyCode == org.eclipse.swt.SWT.TRAVERSE_TAB_PREVIOUS ){
				System.out.println("EventT prev:"+e.keyCode);
			}
			
		}
	}
	
	public OptionsTableRow( OptionsTable parent ){
		this.parentTable = parent;

		Table table = parentTable.getTable();
		item = new TableItem(table, SWT.NONE);	
	}
	
	public abstract void setContent(T content);
	
	public abstract void cellModified(int cellNumber,String newValue);
	
	public abstract boolean isEmpty();
	
	public abstract T getContent();
	
	public void addCell(Text text) {
		
		TableEditor classesToIgnoreEditor=new TableEditor(parentTable.getTable());
	    
	    text.addModifyListener(new TextCellModifyListener(cellNumber,text));
	    //text.addTraverseListener(new OptionsTableTraverseListener());
	    
	    classesToIgnoreEditor.grabHorizontal = true;
        classesToIgnoreEditor.setEditor(text,item,0);
        text.setEditable(true);
        
        cellNumber++;
	}
	
	public void addCell(CCombo combo) {
		
		TableEditor classesToIgnoreEditor=new TableEditor(parentTable.getTable());
	    
	    combo.addModifyListener(new ComboCellModifyListener(cellNumber,combo));
	    //text.addTraverseListener(new OptionsTableTraverseListener());
	    
	    classesToIgnoreEditor.grabHorizontal = true;
        classesToIgnoreEditor.setEditor(combo,item,0);
        combo.setEditable(true);
        
        cellNumber++;
	}

//	public void setItemText(int cellNumber,String content){
//		System.out.println("Setting text for cell "+cellNumber+" "+content);
//		item.setText(cellNumber, content);
//		parentTable.update();
//	}
	
}
