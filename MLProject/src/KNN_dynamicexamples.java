
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;

public class KNN_dynamicexamples 
{
		
		 public static void main(String[] args) 
	     {
			 
		        File file1 = new File("first_file.txt");
		        File file2 = new File("second_file.txt");
		        try {
		            Scanner Inputfile1 = new Scanner(file1);
		            Scanner Inputfile2 = new Scanner(file2);
		            
	                    /*Copying data from the first input file */
		            
		           int fold=Integer.parseInt(Inputfile1.next());
		           int Excount=Integer.parseInt(Inputfile1.next());
		           int Runs=Integer.parseInt(Inputfile1.next());
		           int [][] permutation = new int[Runs][Excount];
		           for(int i=0; i<Runs;i++)
		           {
		        	   for(int j=0;j<Excount;j++)
		        	   {
		        		   permutation[i][j]=Integer.parseInt(Inputfile1.next());
		        	   }
		           }
		           
		           
	               Inputfile1.close(); 
	               
		           /* copying data from the second input file*/ 
		        
	      HashMap<Integer, Indexvalue> datatable=new HashMap<Integer, Indexvalue>();
	      
		           int Noofrows=Integer.parseInt(Inputfile2.next());
		           int Noofcolumns=Integer.parseInt(Inputfile2.next());       	           
		           int DataCount=0;
		           for(int i=0;i<Noofrows;i++)
		           {
		        	   for(int j=0;j<Noofcolumns;j++)
		        	   { 
		        		   String temp=Inputfile2.next();
		        		   if(temp.equals("+") || temp.equals("-") )
		        		   {
		        			   datatable.put(DataCount, new Indexvalue(i, j, temp));
		        			   DataCount++;
	                       }
		        	   }
		           }
		           
		           Inputfile2.close();

		           /* E error calculation for all the permutations */
		           
		           int K = 6;
		           double[][] ErrorSum = new double[Runs][K];           
		           error_calc EC = new error_calc();
		           
		           for(int i=0; i<Runs;i++)
		           {
		        	  ErrorSum[i]= EC.error(permutation[i],datatable,Excount,fold,Runs,i);
		           }
		           
		           /*cross validation 
		            *  Error array starts from index 1. element in 0th index is zero*/
		           		           
		           double min=40000.0000;
		           int BestNN=0;
		           double Esum=0.00;
		           double V1=0.000;
		           double[] Error= new double[6]; 	        	   
        	      	double[] Variance = new double[6];
        	      	double[] Sigma = new double[6];
		           for(int k=1;k<6;k++)
		           {
		        	   Esum = 0.00;
		        	   for(int i=0; i<Runs;i++)
		               {
		         	      
		        		    Esum = Esum + ErrorSum[i][k];
		               }
		         	   
		         	    double e1 = (double) Esum/(double)Runs;
		         	    V1 = 0.00;
		         	   for(int i=0; i<Runs;i++)
		               {
		         	      
		        		    V1= V1 + ( Math.pow((ErrorSum[i][k]-e1),2));
		               }
		         	       //double var = (double) ( Math.pow((g1-e1),2) + Math.pow((g2-e1),2) + Math.pow((g3-e1),2)) / (Runs-1) ;
		         	        double var = (double) V1 / (Runs-1);
		         	        double sig = (double) (Math.sqrt(var));
		         	      if(e1<min)
		         	      {
		         	    	  min = e1;
		         	    	  BestNN = k;
		         	      }
		         	      	Error[k] = e1; 	        	   
		         	      	Variance[k] = var;
		         	      	Sigma[k] = sig;
		           }
		           
		           System.out.println("Best NN algorithm is "+ BestNN + " NN");
		           
		           
		           /*predicting the output file*/
		           
		           File file3 = new File("second_file.txt");
		           Scanner input3 = new Scanner(file3);
		           predictoutput p1 = new predictoutput();
		         
		           int rows=Integer.parseInt(input3.next());
		           int columns=Integer.parseInt(input3.next()); 
		           int knn=5;
		           String[][][] predicoutput = new String[rows][columns][knn];
		           String[] calculatedsign = new String[5];
		           for(int i=0;i<rows;i++)
		           {
		        	   for(int j=0;j<columns;j++)
		        	   { 
		        		   String temp=input3.next();
		        		   if(temp.equals("."))
		        		   {
		        			   calculatedsign = p1.predict(datatable,i,j,Excount);
		        			   predicoutput[i][j] = calculatedsign;
		        			   //System.out.println("predicted output for     "+ i + "  "+ j+" is  "+ predicoutput[i][j][1]);
	                       }
		        		   
		        	   }
		           }
		           	           
		           input3.close();
		           
		           
		            /*printing the predicted output to the file*/
		           
		           
		           int knncount;
		           	           
		           for(knncount=1;knncount<6;knncount++)
		           {
		        	   PrintWriter out = new PrintWriter(new FileWriter("output"+knncount+".txt"));
		        	   File file4 = new File("second_file.txt");
			           Scanner input4 = new Scanner(file4);
			           input4.nextLine();
			           out.println("K="+ knncount +"  "+"Error="+ Error[knncount] +"  "+"Sigma="+ Sigma[knncount] +"  ");
			           
		        	   for(int i=0;i<rows;i++)
		        	   	{
		        		   for(int j=0;j<columns;j++)
		        		   { 
		        			   String temp=input4.next();
		        			   if(temp.equals("."))
		        			   {
		        			     String s1;
		        			      s1 = predicoutput[i][j][knncount];
		        			      out.write(s1+" ");

		        			   }
		        			   else if (temp.equals("+") || temp.equals("-") )
		        				   
		        			   {
		        				 
		        				   out.write(temp+" ");
		        			   }
		        				   
		        		   
		        		   }
		        		   out.println();
		        	   	}
		        	  
		        	     for (int i=0;i<Runs;i++)
		        	     {
		        	        out.write("E"+(i+1)+ ":"+ErrorSum[i][knncount]+" ");
		        	     }
		        	     out.println();
		        	   out.close();
		        	   input4.close();
		           }
		        
		          
		         
	           } 
	                catch (Exception e) 
		        {
		       e.printStackTrace();
		        }
		        
		    }        
	         

		 
}



