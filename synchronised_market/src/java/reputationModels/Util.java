package reputationModels;

import java.util.ArrayList;
import java.util.List;

import entities.model.sellers.Seller;

public abstract class Util 
{
	/*
	 * This method checks the reputation of a given seller
	 * @param priceWeight buyer's preference regarding to price
	 * @paramqualityWeight buyer's preference regarding to quality
	 * @param deliveryWeight buyer's preference regarding to weight
	 * @return If seller's reputation is bigger than 0.5 return true, otherwise, return false
	 */
	public static boolean isAcceptableScore(double cutScore, double pRating, double qRating, double dRating, double pWeight, double qWeight, double dWeight)
	{	
		if(pWeight == 1 && qWeight == 0 && dWeight == 0)
			return pRating >= cutScore ? true : false;
			
		else if(pWeight == 0 && qWeight == 1 && dWeight == 0)
			return qRating >= cutScore ? true : false;
			
		else if(pWeight == 0 && qWeight == 0 && dWeight == 1)
			return dRating >= cutScore ? true : false;
			
		else if(pWeight != 0 && qWeight == 0 && dWeight != 0)
			return ((pRating * pWeight) + (dRating * dWeight))/2 >= cutScore ? true : false;
			
		else if(pWeight == 0 && qWeight != 0 && dWeight != 0)
			return ((qRating * qWeight) + (dRating * dWeight))/2 >= cutScore ? true : false;
			
		else if(pWeight != 0 && qWeight != 0 && dWeight == 0)
			return ((pRating * pWeight ) +(qRating * qWeight))/2 >= cutScore ? true : false;
		
		else
			return ((pRating * pWeight) + (qRating * qWeight) + (dRating * dWeight))/3 >= cutScore ? true : false;		
	}
	
	/*
	 * This method translates a given value belonging to the numeric interval [-1, 1] to interval [0, 1]
	 * @param x A Double value that represent the value within the numeric interval [-1, 1]
	 * @return a double value within the numeric interval [0, 1] 
	 */
	public static double convertToNormalizedInterval(double x)
	{
		if(x <= -1)
			return 0;
		else if(x >= 1)
			return 1;
		else
			return (1 + x)/2;
	}
	
	
	public static List<Rating> getImagesFrom(Seller seller, List<Image> images)
	{
		List<Rating> ratings = new ArrayList<Rating>();
		
		for(Image img : images)
		{
			if (img.getSeller().getName().equals(seller.getName()))
				ratings.add(img);
		}
		
		return ratings;
	}
}
