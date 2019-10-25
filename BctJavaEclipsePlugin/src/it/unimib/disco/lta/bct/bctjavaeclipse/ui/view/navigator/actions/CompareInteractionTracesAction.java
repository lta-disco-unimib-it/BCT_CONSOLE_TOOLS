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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIAbstractMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIFileMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIMapperFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.BctMonitoringConfigurationTreeData;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;

import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.navigator.CommonViewer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.compare.CompareItem;

public class CompareInteractionTracesAction  extends Action {

	private StructuredViewer viewer;
	private boolean analyzeParameters;

	
	public class CompareInput extends CompareEditorInput {
		private BctMonitoringConfigurationTreeData textL;
		private BctMonitoringConfigurationTreeData textR;

		public CompareInput(BctMonitoringConfigurationTreeData toCompare, BctMonitoringConfigurationTreeData toCompare2) {
			super(new CompareConfiguration());
			this.textL = toCompare;
			this.textR = toCompare2;
		}
		
		@Override
		protected Object prepareInput(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			CompareItem ancestor = 
					new CompareItem("Common", "contents", 0 );
			CompareItem left = 
					new CompareItem(textL.getTxtToDisplay(), extractText(textL), 1 );
			CompareItem right = 
					new CompareItem(textR.getTxtToDisplay(),extractText(textR), 2 );
			Differencer d = new Differencer();
			Object diffs = d.findDifferences(false, new NullProgressMonitor(), null, ancestor, left, right);

			System.out.println(diffs.getClass().getCanonicalName());
			
			return diffs;
			//			return new DiffNode(null, Differencer.CONFLICTING, ancestor,
//					left, right);
		}
	}
	
	public CompareInteractionTracesAction(StructuredViewer structuredViewer, boolean analyzeParameters ) {
		this.viewer = structuredViewer;
		this.analyzeParameters = analyzeParameters;
		if ( analyzeParameters ){
			super.setText("Compare traces");
		} else {
			super.setText("Compare traces (no parameters)");
		}
	}

//	public void setAnalyzeParemeters( boolean b){
//		this.analyzeParameters = b;
//		if ( b == false ){
//			super.setText("")
//		}
//	}
	
	@Override
	public void run() {
		TreeSelection treeSelection = (TreeSelection) viewer.getSelection();
		
		BctMonitoringConfigurationTreeData[] toCompare = new BctMonitoringConfigurationTreeData[2];
		
		Iterator it = treeSelection.iterator();
		int pos = 0;
		while ( it.hasNext() ){
			Object element = it.next();
			if ( element instanceof BctMonitoringConfigurationTreeData ){
				BctMonitoringConfigurationTreeData traceData = (BctMonitoringConfigurationTreeData) element;
				String textToDisplay = traceData.getTxtToDisplay();
				toCompare[pos++]=traceData;
			}
			if ( pos == 2 ){
				break;
			}
			
		}
		
	
		
		CompareUI.openCompareDialog(new CompareInput(toCompare[0],toCompare[1]));
	}


	private String extractText(
			BctMonitoringConfigurationTreeData bctMonitoringConfigurationTreeData) {
		MonitoringConfiguration mc = bctMonitoringConfigurationTreeData.getMonitoringConfiguration();
		Resource r = MonitoringConfigurationRegistry.getInstance().getResource(mc);
		String textLabel = bctMonitoringConfigurationTreeData.getTxtToDisplay();
//		URIFileMapper
//		threadStart = textLabel.indexOf(ch);
//		sessionId = Integer.valueOf(textLabel.substring(arg0))
		URIAbstractMapper uriMapper = URIMapperFactory.getMapper(mc);
		InteractionRawTrace interactionTrace;
		try {
			interactionTrace = uriMapper.getInteractionRawTrace(bctMonitoringConfigurationTreeData.getURI());
			return InteractionRawTraceUtil.getTextualRepresentation( interactionTrace, analyzeParameters );
			
		} catch (MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "";
	}


	@Override
	public boolean isEnabled() {
		// check if selection contains only components elements
		TreeSelection treeSelection = (TreeSelection) viewer.getSelection();
		
		Iterator it = treeSelection.iterator();
		while ( it.hasNext() ){
			Object element = it.next();
			if ( element instanceof BctMonitoringConfigurationTreeData ){
				return true;
			}
			System.out.println(element.getClass().getCanonicalName());
		}
		return true;
	}
}
