import java.io.*;
import java.util.*;

public class Expression {
	static String delimiters="+-*%()";
	
	
	// Returns the value of the arithmetic Expression described by expr
	// Throws an exception if the Expression is malformed
	static String evaluate(String expr) throws Exception {     
	 
		
		
	    StringTokenizer st = new StringTokenizer( expr , delimiters , true );    // delimiter separates tokens, true means that delimiter are tokens as well along with in the main method
	    Stack operators = new Stack();
	    Stack numbers = new Stack();
	    boolean isNum1=false;  //is the previous element a number ?
        boolean isNum2;  // is this element a number ?
	    int counter=1; // open parenthsesis counter
        
	    while ( st.hasMoreTokens() ) {
		String element = st.nextToken(); //access element one by one
		System.out.println("Element ="+element);
		
		//recursive step for the parenthesis 
		if(element.equals("(")){
			String recurString="" ;
			while((! element.equals(")") || counter == 1 )){
				
				
				element = st.nextToken();
				if(element.equals("(")) counter++;  //counter for multiple parenthesis
				if(element.equals(")")) counter--;
				
				if(! element.equals(")"))recurString = recurString+element;  // concatenate future string for recursive 
				
			}
			if(counter==0)recurString=recurString+")";
			for(int i=counter;i>0;i--){
			recurString=recurString+")";
		}
			numbers.push(evaluate(recurString));
		}
		
		//part where the none parenthesis part is calculated
		
		
		 
		if (delimiters.contains(element))	isNum2= false;             // set boolean to know if this element is numerical     
			else isNum2= true;
			
		if(isNum1 && isNum2){                                 // takes care of numbers with more than one digits
	     element=numbers.peek()+element;
	     
	     }
	     

	     if (delimiters.contains(element)){              //put in operators , from now on this element becomes the previous element for the boolean
	    	 isNum1= false;
	    	 if(! element.equals(")"))	 operators.push(element);
	    }    
	     else{
	    	 isNum1=true;
	    	 numbers.push(element);
	     }
        
	     
	}	
	  int size =operators.size()+numbers.size();
	 if(size>=3){
	    String[] finalCal= new String[size]; //array for the equation without parenthesis
	   
	    for(int i=0;i<(size);i=i+2){   //fills the array with the reversed equation
	    	finalCal[i]=(String) numbers.peek();
	    	numbers.pop();
	    	if(! operators.isEmpty()){
	    	finalCal[i+1]=(String) operators.peek();
	    	operators.pop();
	    	}
	    }
	    //base case equation without parenthesis
	    for(int i=size-1;i>=2;i=i-2){     // takes the first 3 token of the original expression and replace them with the value of the operation, then takes that value as the first token of the next 3
	    	int temp;
	    	// if for operators +-*%  
	    	if(finalCal[i-1].equals("*"))  {       
	    		temp= Integer.parseInt(finalCal[i])*Integer.parseInt(finalCal[i-2]);
	    		finalCal[i-2]= Integer.toString(temp);
	    	}
	    	else if(finalCal[i-1].equals("%")) {
	    		temp= Integer.parseInt(finalCal[i])/Integer.parseInt(finalCal[i-2]);
	    		finalCal[i-2]= Integer.toString(temp);
	    	}
	    	else if(finalCal[i-1].equals("+")) {
	    		temp= Integer.parseInt(finalCal[i])+(Integer.parseInt(finalCal[i-2])); 
	    		finalCal[i-2]= Integer.toString(temp);
	        }
	    	else if(finalCal[i-1].equals("-")){
	    		temp= Integer.parseInt(finalCal[i])-Integer.parseInt(finalCal[i-2]);
	    		finalCal[i-2]= Integer.toString(temp);
	    	}
	    }
	    
	    numbers.push(finalCal[0]);
	 }
	    if(numbers.size()==1){  //final case when everything has been computed
	    	if(operators.size()!=0) throw new Exception();  // if some operator where wrongly placed they will remain in the stack and send an error
	    return (String) numbers.peek();
	    }
	    else throw new Exception();
	    
	    
	}	
	
	
	
	public static void main(String args[]) throws Exception {
		String line;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                                      	                        
		do {
			line=stdin.readLine();
			if (line.length()>0) {
				try {
					String x=evaluate(line);
					System.out.println(" = " + x);
				}
				catch (Exception e) {
					System.out.println("Malformed Expression: "+e);
				}
			}
		} while (line.length()>0);
	}
}









