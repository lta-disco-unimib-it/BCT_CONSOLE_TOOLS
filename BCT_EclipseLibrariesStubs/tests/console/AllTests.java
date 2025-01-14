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
package console;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import junit.textui.TestRunner;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
		CDTStandaloneCFileAnalyzerTest.class,
		TestABB11_Cpp.class, 
		TestABB11_V3_Cpp.class,
		TestABB11_V4_Cpp.class, 
//		TestCBMCIntegration_Pointers.class,
//		
//		TestCBMCIntegration_VSSE_filtersAndFileFinding.class,
//		TestCBMCIntegration_VSSE_withFiltering.class,
//		TestCBMCIntegration_VSSE.class,
//		
//		TestCBMCIntegration.class,
		TestEnterInput_Cpp.class,
		TestFloat_Cpp.class,
		TestFork_Cpp.class,
	
		TestLongLong_Cpp.class,

		TestModifiedReturnValue_Cpp.class,
		TestModifiedReturnValueISSRE_CConfig_Cpp.class,
		TestModifiedReturnValueISSRE_Cpp.class,
		TestModifiedReturnValueSelf_Cpp.class,
		
		TestNotInitializedVars_Cpp.class,
		
		TestStructPointers_Cpp.class
		})
public class AllTests {

	public static void main(String args[]) throws InitializationError{
		
		
		Result result = JUnitCore.runClasses(AllTests.class);
		
		System.out.flush();
		System.err.flush();
		
		System.out.println("Test cases executed: "+result.getRunCount() );
		System.out.println("Test cases ignored: "+result.getIgnoreCount() );
		System.out.println("Test cases failed: "+result.getFailureCount() );
		
		
		for ( Failure f : result.getFailures() ){
			System.err.println(f.getDescription().getDisplayName());
			System.err.println(f.getMessage());
			System.err.println(f.getTestHeader());
			System.err.println(f.getException().getMessage());
			f.getException().printStackTrace();
			
		}
		
	}
}
