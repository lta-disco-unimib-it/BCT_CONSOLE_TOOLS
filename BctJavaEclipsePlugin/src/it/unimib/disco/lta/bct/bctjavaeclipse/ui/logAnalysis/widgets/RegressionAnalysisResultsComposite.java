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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.RegressionAnalysisResult;



import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.IEditorSite;

public class RegressionAnalysisResultsComposite extends Composite {

	private ViolationsComposite violationsComposite;
	private RegressionAnalysisResult regressionAnalysisResult;

	public RegressionAnalysisResultsComposite(Composite parent, int style) {
		super(parent, style);
		
		
		FillLayout parentLayout = new FillLayout();
//		parentLayout.numColumns=1;
//		parentLayout.marginWidth=0;
//		parentLayout.marginHeight=0;
		
		this.setLayout(parentLayout);
		
				
		violationsComposite = new ViolationsComposite(this, SWT.BORDER, true);
		violationsComposite.setSize(300, 400);
		
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.setLayoutData(gridData);
		this.setSize(300, 400);
		
//		this.pack();
	}

	public void load(RegressionAnalysisResult result) {
		if ( result == null ){
			return;
		}
		this.regressionAnalysisResult = result;
		violationsComposite.setItems(result.getFilteredViolations());
		violationsComposite.redraw();
	}

	public RegressionAnalysisResult getRegressionAnalysisResult() {
		return regressionAnalysisResult;
	}

	public void setSite(IEditorSite site) {
		violationsComposite.setSelectionProvider(site);
	}
	
	public void setMonitoringConfiguration(MonitoringConfiguration mc){
		violationsComposite.setMonitoringConfiguration(mc);
	}

}
