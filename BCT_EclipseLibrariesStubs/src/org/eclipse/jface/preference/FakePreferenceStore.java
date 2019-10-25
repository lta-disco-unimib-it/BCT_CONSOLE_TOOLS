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
