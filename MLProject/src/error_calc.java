
import java.util.HashMap;

public class error_calc
{
	  
	public double[] error(int[] Group,HashMap<Integer, Indexvalue> datatable,int Excount,int fold,int Runs,int permindex)
	{
		   HashMap<Integer,Double> group1 = new HashMap<Integer,Double>();
		   Validation V1 = new Validation();
		   double[] GroupErrorSum = new double[6];
			/* 1,2,3,4,5 NN error for each group  */
					group1= V1.Groupholder(Group,datatable,Excount,fold);
					
					for(int k=1;k<6;k++)
					{
				
						GroupErrorSum[k]= group1.get(k);
	   
                   }
         return GroupErrorSum;
	}
  

}
