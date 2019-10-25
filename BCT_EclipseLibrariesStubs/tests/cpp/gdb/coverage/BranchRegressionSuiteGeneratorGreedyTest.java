package cpp.gdb.coverage;

import org.junit.Before;

public class BranchRegressionSuiteGeneratorGreedyTest extends BranchRegressionSuiteGeneratorTest {

	public BranchRegressionSuiteGeneratorGreedyTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void beforeTest(){

		gen = new BranchRegressionSuiteGenerator();
		gen.setGreedy(true);
		
	}

}
