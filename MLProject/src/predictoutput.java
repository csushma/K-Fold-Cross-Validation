import java.util.HashMap;


public class predictoutput
{
	int [][] distance ;
    int [][] ExIndex;
    HashMap<Integer,String> Signcal = new HashMap<Integer,String>();
    
  
	public String[] predict(HashMap<Integer, Indexvalue> datatable, int x1,int y1,int Excount)
	{
		String[] predicsign = new String[6];
		try {     
            int [] distance = new int[Excount];
            int []  ExIndex = new int[Excount];
            
            /* calculating the distances */
            
             for(int i=0;i<Excount;i++)
          {         	     
                   int x2=datatable.get(i).getX();
                   int y2=datatable.get(i).getY();                   
                   ExIndex[i] = i;
      
                   int dist;
             
	               dist = ((int) (Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2)));
                        distance[i] = dist;
                   	        
           }
             
             /* sorting distance array and   data index array */
       
                for(int i=1;i<Excount;i++)
	          { 
                         
               	 int curent = distance[i];
               	 int curent2 = ExIndex[i];
               	 int j= i-1;
               	 	while (j>=0 && distance[j]> curent)
               	 	{
               	 		distance[j+1] = distance[j];
               	 		ExIndex[j+1] = ExIndex[j];
               	 		j=j-1;
               	 	}
               		 distance[j+1] = curent;
               		 ExIndex[j+1] = curent2;
               		 
               }
               	 
             /* Predicting the sign */

             int poscount;
             int negcount;
             String sign1;

                   for(int k=1;k<6;k++)
                    {
                	   //errorcount=0;
                	    poscount=0;
                	    negcount=0;
                	   
            	                for(int i=0;i<k;i++) 
            	                {
            	                	
            	                	int tmp = ExIndex[i];
            	                	String sign = datatable.get(tmp).getSign();
            	                		if(sign.equals("+"))
            	                			poscount ++;
            	                		if(sign.equals("-"))
            	                			negcount ++;
            	                		     
            	                 }
            	                	     if (poscount>negcount)
   	                		                 {
   	                		                  sign1 = "+";
   	                		                 }
   	                		              else if (poscount == negcount)
   	                		                {
   	                		            	 sign1 = "-";
   	                		                }
   	                		              else 
   	                		                 {
   	                		            	  sign1 = "-";
   	                		                 }
            	                	     predicsign[k]= sign1; 
            	                	     //System.out.println("for the nearest neighbour   "+k+" predicted value is   "+predicsign[k]);
            	                	     
            	           }
            	                
                    
           } 
           catch (Exception e) 
           {
                e.printStackTrace();
           }
		return predicsign;
		
	}// func

}// class
