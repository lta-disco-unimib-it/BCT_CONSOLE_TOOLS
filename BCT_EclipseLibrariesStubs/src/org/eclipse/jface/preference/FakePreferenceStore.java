package org.eclipse.jface.preference;

import java.util.HashMap;

public class FakePreferenceStore implements IPreferenceStore {

	private static HashMap<String,Object> preferences = new HashMap<String,Object>();
	
	@Override
	public String getString(String key) {
		if ( ! preferences.containsKey(key) ){
			return null;
		}
		return preferences.get(key).toString();
	}

	@Override
	public void setValue(String daikonpath, Object property) {
		preferences.put(daikonpath, property);
	}

}
