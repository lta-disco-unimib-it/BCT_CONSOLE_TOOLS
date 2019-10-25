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

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.CDTStandaloneCFileAnalyzer;

import org.junit.Ignore;
import org.junit.Test;

import cpp.gdb.FunctionDeclaration;
import cpp.gdb.LocalVariableDeclaration;
import cpp.gdb.Parameter;

public class CDTStandaloneCFileAnalyzerTest {

	@Test
	public void testExtractFunctionDeclaration_FloatDouble() throws FileNotFoundException{
		CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();

		File f = new File("/home/BCT/workspace_BCT_Testing/Float_v2/src/program.c");
		List<FunctionDeclaration> functions = analyzer.retrieveFunctionsDeclaredInFile(f);

		ArrayList<FunctionDeclaration> expected = new ArrayList<FunctionDeclaration>();
		expected.add(new FunctionDeclaration("calculateDouble", "double", 1, paramList ( new Parameter("double","inp",false,false, 0) ) ));
		expected.add(new FunctionDeclaration("calculateFloat", "float", 1, paramList ( new Parameter("float","inp",false,false, 0) ) ));
		expected.add(new FunctionDeclaration("main", "int", 0, null));

		assertEquals( expected, functions );
	}

	private List<Parameter> paramList(Parameter... parameters) {
		ArrayList<Parameter> l = new ArrayList<Parameter>();
		for ( Parameter p : parameters ){
			l.add( p );
		}
		return l;
	}

	@Test
	public void testExtractFunctionDeclaration_FloatDoublePtrs() throws FileNotFoundException{
		CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();

		File f = new File("/home/BCT/workspace_BCT_Testing/Float_v2Ptr/src/program.c");
		List<FunctionDeclaration> functions = analyzer.retrieveFunctionsDeclaredInFile(f);

		ArrayList<FunctionDeclaration> expected = new ArrayList<FunctionDeclaration>();
		expected.add(new FunctionDeclaration("calculateDouble", "double*",1, paramList ( new Parameter("double","inp",false,false, 1) ) ));
		expected.add(new FunctionDeclaration("calculateFloat", "float*",1, paramList ( new Parameter("float","inp",false,false, 1) )));
		expected.add(new FunctionDeclaration("main", "int",0, new ArrayList<Parameter>() ));

		assertEquals( expected, functions );
	}

	//

	@Test
	public void testExtractFunctionDeclaration_CPP() throws FileNotFoundException{
		CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();

		File f = new File("/home/BCT/workspace_BCT_Testing/WorkersMap/src/WorkersMap.cpp");
		List<FunctionDeclaration> functions = analyzer.retrieveFunctionsDeclaredInFile(f);

		ArrayList<FunctionDeclaration> expected = new ArrayList<FunctionDeclaration>();
		expected.add(new FunctionDeclaration("WorkersMap::WorkersMap()", "WorkersMap", 0, new ArrayList<Parameter>() ));
		expected.add(new FunctionDeclaration("WorkersMap::~WorkersMap()", "WorkersMap", 0, new ArrayList<Parameter>()));
		expected.add(new FunctionDeclaration("WorkersMap::getSalary(string)", "long", 1, paramList ( new Parameter("string","workerId",false,false, 0) )  ));
		expected.add(new FunctionDeclaration("WorkersMap::addWorker(string,long)", "void", 2, paramList ( 
				new Parameter("string","personId",false,false, 0),
				new Parameter("long","annualSalary",false,false, 0)
				) ));
		expected.add(new FunctionDeclaration("WorkersMap::isWorker(string)", "bool", 1,
				paramList ( new Parameter("float","inp",false,false, 1)
						)));
		
		expected.add(new FunctionDeclaration("WorkersMap::getAverageSalary(list)", "long", 1, paramList ( new Parameter("list<string>","personIds",false,false, 1) )));

		assertEquals( expected, functions );
	}
	
	@Test
	public void testExtractFunctionDeclaration_Bug() throws FileNotFoundException{
		CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();

		File f = new File("/home/BCT/workspace_BCT_Testing/Bugs/fmt-merge-msg.c");
		List<FunctionDeclaration> functions = analyzer.retrieveFunctionsDeclaredInFile(f);
		
		{
			FunctionDeclaration fmt_merge_msg = retrieveFunction(functions, "fmt_merge_msg");

			assertNotNull(fmt_merge_msg);

			List<Parameter> allArgs = fmt_merge_msg.getAllArgs();
			String pname = "in";

			Parameter p = retrieveParameter( pname, allArgs );
			assertNotNull(p);

			assertTrue ( p.isPointer() );
		}
		
		
		
		
		{
			FunctionDeclaration fill_directory = retrieveFunction(functions, "fill_directory");
			assertNotNull(fill_directory);

			List<Parameter> allArgs = fill_directory.getAllArgs();
			String pname = "pathspec";

			Parameter p = retrieveParameter( pname, allArgs );
			assertNotNull(p);

			assertTrue ( p.isPointer() );
		}
		
		
		
		{
			FunctionDeclaration fill_directory = retrieveFunction(functions, "check_and_freshen_file");
			assertNotNull(fill_directory);

			List<Parameter> allArgs = fill_directory.getAllArgs();
			String pname = "fn";

			Parameter p = retrieveParameter( pname, allArgs );
			assertNotNull(p);

			assertTrue ( p.isPointer() );
			
			assertEquals("const char", p.getType());
		}
		
	}

	public FunctionDeclaration retrieveFunction(
			List<FunctionDeclaration> functions, String functionName) {
		FunctionDeclaration fmt_merge_msg = null;	
		for ( FunctionDeclaration ff : functions ){
			if ( ff.getName().equals(functionName) ){
				fmt_merge_msg = ff;
			}
		}
		return fmt_merge_msg;
	}

	private Parameter retrieveParameter(String pname, List<Parameter> allArgs) {
		for ( Parameter p : allArgs){
			if ( p.getName().equals(pname) ){
				return p;
			}
		}
		return null;
	}

	@Test
	public void testExtractFunctionDeclaration_ptr() throws FileNotFoundException{
		CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();

		{
			File f = new File("/home/BCT/workspace_BCT_Testing/CBMC_CPP_StructRefs_V0/program.cpp");
			List<FunctionDeclaration> functions = analyzer.retrieveFunctionsDeclaredInFile(f);

			ArrayList<FunctionDeclaration> expected = new ArrayList<FunctionDeclaration>();
			{	
				FunctionDeclaration fd = new FunctionDeclaration("processPoint", "unsigned int", 1,
						paramList ( new Parameter("Point","p",true,false, 1) )
						);
				Set<String> pointerArgs = new HashSet<String>();
				pointerArgs.add("point");
				fd.setPointerArgs(pointerArgs);
				expected.add(fd);
			}

			{	
				FunctionDeclaration fd = new FunctionDeclaration("main", "int", 2,
						paramList ( new Parameter("int","argc",false,false, 0) ,
						 new Parameter("char[]","argv",true,false, 0) )
						);
				Set<String> pointerArgs = new HashSet<String>();
				pointerArgs.add("argv");
				fd.setPointerArgs(pointerArgs);
				expected.add(fd);
			}


			assertEquals( expected, functions );

			{
				FunctionDeclaration processPointFunction = functions.get(0);
				List<LocalVariableDeclaration> localsFound = processPointFunction.getLocalVariables();

				List<LocalVariableDeclaration> expectedLocals =  new ArrayList<LocalVariableDeclaration>();
				expectedLocals.add(new LocalVariableDeclaration("refer", 7) );
				expectedLocals.add(new LocalVariableDeclaration("sum", 8) );
				System.out.println( localsFound);

				assertEquals(expectedLocals, localsFound);
			}

			{
				FunctionDeclaration processPointFunction = functions.get(1);
				List<LocalVariableDeclaration> localsFound = processPointFunction.getLocalVariables();

				List<LocalVariableDeclaration> expectedLocals =  new ArrayList<LocalVariableDeclaration>();
				expectedLocals.add(new LocalVariableDeclaration("pp", 14) );
				expectedLocals.add(new LocalVariableDeclaration("x", 16) );
				expectedLocals.add(new LocalVariableDeclaration("y", 17) );
				expectedLocals.add(new LocalVariableDeclaration("p", 23) );
				System.out.println( localsFound);

				assertEquals(expectedLocals, localsFound);
			}
		}


		{
			File f = new File("/home/BCT/workspace_BCT_Testing/CBMC_CPP_StructRefs_V0/PointProcessor.cpp");
			List<FunctionDeclaration> functions = analyzer.retrieveFunctionsDeclaredInFile(f);

			ArrayList<FunctionDeclaration> expected = new ArrayList<FunctionDeclaration>();
			expected.add(new FunctionDeclaration("PointProcessor::PointProcessor()", "PointProcessor", 0,
					paramList() ));
			expected.add(new FunctionDeclaration("PointProcessor::~PointProcessor()", "PointProcessor", 0,
					paramList() ));
			
			
			{	
				FunctionDeclaration fd = new FunctionDeclaration("PointProcessor::processPoint(Point*)", "unsigned int", 1,
						paramList ( new Parameter("Point","p",true,false, 1) )
						);
				Set<String> pointerArgs = new HashSet<String>();
				pointerArgs.add("p");
				fd.setPointerArgs(pointerArgs);
				expected.add(fd);
			}

			expected.add(new FunctionDeclaration("PointProcessor::processPoint(Point)", "unsigned int", 1,
					paramList ( new Parameter("Point","p",false,false, 0) )
					));

			{	
				FunctionDeclaration fd = new FunctionDeclaration("PointProcessor::processPoints(Point*,Point*)", "unsigned int", 2,
						
						paramList ( 
								new Parameter("Point","p1",true,false, 1),
								new Parameter("Point","p2",true,false, 1)
								)
						
						);
				Set<String> pointerArgs = new HashSet<String>();
				pointerArgs.add("p1");
				pointerArgs.add("p2");
				fd.setPointerArgs(pointerArgs);
				expected.add(fd);
			}



			assertEquals( expected, functions );
		}
	}


	@Test
	public void testExtractFunctionDeclaration_LongLong_C() throws FileNotFoundException{
		CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();

		File f = new File("/home/BCT/workspace_BCT_Testing/LongLong/src/program.c");
		List<FunctionDeclaration> functions = analyzer.retrieveFunctionsDeclaredInFile(f);

		ArrayList<FunctionDeclaration> expected = new ArrayList<FunctionDeclaration>();
		FunctionDeclaration fd = new FunctionDeclaration("calculateLong", "long long", 2,
				paramList ( 
						new Parameter("int","x",false,false, 0),
						new Parameter("long long","inp",false,false, 0)
						)
				
				);
		Set<String> scalarArgs = new HashSet<String>();
		scalarArgs.add("inp");
		scalarArgs.add("x");
		fd.setScalarArgs(scalarArgs);
		expected.add(fd);
		expected.add(new FunctionDeclaration("main", "int"));

		assertEquals( expected, functions );
	}


	@Test
	public void testExtractFunctionDeclaration_VariableParams_C() throws FileNotFoundException{
		CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();

		File f = new File("/home/BCT/workspace_BCT_Testing/coreutils.1/lib/error.c");
		List<FunctionDeclaration> functions = analyzer.retrieveFunctionsDeclaredInFile(f);

		FunctionDeclaration errorFunction = null;
		for ( FunctionDeclaration function : functions ){
			if ( function.getName().equals("error") ){
				errorFunction = function;
			}
		}

		assertNotNull(errorFunction);

		assertEquals ( 3, errorFunction.getParametersNumber() );
		assertTrue(errorFunction.takesVarArgs());

	}


	@Test
	public void testExtractFunctionDeclaration_counterDeclaredInFOrLoop() throws FileNotFoundException{

		{
			File f = new File("/home/BCT/workspace_BCT_Testing/WorkersMap/src/WorkersMapTest.cpp");
			HashMap<String,LocalVariableDeclaration> toCheck = new HashMap<String,LocalVariableDeclaration>();
			toCheck.put("i", new LocalVariableDeclaration("i", 192, 195));
			checkLocalVariablesDeclaredInFunction(f, toCheck);
		}

		{
			File f = new File("/home/BCT/workspace_BCT_Testing/LocalVariables/src/WorkersMapTest.cpp");
			HashMap<String,LocalVariableDeclaration> toCheck = new HashMap<String,LocalVariableDeclaration>();
			toCheck.put("blockVariable", new LocalVariableDeclaration("blockVariable", 201, 203));
			toCheck.put("exitCode", new LocalVariableDeclaration("exitCode", 188, 219));
			toCheck.put("j", new LocalVariableDeclaration("j", 193, 197));
			toCheck.put("i", new LocalVariableDeclaration("i", 192, 197));
			checkLocalVariablesDeclaredInFunction(f, toCheck);
		}

	}

	public void checkLocalVariablesDeclaredInFunction(File f,
			HashMap<String, LocalVariableDeclaration> toCheck)
					throws FileNotFoundException {
		CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();
		List<FunctionDeclaration> functions = analyzer.retrieveFunctionsDeclaredInFile(f);

		FunctionDeclaration mainFunction = null;
		for ( FunctionDeclaration function : functions ){
			if ( function.getName().equals("main") ){
				mainFunction = function;
			}
		}

		assertNotNull(mainFunction);


		List<LocalVariableDeclaration> locals = mainFunction.getLocalVariables();
		for ( LocalVariableDeclaration localVar : locals){
			String vName = localVar.getName();

			if ( toCheck.containsKey(vName) ){
				LocalVariableDeclaration expected = toCheck.get(vName);
				assertEquals("Unexpected line number for variable "+vName, expected.getLineNo(), localVar.getLineNo() );
				assertEquals("Unexpected end line number for variable "+vName, expected.getScopeEndLine(), localVar.getScopeEndLine() );
			}
		}
	}


}
