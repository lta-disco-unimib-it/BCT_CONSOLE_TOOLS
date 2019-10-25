package console;

import conf.management.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CBCMRegressionsDetector;

public class VARTScriptsGenerator  extends ScriptsGenerator  {
	///PROPERTIES-DESCRIPTION: Options that manage the generation of the VART configuration

	
	///if false VART uses as entry points the callers of the modified functions, if true uses only the modified function (default is false)
	public static final String VART_VALIDATE_CHANGED_FUNCTIONS_ONLY = "vart.validateChangedFunctionsOnly";
	
	///indicates whether or not VART has to check if assertions are covered or not (default is true)
	public static final String VART_CHECK_ASSERTIONS_COVERAGE = "vart.checkAssertionsCoverage";
	
	///indicates if the monitored test cases are random test cases without assertions, in this case the filtering step is not executed (and the upgraded software is not monitored)
	public static final String VART_USING_RANDOM_TESTS = "vart.usingRandomTests";
	
	///indicates whether or not to use the makefile for monitoring (the monitored target is test) (default is true)
	public static final String VART_USE_MAKEFILE_FOR_MONITORING = "vart.useMakefileForMonitoring";

	///if true the minimum integer used is not  -2147483648 but -2147483647, it is necessary to have always - (-x) == x (default is false)
	private static final String VART_CBMC_USE_SIMMETRIC_INTEGERS = "vart.cbmc.useSimmetricIntegers";
	
	public VARTScriptsGenerator(){
		super();
		this.setMonitorCallersOfModifiedFunctions(true);
		this.setTraceAllLinesOfMonitoredFunctions(true);
		this.setMonitorCallersOfModifiedFunctions(true);
		this.setTraceAllLinesOfMonitoredFunctions(true);
		
		this.setExcludeUnusedVariables(false);
		this.setMonitorCallersOfModifiedFunctions(true);
		this.setTraceAllLinesOfMonitoredFunctions(true);
		this.setCompleteMonitoring(false);
		
		ConfigurationFilesManager.setDEFAULT_ONE_OF_SIZE("2");
	}

	@Override
	public void createAdditionalConfigurations(String originalSrc,
			String originalExec, MonitoringConfiguration mc) {
		super.createAdditionalConfigurations(originalSrc, originalExec, mc);
		
		VARTRegressionConfiguration vartConfig = createVartConfiguration();
		mc.putAdditionalConfiguration(VARTRegressionConfiguration.class, vartConfig );
	}
	
	
	private VARTRegressionConfiguration createVartConfiguration() {
		VARTRegressionConfiguration vartConfig = new VARTRegressionConfiguration();
		
		updateVartConfiguration( vartConfig );
		
		return vartConfig;
	}

	private void updateVartConfiguration(VARTRegressionConfiguration vartConfig) {
		{
			String val = System.getProperty(VART_CHECK_ASSERTIONS_COVERAGE);
			if ( val != null ){
				vartConfig.setCheckAssertionsCoverage(Boolean.parseBoolean(val));
			}
		}
		{	
			String useMakefileForMonitoringString = System.getProperty( VART_USE_MAKEFILE_FOR_MONITORING );
			if ( useMakefileForMonitoringString != null ){
				vartConfig.setUseMakefileForMonitoring(Boolean.parseBoolean(useMakefileForMonitoringString));
			}
		}

		{	
			String usingRandomTestsString = System.getProperty( VART_USING_RANDOM_TESTS );
			if ( usingRandomTestsString != null ){
				vartConfig.setUsingRandomTests(Boolean.parseBoolean(usingRandomTestsString));
			}
		}
		
		{	
			String unwindString = System.getProperty( CBCMRegressionsDetector.BCT_CBMC_UNWIND );
			if ( unwindString != null ){
				vartConfig.setUnwind(Integer.parseInt(unwindString));
			}
		}
		
		{	
			String redefineMathFunc = System.getProperty( CBCMRegressionsDetector.BCT_REDEFINE_MATH_FUNCS );
			if ( redefineMathFunc != null ){
				vartConfig.setOptimizeSpeed(Boolean.parseBoolean(redefineMathFunc));
			}
		}
		
		{	
			String redefineMathFunc = System.getProperty( VART_VALIDATE_CHANGED_FUNCTIONS_ONLY );
			if ( redefineMathFunc != null ){
				vartConfig.setValidateChangedFunctionsOnly(Boolean.parseBoolean(redefineMathFunc));
			}
		}
		
		{	
			String redefineMathFunc = System.getProperty( VART_CBMC_USE_SIMMETRIC_INTEGERS );
			if ( redefineMathFunc != null ){
				vartConfig.setUseSimmetricIntegers(Boolean.parseBoolean(redefineMathFunc));
			}
		}
		
		
		
	}
	
	
}
