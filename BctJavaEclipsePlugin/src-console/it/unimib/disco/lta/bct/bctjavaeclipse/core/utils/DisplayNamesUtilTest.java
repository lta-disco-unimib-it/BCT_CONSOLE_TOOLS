package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class DisplayNamesUtilTest {

	@Test
	public void testRemoveNamespacesFromSignature() {
	
		String sig = "FASA::Framework::Cyclic_Timer<FASA::Framework::Scheduler>::run(FASA::Timeconst&,FASA::Timeconst&)";
		String actual = DisplayNamesUtil.removeNamespacesFromSignature(sig);
		
		String expected = "Framework::Cyclic_Timer<Framework::Scheduler>::run(Timeconst&,Timeconst&)";
		
		assertEquals( expected, actual );
	}

}
