package org.cprover.model;

import org.cprover.communication.Assignment;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

public class VerifyVariable implements IVariable, IValue {

    private ILaunch launch;
    
    private String name;
    
    private String type;
    
    private String value;
    
    private boolean valueChanged;

	private int order;
    
    public VerifyVariable(ILaunch launch, Assignment assign, boolean valueChanged, int order ) {
        this.launch = launch;
        this.name = assign.identifier;
        this.type = assign.type;
        this.value = assign.value;
        this.valueChanged = valueChanged;
        this.order = order;
    }
    
    public VerifyVariable(ILaunch launch, String name, String type, String value, boolean valueChanged, int order) {
        this.launch = launch;
        this.name = name;
        this.type = type;
        this.value = value;
        this.valueChanged = valueChanged;
        this.order = order;
    }

    public int getOrder() {
		return order;
	}

	public String getName() throws DebugException {
        return name;
	}

	public String getReferenceTypeName() throws DebugException {
	    return type;
	}

	public IValue getValue() throws DebugException {
	    return this;
	}

	public boolean hasValueChanged() throws DebugException {
		return valueChanged;
	}

	public IDebugTarget getDebugTarget() {
	    return launch.getDebugTarget();
	}

	public ILaunch getLaunch() {
	    return launch;
	}

	public String getModelIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setValue(String expression) throws DebugException {
		// TODO Auto-generated method stub

	}

	public void setValue(IValue value) throws DebugException {
		// TODO Auto-generated method stub

	}

	public boolean supportsValueModification() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean verifyValue(String expression) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean verifyValue(IValue value) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

    public String getValueString() throws DebugException {
        return value;
    }

    public IVariable[] getVariables() throws DebugException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean hasVariables() throws DebugException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isAllocated() throws DebugException {
        // TODO Auto-generated method stub
        return false;
    }

}
