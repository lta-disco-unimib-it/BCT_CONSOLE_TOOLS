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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

/**
 * This VerifyListener verifies that the inserted chars are digit
 * 
 * @author Fabrizio Pastore
 *
 */
public class DigitVerifyListener implements VerifyListener {

	public void verifyText(VerifyEvent e) {
		
		//we check the text inserted directly
		//another option is to check the char pressed but in this case we would have to manage also delete and other special chars 
		int size = e.text.length();
		for ( int i = 0; i < size; ++i ){
			char letter = e.text.charAt(i);
			if ( ! ( letter >= '0' && letter<='9' ) ){
				e.doit = false; 
			}
		}
	}

}
