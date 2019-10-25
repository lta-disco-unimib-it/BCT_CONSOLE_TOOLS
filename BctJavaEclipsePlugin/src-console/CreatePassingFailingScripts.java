import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.FileUtil;


public class CreatePassingFailingScripts {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File script = new File(args[0]);
		File referenceOutputs = new File(args[1]);
		File resultOutputs = new File(args[2]);
		
		
		ArrayList<String> passingTests = new ArrayList<String>();
		ArrayList<String> failingTests = new ArrayList<String>();
		
		identifyPassingFailingTests( referenceOutputs, resultOutputs, passingTests, failingTests );
	
		
		
		generateScripts( script, passingTests, failingTests );
	}

	private static void generateScripts(File script,
			ArrayList<String> passingTests, ArrayList<String> failingTests) throws IOException {
		File passingScript = new File( script.getParentFile(), "passing."+script.getName() );
		File failingScript = new File( script.getParentFile(), "failing."+script.getName() );
		
		BufferedWriter passingWriter = new BufferedWriter(new FileWriter(passingScript));
		BufferedWriter failingWriter = new BufferedWriter(new FileWriter(failingScript));
		
		BufferedReader reader = new BufferedReader(new FileReader(script));
		
		String line;
		
		while ( ( line = reader.readLine() ) != null ){
			
			processLine(passingTests, failingTests, passingWriter,
					failingWriter, line);
			
		}
		
		passingWriter.close();
		failingWriter.close();
	}

	public static void processLine(ArrayList<String> passingTests,
			ArrayList<String> failingTests, BufferedWriter passingWriter,
			BufferedWriter failingWriter, String line) throws IOException {
		boolean isFaling = endsWith( line , failingTests ); 
		
		boolean isPassing = endsWith( line , passingTests );
		
		if ( isFaling || isPassing ){
			if ( isFaling ){
				failingWriter.write(line);
				failingWriter.newLine();
			} else {
				passingWriter.write(line);
				passingWriter.newLine();
			}
		} else {
			passingWriter.write(line);
			passingWriter.newLine();
			failingWriter.write(line);
			failingWriter.newLine();
		}
	}

	private static boolean endsWith(String line, ArrayList<String> failingTests) {
		for ( String testName : failingTests ){
			if ( line.endsWith(testName) || line.endsWith(testName+"\"") ){
				return true;
			}
		}
		return false;
	}

	private static void identifyPassingFailingTests(File referenceOutputs,
			File resultOutputs, ArrayList<String> passingTests,
			ArrayList<String> failingTests) {
		
		
		File[] validFiles = referenceOutputs.listFiles();

		for ( File validFile : validFiles ){
			File newFile = new File ( resultOutputs, validFile.getName() );
			
			if ( ! newFile.exists() ){
				failingTests.add(validFile.getName());
			}
			
			if ( contentEqual( validFile, newFile ) ){
				passingTests.add(validFile.getName());
			} else {
				failingTests.add(validFile.getName());
			}
		}
		
		
	}

	private static boolean contentEqual(File validFile, File newFile)  {
		try {
			List<String> validLines = FileUtil.getLines(validFile);
			List<String> faultyLines = FileUtil.getLines(newFile);

			return validLines.equals(faultyLines);
		} catch ( IOException e ){
			return false;
		}
	}

}
