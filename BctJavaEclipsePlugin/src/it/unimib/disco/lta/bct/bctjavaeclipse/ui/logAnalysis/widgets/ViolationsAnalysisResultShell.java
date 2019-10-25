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

import java.util.List;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ViolationsAnalysisResult;

import modelsViolations.BctModelViolation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

public class ViolationsAnalysisResultShell {

	private Shell shell;
	private ViolationsAnalysisResultComposite violationsAnalysisResultComposite;

	public ViolationsAnalysisResultShell(){
		shell = new Shell(SWT.DIALOG_TRIM|SWT.RESIZE);
		shell.setSize(570, 570);
		shell.setText("BCT violations analysis results");
		
		ScrolledComposite composite = new ScrolledComposite(shell, SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);

		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 1;
		
		
//		Composite c = new Composite(composite, SWT.NONE);
//		GridLayout l = new GridLayout();
//		l.numColumns = 1;
//		c.setLayout(l);
		
		violationsAnalysisResultComposite = new ViolationsAnalysisResultComposite(composite,SWT.NONE);
		violationsAnalysisResultComposite.setBounds(15, 15, 575, 550);
		
		
		composite.setContent(violationsAnalysisResultComposite);	
		composite.setMinSize(violationsAnalysisResultComposite.computeSize(SWT.DEFAULT,SWT.DEFAULT));
		composite.pack();
	}

	public void close() {
		shell.close();
	}

	public Point computeSize(int hint, int hint2, boolean changed) {
		return shell.computeSize(hint, hint2, changed);
	}

	public Point computeSize(int hint, int hint2) {
		return shell.computeSize(hint, hint2);
	}

	public void dispose() {
		shell.dispose();
	}

	public void open() {
		shell.open();
	}

	public void pack() {
		shell.pack();
	}

	public void pack(boolean changed) {
		shell.pack(changed);
	}

	public void load(ViolationsAnalysisResult analysisResult, List<BctModelViolation> vilations) {
		violationsAnalysisResultComposite.load(null, analysisResult, vilations);
	}
}
