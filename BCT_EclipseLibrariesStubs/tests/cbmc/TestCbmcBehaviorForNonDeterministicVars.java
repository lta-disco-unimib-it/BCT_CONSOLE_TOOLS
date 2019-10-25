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
