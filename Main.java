//Sanika Buche ssb170002
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class Main {
	public static void main(String[] args) throws Exception {
		String line, fileName;
		//ask user for file name
		Scanner scan = new Scanner(System.in);
		//read user input
		fileName = scan.nextLine();
		
		//user file
		File in = new File(fileName);
		
		//scanner object to read in
		Scanner read = new Scanner(in);
		
		//check if file is openable and loop until there's no more equations
		while(read.hasNext()) {
			//store integral in line
			line = read.nextLine();
			
			//parse line
			parse(line);
		}
		
		//close scanners
		scan.close();
		read.close();
	}
	
	/*
	 * This function takes the while equation and dismantles it into each polynomial
	 * Then it takes the polynomial and finds the coefficient and the exponent and puts all that data
	 * in a payload object and sorts that in a binary tree and calls the integrate and print function
	 * 
	 * Parameters: String line - the equation to integrate
	 * 
	 * Returns: void
	 */
	public static void parse(String line) {
		boolean defined = false;
		Payload p = null;
		BinTree tree = new BinTree();
		String[] pos, splitCoef;
		int coefficient = 0, exponent = 0;
		int upper = 0, lower = 0;
		String upperBound, lowerBound;
		
		//if the first character found is a number it is defined
		if(Character.isDigit(line.charAt(0)) || line.charAt(0) == '-') {
			defined = true;
			
			//substring upper and lower bounds
			lowerBound = line.substring(0, line.indexOf('|'));
			upperBound = line.substring(line.indexOf('|') + 1, line.indexOf(' '));
			
			//convert to integer
			upper = Integer.parseInt(upperBound);
			lower = Integer.parseInt(lowerBound);
		}
		
		//substring the line from the coefficient until d is spotted and -1 to get whole expression
		line = line.substring(line.indexOf(' ') + 1, line.indexOf('d') - 1);
		
		//take out the spaces between everything
		line = line.replaceAll(" ", "");
		
		//make sure that negative exponent values are replaced
		if(line.indexOf("^-") != -1) {
			line = line.replace("^-", "^*");
		}

		//get all positive terms
		pos = line.split("\\+", 0);
		
		//array list to store negative coefficients
		ArrayList<String> neg = new ArrayList<String>();
		int index = 0;
		
		//go through all positive terms
		for(String i: pos) {
			
			//if a - is found loop until there's no more
			Pattern pattern = Pattern.compile("-");
			Matcher matcher = pattern.matcher(i);
			
			int c = 0;
			while(matcher.find())
				c++;
			
			//while there are negative terms
			if(c > 1) {
				while(c > 1) {
					//add those terms to the neg terms array list
					neg.add(i.substring(i.lastIndexOf("-")));
					
					//remove them from the positive terms array
					pos[index] = i.substring(0, i.indexOf("-"));
					i = i.substring(0, i.lastIndexOf("-")); 
					c--;
				}
				if(i.indexOf("-") != -1) {
					neg.add(i.substring(i.lastIndexOf("-")));
					i = i.substring(0, i.lastIndexOf("-")); 
					pos[index] = i;
				}
			}
			else{
				if(i.indexOf("-") != -1) {
					neg.add(i.substring(i.indexOf("-")));
					pos[index] = i.substring(0, i.indexOf("-"));
				}
			}
			index++;
		}
		
		//loop through positive terms
		index = 0;
		for(String i : pos) {
			//if theres nothing in the index continue to the next iteration 
			if(i.isEmpty()) {
				index++;
				continue;
			}
			
			//search for negative exponents and replace the * with -
			if(i.indexOf("^*") != -1)
				pos[index] = i.replace("^*", "^-");
			
			//split the equation by a ^ to get the coefficient in the first index and the equation in the second index
			splitCoef = pos[index].split("\\^");
			
			if(splitCoef.length == 2) { //if there is a coefficient > 1 and an exponent >= 1 or -1
			
				//here coefficient is only positive
				if(splitCoef[0].compareTo("x") == 0)
					coefficient = 1;
				else {
					coefficient = Integer.parseInt(splitCoef[0].substring(0, splitCoef[0].indexOf('x')));
				}
				
				//exponent can be positive or negative
				if(splitCoef[1].charAt(0) == '-') //exp is negative
					exponent = -1 * Integer.parseInt(splitCoef[1].substring(1));
				else { //exp is positive
					exponent = Integer.parseInt(splitCoef[1].substring(0));
				}
			}
			else if(splitCoef.length == 1) { //if theres either a coefficient or exponent but NOT both
				//check if [0] has an x
				if(splitCoef[0].indexOf('x') != -1) { //[0] does have an x -> coefficient with exp of 1
					//if there is an x but no exponent index the exponent is 1
					exponent = 1;
					if(Character.isDigit(splitCoef[0].charAt(0)))
						coefficient = Integer.parseInt(splitCoef[0].substring(0, splitCoef[0].indexOf('x')));
					else {
						coefficient = 1;
					}
				}
				else { //[0] doesn't have x -> coeff with exponent of 0
					exponent = 0;
					coefficient = Integer.parseInt(splitCoef[0].substring(0));
				}
			}
			else {
				index++;
				continue;
			}
			
			//put everything in a payload object
			p = new Payload(coefficient, exponent, upper, lower, defined);
			
			//put the object in the tree
			tree.insert(p);
			index++;
		}
		
		//reset index
		index = 0;
		for(String i : neg) {
			//if the index is empty continue with the next iteration of the loop
			if(i.isEmpty()) {
				index++;
				continue;
			}

			//replace ^* with ^- for negative exponents
			if(i.indexOf("^*") != -1)
				neg.set(index, i.replace("^*", "^-"));
			
			//split based on ^ so the coefficient is in first index and the exponent is in the second index
			splitCoef = neg.get(index).split("\\^");
			
			if(splitCoef.length == 2) { //if there is a coefficient > 1 and an exponent >= 1 or -1
				//if array has 2 indcies [0] is coefficient and [1] is exponent
		
				//here coefficient is only negative
				if(splitCoef[0].compareTo("-x") == 0) {
					coefficient = -1;
				}
				else {
					coefficient = -1 * Integer.parseInt(splitCoef[0].substring(1, splitCoef[0].indexOf('x')));
				}
				
				//exponent can be positive or negative
				if(splitCoef[1].charAt(0) == '-') //exp is neg
					exponent = -1 * Integer.parseInt(splitCoef[1].substring(1));
				else { //exp is positive
					exponent = Integer.parseInt(splitCoef[1].substring(0));
				}
			}
			else if(splitCoef.length == 1) { //if theres either a coefficient or exponent but NOT both
				//check if [0] has an x
				if(splitCoef[0].indexOf('x') != -1) { //[0] does have an x -> coefficient with exp of 1
					//if there is an x but no exponent index the exponent is 1
					exponent = 1;
					
					//if coefficient is -x or -1x
					if(splitCoef[0].compareTo("-x") == 0 || splitCoef[0].compareTo("-1x") == 0)
						coefficient = -1;
					else
						coefficient = -1 * Integer.parseInt(splitCoef[0].substring(1, splitCoef[0].indexOf('x')));
				}
				else { //[0] doesn't have x -> coeff with exponent of 0
					exponent = 0;
					coefficient = -1 * Integer.parseInt(splitCoef[0].substring(1));
				}
			}
			else {
				index++;
				continue;
			}
			
			//put data in payload object
			p = new Payload(coefficient, exponent, upper, lower, defined);
			
			//insert object in tree
			tree.insert(p);
			
			//increment counter
			index++;
		}
		
		//call print which derives and prints the equation
		tree.print();
		
		//if the integral isn't defined add a + C to it otherwise put the answer to the integral
		if(!p.getDefined())
			System.out.println(" + C");
		else {
			System.out.printf(", %d|%d = %.3f\n", lower, upper, tree.finalVal);
		}
	}
	
}