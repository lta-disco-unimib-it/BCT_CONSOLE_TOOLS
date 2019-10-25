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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

import java.util.logging.Logger;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import cpp.gdb.Demangler;
import cpp.gdb.TraceUtils;

public class DisplayNamesUtil {
	private static final Logger LOGGER = Logger.getLogger(DisplayNamesUtil.class.getCanonicalName());
	private static boolean isJava = false;
	private static Demangler demangler = Demangler.INSTANCE;
	
	public static void setIsJava( boolean v ){
		isJava = v;
	}

	public static String getDemangledVersion( String mangledName ){
		if ( isJava ){
			return mangledName;
		}
		
		int firstSpace = mangledName.indexOf(' ');

		String suffix = null;
		if ( firstSpace > 0 ){
			suffix = mangledName.substring(firstSpace);
			mangledName = mangledName.substring(0, firstSpace);
		}

		String model = TraceUtils.getFunctionName( mangledName );


		if ( model.length() < mangledName.length() ){ //_ZmyFunc:34
			if ( suffix == null ){
				suffix = "";
			}
			suffix = mangledName.substring(model.length()) + suffix;
		}

		int newLen = model.length();

		model = demangler.demangle(model );



		if ( suffix != null ){
			model += suffix;
		}

		return model;
	}

	public static String removeNamespacesFromSignature( String signature ){
		try {

			StringBuffer finalSignature = new StringBuffer();

			char[] signatureArray = signature.toCharArray();

			int lastNamespaceToken = 0;
			int lastSeparator = 0;
			boolean withinArguments = false;
			int templates = 0;
			int lastNamespaceTokenClass = 0;
			for ( int i = 0; i < signatureArray.length; i++ ){

				//				System.out.println(signatureArray[i]);
				boolean templateStart = false;
				boolean templateEnd = false;

				if ( signatureArray[i] == '<' ){
					templates++;
					if ( templates == 1 ){
						templateStart = true;
					}
				} else if ( signatureArray[i] == '>' ){
					templates--;
					if ( templates == 0 ){
						templateEnd = true;
					}
				}

				if ( templateEnd ){
					
					if ( withinArguments ){
						finalSignature.append(" ");
					}
					
					lastSeparator = i;
				}

				//				System.out.println("_"+signatureArray[i]);


				if (  ( templates == 1  && templateStart ) ||
						( templates == 0 && (
								signatureArray[i] == ',' || 
								signatureArray[i] == '(' ||
								signatureArray[i] == ')' ||
								signatureArray[i] == '.' ||
								i == signatureArray.length - 1)
								)
						){
					int copyStart;
					if ( lastNamespaceToken == -1 ){
						copyStart = lastSeparator+1;

					} else {
						copyStart = lastNamespaceToken;

						if ( ! withinArguments ){
							copyStart = lastNamespaceTokenClass;
						}

						lastNamespaceToken = -1;

					}

					int end = i + 1;

					if ( templateStart ){
						end = i;
					}
					
//					if ( end == copyStart + 1 ){
//						finalSignature.deleteCharAt(finalSignature.length()-1 );
//					}
				
					String toAppend = signature.substring(copyStart, end);
					
					finalSignature.append( toAppend );
					lastSeparator = i;
				}

				if ( templates > 0 ){
					//					System.out.println("SKIP "+signatureArray[i]);
					continue;
				}

				if ( signatureArray[i] == '(' ){
					withinArguments = true;
				}

				//If not last
				if ( i != signatureArray.length - 1){
					if ( signatureArray[i] == ':' && signatureArray[i+1] == ':' ){
						if ( ! withinArguments ){
							lastNamespaceTokenClass = lastNamespaceToken;
						}
						lastNamespaceToken = i + 2;
					}
				}

			}

			return finalSignature.toString();
		} catch (Throwable t ){
			LOGGER.warning("Error removing namespace from "+signature+" returning raw signature.");
			t.printStackTrace();
			return signature;
		}
	}

	public static String getSignatureToPrint(MonitoringConfiguration mc,
			String signature) {
		return getProgramPointToPrint(mc, signature);
	}
	public static String getProgramPointToPrint(MonitoringConfiguration mc,
			String signature) {
		if ( mc == null || signature == null ){
			return signature;
		}

		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.JavaMonitoring ){
			return signature;
		}
		CConfiguration additonalConfig = null;
		switch ( mc.getConfigurationType() ){
		
		case MonitoringConfiguration.ConfigurationTypes.C_Config:
			additonalConfig = (CConfiguration) mc.getAdditionalConfiguration(CConfiguration.class);
			break;
		case MonitoringConfiguration.ConfigurationTypes.C_Regression_Config:
		case MonitoringConfiguration.ConfigurationTypes.VART_Config:
			additonalConfig = (CConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
			break;
		}

		if ( additonalConfig == null || additonalConfig.isUseDemangledNames() ){
			return signature;
		}

		return getDemangledProgramPoint(signature);
	}

	public static String getDemangledProgramPoint(String signature) {
		String demangled = getDemangledVersion(signature);

		if ( isPrintShorthenedVersion() ){
			String suffix = getProgramPointTypeAndLine(demangled);
			demangled = getSignatureWithoutProgramPointType(demangled);
			demangled = removeNamespacesFromSignature(demangled);
			demangled += suffix;
		}
		
		return demangled;
	}

	private static String getSignatureWithoutProgramPointType(String demangled) {
		int pos = identifyEndOfSignature(demangled);
		return demangled.substring(0, pos+1);
	}
	
	private static String getProgramPointTypeAndLine(String demangled) {
		int pos = identifyEndOfSignature(demangled);
		if ( pos >= demangled.length() ){
			return "";
		}
		return demangled.substring(pos+1);
	}

	private static int identifyEndOfSignature(String demangled) {
		return demangled.lastIndexOf(')');
	}

	private static boolean isPrintShorthenedVersion() {
		return true;
	}

	public static String getBytecodeSignatureOnly(MonitoringConfiguration mc,
			String signature) {

		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.JavaMonitoring ){
			return signature;
		}

		return TraceUtils.getFunctionName(signature);
	}



}
