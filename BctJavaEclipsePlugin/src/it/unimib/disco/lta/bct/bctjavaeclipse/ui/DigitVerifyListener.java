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
