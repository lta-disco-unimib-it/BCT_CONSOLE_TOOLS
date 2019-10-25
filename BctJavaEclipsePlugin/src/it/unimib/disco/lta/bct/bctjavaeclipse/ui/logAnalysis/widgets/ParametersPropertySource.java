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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * Property source to visualize parameters data
 * 
 * @author Fabrizio Pastore
 *
 */
public class ParametersPropertySource  implements IPropertySource  {

	
	private Map<String, String> parametersMap;

	public ParametersPropertySource(Map<String, String> sortedMap ){
		parametersMap = sortedMap;
	}

	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] propertyDescriptors = new PropertyDescriptor[parametersMap.size()];
		
		int i = 0;
		for ( String name : parametersMap.keySet()) {
			propertyDescriptors[i] = new PropertyDescriptor(name, name);
			i++;
		}
		return propertyDescriptors;
	}

	public Object getPropertyValue(Object id) {
		return parametersMap.get(id);
	}

	public boolean isPropertySet(Object id) {
		return parametersMap.containsKey(id);
	}

	public void resetPropertyValue(Object id) {
		
	}

	public void setPropertyValue(Object id, Object value) {
		
	}

}
