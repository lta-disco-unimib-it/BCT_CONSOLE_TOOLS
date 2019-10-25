package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointVariable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue.ModifiedInfo;

public class FindDialogInput {
	private String programPointNameExpression, variableNameExpression, variableValueExpression;
	private ProgramPoint.Type programPointType;
	private ProgramPointVariable.Type variableType;
	private ModifiedInfo variableModifiedInfo;

	public String getProgramPointNameExpression() {
		return programPointNameExpression;
	}

	public String getVariableNameExpression() {
		return variableNameExpression;
	}

	public String getVariableValueExpression() {
		return variableValueExpression;
	}

	public ProgramPoint.Type getProgramPointType() {
		return programPointType;
	}

	public ProgramPointVariable.Type getVariableType() {
		return variableType;
	}

	public ModifiedInfo getVariableModifiedInfo() {
		return variableModifiedInfo;
	}

	public void setProgramPointNameExpression(String programPointExpression) {
		this.programPointNameExpression = programPointExpression;
	}

	public void setVariableNameExpression(String variableNameExpression) {
		this.variableNameExpression = variableNameExpression;
	}

	public void setVariableValueExpression(String variableValueExpression) {
		this.variableValueExpression = variableValueExpression;
	}

	public void setProgramPointType(ProgramPoint.Type programPointType) {
		this.programPointType = programPointType;
	}

	public void setVariableType(ProgramPointVariable.Type variableType) {
		this.variableType = variableType;
	}

	public void setVariableModifiedInfo(ModifiedInfo variableModifiedInfo) {
		this.variableModifiedInfo = variableModifiedInfo;
	}
}
