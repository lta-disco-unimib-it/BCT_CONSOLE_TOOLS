package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.ProgramPointsRawFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

/**
 * Program point mapper, provides the methods to store and load program points.
 * 
 * @author Fabrizio Pastore - fabrizio.pastore@disco.unimib.it
 *
 */
public interface ProgramPointRawMapper extends EntityMapper<ProgramPointRaw>, ProgramPointsRawFinder {

	public abstract List<ProgramPointRaw> find(IoRawTrace trace) throws MapperException;

}
