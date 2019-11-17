//Sanika Buche ssb170002
import java.math.BigInteger;

public class BinTree {
	private Node<Payload> root;
	boolean firstValue;
	double finalVal = 0;
	
	public BinTree() {
		root = null;
		firstValue = true;
	}
	
	//getter
	public Node<Payload> getRoot(){
		return root;
	}
	public boolean getFirstValue() {
		return firstValue;
	}
	public double getFinalVal() {
		return finalVal;
	}
	
	//setter
	public void setRoot(Node<Payload> r) {
		root = r;
	}
	public void setFirstValue(boolean b) {
		firstValue = b;
	}
	public void setFinalVal(double f) {
		finalVal = f;
	}
	
	/*
	 * A function for the user to call that calls recursiveInsert
	 * which acutally inserts the node in the tree
	 * 
	 * This is here so the user can call insert without using having
	 * access to nodes in the tree
	 * 
	 * parameters: Payload p - the object which to put in the tree
	 * 
	 * return: void
	 */
	public void insert(Payload p) {
		root = recursiveInsertNode(root, p);
	}
	
	/*
	 * A function which recursivly inserts a node into the tree with inorder traversal
	 * 
	 * parameters: Node<Payload> root - the node to put in the tree
	 * 			   Payload p - the payload to put in the node
	 * 
	 * returns: Node<Payload> - the payload that goes in the tree
	 */
	public Node<Payload> recursiveInsertNode(Node<Payload> root, Payload p){
		//if root is null put the node in that index
		if(root == null) {
			root = new Node<Payload>(p);
			return root;
		}
		
		//otherwise go down the tree
		//if current > root go right
		if(p.getExponent() > root.getPayload().getExponent())
			root.setRight(recursiveInsertNode(root.getRight(), p));
		//if current < root go left
		else if(p.getExponent() < root.getPayload().getExponent()) {
			root.setLeft(recursiveInsertNode(root.getLeft(), p));
		}
		//if current == root add the polynomials together
		else if(p.getExponent() == root.getPayload().getExponent()) {
			p.add(root.getPayload());
		}
		
		//return the node pointer
		return root;
	}
	
	/*
	 * A function that the user can call that searches through the tree
	 * to find the exponent the user is looking for
	 * 
	 * This is here so the user doesn't have access to the nodes in the tree
	 * 
	 * parameter: int exp - the exponent the find
	 * 
	 * return: boolean - true if node is found and false if roo isn't found
	 */
	public boolean search(int exp){
		root = recSearch(root, exp);
		if(root == null)
			return false;
		return true;
	}
	
	/*
	 * A function that actually searches through the tree for the exponent the user 
	 * is looking for
	 * 
	 * parameter: Node<Payload> root - the root of the entire tree
	 * 			  int exp - the exponent the user is looking for
	 * 
	 * returns: Node<Payload> - the node, null if not found and payload object if value was found
	 */
	public Node<Payload> recSearch(Node<Payload> root, int exp){
		//base case root is null or key is at root
		if(root == null || root.getPayload().getExponent() == exp)
			return root;
		
		//if user val is greater than root exp
		if(root.getPayload().getExponent() < exp) {
			//go left
			root = root.getLeft();
			return recSearch(root, exp);
		}
		
		//otherwise user val is less than root exp -> go right
		root = root.getRight();
		return recSearch(root, exp);
	}
	
	/*
	 * A function the user can call that calls recursivePrint 
	 * 
	 * This function is here so the user doesn't have access to the 
	 * nodes in the binary tree
	 * 
	 * parameters: none
	 * 
	 * returns: void
	 */
	public void print() {
		recursivePrint(root);
	}
	
	/*
	 * A function that recursively goes inorder through the tree
	 * and calls the integrate function. It checks if the equation is
	 * defined or not and if it is defined the function calls definateInt to
	 * get the answer of the integral
	 * 
	 * parameter: Node<Payload> root - the node to integrate
	 * 
	 * returns: void
	 */
	public void recursivePrint(Node<Payload> root) {
		if(root == null)
			return;
		recursivePrint(root.getRight());
		if(!root.getPayload().getDefined())
			integrate(root);
		else {
			integrate(root);
			definateInt(root);
		}
		recursivePrint(root.getLeft());
	}
	
    /*
     * A function that the user can call recursiveDelete
     * 
	 * This function is here so the user doesn't have access to the 
	 * nodes in the binary tree
	 * 
	 * Parameter: int exp - which node to delete
	 * 
	 * returns: void
     */
    public void delete(int exp) { 
        recursiveDelete(root, exp); 
    } 
  
    /* 
     * Finds the exponent that the user wants to delete and deletes it
     * 
     * Parameter: Node<Payload> root - the root to check the exponent
     * 			  int exp - the exponent to find and delete the node it's inside of
     * 
     * returns: Node<Payload> - null if the node isn't deleted and the root that is deleted
     */
    public Node<Payload> recursiveDelete(Node<Payload> root, int exp) {
        //if the tree is empty
        if (root == null)
        	return root; 
  
        //otherwise if the user exponent is less than the node exponent go left
        if (exp < root.getPayload().getExponent()) 
            root.setLeft(recursiveDelete(root.getLeft(), exp)); 
        
        //otherwise if the user exponent is greater than the node exponent go right
        else if (exp > root.getPayload().getExponent()) 
        	root.setRight(recursiveDelete(root.getRight(), exp));
  
        //if the user exponent is the same as the node exponent delete that node
        else{
            // node with only one child or no child 
            if (root.getLeft() == null) 
                return root.getRight(); 
            else if (root.getRight() == null) 
                return root.getLeft();
  
            // node with two children
            root.getPayload().setExponent(root.getRight().getPayload().getExponent()); 
  
            //delete the child of the node
            root.setRight(recursiveDelete(root.getRight(), root.getPayload().getExponent()));
        }
        return root; 
    } 
	
	/*
	 * A function that prints the integrated formula of
	 * the equation passed in by the user
	 * 
	 * parameters: Node<Payload> n - the node which carries the data needed for integration
	 * 
	 * returns: void
	 */
	public void integrate(Node<Payload> n) {
		int exp = n.getPayload().getExponent();
		int coeff = n.getPayload().getCoefficient();
		
		//check if what node is being integrated is the first value of the equation
		if(firstValue) {
			
			//check if exponent is -1 to see whether or not to take natural log
			if(exp == -1) {
				if(coeff == -1)
					//if coefficient is -1 print -ln x to not have -1ln x
					System.out.print("-ln x");
				else if(coeff == 1)
					//if coefficient is 1 print ln x to not have 1ln x
					System.out.print("ln x");
				else
					//otherwise print the coefficient before ln x
					System.out.print(coeff + "ln x");
			}
			
			//if the coefficient is 0 just print a 0
			else if(coeff == 0) {
				System.out.print(0);
			}
			
			//otherwise use power rule to calulate integral
			else {
				//add 1 to exponent
				exp += 1;
				
				//if coefficient and exponent are the exact same number coefficient is going to be 1 or -1
				if(coeff / exp == -1 && coeff % exp == 0) { //coefficient is -1
					System.out.print("-x");
					
					//if the exponent is 1 there's no need for a ^
					if(exp != 1)
						System.out.print("^" + exp);
				}
				else if(coeff / exp == 1 && coeff % exp == 0) { //coefficient is 1
					System.out.print("x");
					
					//if the exponent is 1 there's no need for a ^
					if(exp != 1)
						System.out.print("^" + exp);
				}
				
				//otherwise the coefficient and exponent are not the same number
				else {
					//simplify the fraction by getting the gcd of both and dividing exponent and coefficient by it
					int div = gcd(Math.abs(coeff), Math.abs(exp));
					
					//if fraction is negative
					if((double)coeff / (double)exp < 0) {
						
						//if dividing by the gcd makes the exponent 1 or -1 then the new coefficient is a whole number rather than an exponent
						if(((exp / div == 1) || (exp / div == -1)) && (exp % div == 0)) {
							
							//if the exponent is 1 there's no need for a ^
							if(exp != 1)
							System.out.print("-" + Math.abs(coeff / div) + "x^" + exp);
							else {
								System.out.print("-" + Math.abs(coeff / div) + "x");
							}
						}
						
						//otherwise divide both coefficient and exponent by the gcd to simplify the fraction and print it
						else {
							
							//if the exponent is 1 there's no need for a ^
							if(exp != 1)
								System.out.print("(-" + Math.abs(coeff / div) + "/" + Math.abs(exp / div) + ")x^" + exp);
							else {
								System.out.print("-" + Math.abs(coeff / div) + "x");
							}
						}
					}
					
					//if fraction is positive
					else {
						
						//if dividing by the gcd makes the exponent 1 or -1 then the new coefficient is a whole number rather than an exponent
						if(((exp / div == 1) || (exp / div == -1)) && (exp % div == 0)) {
							
							//if the exponent is 1 there's no need for a ^
							if(exp != 1)
								System.out.print(Math.abs(coeff / div) + "x^" + exp);
							else {
								System.out.print(Math.abs(coeff / div) + "x");
							}
						}
						
						//otherwise divide both coefficient and exponent by the gcd to simplify the fraction and print it
						else {
							
							//if the exponent is 1 there's no need for a ^
							if(exp != 1)
								System.out.print("(" + Math.abs(coeff / div) + "/" + Math.abs(exp / div) + ")x^" + exp);
							else {
								System.out.print(Math.abs(coeff / div) + "x");
							}
						}
					}
				}
				
			}
			
			//just printed out the first value so set it to false
			firstValue = false;
		}
		
		//if the function isn't the first value of the equation
		else {
			
			//check if exponent is -1 to see whether or not to take natural log
			if(exp == -1) {
				
				//if the coefficient is negative print a minus sign
				if(coeff < 0)
					System.out.print(" - ");
				
				//otherwise print a plus sign
				else {
					System.out.print(" + ");
				}
				
				//if the coefficient is 1 or -1 just print ln x
				if(coeff == 1 || coeff == -1)
					System.out.print("ln x");
				
				//otherwise print the abs of the coefficient and ln x
				else
					System.out.print(Math.abs(coeff) + "ln x");
			}
			
			//if the coefficient is 0 just print + 0
			else if(coeff == 0) {
				System.out.print(" + 0");
			}
			
			//otherwise use the power rule to integrate
			else {
				
				//add one to the exponent
				exp += 1;
				
				//if coefficient and the exponent are the exact same and one is negative
				if(coeff / exp == -1 && coeff % exp == 0) {
					System.out.print(" - x");
					
					//if the exponent is 1 there's no need for a ^
					if(exp != 1)
						System.out.print("^" + exp);
					
				}
				
				//if coefficient and the exponent are the exact same and both are negative or positive
				else if(coeff / exp == 1 && coeff % exp == 0) {
					System.out.print(" + x");
					
					//if the exponent is 1 there's no need for a ^
					if(exp != 1)
						System.out.print("^" + exp);
					
				}
				
				//otherwise the answer is a fraction
				else {
					
					//get the gcd and divide exponent and coefficient by it to get the simplified fraction
					int div = gcd(Math.abs(coeff), Math.abs(exp));
					
					//if dividing by the gcd makes the exponent 1 or -1 then the new coefficient is a whole number rather than an exponent
					if(((exp / div == 1) || (exp / div == -1)) && (exp % div == 0)) {
						if((double)coeff / (double)exp < 0) {
							
							//if the exponent is 1 there's no need for a ^
							if(exp != 1)
								System.out.print(" - " + Math.abs(coeff / div) + "x^" + exp);
							else {
								System.out.print(" - " + Math.abs(coeff / div) + "x");
							}
						}
						else {
							
							//if the exponent is 1 there's no need for a ^
							if(exp != 1)
								System.out.print(" + " + Math.abs(coeff / div) + "x^" + exp);
							else {
								System.out.print(" + " + Math.abs(coeff / div) + "x");
							}
						}
					}
					
					//otherwise the answer is a fraction rather than a whole number
					else {
						
						//if the fraction is negative, print -
						if((double)coeff / (double)exp < 0) {
							
							//if the exponent is 1 there's no need for a ^
							if(exp != 1)
								System.out.print(" - (" + Math.abs(coeff / div) + "/" + Math.abs(exp / div) + ")x^" + exp);
							else {
								System.out.print(" - " + Math.abs(coeff / div) + "x");
							}
						}
						
						//if the fraction is positive print +
						else {
							
							//if the exponent is 1 there's no need for a ^
							if(exp != 1)
								System.out.print(" + (" + Math.abs(coeff / div) + "/" + Math.abs(exp / div) + ")x^" + exp);
							else {
								System.out.print(" + " + Math.abs(coeff / div) + "x");
							}
							
						}
					}
				}
			}
		}
	}
	
	
	
	/*
	 * a function that finds the definite integral of the equation
	 * 
	 * parameters: Node<Payload> root - the polynomial that holds one part of the equation
	 * 
	 * returns: void
	 */
	public void definateInt(Node<Payload> root) {
		double coeff = (double)root.getPayload().getCoefficient();
		double exp = (double)root.getPayload().getExponent();
		
		// the initial bounds
		double ub = (double)root.getPayload().getUpper();
		double lb = (double)root.getPayload().getLower();
		
		
		//the initial upper and lower bound answers
		double upperVal = 0, lowerVal = 0;
		
		
		//if the exponent is -1, take natural log of upper and lower bounds
		if(exp == -1) {
			upperVal += Math.log(ub);
			lowerVal += Math.log(lb);
			
			//the answer for the whole equation
			finalVal += (upperVal - lowerVal);
		}
		
		//otherwise
		else {
			exp += 1;
			
			//get the gcd 
			int div = gcd((int)exp, (int)coeff);
			
			//simplify the coefficient by the gcd
			coeff /= div;
			
			//simplify the exponent by gcd
			double simplifiedExp = exp / div;
			
			//calculate the integral of the upper bound
			upperVal += Math.pow(ub, exp);
			upperVal *= coeff;
			upperVal /= simplifiedExp;
			
			// calculate the integral of the lower bound
			lowerVal += Math.pow(lb, exp);
			lowerVal *= coeff;
			lowerVal /= simplifiedExp;
			
			//and the final answer
			finalVal += (upperVal - lowerVal);
		}
		
	}
	
	/*
	 * a function that calculates the gcd of two integers
	 * 
	 * parameters: int a, b - integers to calculate the gcd of 
	 * 
	 * returns: int - gcd
	 */
	public int gcd(int a, int b) {
	    BigInteger b1 = BigInteger.valueOf(a);
	    BigInteger b2 = BigInteger.valueOf(b);
	    BigInteger gcd = b1.gcd(b2);
	    return gcd.intValue();
	}
}