package cpp.gdb.coverage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import console.TestsRunner;
import cpp.gdb.coverage.BranchRegressionSuiteGenerator.Suite;
import cpp.gdb.coverage.BranchRegressionSuiteGenerator.TestCoverage;

public class BranchRegressionSuiteGeneratorTest {

	
	private static File workingDir = new File ( TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/BranchRegressionSuiteGenerator.1" ) );
	
	static {
		System.setProperty("java.library.path", "/usr/local/lib");
	}


	private static File generatedCoverageFile4;
	private static File generatedCoverageFile3;
	private static File generatedCoverageFile2;
	private static File generatedCoverageFile1;
	
	protected BranchRegressionSuiteGenerator gen;
	
	@BeforeClass
	public static void before(){
		
		generatedCoverageFile1 = new File( workingDir, "fcov.1.ser" );
		generatedCoverageFile2 = new File( workingDir, "fcov.2.ser" );
		generatedCoverageFile3 = new File( workingDir, "fcov.3.ser" );
		generatedCoverageFile4 = new File( workingDir, "fcov.4.ser" );
		
		
		
		Hashtable<FileNameAndCoverageKey<BranchId>, Integer> map1 = createMap1();
		GCovParser.storeCoverageMap(map1, generatedCoverageFile1.getAbsolutePath());
		
		
		Hashtable<FileNameAndCoverageKey<BranchId>, Integer> map2 = createMap2();
		GCovParser.storeCoverageMap(map2, generatedCoverageFile2.getAbsolutePath());
		
		Hashtable<FileNameAndCoverageKey<BranchId>, Integer> map3 = createMap3();
		GCovParser.storeCoverageMap(map3, generatedCoverageFile3.getAbsolutePath());
		
		Hashtable<FileNameAndCoverageKey<BranchId>, Integer> map4 = createMap4();
		GCovParser.storeCoverageMap(map4, generatedCoverageFile4.getAbsolutePath());
		
		
	}
	
	@Before
	public void beforeTest(){
			gen = new BranchRegressionSuiteGenerator();
	}

	@After
	public void afterTest(){
		gen = null;
	}
	
	private static Hashtable<FileNameAndCoverageKey<BranchId>, Integer> createMap2() {
		Hashtable<FileNameAndCoverageKey<BranchId>, Integer> map1 = new Hashtable<FileNameAndCoverageKey<BranchId>, Integer>();
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(12, 0)), 1);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(12, 1)), 0);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 0)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 1)), 1);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 2)), 0);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 0)), 1);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 1)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 2)), 1);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 0)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 1)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 2)), 0);
		return map1;
	}

	public static Hashtable<FileNameAndCoverageKey<BranchId>, Integer> createMap1() {
		Hashtable<FileNameAndCoverageKey<BranchId>, Integer> map1 = new Hashtable<FileNameAndCoverageKey<BranchId>, Integer>();
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(12, 0)), 1);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(12, 1)), 1);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 0)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 1)), 1);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 2)), 1);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 0)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 1)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 2)), 0);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 0)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 1)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 2)), 0);
		return map1;
	}
	
	public static Hashtable<FileNameAndCoverageKey<BranchId>, Integer> createMap3() {
		Hashtable<FileNameAndCoverageKey<BranchId>, Integer> map1 = new Hashtable<FileNameAndCoverageKey<BranchId>, Integer>();
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(12, 0)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(12, 1)), 0);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 0)), 1);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 1)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 2)), 0);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 0)), 1);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 1)), 1);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 2)), 0);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 0)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 1)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 2)), 0);
		return map1;
	}
	
	public static Hashtable<FileNameAndCoverageKey<BranchId>, Integer> createMap4() {
		Hashtable<FileNameAndCoverageKey<BranchId>, Integer> map1 = new Hashtable<FileNameAndCoverageKey<BranchId>, Integer>();
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(12, 0)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(12, 1)), 0);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 0)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 1)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(13, 2)), 0);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 0)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 1)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file1.c", new BranchId(14, 2)), 0);
		
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 0)), 0);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 1)), 2);
		map1.put(new FileNameAndCoverageKey<BranchId>("/home/file2.c", new BranchId(14, 2)), 2);
		return map1;
	}
	
	@Test
	public void test_oneTest() {
		
		
		
		File coverageFile = new File( workingDir, "coverage.1.ser" );
		
		
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 173);
		
		String[] args = new String[]{coverageFile.getAbsolutePath()};
		
		Suite suite = gen.generateSuite(args);

		System.out.println(suite.getRequiredTests());
		System.out.println(suite.getAdditionalTests());
		
		assertEquals( 1, suite.getRequiredTests().size() );
		
		assertArrayEquals ( new String[]{"1"}, getTests( suite.getRequiredTests() ) );
	}

	@Test
	public void test_bug_437() {
		
		
		
		File coverageFile = new File( workingDir, "coverage.437.ser" );
		
		
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP6/V1/src/regex.c", 5538);
		
		String[] args = new String[]{coverageFile.getAbsolutePath()};
		
		Suite suite = gen.generateSuite(args);

		System.out.println(suite.getRequiredTests());
		System.out.println(suite.getAdditionalTests());
		
		assertEquals( 1, suite.getRequiredTests().size() );
		
		assertArrayEquals ( new String[]{"437"}, getTests( suite.getRequiredTests() ) );
		
		System.out.println(suite.getRequiredTests().get(0).getCoveredBranchesSet());
		assertEquals(1, suite.getRequiredTests().get(0).getCoveredBranches() );
	}
	
	@Test
	public void test_twoTestsSameCoverage() {
		
		
		File coverageFile1 = new File( workingDir, "coverage.1.ser" );
		File coverageFile2 = new File( workingDir, "coverage.2.ser" );
		
		
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 173);
		
		String[] args = new String[]{coverageFile1.getAbsolutePath(), coverageFile2.getAbsolutePath()};
		
		Suite suite = gen.generateSuite(args);

		System.out.println(suite.getRequiredTests());
		System.out.println(suite.getAdditionalTests());
		
		assertEquals( 1, suite.getRequiredTests().size() );
		assertEquals( 1, suite.getAdditionalTests().size() );
		
		assertArrayEquals( new String[]{"1"}, getTests( suite.getRequiredTests() ) );
		assertArrayEquals( new String[]{"2"}, getTests( suite.getAdditionalTests() ) );
	}
	
	
	private String[] getTests(List<TestCoverage> requiredTests) {
		ArrayList<String> list = new ArrayList<String>(requiredTests.size());
		
		for ( TestCoverage t : requiredTests ){
			String name = t.getTestName();
			int start = name.indexOf('.');
			int end = name.lastIndexOf('.');
			list.add(name.substring(start+1,end));
		}
		
		return list.toArray(new String[list.size()]);
	}


	@Test
	public void test_twoTests_TwoCoversEverything() {
		
		
		File coverageFile1 = new File( workingDir, "coverage.1.ser" );
		File coverageFile2 = new File( workingDir, "coverage.2.ser" );
		
		
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 173);
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 487);
		
		String[] args = new String[]{coverageFile1.getAbsolutePath(), coverageFile2.getAbsolutePath()};
		
		Suite suite = gen.generateSuite(args);

		System.out.println(suite.getRequiredTests());
		System.out.println(suite.getAdditionalTests());
		
		assertEquals( 1, suite.getRequiredTests().size() );
		assertEquals( 1, suite.getAdditionalTests().size() );
		
		assertArrayEquals( new String[]{"2"}, getTests( suite.getRequiredTests() ) );
		assertArrayEquals( new String[]{"1"}, getTests( suite.getAdditionalTests() ) );
	}
	
	@Test
	public void test_twoTests_OneCovers442_21__TwoCovers442_3() {
		
		
		File coverageFile1 = new File( workingDir, "coverage.1.ser" );
		File coverageFile2 = new File( workingDir, "coverage.2.ser" );
		
		
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 173);
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 442);
		
		String[] args = new String[]{coverageFile1.getAbsolutePath(), coverageFile2.getAbsolutePath()};
		
		Suite suite = gen.generateSuite(args);

		System.out.println(suite.getRequiredTests());
		System.out.println(suite.getAdditionalTests());
		
		assertEquals( 2, suite.getRequiredTests().size() );
		assertEquals( 0, suite.getAdditionalTests().size() );
		
		assertArrayEquals( new String[]{"1","2"}, sort ( getTests( suite.getRequiredTests() ) ) );
		assertArrayEquals( new String[]{}, getTests( suite.getAdditionalTests() ) );
	}
	
	@Test
	public void test_twoTests_OneCovers442_21__TwoCovers442_3__TwoCovers487() {
		
		
		File coverageFile1 = new File( workingDir, "coverage.1.ser" );
		File coverageFile2 = new File( workingDir, "coverage.2.ser" );
		
		
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 173);
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 442);
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 487);
		
		String[] args = new String[]{coverageFile1.getAbsolutePath(), coverageFile2.getAbsolutePath()};
		
		Suite suite = gen.generateSuite(args);

		System.out.println(suite.getRequiredTests());
		System.out.println(suite.getAdditionalTests());
		
		assertEquals( 2, suite.getRequiredTests().size() );
		assertEquals( 0, suite.getAdditionalTests().size() );
		
		assertArrayEquals( new String[]{"2","1"}, getTests( suite.getRequiredTests() ) );
		assertArrayEquals( new String[]{}, getTests( suite.getAdditionalTests() ) );
	}
	
	
	@Test
	public void test_twoTests_OneCovers442_21__TwoCovers442_3__TwoCovers487__ThreeCovers202() {
		
		
		File coverageFile1 = new File( workingDir, "coverage.1.ser" );
		File coverageFile2 = new File( workingDir, "coverage.2.ser" );
		File coverageFile3 = new File( workingDir, "coverage.3.ser" );
		
		
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 173);	// 1(0,1) 2(0,1) 3(0,1)
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 442);	// 1(21) 2(3) 3(0,10) 
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/dfa.c", 487); // 2(0,1)
		gen.addLineToInclude("/mnt/WesternTera-Ext3/BCT/grep/Experiments/v3_v4_KP8/V1/src/search.c", 202); //3 (0,2,3) 
		
		String[] args = new String[]{coverageFile1.getAbsolutePath(), coverageFile2.getAbsolutePath(), coverageFile3.getAbsolutePath()};
		
		Suite suite = gen.generateSuite(args);

		System.out.println(suite.getRequiredTests());
		System.out.println(suite.getAdditionalTests());
		
		assertEquals( 3, suite.getRequiredTests().size() );
		assertEquals( 0, suite.getAdditionalTests().size() );
		
		assertArrayEquals( new String[]{"3","2","1"}, getTests( suite.getRequiredTests() ) );
		assertArrayEquals( new String[]{}, getTests( suite.getAdditionalTests() ) );
		
		
		List<TestCoverage> req = suite.getRequiredTests();
		req.get(0).getCoveredBranches();
	}
	
	
	@Test
	public void testMultiMaps() {
		
		
		
		
		String[] args = new String[]{generatedCoverageFile1.getAbsolutePath(), generatedCoverageFile2.getAbsolutePath(), generatedCoverageFile3.getAbsolutePath(), generatedCoverageFile4.getAbsolutePath()};
		
		Suite suite = gen.generateSuite(args);

		System.out.println(suite.getRequiredTests());
		System.out.println(suite.getAdditionalTests());
		
		assertEquals( 4, suite.getRequiredTests().size() );
		assertEquals( 0, suite.getAdditionalTests().size() );
		
		assertArrayEquals( new String[]{"1","3","4","2"}, getTests( suite.getRequiredTests() ) );
		assertArrayEquals( new String[]{}, getTests( suite.getAdditionalTests() ) );
		
		
		List<TestCoverage> req = suite.getRequiredTests();
		System.out.println( req.get(0).getCoverageIncrease() );
		System.out.println( req.get(1).getCoverageIncrease() );
		System.out.println( req.get(2).getCoverageIncrease() );
		System.out.println( req.get(3).getCoverageIncrease() );
	}


	private String[] sort(String[] tests) {
		Arrays.sort(tests);
		return tests;
	}

	@Test
	public void testHighLoad(){
		File dir = new File ( TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/BranchRegressionSuiteGenerator.2" ) );
		//FIXME: should unzip the allTests.zip file...
		File[] fs = dir.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith("ser");
			}
		});
		
		ArrayList<String> args = new ArrayList<String>();
		for ( File f : fs ){
			args.add( f.getAbsolutePath() );
		}
		gen.setGreedy(true);
		Suite suite = gen.generateSuite(args.toArray(new String[args.size()]));

		System.out.println(suite.getRequiredTests());
		System.out.println(suite.getAdditionalTests());
	}
}
