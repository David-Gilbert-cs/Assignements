import java.lang.Math; 
//The method you asked for are display in order from bottom to top

//Q7 This class will check the credit card number,print the monthly redit card balance, print the monthly savings,print total savings
public class YearlyBudget {
  
  //input income card balance, interest rate,card number,monthly rent
  public static void main(String[] args){
    double income= Double.parseDouble( args[0]);
    double cardBalance=Double.parseDouble( args[1]);
    double interestRate= Double.parseDouble( args[2]);
    Long cardNumber= Long.parseLong (args[3]);
    double rent= Double.parseDouble (args[4]); 
     //calling method 
   // testing credit card
    if(!validateCreditCard(cardNumber)){
      System.out.print("Invalid card");
    }
    else{
    
//printing the credit card balance
       System.out.println("credit card balance: ");
    printBalance(cardBalance, interestRate, buildExpenses(rent), buildPayments((income-calculateTax(income,10000, 20, 20000, 30, 45000, 50))) );
 
    // printing monthly savings with a loop 
      System.out.println("monthly savings: ");
    for(int i=0;i<12;i++){
      System.out.print("month "+(i+1)+" ");
 System.out.println(calculateMonthlySaving((income-calculateTax(income,10000, 20, 20000, 30, 45000, 50)),buildExpenses(rent)[i])+"$");
    }
  
     // printing total savings (sum with loop)
    double totalSaving=0.0;
    for(int i=0;i<12;i++){
 totalSaving+= calculateMonthlySaving((income-calculateTax(income,10000, 20, 20000, 30, 45000, 50)),buildExpenses(rent)[i]);
    }
      System.out.println("Hence forth the total saving is "+totalSaving+"$");
  }
 }
  
  
  
  //Q6 print the value of the credit card balance for each taking into account the inputs initial balance, interrest rate for a year, the expenses for per month(array),and the payment of the card per month(array)
  public static void printBalance(double creditCardBalance, double yearlyInterestRatePercent, double[] expensesPerMonth, double[] paymentPerMonth){
    
    double[] balance= new double[12];
    
    //set for loop to fill the array ill be printing 
    for(int i=0; i<12;i++){
      if (i==0){ //balance for the first month
        balance[i]+= (creditCardBalance+expensesPerMonth[i]-paymentPerMonth[i]);
        if(balance[i]>0){
          balance[i] += (balance[i]*yearlyInterestRatePercent/12.0) ;
        }
        else{} // closing it for good mesures
      }
      else{ //balance for the other months (idem)
        balance[i]+= (balance[i-1]+expensesPerMonth[i]-paymentPerMonth[i]);
        if(balance[i]>0){
          balance[i] += (balance[i]*(yearlyInterestRatePercent/12.0)) ;
        }
        else{}
      }
      
      System.out.println("Month "+(i+1)+" balance "+ balance[i]);
    }
    
  }
  
  
  
  
  
  //Q5 compute monthly credit card payment and return an array with a double assign to each month, input: yearly income AFTER TAX
  public static double[] buildPayments(double yearlyIncome){
    
    double[] monthlyCardPay = new double[12];
    for(int i=0;i<12;i++){// the loop fill the array and the if statement account for the difference for different months
      if (i==11){
        monthlyCardPay[i]= (0.1*yearlyIncome/12.0+150.0);
      }
      else if(i==8){
        monthlyCardPay[i]= (0.1*yearlyIncome/12.0+200.0);
      }
      else{
        monthlyCardPay[i]= (0.1*yearlyIncome/12.0);
      }
      
    }
    return monthlyCardPay ;
  }
  
  
  
  
  
  //Q4 compute all expense for every month and put them in an array of double (one per month), input rent,output array which sum is the yearly cost
  public static double[]  buildExpenses(double rent){
    
    double[] monthlyCost= new double[12];
    for(int i=0;i<12;i++){// the loop fill the array and the if statement account for the difference for different months
      if(i==0 || i==5 || i==11) {
        monthlyCost[i]= (600+200+rent);
      }
      else if (i==3 ||i==6){
        monthlyCost[i]=(600+100+rent);
      }
      else if (i==8){
        monthlyCost[i]=(600+300+100+rent);
      }
      else{
        monthlyCost[i]=600+rent;
      }
    }
    return monthlyCost;
  }
  
  
//Q3 this method return true if a credit card number is valid false otherwise, input:credit card number
  public static boolean validateCreditCard(long creditCardNumber){
    
    int sumEvenDigits=0;
    int sumOddDigits=0;
    
    for(int i=0;i<=16;i=i +2) {// select and sum all the even digits
      int ithDigit=(int)(((creditCardNumber/(Math.pow(10,i))))%10);
      sumEvenDigits= sumEvenDigits + ithDigit;
    }
    
    for(int x=1;x<16;x= x+2){ //select, double, mod 9 and then sum all digits
      int xthDigit=(int)(creditCardNumber/(Math.pow(10,x))%10);
      
      xthDigit= (xthDigit*2%9);
      
      sumOddDigits = sumOddDigits+ xthDigit ;  
    }
    if((sumOddDigits+sumEvenDigits)%10==0){
      return true ;
    }
    else{
      return false;
    }
    
  }
  
  
  
//Q2 method to get monthly saving : (income minus tax per month) - expense for each month
  public static double calculateMonthlySaving(double monthlyIncome, double monthlyExpense){
    
    double monthlySaving = monthlyIncome-monthlyExpense;
    
    return monthlySaving;
  }
  
  
  
//Q1. This method returns total taxe paid in a year and take as inputs the income,the first bracket, the rate after that bracket, the second braket, the rate after that braket, the third braket and the rate after that braket
  public static double calculateTax(double yearlyIncome, double bracket1Dollars, double bracket1Rate, double bracket2Dollars, double bracket2Rate, double bracket3Dollars, double bracket3Rate){
    
    double totalTaxPaid ; 
    //there is a if staement per bracket such that if the the income is inside the bracket the code goes to the else statement and end
    if ((yearlyIncome-bracket1Dollars) >0){
      if(yearlyIncome>bracket2Dollars){                                
        totalTaxPaid=(bracket2Dollars-bracket1Dollars)*bracket1Rate/100.0;
        if(yearlyIncome>bracket3Dollars){                                
          totalTaxPaid = totalTaxPaid + (bracket3Dollars-bracket2Dollars)*bracket2Rate/100.0;
          totalTaxPaid = totalTaxPaid + (yearlyIncome-bracket3Dollars)*bracket3Rate/100.0;
          return totalTaxPaid; 
          
        }
        else{
          totalTaxPaid = totalTaxPaid + (yearlyIncome-bracket2Dollars)*bracket2Rate/100.0;
          return totalTaxPaid; 
        } 
      }
      else{
        totalTaxPaid =(yearlyIncome-bracket1Dollars)*bracket1Rate/100.0;
        return totalTaxPaid;
      }
    }
    else{ 
      return 0.0;
    }
    
  }
}