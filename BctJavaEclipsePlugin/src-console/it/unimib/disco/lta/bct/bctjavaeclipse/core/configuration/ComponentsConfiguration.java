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
