package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.db;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointVariable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint.Type;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue.ModifiedInfo;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.ProgramPointRawMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

import util.componentsDeclaration.SystemElement;

public class ProgramPointRawMapperDB implements ProgramPointRawMapper {

	public List<ProgramPointRaw> find(IoRawTrace trace) throws MapperException {
		throw new RuntimeException("Not Implemented");
	}

	public List<ProgramPointRaw> find(List<SystemElement> components, String programPointExpression, Type ppType,
			String varNameExpr, ProgramPointVariable.Type varTypeExpr, String varValueExpr,
			ModifiedInfo variableModified) {
		throw new RuntimeException("Not Implemented");
	}

	public void update(ProgramPointRaw entity) throws MapperException {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public ProgramPointRaw find(MethodCallPoint methodCall)
			throws MapperException {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public ProgramPointRaw find(String methodSignature, String methodCallId)
			throws MapperException {
		throw new RuntimeException("Not Implemented");
	}
}
