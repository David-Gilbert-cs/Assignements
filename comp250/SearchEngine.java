import java.util.*;
import java.io.*;

// This class implements a google-like search engine
public class SearchEngine {

    public HashMap<String, LinkedList<String> > wordIndex;                  // this will contain a set of pairs (String, LinkedList of Strings)	
    public DirectedGraph internet;             // this is our internet graph
    
    
    
    // Constructor initializes everything to empty data structures
    // It also sets the location of the internet files
    SearchEngine() {
	// Below is the directory that contains all the internet files
	HtmlParsing.internetFilesLocation = "internetFiles";
	wordIndex = new HashMap<String, LinkedList<String> > ();		
	internet = new DirectedGraph();				
    } // end of constructor//2017
    
    
    // Returns a String description of a searchEngine
    public String toString () {
	return "wordIndex:\n" + wordIndex + "\ninternet:\n" + internet;
    }
    
    
    // This does a graph traversal of the internet, starting at the given url.
    // For each new vertex seen, it updates the wordIndex, the internet graph,
    // and the set of visited vertices.
    
    void traverseInternet(String url) throws Exception {

	                                     // wordIndex hashmap  first is list of words each words have a linked list of  url that has it
    	internet.addVertex(url);
    	internet.setVisited(url, true);
    	 
    	
    	LinkedList<String> adjacent = HtmlParsing.getLinks(url); 	// set edges between adjacent sites
    	Iterator<String> i= adjacent.iterator();
    	while(i.hasNext()){
    	String adjUrl	=i.next();
    		if(internet.getVertices().contains(adjUrl)){
    		internet.addEdge(url,adjUrl );
    		}
    		else{
    			internet.addVertex(adjUrl);
    			internet.addEdge(url,adjUrl );
    		}
    	}
    	
    	
    	   	             
    	
    	//fills the wordindex with the given url
    	LinkedList<String> content = HtmlParsing.getContent(url); // content is all the words from the url
    		Iterator<String> j = content.iterator();
    		while ( j.hasNext() ) {
    		String s = j.next();
    		   		
    		LinkedList<String> words = wordIndex.get(s); //adds the url in the  key word's value . we create a new linked list if its the first time the word appears
    	    if (words == null) {
    	        words = new LinkedList<String>();
    	        wordIndex.put(s,words);
    	    }
    	    words.addLast(url);
    		}
    	//end wordIndex filling
    	    
    	    //start recursive step : iterate breath first search with recursion if adjacent not visited
    	    
    	    LinkedList<String> newUrls = internet.getNeighbors(url);
    	    Iterator<String> k = newUrls.iterator(); 
    	    while ( k.hasNext() ) {
        		String nextUrl = k.next();
        		if(internet.getVisited(nextUrl)){
        		}	
        		else{
        			traverseInternet(nextUrl);		
        		}	
    	    
    	    }
    		//end of recursive step
 
    		
    } // end of traverseInternet
    
    
  
    void computePageRanks() {
    	
    	double pr=0.0 ;
    	
    	//base case PR = 1 for all sites
    	 LinkedList<String> sites = internet.getVertices();
 	    Iterator<String> k = sites.iterator(); 
 	    while ( k.hasNext() ) {
     		String nextUrl = k.next();
     		internet.setPageRank(nextUrl,1.0); 
 	    }
    	
 	    for(int i=0;i<100;i++){
 	    	double sum = 0 ;
 	    	
 	    	LinkedList<String> sites2 = internet.getVertices();  //iterates through all vertices finding a closer approximation of pr each time (repeated 100 times)
 	 	    Iterator<String> j = sites2.iterator(); 
 	 	    while ( j.hasNext() ) {
 	     		String nextUrl2 = j.next();
 	     		
 	     		// computing pr by iterating trough every adjacent vertices
 	     		 LinkedList<String> adjacents = internet.getEdgesInto(nextUrl2);
 	     		 if(adjacents== null) sum=0;
 	     	    Iterator<String> q = adjacents.iterator(); 
 	     	    while ( q.hasNext() ) {
 	         		String nextAdjacent = q.next();     
 	         		double out = (double) internet.getOutDegree(nextAdjacent);
 	         		double pageranke =internet.getPageRank(nextAdjacent);
 	         			if(out != 0){
 	         				sum= sum +internet.getPageRank(nextAdjacent)/(double)internet.getOutDegree(nextAdjacent);	
 	         			}
 	     	    }
 	     		 	     		
 	     	pr= 0.5 +0.5*(sum);
 	     	sum=0;
 	     	internet.setPageRank(nextUrl2,pr); 
 	 	    }
 	    	
 	    }
 	    
	
	
    } // end of computePageRanks
    
	
    /* Returns the URL of the page with the high page-rank containing the query word
       Returns the String "" if no web site contains the query.
       This method can only be called after the computePageRanks method has been executed.
       Start by obtaining the list of URLs containing the query word. Then return the URL 
       with the highest pageRank.
       This method should take about 25 lines of code.
    */
    String getBestURL(String query) {
    	String bestUrl = "";
    	double bestRank = 0;
    	
      		if(wordIndex.containsKey(query)){
      			
      			LinkedList<String> querySites = wordIndex.get(query);  //make a linked list with all sites with the query word in it
      	  	    
      			Iterator<String> k = querySites.iterator(); 
      	  	    while ( k.hasNext() ) {
      	      		String nextUrl = k.next();
      	      			if(bestUrl.equals("")){
      	      				bestUrl=nextUrl;
      	      				bestRank = internet.getPageRank(nextUrl);
      	      			}
      	      			else{
      	      				if(bestRank<internet.getPageRank(nextUrl)){
      	      					bestUrl=nextUrl;
      	      					bestRank=internet.getPageRank(nextUrl);
      	      				}
      	      			}
      	  	    }
      	  	    return bestUrl;
      	  	    
      	  	}
      		else {
      			return "";
      		}
      		
			

    } // end of getBestURL
    
    
	
    public static void main(String args[]) throws Exception{		
	SearchEngine mySearchEngine = new SearchEngine();
	// to debug your program, start with.
		//mySearchEngine.traverseInternet("http://www.cs.mcgill.ca/~blanchem/250/a.html");
	
	// When your program is working on the small example, move on to
	mySearchEngine.traverseInternet("http://www.cs.mcgill.ca");
	
	
	mySearchEngine.computePageRanks();
	
	BufferedReader stndin = new BufferedReader(new InputStreamReader(System.in));
	String query;
	do {
	    System.out.print("Enter query: ");
	    query = stndin.readLine();
	    if ( query != null && query.length() > 0 ) {
		System.out.println("Best site = " + mySearchEngine.getBestURL(query));
	    }
	} while (query!=null && query.length()>0);				
    } // end of main
}