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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.Environment;

public class ComponentsConfiguration {

	private String configurationName;
	private List<CallFilter> callFilters = new ArrayList<CallFilter>();
	private List<Component> components = new ArrayList<Component>() ;
	private Boolean monitorInternalCalls;
	
	public ComponentsConfiguration(){
		
	}
	
	public ComponentsConfiguration(String configurationName, 
			Collection<CallFilter> callFilters,
			Collection<Component> components,
			Boolean monitorInternalCalls) {
		this.configurationName = configurationName;
		this.callFilters.addAll( callFilters );
		this.components.addAll( components );
		this.monitorInternalCalls = monitorInternalCalls;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	public Collection<CallFilter> getCallFilters() {
		return callFilters;
	}

	public void setCallFilters(Collection<CallFilter> callFilters) {
		this.callFilters = new ArrayList<CallFilter>();
		callFilters.addAll(callFilters);
	}

	public Collection<Component> getComponents() {
		return components;
	}

	public void setComponents(Collection<Component> components) {
		this.components = new ArrayList<Component>();
		this.components.addAll(components);
	}

	public Boolean getMonitorInternalCalls() {
		if ( monitorInternalCalls == null ){
			return Boolean.FALSE;
		}
		return monitorInternalCalls;
	}

	public void setMonitorInternalCalls(Boolean monitorInternaCalls) {
		this.monitorInternalCalls = monitorInternaCalls;
	}

	public void setCallFilters(List<CallFilter> callFilters) {
		this.callFilters = callFilters;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public Environment getEnvironment(){
		return new Environment("(environment)",components);
	}
	
	public Component getComponentCoveringMethod( String bytecodeMethodSignature ){
		for ( Component c : components ){
			if ( c.acceptBytecodeMethodSignature(bytecodeMethodSignature) ){
				return c;
			}
		}
		return null;
	}
}
