package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ThreadInfo;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

public interface ThreadInfoFinder {

	public ThreadInfo findThreadInfo(String threadInfoId) throws MapperException;
	
	public ThreadInfo findThreadInfo(String sessionId,String threadId) throws MapperException;
	
}
