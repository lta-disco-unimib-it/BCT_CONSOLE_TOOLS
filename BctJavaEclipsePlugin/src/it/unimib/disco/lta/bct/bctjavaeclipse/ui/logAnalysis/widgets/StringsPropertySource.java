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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class StringsPropertySource implements IPropertySource {

	private String textToDisplay;
	private String[] values;
	private boolean noDesc;

	public StringsPropertySource(String textToDisplay, String[] values) {
		this(textToDisplay, values, false);
	}
	
	public StringsPropertySource(String textToDisplay, String[] values, boolean noDesc) {
		this.textToDisplay = textToDisplay;
		this.values = values;
		this.noDesc = noDesc;
	}
	
	
	
	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] propertyDescriptors = new PropertyDescriptor[values.length];
		
		for (int i = 0; i < propertyDescriptors.length; i++) {
			int pos = (i + 1);
			String posText;
			if ( pos < 10 ){
				posText = "0"+pos;
			}else {
				posText = String.valueOf(pos);
			}
			
			if ( noDesc ){
				propertyDescriptors[i] = new PropertyDescriptor(i, "" );
			} else {
				propertyDescriptors[i] = new PropertyDescriptor(i, textToDisplay + " " +posText );
			}
		}
		return propertyDescriptors;
	}

	public Object getPropertyValue(Object id) {
		int index = Integer.parseInt(id.toString());
		return values[index];
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {
	}

	public void setPropertyValue(Object id, Object value) {
	}
}
