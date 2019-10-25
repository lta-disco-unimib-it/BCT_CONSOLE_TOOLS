package it.unimib.disco.lta.bct.bctjavaeclipse.core.ava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import it.unimib.disco.lta.ava.engine.AVAResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.AvaAnalysisResult;

public class AvaResultsSerializer {

	public static void serialize ( AvaAnalysisResult avaAnalysisResult, File dest ) throws IOException{
		FileOutputStream fos = new FileOutputStream(dest);
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(avaAnalysisResult);
		fos.close();
	}
	
	public static AvaAnalysisResult load( File src ) throws IOException, ClassNotFoundException{
		FileInputStream fin = new FileInputStream(src);
		ObjectInputStream oin = new ObjectInputStream(fin);
		return (AvaAnalysisResult) oin.readObject();
	}
}
