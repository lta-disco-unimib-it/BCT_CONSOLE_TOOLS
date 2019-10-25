package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class SortedIndexTable {
	Table table;
	final TableColumn[] columns = new TableColumn[3];

	public SortedIndexTable(Composite parent, int style, String[] headers) {
		createTable(parent, style, headers);
	}

	private void createTable(final Composite parent, int style, String[] headers) {
		table = new Table(parent, style);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setToolTipText("Double click to open");

		Listener sortListener = new Listener() {
			public void handleEvent(Event e) {
				int dir = table.getSortDirection();
				int index = 0;
				TableItem[] items = table.getItems();
				TableColumn column = (TableColumn) e.widget;
				TableColumn sortColumn = table.getSortColumn();

				if (sortColumn == column) {
					dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
				} else {
					table.setSortColumn(column);
					dir = SWT.UP;
				}

				for (int i = 0; i < columns.length; i++) {
					if (column == columns[i]) {
						index = i;
					}
				}

				table.setRedraw(false);
				final int ind = index;
				final int direction = dir;
				Arrays.sort(items, new Comparator<TableItem>() {
					public int compare(TableItem arg0, TableItem arg1) {
						TableItem a = (TableItem) arg0;
						TableItem b = (TableItem) arg1;

						if (a.getText(ind).equals(b.getText(ind)))
							return 0;

						if (ind != 2) {
							if (direction == SWT.UP) {
								return a.getText(ind).compareTo(b.getText(ind));
							}
							return b.getText(ind).compareTo(a.getText(ind));
						} else {
							long la = Long.parseLong(a.getText(ind));
							long lb = Long.parseLong(b.getText(ind));
							if (direction == SWT.UP) {
								return la < lb ? -1 : 1;
							}
							return la < lb ? 1 : -1;
						}
					}
				});

				for (int i = 0; i < items.length; i++) {
					TableItem item = new TableItem(table, SWT.NONE, i);
					item.setText(getData(items[i]));
					item.setData(items[i].getData());
					items[i].dispose();
				}

				table.setRedraw(true);
				table.setSortDirection(dir);
			}
		};

		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TableColumn(table, SWT.NONE);
			columns[i].setText(headers[i]);
			columns[i].addListener(SWT.Selection, sortListener);
		}

		addMouseListener();
	}

	public Table getTable() {
		return table;
	}

	public void addElement(String element, String resource, String size, final IFile file) {
		addElement(element, resource, size, file, false);
		autoSizeColumns();
	}

	public void addElement(String element, String resource, String size, final IFile file, boolean highlight) {
		TableItem item = new TableItem(table, SWT.NONE);
		
		item.setText(0, element);
		item.setText(1, resource);
		item.setText(2, size);
		item.setData(file);
		if (highlight) {
			item.setBackground(new Color(table.getParent().getDisplay(), 255, 85, 0));
		}
	}

	private void addMouseListener() {
		table.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
				Table t = (Table) e.getSource();
				final TableItem item = t.getItem(new Point(e.x, e.y));

				if(item != null) {
					table.getParent().getShell().getDisplay().asyncExec(new Runnable() {
						public void run() {
							IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
							try {
								IDE.openEditor(page, (IFile) item.getData(), true);
							} catch (PartInitException e) {
							}
						}
					});
				}
			}

			public void mouseDown(MouseEvent e) {}

			public void mouseUp(MouseEvent e) {}

		});
	}

	private String[] getData(TableItem t) {
		int colCount = table.getColumnCount();
		String[] s = new String[colCount];

		for (int i = 0; i < colCount; i++)
			s[i] = t.getText(i);

		return s;
	}

	private void autoSizeColumns() {
		for (TableColumn i : columns) {
			i.pack();
		}
	}
}
