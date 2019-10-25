package org.cprover.views;


import org.cprover.core.CProverPlugin;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class SampleView extends ViewPart {
	private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private Action checkbyFile_action;
	private Action checkbyProp_action;
	private Action checkall_action;
	private Action checkSel_action;
	private Action stopSel_action;
	private Action stopall_action;
	private Action resetsession_action;
	private Action terminatesession_action;
	private Action stopsession_action;
	
	private Action check_actions;
	private Action stop_actions;
	private Action session_actions;
	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return new String[] { "One", "Two", "Three" };
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public SampleView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "org.cprover.plugin.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SampleView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
		manager.add(new Separator());
		manager.add(check_actions);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(check_actions);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
		
		checkSel_action = new Action() {
			public void run() {
			}
		};
		checkSel_action.setText("Selection");
		checkSel_action.setToolTipText("Check the selected claims");
		
		checkbyFile_action = new Action() {
			public void run() {
			}
		};
		checkbyFile_action.setText("by File");
		checkbyFile_action.setToolTipText("Check all the claims from the selected file");
		
		checkbyProp_action = new Action() {
			public void run() {
			}
		};
		checkbyProp_action.setText("by Property");
		checkbyProp_action.setToolTipText("Check all the claims with the selected property");
		
		checkall_action = new Action() {
			public void run() {
			}
		};
		checkall_action.setText("All");
		checkall_action.setToolTipText("Check all the claims");
		
		stopSel_action = new Action() {
			public void run() {
			}
		};
		stopSel_action.setText("Selection");
		stopSel_action.setToolTipText("Stop checking the selected claims");
		
		stopall_action = new Action() {
			public void run() {
			}
		};
		stopall_action.setText("All");
		stopall_action.setToolTipText("Stop all the running claim checking threads");
		
		stopsession_action = new Action() {
			public void run() {
//				CProverPlugin.stop();
			}
		};
		stopsession_action.setText("Stop");
		stopsession_action.setToolTipText("Stop the current session");
		
		terminatesession_action = new Action() {
			public void run() {
			}
		};
		terminatesession_action.setText("Terminate");
		terminatesession_action.setToolTipText("Terminate the current session");
		
		resetsession_action = new Action() {
			public void run() {
//				CProverPlugin.reset();				
			}
		};
		resetsession_action.setText("Reset");
		resetsession_action.setToolTipText("Reset the current session");
		
		checkbyFile_action.setEnabled(false);
		checkbyProp_action.setEnabled(false);
		checkall_action.setEnabled(false);
		checkSel_action.setEnabled(false);
		stopSel_action.setEnabled(false);
		stopall_action.setEnabled(false);
		stopsession_action.setEnabled(false);
		terminatesession_action.setEnabled(false);
		resetsession_action.setEnabled(false);
		
		this.check_actions = new ActionsGroup("Check", new Action[]{checkall_action, checkSel_action, checkbyFile_action, checkbyProp_action});
		this.stop_actions = new ActionsGroup("Stop", new Action[]{stopall_action, stopSel_action});
		this.session_actions = new ActionsGroup("Session", new Action[]{stopsession_action, terminatesession_action, resetsession_action}); 
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Sample View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}