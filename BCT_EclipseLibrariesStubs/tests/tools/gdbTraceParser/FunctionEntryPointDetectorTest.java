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
package tools.gdbTraceParser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import tools.gdbTraceParser.FunctionEntryPointDetector.FunctionEntryPoint;
import util.HexUtil;



import console.TestsRunner;

public class FunctionEntryPointDetectorTest {

	private static String originalSrc =  TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/VSSE_short_v1/" );
	private static File originalProgram = new File ( TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/VSSE_short_v1/program" ) );
	
	private static String originalSrcCpp = TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/Store_V1/" );
	private static File originalProgramCpp = new File ( TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/Store_V1/Store" ) );
	
	{
		try {
			TestsRunner.compile( originalSrc );
			TestsRunner.compile( originalSrcCpp );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetFunctionEntryPoint() {
		FunctionEntryPointDetector detector = new FunctionEntryPointDetector(originalProgram);
		
		SymbolsExtractor se = new SymbolsExtractor();
		String mangledName = "available_items";
		String address = se.getAddressForFunction(originalProgram.getAbsolutePath(), mangledName );
		
		FunctionEntryPoint entryPoint = detector.getFunctionEntryPoint(mangledName,address);
		
		
		assertEquals( 30 , entryPoint.getLine() );
		
		long expected = HexUtil.hexToLong(address);
		long actual = HexUtil.hexToLong( entryPoint.getAddress() );	
		
		assertEquals( expected+12, actual);
	//	assertTrue( differButLowerThan( actual, expected, 20 ) );
	}
	
	@Test
	public void testGetFunctionEntryPointCpp() {
		SymbolsExtractor se = new SymbolsExtractor();
		
		FunctionEntryPointDetector detector = new FunctionEntryPointDetector(originalProgramCpp);
		
		
		String mangledName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE";
		String address = se.getAddressForFunction(originalProgramCpp.getAbsolutePath(), mangledName );
		//the address returned is the address of the first instruction, which is usually not good for tracing the function entry point 
		//detector should return something different
		FunctionEntryPoint entryPoint = detector.getFunctionEntryPoint(mangledName,address);
		

		assertEquals( 24 , entryPoint.getLine() );
		
		long expected = HexUtil.hexToLong(address);
		long actual = HexUtil.hexToLong( entryPoint.getAddress() );	
		
		assertEquals( expected + 23 , actual);
		//assertTrue( differButLowerThan( actual, expected, 20 ) );
		
	}

	private boolean differButLowerThan(long actual, long expected, int delta) {
		double diff = ( actual - expected );
		return diff > 0 && diff < delta;
	}

}
