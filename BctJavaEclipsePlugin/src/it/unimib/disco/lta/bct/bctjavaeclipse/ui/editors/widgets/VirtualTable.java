package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIBuilderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorOpener;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.DomainObjectsLabelProvider;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class VirtualTable<T> extends Composite {

	private Table table;
	private TableColumn[] columns;
	private List<T> entities;
	private MonitoringConfiguration mc;

	public VirtualTable(Composite parent, String[] headers) {
		super(parent, SWT.NONE);
		createTable(this);
		createColumns(headers);
		this.setLayout(new FillLayout());
	}

	@Override
	public boolean setFocus() {
		return table.setFocus();
	}
	
	public void setContent(List<T> entities) {
		this.entities = entities;
		table.setItemCount(entities.size());
		table.setData(entities);
	}
	
	private void createTable(Composite parent) {
		table = new Table(parent, SWT.VIRTUAL | SWT.MULTI | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setToolTipText("Double click to open");
		
		table.addListener(SWT.SetData, new Listener() {
			DomainObjectsLabelProvider labelProvider = new DomainObjectsLabelProvider(mc);
			
			public void handleEvent(Event event) {
				TableItem item = (TableItem) event.item;
				int index = table.indexOf(item);
				T entity = entities.get(index);
				item.setText(0, labelProvider.getText(entity));
				packColumns();
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Table t = (Table) e.getSource();
				final TableItem item = t.getItem(new Point(e.x, e.y));

				if(item != null) {
					int index = getItemIndex(table.getItems(), item);
					T entity = entities.get(index);
					URI uri = URIBuilderFactory.getBuilder(mc).buildURI(entity);
					EditorOpener.openEditor(table.getDisplay(), uri);
				}
			}
			
			private int getItemIndex(TableItem[] items, TableItem item) {
				for (int i = 0; i < items.length; i++) {
					if(items[i].equals(item))
						return i;
				}
				throw new IndexOutOfBoundsException();
			}
		});
	}

	private void createColumns(String[] headers) {
		columns = new TableColumn[headers.length];
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TableColumn(table, SWT.NONE);
			columns[i].setText(headers[i]);
		}
	}

	private void packColumns() {
		for (TableColumn column : columns) {
			column.pack();
		}
	}

	public void setMonitoringConfiguration(MonitoringConfiguration mc) {
		this.mc = mc;
	}

	
	/**
	 * Select all table items that match given regular expression.
	 * @param regex regular expression
	 */
	public void find(String regex) {
		List<TableItem> selection = new ArrayList<TableItem>();
		System.out.println("FINDING ");
		for(TableItem item : table.getItems()) {
			System.out.println("FINDING "+item.getText());
			if(item.getText().matches(regex)) {
				selection.add(item);
			}
		}
		table.setSelection(selection.toArray(new TableItem[0]));
	}
}
