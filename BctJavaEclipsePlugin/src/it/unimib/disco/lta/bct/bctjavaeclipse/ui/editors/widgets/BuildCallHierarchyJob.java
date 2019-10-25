package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class BuildCallHierarchyJob extends Job {

	private List<MethodCallItem> rootItems;
	private List<MethodCallPoint> methodCallPoints;

	public BuildCallHierarchyJob(String name, List<MethodCallItem> rootItems, List<MethodCallPoint> methodCallPoints) {
		super(name);
		this.rootItems = rootItems;
		this.methodCallPoints = methodCallPoints;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask("Building method call hierarchy", IProgressMonitor.UNKNOWN);
		
		Iterator<MethodCallPoint> iterator = methodCallPoints.listIterator();
		try {
			while (iterator.hasNext() && !monitor.isCanceled()) {
				MethodCallPoint methodCallPoint = iterator.next();
				if (methodCallPoint.isEnter()) {
					MethodCallItem item = new MethodCallItem(methodCallPoint);
					rootItems.add(item);
					searchChildren(item, iterator, monitor);
				} else if (methodCallPoint.isGeneric()) {
					MethodCallItem item = new MethodCallItem(methodCallPoint);
					rootItems.add(item);
				}
			}
		} catch (NoSuchElementException e) {
			// Nothing to do.
		}
		
		monitor.done();
		if (!monitor.isCanceled()) {
			return Status.OK_STATUS;
		} else {
			return Status.CANCEL_STATUS;
		}
	}

	private void searchChildren(MethodCallItem parent, Iterator<MethodCallPoint> iterator, IProgressMonitor monitor) {
		while (iterator.hasNext() && !monitor.isCanceled()) {
			MethodCallItem item = new MethodCallItem(iterator.next());

			if (item.getMethodCall().isExit()) {
				parent.setMethodCallExit( item.getMethodCall() );
				break;
			} else {
				if (item.getMethodCall().isEnter()) {
					parent.addChild(item);
					searchChildren(item, iterator, monitor);
				} else if (item.getMethodCall().isGeneric()) {
					parent.addChild(item);
				}
			}
		}
	}
}
