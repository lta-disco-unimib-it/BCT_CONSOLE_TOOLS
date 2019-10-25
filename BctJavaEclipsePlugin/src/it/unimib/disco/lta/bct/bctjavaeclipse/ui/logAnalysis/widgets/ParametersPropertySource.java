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
