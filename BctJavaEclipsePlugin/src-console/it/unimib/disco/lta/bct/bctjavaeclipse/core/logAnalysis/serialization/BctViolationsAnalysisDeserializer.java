package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization;

import it.unimib.disco.lta.ava.engine.configuration.AvaConfiguration;
import it.unimib.disco.lta.ava.engine.configuration.AvaConfigurationFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ViolationsAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.HackUtil;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

public class BctViolationsAnalysisDeserializer implements ExceptionListener {



	public static BctViolationsAnalysisConfiguration deserialize(File xmlSerializedObject) throws FileNotFoundException {
		
		
		//
		//   HACK HACK HACK
		//
		//  XmlEncoders Decoders are not the right choice for eclipse, should switch to another solution
		//  To decode an object we need taht its classes are loaded, so we need to run a fake serialier before running the deserializer
		//
		
		BctViolationsAnalysisConfiguration cc = getFakeBctViolationsAnalysisConfiguration();
		XMLEncoder enc = new XMLEncoder(new HackUtil.NullOutputStream());
		enc.writeObject(cc);
		
		
		
		//Decode
		
		XMLDecoder decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(xmlSerializedObject)));
		decoder.setExceptionListener(new BctViolationsAnalysisDeserializer());
		System.out.println("Inizio decodifica XML");

		BctViolationsAnalysisConfiguration result = (BctViolationsAnalysisConfiguration) decoder.readObject();

		decoder.close();

		for ( File file : result.retrieveLogFiles() ){
			System.out.println(file.getAbsolutePath());
		}
		return result;

	}

	private static BctViolationsAnalysisConfiguration getFakeBctViolationsAnalysisConfiguration() {
		BctViolationsAnalysisConfiguration c = new BctViolationsAnalysisConfiguration();
		ViolationsAnalysisResult r = new ViolationsAnalysisResult("1");
		AvaConfiguration conf = AvaConfigurationFactory.createAvaConfigurationHighThresholds(3);
		c.setAvaConfiguration(conf);
		r.newElement("Name", "path", "path2", 3, new ArrayList<Set<String>>(), false);
		c.setViolationsAnalysisResult(r);
		return c;
	}

	public void exceptionThrown(Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}
	
}
