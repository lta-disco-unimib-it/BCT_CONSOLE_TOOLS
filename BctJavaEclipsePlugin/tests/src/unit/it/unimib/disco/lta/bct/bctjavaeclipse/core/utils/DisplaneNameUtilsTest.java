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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class DisplaneNameUtilsTest {

	@Test
	public void testRemoveNamespacesFromSignature(){
		
//		assertEquals( "map.run(string arg1,string arg2)",
//		DisplayNamesUtil.removeNamespacesFromSignature("std::map.run(std::string arg1,std::string arg2)") );
		
		
		//WorkersMap::WorkersMap() 
		assertEquals ( "Data::getData():29", DisplayNamesUtil.removeNamespacesFromSignature("Data::getData():29") );
		
		
		assertEquals( "WorkersMap::getAverageSalary(list)",
				DisplayNamesUtil.removeNamespacesFromSignature("WorkersMap::getAverageSalary(std::list<std::basic_string<char, std::char_traits<char>, std::allocator<char> >, std::allocator<std::basic_string<char, std::char_traits<char>, std::allocator<char> > > >)") );
	
	
		assertEquals( "Kernel::create_configuration(basic_string const&)",
				DisplayNamesUtil.removeNamespacesFromSignature("Isograft::Framework::Kernel::create_configuration(std::basic_string<char,std::char_traits<char>,std::allocator<char>>const&)") );
		
		
//		assertEquals( "WorkersMap::getAverageSalary(list)",
//				DisplayNamesUtil.removeNamespacesFromSignature("Isograft::Map" +
//						"<std::basic_string<char,std::char_traits<char>,std::allocator<char>>,Isograft::Framework::Configuration*,Isograft::Invalid_Key<std::basic_string<char" +
//						"(std::basic_string<char,std::char_traits<char>,std::allocator<char>>const&)") );
		
	}
	
	@Test
	public void testGetDemangledProgramPoint(){
		
		assertEquals ( "Data::getData():27 :::ENTER ", DisplayNamesUtil.getDemangledProgramPoint("_ZN4Data7getDataEv:27 :::ENTER ") );
		assertEquals ( "Data::getData():29 :::ENTER ", DisplayNamesUtil.getDemangledProgramPoint("_ZN4Data7getDataEv:29 :::ENTER ") );
		assertEquals ( "Data::getData():29 ", DisplayNamesUtil.getDemangledProgramPoint("_ZN4Data7getDataEv:29 ") );
		assertEquals ( "Data::getData():29", DisplayNamesUtil.getDemangledProgramPoint("_ZN4Data7getDataEv:29") );
	}
	

}
