package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

public abstract class DomainEntity {
	private String id;
	
	protected DomainEntity( String id ){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
}
