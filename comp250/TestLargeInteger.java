import java.lang.Math;

/*********************************************************/
/* NAME: David Gilbert                                                  */
/* STUDENT ID: 260746680                                           */
/*********************************************************/

/* This class stores and manipulates very large non-negative integer numbers 
   The digits of the number are stored in an array of bytes. */
class LargeInteger {

    /* The digits of the number are stored in an array of bytes. 
       Each element of the array contains a value between 0 and 9. 
       By convention, digits[digits.length-1] correspond to units, 
       digits[digits.length-2] corresponds to tens, digits[digits.length-3] 
       corresponds to hundreds, etc. */

    byte digits[];


    
    /* Constructor that creates a new LargeInteger with n digits */
    public LargeInteger (int n) {
        digits= new byte[n];
    }

        
    /* Constructor that creates a new LargeInteger whose digits are those of the string provided */
    public LargeInteger (String s) {        
        digits = new byte[s.length()]; /* Note on "length" of arrays and strings: Arrays can be seen 
                                          as a class having a member called length. Thus we can access 
                                          the length of digits by writing digits.length
                                          However, in the class String, length is a method, so to access 
                                          it we need to write s.length() */

        for (int i=0;i<s.length();i++) digits[i] = (byte)Character.digit(s.charAt(i),10);
        /* Here, we are using a static method of the Character class, called digit, which 
           translates a character into an integer (in base 10). This integer needs to be 
           cast into a byte. ****/
    }


    /* Constructor that creates a LargeInteger from an array of bytes. Only the bytes  
       between start and up to but not including stop are copied. */
    public LargeInteger (byte[] array, int start, int stop) {
        digits = new byte[stop-start];
        for (int i=0;i<stop-start;i++) digits[i] = array[i+start];
    }


    /* This method returns a LargeInteger where eventual leading zeros are removed. 
       For example, it turns 000123 into 123. Special case: it turns 0000 into 0. */
    public LargeInteger removeLeadingZeros() {
        if (digits[0]!=0) return this;
        int i = 1;
        while (i<digits.length && digits[i]==0) i++;
        if (i==digits.length) return new LargeInteger("0");
        else return new LargeInteger(digits,i,digits.length);
    } // end of removeLeadingZeros
   

    /* This methods multiplies a given LargeInteger by 10^nbDigits, simply by shifting 
    the digits to the left and adding nbDigits zeros at the end */
 public LargeInteger shiftLeft(int nbDigits) {
	 
     LargeInteger ret = new LargeInteger( digits.length + nbDigits );
     for (int i = 0 ; i < digits.length ; i++) ret.digits[ i ] = digits[ i ];
     for (int i = 0; i <  nbDigits; i++) ret.digits[ digits.length + i ] = 0;
     return ret;
 } // end of shiftLeft


      /* Returns true if the value of this is the same as the value of other */                        //equals for large int
    public boolean equals (LargeInteger other) {
        if ( digits.length != other.digits.length ) return false;
        for (int i = 0 ; i < digits.length ;i++ ) {
            if ( digits[i] != other.digits[i] ) return false;
        }
        return true;
    } // end of equals


      /* Returns true if the value of this is less than the value of other ****/                     //Large int  smaller than
    public boolean isSmaller (LargeInteger other) {
        if ( digits.length > other.digits.length ) return false;
        if ( digits.length < other.digits.length ) return true;
        for (int i = 0 ; i < digits.length ; i++ ) {
            if ( digits[i] < other.digits[i] ) return true;
            if ( digits[i] > other.digits[i] ) return false;
        }
        return false;
    } // end of isSmaller
    


    /* This method adds two LargeIntegers: the one on which the method is                       // usdefull for Q1
       called and the one given as argument. The sum is returned. The algorithms 
       implemented is the normal digit-by-digit addition with carry. */

    LargeInteger add(LargeInteger other) {                                                           
        int size = Math.max( digits.length,other.digits.length );

        /* The sum can have at most one more digit than the two operands */
        LargeInteger sum = new LargeInteger( size + 1 ); 
        byte carry = 0;

        for (int i = 0; i < size + 1 ;i++) {
            // sumColumn will contain the sum of the two digits at position i plus the carry
            byte sumColumn = carry; 
            if ( digits.length - i  - 1 >= 0) sumColumn += digits[ digits.length - i - 1 ];
            if (other.digits.length - i - 1  >= 0) sumColumn += other.digits[ other.digits.length - i - 1 ];
            sum.digits[ sum.digits.length - 1 - i ] = (byte)( sumColumn % 10 ); // The i-th digit in the sum is sumColumn mod 10
            carry = (byte)( sumColumn / 10 );          // The carry for the next iteration is sumColumn/10
        }        
        return sum.removeLeadingZeros();
    } // end of add



    /* This method subtracts the LargeInteger other from that from where the method is called.                                     // Q3 ???
       Assumption: the argument other contains a number that is not larger than the current number. 
       The algorithm is quite interesting as it makes use of the addition code.
       Suppose numbers X and Y have six digits each. Then X - Y = X + (999999 - Y) - 1000000 + 1.
       It turns out that computing 999999 - Y is easy as each digit d is simply changed to 9-d. 
       Moreover, subtracting 1000000 is easy too, because we just have to ignore the '1' at the 
       first position of X + (999999 - Y). Finally, adding one can be done with the add code we already have.
       This tricks is the equivalent of the method used by most computers to do subtractions on binary numbers. ***/

    public LargeInteger subtract( LargeInteger other ) {
        // if other is larger than this number, simply return 0;
        if (this.isSmaller( other ) || this.equals( other ) ) return new LargeInteger( "0" );

        LargeInteger complement = new LargeInteger( digits.length ); /* complement will be 99999999 - other.digits */
        for (int i = 0; i < digits.length; i++) complement.digits[ i ] = 9;
        for (int i = 0; i < other.digits.length; i++) 
            complement.digits[ digits.length - i - 1 ] -= other.digits[other.digits.length - i -  1];

        LargeInteger temp = this.add( complement );     // add (999999- other.digits) to this
        temp = temp.add(new LargeInteger( "1" ));       // add one

        // return the value of temp, but skipping the first digit (i.e. subtracting 1000000)
        // also making sure to remove leading zeros that might have appeared.
        return new LargeInteger(temp.digits,1,temp.digits.length).removeLeadingZeros();
    } // end of subtract


    /* Returns a randomly generated LargeInteger of n digits */                                       //randomly generates large int
    public static LargeInteger getRandom( int n ) {
        LargeInteger ret = new LargeInteger( n );
        for (int i = 0 ; i < n ; i++) {
            // Math.random() return a random number x such that 0<= x <1
            ret.digits[ i ]=(byte)( Math.floor( Math.random() * 10) );
            // if we generated a zero for first digit, regenerate a draw
            if ( i==0 && ret.digits[ i ] == 0 ) i--;
        }
        return ret;
    } // end of getRandom



    /* Returns a string describing a LargeInteger 17*/                                                  //large int to string
    public String toString () {        

        /* We first write the digits to an array of characters ****/
        char[] out = new char[digits.length];
        for (int i = 0 ; i < digits.length; i++) out[ i ]= (char) ('0' + digits[i]);

        /* We then call a String constructor that takes an array of characters to create the string */
        return new String(out);
    } // end of toString




    /* This function returns the product of this and other by iterative addition */                                    //  Q1 here
    public LargeInteger iterativeAddition(LargeInteger other) {
        LargeInteger value = new LargeInteger(this.digits.length+other.digits.length+2);
        
     if (this.equals(value) || other.equals(value))   //if either int is zero it'll return zero
      return value.removeLeadingZeros(); 
     

     for(int j=0; j<this.digits.length;j++){
      if(this.digits[j]==0) {
      }
      
     for(int i=1; i<=(this.digits[j]*(Math.pow(10, this.digits.length-j-1))); i++){
     value= value.add(other);
     
     }
     }
     
     
     
     value.removeLeadingZeros();
     
        return value ;
    } // end of iterativeAddition



    /* This function returns the product of this and other by using the standard multiplication algorithm */     // write Q2 here
    public LargeInteger standardMultiplication(LargeInteger other) {
        
     LargeInteger result = new LargeInteger(this.digits.length+other.digits.length+2);
     int carry=0;
     
     int size=0;
     if(this.isSmaller(other)){
         size= other.digits.length+2;
     }
     else{
         size= this.digits.length+2; 
     }
     
     LargeInteger value = new LargeInteger(size);
     
    
      
     
     for(int j=0;j<=this.digits.length;j++ ) { //position of the digit multiplying.  AFTER EACH VALUE (SMALL NUMBER that we add) is created we add it to the result Large int 
      if(j==this.digits.length){
      result= result.add(value.shiftLeft(j-1));
      return result ; 
      }
      else if (j==0){
      }
      
      else{
     result= result.add(value.shiftLeft(j-1));
      }
     
     for(int k=0;k<other.digits.length;k++){    //position of the one being multiplied
    	 //value at the digits place
     value.digits[value.digits.length-k-1]=(byte) ((this.digits[this.digits.length-j-1]*other.digits[other.digits.length-k-1])%10+carry);  
     // value to add to next value of k
        carry= (this.digits[this.digits.length-j-1]*other.digits[other.digits.length-k-1])/10;    
        
        if(k==(other.digits.length-1)){
        	value.digits[value.digits.length-k-2]=(byte) carry;
        	carry=0;
        }
        }
     }
      
     
     


     
     return result ; 
    } // end of standardMultiplication
                


    /* This function returns the product of this and other by using the basic recursive approach described 
       in the homework. Only use the built-in "*" operator to multiply single-digit numbers */              // slow version of Q3
    public LargeInteger recursiveMultiplication(LargeInteger other ){

        // left and right halves of this and number2                                                                                        
        LargeInteger leftThis, rightThis, leftOther, rightOther;
        LargeInteger term1,  term2,  term3,  term4, sum; // temporary terms                                                                      

        if ( digits.length==1 && other.digits.length==1 ) {
            int product = digits[0] * other.digits[0];
            return new LargeInteger( String.valueOf( product ) );
        }

        int k = digits.length;
        int n = other.digits.length;
        leftThis = new LargeInteger( digits, 0, k - k/2 );
        rightThis = new LargeInteger( digits, k - k/2, k );
        leftOther = new LargeInteger( other.digits, 0, n - n/2 );
        rightOther = new LargeInteger( other.digits, n - n/2, n );

        /* now recursively call recursiveMultiplication to compute the                    
           four products with smaller operands  */

        if ( n > 1 && k > 1 )  term1 = rightThis.recursiveMultiplication(rightOther );
        else term1 = new LargeInteger( "0" );

        if ( k>1 ) term2 = ( rightThis.recursiveMultiplication( leftOther ) ).shiftLeft( n/2 );
        else term2 = new LargeInteger( "0" );

        if ( n>1 ) term3 = ( leftThis.recursiveMultiplication( rightOther ) ).shiftLeft( k/2 );
        else term3 = new LargeInteger( "0" );

        term4 = ( leftThis.recursiveMultiplication( leftOther ) ).shiftLeft( k/2 + n/2 );

        sum = new LargeInteger( "0" );
        sum = sum.add( term1 );
        sum = sum.add( term2 );
        sum = sum.add( term3 );
        sum = sum.add( term4 );

        return sum;
    } // end of recursiveMultiplication             


    /* This method returns the product of this and other by using the faster recursive approach                                      //write Q3 here
       described in the homework. It only uses the built-in "*" operator to multiply single-digit numbers */
    public LargeInteger recursiveFastMultiplication(LargeInteger other) {
    	
    	//declaring variables
    	LargeInteger leftThis, rightThis, leftOther, rightOther;
        LargeInteger term1,  term2,  term3, sum;                                                                       


        int k = this.digits.length;
        int n = other.digits.length;
        
      //reverse this and other so that this is smaller than other
        if (n<k){
        	return other.recursiveFastMultiplication(this);
        }
    //base case
        if (k==1) {
        return this.standardMultiplication(other);
        }
        
        
        leftThis = new LargeInteger( this.digits,0, k - k/2  );
        rightThis = new LargeInteger( this.digits, k - k/2, k );
        leftOther = new LargeInteger( other.digits, 0, n - n/2);
        rightOther = new LargeInteger( other.digits, n - n/2, n);
 
        // the algorithm
        term1=rightThis.recursiveFastMultiplication(rightOther);
    	term2=leftThis.recursiveFastMultiplication(leftOther);
    	term3=(((leftThis.add(rightThis)).recursiveFastMultiplication((leftOther.shiftLeft((n/2)-(k/2))).add(rightOther))).subtract(term2.shiftLeft((n/2)-(k/2)))).subtract(term1);  // something wrong in term 3  
    	
    	
    	sum= (term2.shiftLeft((k/2)+(n/2))).add(term3.shiftLeft((k/2))).add(term1) ;
    	
    	return sum; 
    	
    }


}  // end of the LargeInteger class




// This class contains code to test the methods of the LargeInteger class. 
// Modify it as you wish to thorougly test each of the multiplication methods
// and to measure their running time.
// THE CODE IN THIS CLASS WILL NOT BE EVALUATED OR TESTED BY THE TAS.

public class TestLargeInteger {                                                                                                            // for testing (main)
    public static void main( String args[] ) {
       /* TEST YOUR METHODS BY ADDING CODE HERE */
        /* THIS CODE IS NOT GOING TO BE GRADED. IT'S JUST FOR YOU TO TEST YOUR PROGRAM */

        /* For example 
        LargeInteger a = new LargeInteger( "123" );
        LargeInteger b = new LargeInteger( "76543210" );
        LargeInteger c = new LargeInteger( "0" );
        LargeInteger d = LargeInteger.getRandom(250);
        LargeInteger e = LargeInteger.getRandom(250);
           System.out.println(d.standardMultiplication(e));
        System.out.println(e + " * " + d + " = " + e.recursiveMultiplication( d ) );*/
        
        for(int i=2; i<=8000000; i= 2*i){
        LargeInteger d = LargeInteger.getRandom(i);
        LargeInteger e = LargeInteger.getRandom(i);
   double ini=System.nanoTime();
   LargeInteger a = d.standardMultiplication(e);
        double fin=System.nanoTime();
        double t1=(fin-ini);
		System.out.println(t1);
       
            
       double ini2=System.nanoTime();
       LargeInteger a2 = d.recursiveFastMultiplication(e);
            double fin2=System.nanoTime();
            double t12=(fin2-ini2);
    		System.out.println(t12);
    		
    		System.out.println(t12==t1);
            }
     
    }
}
              
        