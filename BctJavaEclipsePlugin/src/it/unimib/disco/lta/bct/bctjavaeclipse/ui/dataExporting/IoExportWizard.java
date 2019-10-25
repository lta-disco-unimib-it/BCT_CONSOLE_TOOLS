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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.dataExporting;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.BctMonitoringConfigurationTreeData;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

public class IoExportWizard extends Wizard implements IExportWizard {

	private IFile mcFile;
	private IoExportWizardPage ioExportPage;
	private MonitoringConfiguration mc;

	public IoExportWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		Object element = selection.getFirstElement();

		System.out.println(element.getClass().getCanonicalName());

		
		if ( element instanceof BctMonitoringConfigurationTreeData ){
			BctMonitoringConfigurationTreeData treeData = (BctMonitoringConfigurationTreeData) element;
			mc = treeData.getMonitoringConfiguration();
		} else if ( element instanceof IFile ){
			mcFile = (IFile) element;
		} else {
			return;
		}



		
		ioExportPage = new IoExportWizardPage("Export BCT IO models");



	}


	public boolean canFinish(){
		return ioExportPage.isPageComplete(); 
	}


	public void addPages() {
		super.addPages();
		addPage(ioExportPage);


	}

	@Override
	public boolean performFinish() {
		final String dest = ioExportPage.getDestinationFolder();
		Job j = new Job("Exporting IO") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				MonitoringConfiguration mc = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(mcFile);

				try {
					Resource res = MonitoringConfigurationRegistry.getInstance().getResource(mc);

					Collection<IoRawTrace> traces = res.getFinderFactory().getIoRawTraceHandler().findAllTraces();
					
					IoTracesExporter exporter = new IoTracesExporter();
					
					try {
						exporter.export( new File(dest), traces );
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (LoaderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (MapperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return Status.OK_STATUS;
			}
		};

		j.schedule();

		return true;
	}

}
