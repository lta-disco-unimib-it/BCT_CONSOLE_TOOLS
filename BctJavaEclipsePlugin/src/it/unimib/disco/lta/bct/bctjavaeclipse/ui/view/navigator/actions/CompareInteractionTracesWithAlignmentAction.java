package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import it.unimib.disco.lta.ava.alignment.AlignmentPreprocess;
import it.unimib.disco.lta.ava.alignment.AlignmentResult;
import it.unimib.disco.lta.ava.alignment.StringsAlignmentPreciseGap;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIAbstractMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIFileMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIMapperFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CRegressionAnalysisUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.InteractionTracesDifferencesInput;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.BctMonitoringConfigurationTreeData;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions.CallPointDifferences.Difference;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.eclipse.jdt.internal.ui.search.NewSearchResultCollector;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonViewer;

import cpp.gdb.FileChangeInfo;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.compare.CompareItem;

public class CompareInteractionTracesWithAlignmentAction  extends Action {

	private StructuredViewer viewer;
	private boolean analyzeParameters;
	private boolean findNonDeterministicVariables ;
	private boolean run = false;

	
	public class CompareData {
		private BctMonitoringConfigurationTreeData data;
		private ArrayList<String> callPointsAsString;
		private List<MethodCallPoint> callPoints;
		
		public BctMonitoringConfigurationTreeData getData() {
			return data;
		}

		public ArrayList<String> getCallPointsAsString() {
			return callPointsAsString;
		}

		public List<MethodCallPoint> getCallPoints() {
			return callPoints;
		}

		public CompareData(BctMonitoringConfigurationTreeData toCompare) {
			data = toCompare;
			
			MonitoringConfiguration mc = data.getMonitoringConfiguration();
			Resource r = MonitoringConfigurationRegistry.getInstance().getResource(mc);
			
			URIAbstractMapper uriMapper = URIMapperFactory.getMapper(mc);
			InteractionRawTrace interactionTrace;
			try {
				interactionTrace = uriMapper.getInteractionRawTrace(data.getURI());
				callPoints = interactionTrace.getMethodCallPoints();
				callPointsAsString = new ArrayList<String>();
				for ( MethodCallPoint mcp : callPoints ){
					callPointsAsString.add(mcp.getMethod().getSignature());
				}
				
			} catch (MapperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LoaderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public CompareInteractionTracesWithAlignmentAction(StructuredViewer structuredViewer, boolean analyzeParameters, boolean findNonDeterministicVariables ) {
		this.viewer = structuredViewer;
		this.analyzeParameters = analyzeParameters;
		this.findNonDeterministicVariables = findNonDeterministicVariables;
		if ( findNonDeterministicVariables ){
			super.setText("Identify non deterministic variables");
		} else if ( analyzeParameters ){
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
		
		MonitoringConfiguration mc = null;
		Iterator it = treeSelection.iterator();
		int pos = 0;
		while ( it.hasNext() ){
			Object element = it.next();
			if ( element instanceof BctMonitoringConfigurationTreeData ){
				BctMonitoringConfigurationTreeData traceData = (BctMonitoringConfigurationTreeData) element;
				String textToDisplay = traceData.getTxtToDisplay();
				toCompare[pos++]=traceData;
				
				if (mc == null)
					mc = traceData.getMonitoringConfiguration();
			}
			if ( pos == 2 ){
				break;
			}
			
		}
		
		CompareData left = new CompareData(toCompare[0]);
		CompareData right = new CompareData(toCompare[1]);
		
		try {
			
			boolean renameTraces = true;
			
			if ( findNonDeterministicVariables ){
				renameTraces = false;
			}
			
			boolean filterOutNotModifiedLines = true;
			
			CRegressionConfiguration regressionConfig = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
			if ( regressionConfig != null ){
				if ( regressionConfig.isUseUpdatedReferencesForModels() ){
					renameTraces = false; //already renamed
				}
				
				if ( regressionConfig.isMonitorOnlyNotModifiedLines() ){
					filterOutNotModifiedLines = false;
				}
			}
			
			boolean modifiedBelongsToOriginal = false;
			
			if ( findNonDeterministicVariables ){
				modifiedBelongsToOriginal = true;
			}
			
			TracesFilter filter = new TracesFilter(mc, left.getCallPoints(), right.getCallPoints(), renameTraces, modifiedBelongsToOriginal, filterOutNotModifiedLines  );
			List<MethodCallPoint> filteredOriginalTrace = filter.getFilteredOriginalTrace();
			List<MethodCallPoint> filteredModifiedTrace = filter.getFilteredModifiedTrace();
			List<String> filteredOriginalTraceString = filter.getFilteredOriginalTraceAsString();
			List<String> filteredModifiedTraceString = filter.getFilteredModifiedTraceAsString();
			
			
			AlignmentResult alignmentResult = alignProgramTraces(
					filteredOriginalTraceString, filteredModifiedTraceString);
			
			NonDeterministicVariablesRegistry nonDeterministicVariablesRegistry = NonDeterministicVariablesRegistry.getInstance();
			
			TracesComparator compare = new TracesComparator(filteredOriginalTrace, filteredModifiedTrace, alignmentResult.getFirstSequenceAligned(), alignmentResult.getSecondSequenceAligned());
			List<List<Difference>> differences = compare.getDifferences();
			for (List<Difference> list : differences) {
				for (Difference difference : list) {
					
					if ( findNonDeterministicVariables ) {
						System.out.println("!" + difference.getVariable().getMethod().getSignature() + " @ " + difference.getVariable().getName() + ": " + difference.getValue1() + " -> " + difference.getValue2());
						nonDeterministicVariablesRegistry.addNonDetermnisticVariable( difference.getVariable().getMethod(), difference.getVariable().getName() );
					} else {
						if ( ! nonDeterministicVariablesRegistry.isNonDeterministic(difference.getVariable().getMethod(), difference.getVariable().getName()) ){
							System.out.println("!" + difference.getVariable().getMethod().getSignature() + " @ " + difference.getVariable().getName() + ": " + difference.getValue1() + " -> " + difference.getValue2());
						}
					}
				}
			}
			
			
			run  = true;
			
			/*-----------------------------------------------------------------------
			StorageConfiguration storageConfiguration = mc.getStorageConfiguration();
			//FIXME: add other store configuration (e.g. DBStorageConfiguration)
			if (storageConfiguration instanceof FileStorageConfiguration) {
				// Serialize TracesComparator
				FileStorageConfiguration sc = (FileStorageConfiguration)storageConfiguration;
				String path = ResourcesPlugin.getWorkspace().getRoot().getLocation() + sc.getDataDirPath() + "/differences.itd";
					
				System.out.println("&&& " + path);
				File file = new File(path);
				if (!file.exists())
					file.createNewFile();
				ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
				stream.writeObject(compare);
				stream.close();
				
				// Open differences editor
				IWorkspace workspace= ResourcesPlugin.getWorkspace();    
				IPath location= Path.fromOSString(file.getAbsolutePath()); 
				IFile ifile= workspace.getRoot().getFileForLocation(location);
				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(ifile.getName());
			    
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(ifile), desc.getId());
			}*/
			
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new InteractionTracesDifferencesInput(compare), "bctjavaeclipse.editors.differences");
			
			
		} catch (ConfigurationFilesManagerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( Throwable t ){
			t.printStackTrace();
		}
		
		//CompareUI.openCompareDialog(new CompareInput(toCompare[0],toCompare[1]));
	}
	
//	private void fillWithEmptyElements(List<String> listToFill, int size)
//    {
//        for(int i=listToFill.size();i<size;i++)
//        {
//            listToFill.add("-");
//        }        
//    }

	public AlignmentResult alignProgramTraces(
			List<String> filteredOriginalTraceString,
			List<String> filteredModifiedTraceString) {
		//THIS code fixes the issue of match at the bottom
		//Without this code:
		// A B A B 
		// A B
		//will lead to:
		// A B A B
		// - - A B
		
		Collections.reverse(filteredOriginalTraceString);
		Collections.reverse(filteredModifiedTraceString);
		
		AlignmentResult alignmentResult = StringsAlignmentPreciseGap.align(1, -1, 0, 0, filteredOriginalTraceString, filteredModifiedTraceString, new AlignmentPreprocess() );
		
		
		//Bug fix again
		Collections.reverse(alignmentResult.getFirstSequenceAligned());
		Collections.reverse(alignmentResult.getSecondSequenceAligned());
		
		System.out.println("### Alignment");
		List<String> firstAlign = alignmentResult.getFirstSequenceAligned();
		List<String> secondAlign = alignmentResult.getSecondSequenceAligned();
		
		
		for ( int i = 0; i < firstAlign.size(); i++ ){
			System.out.println(firstAlign.get(i) + "\t\t\t" + secondAlign.get(i) );
		}
		
		System.out.println("### Alignment END");
		return alignmentResult;
	}
	
	
	/**
	 * @param alignedSequence Aligned sequence of call points
	 * @param sequence Sequence of call points
	 * @return Sequence of call points filled with null elements corresponding to gaps in aligned sequence 
	 */
	private List<MethodCallPoint> fillGap(List<String> alignedSequence, List<MethodCallPoint> sequence) {
		List<MethodCallPoint> newSequence = new ArrayList<MethodCallPoint>(alignedSequence.size());
	    
		for (int i = 0; i < alignedSequence.size(); i++) {
			String s = alignedSequence.get(i);
			if (!s.equals("-"))
				newSequence.add(i, sequence.get(i));
			else
				newSequence.add(i, null);
		}
		
		return newSequence;
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
