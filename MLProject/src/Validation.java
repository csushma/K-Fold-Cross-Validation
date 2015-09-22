
import java.util.*;


public class Validation
{
   
    HashMap<Integer,Integer> group1 = new HashMap<Integer,Integer>();
    HashMap<Integer,Double> group2 = new HashMap<Integer,Double>();
	
	public HashMap<Integer,Double> Groupholder(int [] group, HashMap<Integer, Indexvalue> datatable, int Excount, int fold )
	{

		try {     
		             int size = Excount/fold; 
			         int Set1Ex = (int)Math.floor(Excount/fold);
	                 int Set2Ex = Excount-Set1Ex; 

	                
	                 int [][] distance = new int[Excount][Set2Ex]; 
	               
 	                 int poscount;
	                 int negcount;
	                 int m,c=0;
	                

	               
	                 /* calculating the distances */
	               for(int k=1; k<6;k++){
	            	   int fcount = 0;
	            	   int testex = 0;
	            	   int [] TrainExIndex = new int[Set2Ex];	
		                 int [] TrainExIndex1 = new int[Set2Ex];	
	 	                 int [] TestExIndex = new int[Set1Ex];	
	 	                 int sum = 0;
	                  for(int i=0; i<fold; i++)		
	                  {  
		            	   int errorcount = 0;
	            		  if(testex == 0){
                			  int z=0;
                			  for(int p=size; p<Excount; p++){
                				  TrainExIndex[z] = group[p];
                				  z++;
                			  }
                			  fcount++;
                		  }
                		  else if(fcount != fold-1){
                			  int r=0;
                			  for(int p=0; p<testex && p<Excount; p++){
                				  TrainExIndex[r] = group[p];
                				  r++;
                			  }
                			  for(int p=testex+size; p<Excount; p++){
                				  TrainExIndex[r] = group[p];
                				  r++;
                			  }
                			  fcount++;
                		  }
                		  else{
                			  int r=0;
                			  for(int p=0; p<testex; p++){
                				  TrainExIndex[r] = group[p];
                				  r++;
                			  }
                		  }
                		  int y=0;
	                	  for(m=testex; m<testex+size && testex!=Excount;m++)
	                      {
	                		   TestExIndex[y] = group[m];
		                	   int x1=datatable.get(TestExIndex[y]).getX();
		                       int y1=datatable.get(TestExIndex[y]).getY();
		                       int dist;
		                       y++;
		                       for(int j=0;j<Set2Ex;j++)
		                       { 
		           		 
			                 	   int x2=datatable.get(TrainExIndex[j]).getX();
			                 	   int y2=datatable.get(TrainExIndex[j]).getY();
			                    
			                 	   dist = ((int) (Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2)));
			                       distance[m][j] = dist;
			                           
		                       }
	                       if(testex == Excount)
	                     	  break;
	                    }
		        	//   testex = m; 
		        	   
		        	  
		        		   for(c=testex; c<testex+size && testex!=Excount;c++){
		        			   poscount=0;
                         	   negcount=0;
                         	  Map<Integer,Integer> map1 = new TreeMap<Integer,Integer>();
                         	
                         	  for(int b=0; b<Set2Ex; b++){
   		        			   map1.put(TrainExIndex[b],distance[c][b]); 
                         	  }
                         	 Map<Integer, Integer> sortedMap = sortByComparator(map1);
                         	  List<Integer> list = new ArrayList<Integer>(sortedMap.keySet());
                         	  for(int b=0; b<Set2Ex; b++){
                         		  TrainExIndex1[b] = list.get(b);
                         	  }
                         	 
                         	   for(int d=0; d<k;d++){
                         		   	int tmp2 = TrainExIndex1[d];
	       	                		String sign1 = datatable.get(tmp2).getSign();
	       	                		if(sign1.equals("+"))
	       	                			poscount ++;
	       	                		if(sign1.equals("-"))
	       	                			negcount ++; 
                         	   }
                         		  	  int tmp = TestExIndex[c-testex];
                         		  	  String sign = datatable.get(tmp).getSign();
		                         	  if (poscount>negcount)
		                         	  {
			     		                  if(!sign.equals("+"))
			     		                    errorcount = errorcount + 1;
		                         	  }
		                         	  else if (poscount == negcount)
		                         	  {
		     		    	             if(!sign.equals("-"))
			                		        errorcount = errorcount + 1;
		                         	  }
		                         	  else if(!sign.equals("-"))
			                		        errorcount = errorcount + 1;
		        		   }
		        		   testex = m;
		        		   group1.put(i, errorcount); 
		        	   }
	                  	for(int i=0;i<fold;i++){
							sum = sum + group1.get(i);
						}
						
						double avg = (double)(sum)/(double)Excount;
						group2.put(k,avg);
		            }

	                } 
	                catch (Exception e) 
		            {
		                 e.printStackTrace();
		            }
		       return group2;
		    }//func
	private static Map<Integer, Integer> sortByComparator(Map<Integer, Integer> unsortedMap2) {
		 
		// Convert Map to List
		List<Map.Entry<Integer, Integer>> list = 
			new LinkedList<Map.Entry<Integer, Integer>>(unsortedMap2.entrySet());
 
		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
 
		// Convert sorted map back to a Map
		Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
		for (Iterator<Map.Entry<Integer, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Integer, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	} // class


