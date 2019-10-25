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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import java.net.URI;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint.Types;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIBuilderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorOpener;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import cpp.gdb.LineData;

public class OpenTraceViolationsCompositeAction extends ViolationsCompositeAction {


		@Override
		public String getText() {
			return "Open Trace";
		}

		@Override
		public String getToolTipText() {
			return "Open Trace";
		}

		@Override
		public String getDescription() {
			return "Open Trace";
		}
		
		@Override
		public void run(BctModelViolation violation,
				MonitoringConfiguration mc) {
			// TODO Auto-generated method stub
						Types callPointType = MethodCallPoint.Types.ENTER;
						String modelName = violation.getViolatedModel();
						if(violation instanceof BctIOModelViolation) {
							if ( ! ( modelName.endsWith("ENTER") || modelName.endsWith("POINT") ) ){
								callPointType = Types.EXIT;
							}
						}

						try {
						URI uri = URIBuilderFactory.getBuilder(mc).buildRawTraceURI(violation.getPid(), violation.getThreadId(), violation.getCallId(), callPointType );
						EditorOpener.openEditor(Display.getDefault(), uri);
						} catch ( Throwable t ){
							t.printStackTrace();
						}
			

		}

		
	}