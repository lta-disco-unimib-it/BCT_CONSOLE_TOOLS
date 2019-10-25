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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ComponentsConfigurationSerializer {
	
	public static void serialize(File destination, ComponentsConfiguration componentsConfiguration) throws FileNotFoundException{
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new  FileOutputStream(destination)));
		
		System.out.println("Scrittura oggetto in Xml iniziata");
		try{
			encoder.writeObject(componentsConfiguration);
		}
		catch ( Throwable t ){

			t.printStackTrace();
		
		}
		encoder.close();
		System.out.println("Scrittura oggetto in Xml riuscita");

	}
}
