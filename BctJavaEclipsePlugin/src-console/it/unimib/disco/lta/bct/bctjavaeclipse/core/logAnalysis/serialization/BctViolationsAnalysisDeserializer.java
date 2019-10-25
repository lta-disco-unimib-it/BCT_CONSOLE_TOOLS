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
