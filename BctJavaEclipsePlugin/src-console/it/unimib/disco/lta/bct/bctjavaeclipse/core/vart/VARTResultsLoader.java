package it.unimib.disco.lta.bct.bctjavaeclipse.core.vart;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import tools.fshellExporter.CBMCExecutor.ValidationResult;

public class VARTResultsLoader {

	private MonitoringConfiguration loadedConfiguration;

	public VARTResultsLoader(MonitoringConfiguration loadedConfiguration) {
		this.loadedConfiguration = loadedConfiguration;
	}

	public Set<VARTDataProperty> loadRegressedProperties() throws ConfigurationFilesManagerException {
		File nrpFile = ConfigurationFilesManager.getVARTresult_NonRegressionPropertiesVerification( loadedConfiguration );
		Set<ValidationResult> filter = new HashSet<ValidationResult>();
		filter.add(ValidationResult.INVALID);
		return loadResults(nrpFile, filter);
	}
	
	public Set<VARTDataProperty> loadRegressionAnalysisResults() throws ConfigurationFilesManagerException {
		File nrpFile = ConfigurationFilesManager.getVARTresult_NonRegressionPropertiesVerification( loadedConfiguration );
		return loadResults(nrpFile, null);
	}
	
	public Set<VARTDataProperty> loadDynamicProperties() throws ConfigurationFilesManagerException {
		File nrpFile = ConfigurationFilesManager.getVARTresult_DynamicPropertiesVerification( loadedConfiguration );
		return loadResults(nrpFile, null);
	}
	
	public Set<VARTDataProperty> loadOutdatedProperties() throws ConfigurationFilesManagerException {
		File nrpFile = ConfigurationFilesManager.getVARTresult_OutdatedProperties( loadedConfiguration );
		return loadResults(nrpFile, null);
	}
	
	public Set<VARTDataProperty> loadResults(File rnpFile, Set<ValidationResult> filter) {
		
		HashSet<VARTDataProperty> set = new HashSet<VARTDataProperty>();
		if ( ! rnpFile.exists() ){
			return set;
		}
		
		try {
			
			System.out.println("OPENING LOADRESULTS : "+rnpFile);
			BufferedReader r = new BufferedReader(new FileReader(rnpFile) );
			
			//HACK to remove duplicates
			HashSet<String> linesRead = new HashSet<String>();
			
			String line;
			
			while ( ( line = r.readLine() ) != null ){
				System.out.println(line);
				if ( linesRead.contains(line) ){
					continue;
				}
				linesRead.add(line);
				
				String[] tokens = line.split("\t");
				if  ( tokens == null ){
					continue;
				}
				
				int lineNo;
				try {
					lineNo = Integer.parseInt( tokens[1] );
				} catch (Throwable e) {
					lineNo = -1;
				}
				
				String file = tokens[0];
				String assertion = tokens[2];
				
				ValidationResult validationresult = ValidationResult.valueOf(tokens[3]);
				
				if ( filter != null ){
					if ( ! filter.contains(validationresult) ){
						continue;
					}
				}
				
				
				int injectedLine;
				try {
					injectedLine = Integer.parseInt( tokens[4] );
				} catch (Throwable e) {
					injectedLine = -1;
				}
				
				
				VARTDataProperty dp;
				if ( lineNo > -1 ){
					dp = new VARTDataPropertyLine( loadedConfiguration, file, lineNo, assertion, validationresult, injectedLine );
				} else {
					String functionaName = tokens[1];
					dp = new VARTDataPropertyExit( loadedConfiguration, file, functionaName, assertion, validationresult,injectedLine );
				}
				
				set.add(dp);
				
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;
	}



}
