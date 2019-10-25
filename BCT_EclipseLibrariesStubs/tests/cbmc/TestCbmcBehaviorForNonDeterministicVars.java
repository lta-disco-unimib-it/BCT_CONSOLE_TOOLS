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
package cbmc;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import console.TestsRunner;
import console.TestsUtil;

import util.FileUtil;
import util.ProcessRunner;


public class TestCbmcBehaviorForNonDeterministicVars {

	@Test
	public void testCbmc() throws IOException{
		StringBuffer _outputBuffer = new StringBuffer();
		List<String> command = new ArrayList<String>();
		command.add("make");
		command.add("all");
		File executionDir = new File ( TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/CbmcStaticNondeterministic" ) );

		int exitCode = ProcessRunner.run(command, _outputBuffer, null, 0, executionDir, null );
		
		assertEquals( 0, exitCode );
		
		checkAllFailed(_outputBuffer);
	}

	private void checkAllFailed(StringBuffer cbmcOutput) throws IOException {

		List<String> lines = FileUtil.getLines( new BufferedReader( new StringReader(cbmcOutput.toString() ) ) );
		
		boolean results = false;
		for ( String line : lines ){
			if ( line.startsWith("** Results") || line.startsWith("**Results") ){
				results = true;
				continue;
			}
			if ( results && ! line.isEmpty() && ! line.startsWith("**") ){
				assertTrue("Wrong line: "+line, line.endsWith("FAILED"));
			}
		}
	}
}
