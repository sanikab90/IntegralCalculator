//Sanika Buche ssb170002
public class Payload {
	private int coefficient;
	private int exponent;
	private int upperBound;
	private int lowerBound;
	private boolean defined;
	
	public Payload() {
		this.coefficient = 0;
		this.exponent = 0;
		this.lowerBound = 0;
		this.upperBound = 0;
		this.defined = false;
	}
	
	public Payload(int c, int e, int u, int l, boolean d) {
		this.coefficient = c;
		this.exponent = e;
		this.upperBound = u;
		this.lowerBound = l;
		this.defined = d;
	}
	
	//setters
	public void setCoefficient(int c) {this.coefficient = c;}
	public void setExponent(int e) {this.exponent = e;}
	public void setUpper(int u) {this.upperBound = u;}
	public void setLower(int l) {this.lowerBound = l;}
	public void setDefined(boolean d) {this.defined = d;}
	
	//getters
	public int getCoefficient() {return this.coefficient;}
	public int getExponent() {return this.exponent;}
	public int getUpper() {return this.upperBound;}
	public int getLower() {return this.lowerBound;}
	public boolean getDefined() {return this.defined;}
	
	/*
	 * a functions that adds coefficients together
	 * 
	 * parameters: Payload p - the coefficient to add
	 * 
	 * returns: Payload - the coefficients added together 
	 */
	public Payload add(Payload p) {
		p.coefficient += this.coefficient;
		return p;
	}
}
