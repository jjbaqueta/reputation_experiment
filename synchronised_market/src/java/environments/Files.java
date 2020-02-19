package environments;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import entities.model.sellers.Seller;
import enums.CriteriaType;
import reputationModels.Reputation;

public abstract class Files 
{
	/*
	 * This method writes in the output file 'sales.txt' the current sale state of each seller
	 * @param seller represents the seller who will be write his sale state
	 * @param time current time, it is used to sort the writing events
	 */
	public static void writeSaleStatus(Seller seller, long time)
	{
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(Experiment.fileSales, true));
			writer.append(seller.getName() + ";" + seller.getMyType() + ";" + time + ";" + seller.getSaleMadeCount() +"\n");			     
		    writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*
	 * This method writes in the output file 'sales.txt' the current sale state of each seller
	 * @param seller represents the seller who will be write his sale state
	 * @param time current time, it is used to sort the writing events
	 */
	public static void writeReputations()
	{
		try 
		{		
			BufferedWriter writer = new BufferedWriter(new FileWriter(Experiment.fileRep, true));
			
			int maxSize = 0;
			long maxTime = 0;
			
			// Getting the size of the largest list
			for(Seller seller : Market.sellers)
			{
				int currentSize = seller.getReputations().size();
				
				if(maxSize < currentSize)
				{
					maxSize = currentSize;
					maxTime = seller.getReputations().get(seller.getReputations().size() - 1).getTime();
				}
			}
			
			// Writing reputation in file
			for(Seller seller : Market.sellers)
			{		
				for(Reputation rep : seller.getReputations())
				{
					writer.append(rep.getAgent().getName() + ";" + 
								  rep.getTime() + ";" + 
								  rep.getReputationRatings().get(CriteriaType.PRICE.getValue()) + ";" +
							      rep.getReputationRatings().get(CriteriaType.QUALITY.getValue()) + ";" +
							      rep.getReputationRatings().get(CriteriaType.DELIVERY.getValue()) + ";" +
							      rep.getReliabilityRatings().get(CriteriaType.PRICE.getValue()) + ";" +
							      rep.getReliabilityRatings().get(CriteriaType.QUALITY.getValue()) + ";" +
							      rep.getReliabilityRatings().get(CriteriaType.DELIVERY.getValue()) + "\n");
				}
				
				if(seller.getReputations().size() < maxSize)
				{
					Reputation lastRep = seller.getReputations().get(seller.getReputations().size() - 1);
						
					writer.append(lastRep.getAgent().getName() + ";" + 
								  maxTime + ";" + 
								  lastRep.getReputationRatings().get(CriteriaType.PRICE.getValue()) + ";" +
								  lastRep.getReputationRatings().get(CriteriaType.QUALITY.getValue()) + ";" +
								  lastRep.getReputationRatings().get(CriteriaType.DELIVERY.getValue()) + ";" +
								  lastRep.getReliabilityRatings().get(CriteriaType.PRICE.getValue()) + ";" +
								  lastRep.getReliabilityRatings().get(CriteriaType.QUALITY.getValue()) + ";" +
								  lastRep.getReliabilityRatings().get(CriteriaType.DELIVERY.getValue()) + "\n");
				}
			}
		    writer.close();    
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*
	 * This method writes in the output file 'sales.txt' the current sale state of each seller
	 * @param seller represents the seller who will be write his sale state
	 * @param time current time, it is used to sort the writing events
	 * 
	 * "image(seller,product_name,time,[price,quality,delivery])"
	 */
	public static void writeImgStatus(String buyerName, String sellerName, String productName, double pScore, double qScore, double dScore)
	{
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(Experiment.fileImg, true));
			writer.append(buyerName + ";" +
						  sellerName + ";" + 
						  productName + ";" + 
						  System.currentTimeMillis() + ";" + 
						  pScore + ";" +
						  qScore + ";" +
						  dScore + "\n");			     
		    writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
