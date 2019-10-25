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



import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.ComponentsConfigurationDeserializer.NullOutputStream;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class FlattenerOptionsDeserializer {


//	static MonitoringConfiguration result;

	public static FlattenerOptions deserialize(File file)throws FileNotFoundException
	{
		//
		//HACK HACK HACK
		//


		FlattenerOptions fo = new FlattenerOptions();



		XMLEncoder encoder = new XMLEncoder(new NullOutputStream());
		encoder.writeObject(fo);
		encoder.close();

		

		//HACK END


		XMLDecoder decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));

		FlattenerOptions result = (FlattenerOptions) decoder.readObject();

		decoder.close();

		return result;

	}
}
