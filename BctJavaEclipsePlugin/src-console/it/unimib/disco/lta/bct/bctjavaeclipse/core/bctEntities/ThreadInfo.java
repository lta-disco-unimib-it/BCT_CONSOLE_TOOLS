package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

public class ThreadInfo extends DomainEntity {
	private String threadId;
	private String sessionId;
	private String sessionName;


	public String getSessionName() {
		return sessionName;
	}

	public ThreadInfo( String id, String sessionId, String threadId, String sessionName ){
		super(id);
		this.sessionId = sessionId;
		this.threadId = threadId;
		this.sessionName = sessionName;
	}

	public String getThreadId() {
		return threadId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
}
