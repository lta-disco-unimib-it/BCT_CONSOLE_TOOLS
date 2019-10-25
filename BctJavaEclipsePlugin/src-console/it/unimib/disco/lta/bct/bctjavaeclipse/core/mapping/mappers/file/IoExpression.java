package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

public class IoExpression {
	private String expression;

	public IoExpression(String expression){
		this.expression = expression;
	}
	
	public String toString(){
		return expression;
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null ){
			return false;
		}
		if ( ! ( obj instanceof IoExpression ) ){
			return false;
		}
		return expression.equals(((IoExpression)obj).expression);
	}

	@Override
	public int hashCode() {
		return expression.hashCode();
	}
	
	
}
