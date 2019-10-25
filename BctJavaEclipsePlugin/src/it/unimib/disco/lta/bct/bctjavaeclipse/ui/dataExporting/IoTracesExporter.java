package it.unimib.disco.lta.bct.bctjavaeclipse.ui.dataExporting;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointVariable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class IoTracesExporter {

	public void export(File dest, Collection<IoRawTrace> traces) throws IOException, LoaderException {
		for ( IoRawTrace trace : traces ){
			export(new File( dest, trace.getMethod().getSignature()+".csv" ) , trace );
		}
	}

	private void export(File dest, IoRawTrace trace) throws IOException, LoaderException {
		BufferedWriter w = new BufferedWriter ( new FileWriter( dest ) );
		
		boolean firstLine = true;
		
		try {
			for ( ProgramPointRaw pp : trace.getProgramPoints() ){
				if ( pp.isEnter() ){
					continue;
				}
				
				if ( firstLine ){
					printHeader(w,pp);

					firstLine = false;
				}

				printValues(w, pp);

				
			}
		} finally {
			w.close();
		}
		
	}

	private void printHeader(BufferedWriter w, ProgramPointRaw pp) throws IOException {
		for ( ProgramPointValue ppv  : pp.getProgramPointVariableValues() ){
			ProgramPointVariable var = ppv.getVariable();
			w.write(var.getName() );
			w.write(',');
		}
		w.newLine();
	}
	
	
	private void printValues(BufferedWriter w, ProgramPointRaw pp) throws IOException {
		for ( ProgramPointValue ppv  : pp.getProgramPointVariableValues() ){
			w.write(ppv.getValue() );
			w.write(',');
		}
		w.newLine();
	}

}
