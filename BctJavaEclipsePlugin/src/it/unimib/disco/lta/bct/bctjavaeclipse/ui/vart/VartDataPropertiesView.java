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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.vart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTDataProperty;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTDataPropertyExit;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTResultsLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.OpenSourceFileVARTPropertyAction;


public abstract class VartDataPropertiesView extends ViewPart implements ISelectionListener {


	private static final boolean UPDATE_MARKERS_ON_FOCUS = true;
	private static final boolean ECLIPSE3 = false;
	private TableViewer viewer;
	private static Object lastSelected;
	private static MonitoringConfiguration loadedConfiguration;
	private ArrayList<VARTDataProperty> loadedResults;

	protected int markerSeverityForInvalid = IMarker.SEVERITY_ERROR;
	protected int markerSeverityForOutdated = IMarker.SEVERITY_WARNING;
	private VartDataPropertiesComparator comparator;
	protected boolean settingFocus;
	private String viewTitle;


	public VartDataPropertiesView() {
		// TODO Auto-generated constructor stub
	}

	private void createContextMenu() {
		// Create menu manager.
		MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				mgr.add(new Action("CleanUp Markers"){

					@Override
					public void run() {
						VARTMarker.INSTANCE.cleanupMarkers();
					}

				});



				mgr.add(new GroupMarker("additions"));
			}
		});




		// Create menu.
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);

		// Register menu for extension.
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column,
			final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//		        comparator.setColumn(index);
				int dir = comparator.getDirection();
				viewer.getTable().setSortDirection(dir);
				viewer.getTable().setSortColumn(column);
				viewer.refresh();
			}
		};
		return selectionAdapter;
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		// define the TableViewer
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		// create the columns 
		// not yet implemented
		createColumns(viewer);

		// make lines and header visible
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true); 

		getViewSite().getPage().addSelectionListener(this);
		ISelectionProvider p;
		viewer.setContentProvider(new ArrayContentProvider());

		viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				int selectedItem = viewer.getTable().getSelectionIndex();

				if ( selectedItem < 0 ){
					return;
				}

				VARTDataProperty selectedDataProperty = loadedResults.get(selectedItem);
				OpenSourceFileVARTPropertyAction openAction = new OpenSourceFileVARTPropertyAction(loadedConfiguration, selectedDataProperty);
				openAction.run();
			}
		});

		comparator = new VartDataPropertiesComparator();
		viewer.setComparator(comparator);

		createContextMenu();
	}

	/**
	 * @see IViewPart.init(IViewSite)
	 */
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		viewTitle = getPartName();
	}


	private void createColumns(TableViewer viewer2) {

		// create more text columns if required...

		// create column for married property of Person
		// uses getImage() method instead of getText()
		// CHECKED and UNCHECK are fields of type Image

		TableViewerColumn colFile = new TableViewerColumn(viewer, SWT.NONE);
		colFile.getColumn().setWidth(200);
		colFile.getColumn().setText("File");
		colFile.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				VARTDataProperty dp = (VARTDataProperty) element;
				return dp.getFile();  
			}
		});

		TableViewerColumn colLine = new TableViewerColumn(viewer, SWT.NONE);
		colLine.getColumn().setWidth(40);
		colLine.getColumn().setText("Line");
		colLine.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				VARTDataProperty dp = (VARTDataProperty) element;
				if ( dp instanceof VARTDataPropertyExit ){
					return String.valueOf(((VARTDataPropertyExit) dp).getFunctionaName()+"::EXIT ("+dp.getLine()+")");
				}
				return String.valueOf(dp.getLine()); 
			}
		});


		TableViewerColumn colAssertion = new TableViewerColumn(viewer, SWT.NONE);
		colAssertion.getColumn().setWidth(200);
		colAssertion.getColumn().setText("Assertion");
		colAssertion.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				VARTDataProperty dp = (VARTDataProperty) element;
				return dp.getAssertion();
			}
		});

		TableViewerColumn colRes = new TableViewerColumn(viewer, SWT.NONE);
		colRes.getColumn().setWidth(200);
		colRes.getColumn().setText("Result");
		colRes.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				VARTDataProperty dp = (VARTDataProperty) element;
				return dp.getResult().toString();
			}
		});
	}

	@Override
	public void setFocus() {
		if ( ! UPDATE_MARKERS_ON_FOCUS ){
			return;
		}
		
		if ( settingFocus ){
			return;
		}
		
		VartDataPropertiesView propertiesView = this;
		propertiesView.settingFocus = true;
		
		System.out.println("Set focus: "+this.getClass().getCanonicalName());
		
		if ( ECLIPSE3 ){
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if ( ! VARTMarker.INSTANCE.cleanupMarkers(propertiesView) ){
						return;
					}
					if ( loadedResults == null ){
						try {
							reloadResults();
						} catch (ConfigurationFilesManagerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if ( loadedResults == null )
						return;
					
					VARTMarker.INSTANCE.markAll(loadedResults, markerSeverityForInvalid, markerSeverityForOutdated );
					viewer.setInput(loadedResults);
				}
			});
		} else {
			updateViewInAJob(propertiesView);
		}
	}

	private void updateViewInAJob(VartDataPropertiesView propertiesView) {
		Job j = new Job("Updating View: "+this.getTitle()){

			@Override
			protected IStatus run(IProgressMonitor arg0) {
				arg0.beginTask("Loading data", 2);
				
				
				if ( ! VARTMarker.INSTANCE.cleanupMarkers(propertiesView) ){
					return Status.OK_STATUS;
				}
				
				if ( loadedResults == null ){
					try {
						reloadResults();
					} catch (ConfigurationFilesManagerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if ( loadedResults == null )
					return Status.OK_STATUS;
				arg0.worked(1);
				arg0.subTask("Loading data");
				
				VARTMarker.INSTANCE.markAll(loadedResults, markerSeverityForInvalid, markerSeverityForOutdated );
				arg0.worked(1);
				
				
				
				arg0.done();
				return Status.OK_STATUS;
			}
		};

		j.addJobChangeListener(new IJobChangeListener() {

			@Override
			public void sleeping(IJobChangeEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void scheduled(IJobChangeEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void running(IJobChangeEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void done(IJobChangeEvent arg0) {
				
				Display.getDefault().asyncExec( new Runnable() {

					@Override
					public void run() {
						viewer.setInput(loadedResults);
						propertiesView.settingFocus = false;
						
					}
				});
			}

			@Override
			public void awake(IJobChangeEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void aboutToRun(IJobChangeEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		j.schedule();
	}



	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if ( selection == null ){
			return;
		}



		if ( selection instanceof IStructuredSelection ){
			IStructuredSelection structSel = (IStructuredSelection) selection;
			Object selected = structSel.getFirstElement();

			if ( selected instanceof IFile ){
				final IFile fSelected = (IFile) selected;


				if ( "bctmc".equals( fSelected.getFileExtension() ) ){
					VartDataPropertiesView propertiesView = this;
					
					if ( ECLIPSE3 ){
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							loadDataAfterSelectionUpdateOfConfigFile(fSelected, propertiesView);
							viewer.setInput(loadedResults);
						}
					});
					} else {
						updateMarkersInAJob(fSelected, propertiesView);
					}
				}


			} else {


			}


		}


	}

	private void updateMarkersInAJob(final IFile fSelected, VartDataPropertiesView propertiesView) {
		Job j = new Job("Reloading Results from "+fSelected){

			@Override
			protected IStatus run(IProgressMonitor arg0) {
				arg0.beginTask("Loading data", 2);
				loadDataAfterSelectionUpdateOfConfigFile(fSelected, propertiesView);
				arg0.subTask("Loading data");
				arg0.worked(2);
				arg0.done();
				return Status.OK_STATUS;
			}
		};

		j.addJobChangeListener(new IJobChangeListener() {

			@Override
			public void sleeping(IJobChangeEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void scheduled(IJobChangeEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void running(IJobChangeEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void done(IJobChangeEvent arg0) {
				Display.getDefault().asyncExec( new Runnable() {

					@Override
					public void run() {
						viewer.setInput(loadedResults);
					}
				});
			}

			@Override
			public void awake(IJobChangeEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void aboutToRun(IJobChangeEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		j.schedule();
	}





	@Override
	public void dispose() {
		getViewSite().getPage().removeSelectionListener(this);
		super.dispose();
	}

	protected abstract Collection<? extends VARTDataProperty> loadResults( VARTResultsLoader resLoader) throws ConfigurationFilesManagerException;

	private void loadDataAfterSelectionUpdateOfConfigFile(final IFile fSelected, VartDataPropertiesView propertiesView) {
		if ( ( lastSelected == fSelected ) && ( ! VARTMarker.INSTANCE.cleanupMarkers(propertiesView) ) ){
			return;
		}

		synchronized (VartDataPropertiesView.class) {
			lastSelected = fSelected;
			loadedConfiguration = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(fSelected);			
		}


		

		try {

			reloadResults();
			VARTMarker.INSTANCE.markAll(loadedResults, markerSeverityForInvalid, markerSeverityForOutdated);

		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void reloadResults() throws ConfigurationFilesManagerException {
		loadedResults = new ArrayList<VARTDataProperty>();
		VARTResultsLoader resLoader = new VARTResultsLoader( loadedConfiguration );
		//results.addAll( resLoader.loadRegressedProperties() );
		loadedResults.addAll( loadResults( resLoader ) );
		Collections.sort(loadedResults, comparator);
	}

}
