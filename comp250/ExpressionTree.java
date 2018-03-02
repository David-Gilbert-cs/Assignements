import java.lang.Math.*;

class ExpressionTree {
    private String value;
    private ExpressionTree leftChild, rightChild, parent;
    
    
    ExpressionTree() {
        value = null; 
        leftChild = rightChild = parent = null;
    }
    
    // Constructor
    /* Arguments: String s: Value to be stored in the node
                  ExpressionTree l, r, p: the left child, right child, and parent of the node to created      
       Returns: the newly created ExpressionTree               
    */
    ExpressionTree(String s, ExpressionTree l, ExpressionTree r, ExpressionTree p) {
        value = s; 
        leftChild = l; 
        rightChild = r;
        parent = p;
    }
    
    /* Basic access methods */
    String getValue() { return value; }

    ExpressionTree getLeftChild() { return leftChild; }

    ExpressionTree getRightChild() { return rightChild; }

    ExpressionTree getParent() { return parent; }


    /* Basic setting methods */ 
    void setValue(String o) { value = o; }
    
    // sets the left child of this node to n
    void setLeftChild(ExpressionTree n) { 
        leftChild = n; 
        n.parent = this; 
    }
    
    // sets the right child of this node to n
    void setRightChild(ExpressionTree n) { 
        rightChild = n; 
        n.parent=this; 
    }
    

    // Returns the root of the tree describing the expression s
    // Watch out: it makes no validity checks whatsoever!
    ExpressionTree(String s) {
        // check if s contains parentheses. If it doesn't, then it's a leaf
        if (s.indexOf("(")==-1) setValue(s);
        else {  // it's not a leaf

            /* break the string into three parts: the operator, the left operand,
               and the right operand. ***/
            setValue( s.substring( 0 , s.indexOf( "(" ) ) );
            // delimit the left operand 2008
            int left = s.indexOf("(")+1;
            int i = left;
            int parCount = 0;
            // find the comma separating the two operands
            while (parCount>=0 && !(s.charAt(i)==',' && parCount==0)) {
                if ( s.charAt(i) == '(' ) parCount++;
                if ( s.charAt(i) == ')' ) parCount--;
                i++;
            }
            int mid=i;
            if (parCount<0) mid--;

        // recursively build the left subtree
            setLeftChild(new ExpressionTree(s.substring(left,mid)));
    
            if (parCount==0) {
                // it is a binary operator
                // find the end of the second operand.F13
                while ( ! (s.charAt(i) == ')' && parCount == 0 ) )  {
                    if ( s.charAt(i) == '(' ) parCount++;
                    if ( s.charAt(i) == ')' ) parCount--;
                    i++;
                }
                int right=i;
                setRightChild( new ExpressionTree( s.substring( mid + 1, right)));
        }
    }
    }


    // Returns a copy of the subtree rooted at this node... 2014
    ExpressionTree deepCopy() {
        ExpressionTree n = new ExpressionTree();
        n.setValue( getValue() );
        if ( getLeftChild()!=null ) n.setLeftChild( getLeftChild().deepCopy() );
        if ( getRightChild()!=null ) n.setRightChild( getRightChild().deepCopy() );
        return n;
    }
    
    // Returns a String describing the subtree rooted at a certain node.
    public String toString() {
        String ret = value;
        if ( getLeftChild() == null ) return ret;
        else ret = ret + "(" + getLeftChild().toString();
        if ( getRightChild() == null ) return ret + ")";
        else ret = ret + "," + getRightChild().toString();
        ret = ret + ")";
        return ret;
    } 


    // Returns the value of the expression rooted at a given node
    // when x has a certain value
    double evaluate(double x) {
		// WRITE YOUR CODE HERE
	     String operators = ("add  minus  mult cos sin exp ");
	     
	     ExpressionTree tree = this.deepCopy();  // we copy the tree to not overwrite the initial one, it would have been a good idea not to name the both trees from evaluate and differentiate tree
	     
	     //base case, where calculations are made  
	  if(! (tree.getLeftChild() == null && tree.getRightChild() == null)){
	    if ( tree.getLeftChild() == null || !operators.contains((CharSequence) tree.leftChild.value) && ! operators.contains((CharSequence) tree.leftChild.value) ){ // left null ?***
	    	if( tree.getRightChild() != null){
	    	   if(!(tree.rightChild.value.equals("x") || tree.leftChild.value.equals("x"))){
	    	if (tree.value.equals("add")){
	    		tree.setValue(String.valueOf(Double.parseDouble(tree.getLeftChild().getValue()) + Double.parseDouble(tree.getRightChild().getValue()))); 
	    		tree.leftChild=null;
				tree.rightChild=null;
	    	}
	    	else if (tree.value.equals("minus")){
	    		tree.setValue(String.valueOf(Double.parseDouble(tree.getLeftChild().getValue()) - Double.parseDouble(tree.getRightChild().getValue())));
	    		tree.leftChild=null;
				tree.rightChild=null;
	    	}
	    	
	    	else if (tree.value.equals("mult")){
	    		tree.setValue(String.valueOf(Double.parseDouble(tree.getLeftChild().getValue()) * Double.parseDouble(tree.getRightChild().getValue()))); 
	    		tree.leftChild=null;
				tree.rightChild=null;
	    	}
	    	   }   	    	
	    	}
	    	else if(! tree.leftChild.value.equals("x")){
	    	if (tree.value.equals("cos")){
	    		if(tree.leftChild.equals(null)){   // *** does tree null is fine or should it be string ?
	    			tree.setValue(String.valueOf(Math.cos(Double.parseDouble(tree.getRightChild().getValue()))));
	    			tree.leftChild=null;
	    			tree.rightChild=null;
	    		}
	    		else{
	    			tree.setValue(String.valueOf(Math.cos(Double.parseDouble(tree.getLeftChild().getValue()))));
	    			tree.leftChild=null;
	    			tree.rightChild=null;
	    		}
	    		}
	    	
	    	else if (tree.value.equals("sin")){
	    		if(tree.leftChild.equals(null)){
	    			tree.setValue(String.valueOf(Math.sin(Double.parseDouble(tree.getRightChild().getValue()))));
	    			tree.leftChild=null;
	    			tree.rightChild=null;
	    		}
	    		else{
	    			tree.setValue(String.valueOf(Math.sin(Double.parseDouble(tree.getLeftChild().getValue()))));
	    			tree.leftChild=null;
	    			tree.rightChild=null;
	    		}
	    		}
	    	
	    	else if (tree.value.equals("exp")){
	    		if(tree.leftChild.equals(null)){
	    			tree.setValue(String.valueOf(Math.exp(Double.parseDouble(tree.getRightChild().getValue()))));
	    			tree.leftChild=null;
	    			tree.rightChild=null;
	    		}
	    		else{
	    			tree.setValue(String.valueOf(Math.exp(Double.parseDouble(tree.getLeftChild().getValue()))));
	    			tree.leftChild=null;
	    			tree.rightChild=null;
	    		}
	    		}
	    	
	    	 }
	    }
	    
	    
	    else if(tree.getRightChild() == null && tree.getLeftChild() != null){
	    	if (tree.value.equals("cos")){
        		tree.setValue(String.valueOf(Math.cos(x)));
        		tree.leftChild=null;
        		tree.rightChild=null;
        		}
        	else if (tree.value.equals("sin")){
        		tree.setValue(String.valueOf(Math.sin(x)));
        		tree.leftChild=null;
        		tree.rightChild=null;	
        	}
        	
        	else if (tree.value.equals("exp")){
        		tree.setValue(String.valueOf(Math.exp(x)));
        		tree.leftChild=null;
        		tree.rightChild=null;
        		}
	    }
	    
	  //calculations if there is an x in the equation
	    else if (( (! operators.contains((CharSequence) tree.leftChild.value)) || tree.leftChild.value.equals("x") ) && ((! operators.contains((CharSequence) tree.rightChild.value)|| tree.rightChild.value.equals("x"))) ){
	    	   
	             if (tree.value.equals("add")){
	            	 if(tree.getRightChild().value.equals("x")){
	            		tree.setValue(String.valueOf(Double.parseDouble(tree.getLeftChild().getValue())+x)); 
	            		tree.leftChild=null;
	        			tree.rightChild=null;
	            	 }
	            	 else{
		            		tree.setValue(String.valueOf(x+Double.parseDouble(tree.getRightChild().getValue()))); 
		            		tree.leftChild=null;
		        			tree.rightChild=null;
		            		}
	             
	            		
	        	}
	        	else if (tree.value.equals("minus")){
	        		if(tree.getLeftChild().value.equals("x")){
	        			tree.setValue(String.valueOf(x-Double.parseDouble(tree.getRightChild().getValue()))); 
	        			tree.leftChild=null;
	        			tree.rightChild=null;
	            		}
	            		else{
	            		tree.setValue(String.valueOf(Double.parseDouble(tree.getLeftChild().getValue())-x)); 
	            		tree.leftChild=null;
	        			tree.rightChild=null;
	            		}
	        	}
	        	
	        	else if (tree.value.equals("mult")){
	        		if(tree.getLeftChild().value.equals("x")){
	        		tree.setValue(String.valueOf(x*Double.parseDouble(tree.getRightChild().getValue()))); 
	        		tree.leftChild=null;
	    			tree.rightChild=null;
	        		}
	        		else{
	        		tree.setValue(String.valueOf(Double.parseDouble(tree.getLeftChild().getValue())*x));
	        		tree.leftChild=null;
	    			tree.rightChild=null;
	        		}
	        		}
	             		    	
	        	
	           }
	    
	        	//if has x end block
	    
	    
	    	   
	    	       
	    
	    //recursive step
	   
	    
	    if( tree.getLeftChild() != null  ){
	    if( operators.contains((CharSequence) tree.getLeftChild().value)){
	    	tree.getLeftChild().setValue( String.valueOf(tree.getLeftChild().evaluate(x)));
	    	tree.leftChild.leftChild=null;
    		tree.leftChild.rightChild=null;
	    }
	    }
	    
	    if( tree.getRightChild() != null ){
	        if( operators.contains((CharSequence) tree.getRightChild().value)){
	        	tree.getRightChild().setValue( String.valueOf(tree.getRightChild().evaluate(x)));
	        	tree.rightChild.leftChild=null;
	    		tree.rightChild.rightChild=null;
	        	
	        }
	        }
	  }
	  if(tree.value.equals("x"))return x; // this line is only for an exeptional case
	   
	    if(! operators.contains(tree.value)){    //  return the value corresponding to the caculations     these lines of code are repeated to avoid some very specific case
		return Double.parseDouble(tree.value);                                                 
	    }
	    else{
	    	tree.setValue(String.valueOf(tree.evaluate(x)));   	
	    }
		
	    if(! operators.contains(tree.value)){
	    	return Double.parseDouble(tree.value);                                                 
	        }
	        else{
	        	tree.setValue(String.valueOf(tree.evaluate(x)));
	        }
	    
	    if(! operators.contains(tree.value)){
	    	return Double.parseDouble(tree.value);                                                 
	        }
	        else{
	        	tree.setValue(String.valueOf(tree.evaluate(x)));	
	        }
	    
	    if(! operators.contains(tree.value)){
	    	return Double.parseDouble(tree.value);                                                 
	        }
	        else{
	        	tree.setValue(String.valueOf(tree.evaluate(x)));  	
	        }
	    
	    if(! operators.contains(tree.value)){
	    	return Double.parseDouble(tree.value);                                                 
	        }
	        else{
	        	tree.setValue(String.valueOf(tree.evaluate(x)));  	
	        }
	    
	    if(! operators.contains(tree.value)){
	    	return Double.parseDouble(tree.value);                                                 
	        }
	        else{
	        	tree.setValue(String.valueOf(tree.evaluate(x)));	
	        }
	    
	    if(! operators.contains(tree.value)){
	    	return Double.parseDouble(tree.value);                                                 
	        }
	        else{
	        	tree.setValue(String.valueOf(tree.evaluate(x)));	
	        }
	    System.out.println("too many equations");
		return -1;
	    
	    
	    }
       
      
    /* returns the root of a new expression tree representing the derivative of the
       original expression */
    ExpressionTree differentiate() {
    	String operators = ("add  minus  mult cos sin exp ");
	
 ExpressionTree tree = this.deepCopy(); // we work on a copy as to not overwrite the initial
 ExpressionTree nullTree = new ExpressionTree();  // nullTree and one treee are just variable to use if needed
 ExpressionTree oneTree= new ExpressionTree("1", nullTree,nullTree,nullTree);
 
    	//base case 
 if ( tree.getLeftChild() == null && tree.getRightChild() == null ){
	 if(tree.value.equals("x")) tree.setValue("1");
	 else tree.setValue("0");	 
 }

    	
 //recursive step for each operators
 if(operators.contains((tree.value))){
	 if(tree.value.equals("add") || tree.value.equals("minus")){
		 tree.setLeftChild(tree.leftChild.differentiate());
		 tree.setRightChild(tree.rightChild.differentiate());
	 }
	 
 
	 else if(tree.value.equals("mult")){
		 tree.setValue("add");
		 
		 
		 
		 nullTree = tree.leftChild.deepCopy();
		 oneTree = tree.rightChild.deepCopy();
		 tree.leftChild.setLeftChild(nullTree);
		 
		 tree.leftChild.setRightChild(oneTree.differentiate());
		 
		
		 tree.rightChild.setLeftChild(oneTree);
		 
		 tree.rightChild.setRightChild(nullTree.differentiate());
		 
		 
		 
		 tree.leftChild.setValue("mult");
		 tree.rightChild.setValue("mult");
	 }
	 else if(tree.value.equals("exp")){
		 tree.setValue("mult");
		 nullTree = tree.leftChild.deepCopy();
		 
		 tree.leftChild.setValue("exp");
		 tree.leftChild.setLeftChild(nullTree);
		 tree.setRightChild(nullTree.differentiate());
		 nullTree = new ExpressionTree();
		 tree.leftChild.setRightChild(nullTree);
		 
	 }
	 
	 else if(tree.value.equals("sin")){
		 tree.setValue("mult");
		 nullTree = tree.leftChild.deepCopy();
		 
		 tree.leftChild.setValue("cos");
		 tree.leftChild.setLeftChild(nullTree);
		 tree.setRightChild(nullTree.differentiate());
		 nullTree = new ExpressionTree();
		 tree.leftChild.setRightChild(nullTree);
		 
	 }
	 
	 else if(tree.value.equals("cos")){
		 tree.setValue("mult");
		 nullTree = tree.leftChild.deepCopy();
		 oneTree= new ExpressionTree();
		 oneTree.setValue("minus");
		 tree.leftChild.setValue("mult");
		 tree.leftChild.setLeftChild(oneTree);
		 tree.leftChild.leftChild.setLeftChild(new ExpressionTree("0",new ExpressionTree(),new ExpressionTree(),new ExpressionTree()));
		 tree.leftChild.leftChild.setRightChild(new ExpressionTree("1", new ExpressionTree(),new ExpressionTree(),new ExpressionTree()));
		 
		 tree.leftChild.setRightChild(new ExpressionTree("sin", new ExpressionTree(),new ExpressionTree(),new ExpressionTree()));
		 tree.leftChild.rightChild.setLeftChild(nullTree);
		 tree.setRightChild(nullTree.differentiate());
		 
		 nullTree = new ExpressionTree();
		 tree.leftChild.rightChild.setRightChild(nullTree);
		 
	 }
	 
 }
 
    	
    	
	// AND CHANGE THIS RETURN STATEMENT                        
        return tree;
    }
        
    
    public static void main(String args[]) {
     ExpressionTree e = new ExpressionTree("add(mult(add(2,x),cos(x)),1)");
    	//ExpressionTree e = new ExpressionTree("add(x,1)");                           
        System.out.println("input:  "+e);
       System.out.println("eval:   "+e.evaluate(1));
        System.out.println("diff:   "+e.differentiate());
        System.out.println("output: "+e);
 }
}