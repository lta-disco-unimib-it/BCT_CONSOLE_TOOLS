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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors.IndexTableComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class BctLogAnalysisResultsViewPart extends ViewPart {

	private IndexTableComposite anomalyTable;
	

	public BctLogAnalysisResultsViewPart() {
		
	}

	@Override
	public void createPartControl(Composite parent) {
		
		ScrolledComposite composite = new ScrolledComposite(parent, SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);
//		
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 1;
//		
		
		Composite c = new Composite(composite, SWT.NONE);
		GridLayout l = new GridLayout();
		l.numColumns = 1;
		composite.setLayout(l);
		
		anomalyTable = new IndexTableComposite(composite, SWT.NONE);
        anomalyTable.setBounds(20, 20, 400, 400);
        anomalyTable.setTitles("Graph #", "Resource", "Connected components", "Open");
		
		composite.setContent(c);	
		composite.setMinSize(c.computeSize(SWT.DEFAULT,SWT.DEFAULT));
		
		}

	@Override
	public void setFocus() {
		
	}

}
