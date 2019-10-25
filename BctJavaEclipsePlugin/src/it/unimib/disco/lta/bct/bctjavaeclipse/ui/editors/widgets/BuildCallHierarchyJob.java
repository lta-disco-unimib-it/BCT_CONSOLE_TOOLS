/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
