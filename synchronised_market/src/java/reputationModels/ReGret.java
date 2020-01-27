package reputationModels;

import java.util.List;

import entities.model.Impression;
import environments.Market;

/*
 * This class implements the ReGret reputation model. Such a model was proposed by Jordi Sabater and Carles Sierra at 2001.
 * In particular, ReGret model support three different dimensions (individual, social and ontological dimension).
 * However, in this implementation we are only using the individual and social dimensions.
 * For more details: @see https://pdfs.semanticscholar.org/3cf4/0b315a0ea08184b6699465c51d73fec1dc4e.pdf 
 */

public abstract class ReGret extends ReputationModel{

	/*
	 * In order to compute the reputation reliability, the number of iterations and the reputation deviation must be into account.
	 * According to (Sabater; Sierra, 2001), an isolated experience should not be enough to make a correct judgement of somebody (number of interactions - Ni).
	 * In turn, a large the variability in the rating values tends to become the subjective reputation less reliable (reputation deviation - Deviation).
	 */
	
	// Intimate level of interactions (ITM) - limit to define the importance level of impressions
	private static final double ITM = Math.pow(Market.buyers.length, 2) * Market.sellers.length;
	
	/*
	 * This method computes the subjective reputation for all evaluated criteria 
	 * @param currentTime Time instant used during the computation of subjective reputation.
	 * @param impressions List of impressions considered to compute the subjective reputation.
	 * @return the reputation value for each criterion.
	 */
	public static double[] computeSubjectiveReputation(long currentTime, List<Impression> impressions)
	{
		double functionTj = getFunctionTj(impressions, currentTime);
		double functionTi, rPrice = 0, rQuality = 0, rDelivery = 0;
	
		double[] reputations = new double[criteria.size()];		// Stores reputation of each evaluated criterion 
		
		for(Impression imp : impressions)
		{
			functionTi = (((double) imp.getTime()/currentTime)/functionTj);
			
			rPrice += functionTi * (double) imp.getRatings().get(criteria.get(0).getName());
			rQuality += functionTi * (double) imp.getRatings().get(criteria.get(1).getName());
			rDelivery += functionTi * (double) imp.getRatings().get(criteria.get(2).getName());
		}
		
		reputations[0] = rPrice;
		reputations[1] = rQuality;
		reputations[2] = rDelivery;
		
		return reputations;
	}
	
	/*
	 * This method computes the reliability of subjective reputation 
	 * @param subjectiveRep Subjective reputation from criterion selected.
	 * @param currentTime Time instant used during the computation of subjective reputation.
	 * @param impressions List of impressions considered to compute the subjective reputation.
	 * @return how much reliable is the subjective reputation for each evaluated criterion
	 */
	public static double[] computeReliability(double[] subjectiveRep, long currentTime, List<Impression> impressions)
	{
		// Computing population average
		double averagePrice = 0, averageQuality = 0, averageDelivery = 0;
		
		double[] reliabilities = new double[criteria.size()];		// Stores reliability degree for each subjective reputation value
		double[] deviations = computeDeviation(subjectiveRep, currentTime, impressions);
		double ni = computeNi(impressions);
		
		for(Impression imp : impressions)
		{
			averagePrice += (double) imp.getRatings().get(criteria.get(0).getName());
			averageQuality += (double) imp.getRatings().get(criteria.get(1).getName());
			averageDelivery += (double) imp.getRatings().get(criteria.get(2).getName());
		}
		averagePrice /= impressions.size();
		averageQuality /= impressions.size();
		averageDelivery /= impressions.size();
		
		// Computing reliability
		reliabilities[0] = (1 - averagePrice) * ni + averagePrice * deviations[0];
		reliabilities[1] = (1 - averageQuality) * ni + averageQuality * deviations[1];
		reliabilities[2] = (1 - averageDelivery) * ni + averageDelivery * deviations[2];
		
		return reliabilities;
	}
	
	/*
	 * This method computes the importance level of impressions list.
	 * The intimate level of interactions (ITM) is adopted in order to minimize the effects from ratings with low occurrence levels.
	 * @param impressions List of impressions considered to compute the subjective reputation.
	 * @return how much expressive is the list of impressions
	 */
	private static double computeNi(List<Impression> impressions)
	{	
		// Number of impressions used to calculate the reputation.
		int cardinality = impressions.size();
	
		/*
		 * Computing Ni
		 * If the rating cardinality is below the ITM, the importance of impressions is reduced (to a value less than 1).
		 * Otherwise, the importance level is defined as 1. 
		 */
		if(cardinality <= ITM)
			return Math.sin(Math.PI/(2 * ITM) * cardinality);
		else
			return 1;
	}
	
	/*
	 * This method computes the deviation of subjective reputation considering a given evaluation criterion (price, quality or delivery)
	 * A deviation value near 0 indicates a high variability, in turn, close to 1 indicates a low variability (bigger reliability).
	 * @param subjectiveRep Subjective reputation from criterion selected.
	 * @param currentTime Time instant used during the computation of subjective reputation.
	 * @param impressions List of impressions considered to compute the subjective reputation.
	 * @return Deviation of subjective reputation.
	 */
	private static double[] computeStardardDeviation(double[] subjectiveRep, long currentTime, List<Impression> impressions)
	{
		double functionTj = getFunctionTj(impressions, currentTime);	
		double functionTi = 0, dPrice = 0, dQuality = 0, dDelivery = 0;		
		
		double[] deviations = new double[criteria.size()];		// Stores deviation values of each evaluated criterion
		
		// Computing standard deviation in relation to subjective reputation
		for(Impression imp : impressions)
		{
			functionTi = (((double) imp.getTime()/currentTime)/functionTj);
			
			dPrice += functionTi * Math.pow(((double) imp.getRatings().get(criteria.get(0).getName()) - subjectiveRep[0]), 2);
			dQuality += functionTi * Math.pow(((double) imp.getRatings().get(criteria.get(1).getName()) - subjectiveRep[1]), 2);
			dDelivery += functionTi * Math.pow(((double) imp.getRatings().get(criteria.get(2).getName()) - subjectiveRep[2]), 2);
		}
		
		deviations[0] = Math.sqrt(dPrice / impressions.size());
		deviations[1] = Math.sqrt(dQuality / impressions.size());
		deviations[2] = Math.sqrt(dDelivery / impressions.size());
		
		return deviations;		
	}
	
	/*
	 * This method computes the deviation of subjective reputation considering a given evaluation criterion (price, quality or delivery)
	 * A deviation value near 0 indicates a high variability, in turn, close to 1 indicates a low variability (bigger reliability).
	 * @param subjectiveRep Subjective reputation from criterion selected.
	 * @param currentTime Time instant used during the computation of subjective reputation.
	 * @param impressions List of impressions considered to compute the subjective reputation.
	 * @return Deviation of subjective reputation.
	 */
	private static double[] computeDeviation(double[] subjectiveRep, long currentTime, List<Impression> impressions)
	{
		double functionTj = getFunctionTj(impressions, currentTime);	
		double functionTi = 0, dPrice = 0, dQuality = 0, dDelivery = 0;		
		
		double[] deviations = new double[criteria.size()];		// Stores deviation values of each evaluated criterion
		
		// Computing standard deviation in relation to subjective reputation
		for(Impression imp : impressions)
		{
			functionTi = (((double) imp.getTime()/currentTime)/functionTj);
			
			dPrice += functionTi * Math.abs((double) imp.getRatings().get(criteria.get(0).getName()) - subjectiveRep[0]);
			dQuality += functionTi * Math.abs((double) imp.getRatings().get(criteria.get(1).getName()) - subjectiveRep[1]);
			dDelivery += functionTi * Math.abs((double) imp.getRatings().get(criteria.get(2).getName()) - subjectiveRep[2]);
		}
		
		deviations[0] = 1.0 - dPrice;
		deviations[1] = 1.0 - dQuality;
		deviations[2] = 1.0 - dDelivery;
		
		return deviations;		
	}
	
	/*
	 * This method applies a time adjusting according to function f(t, tj), the importance level of a rating must reduce with time.
	 * @param impressions List of impressions considered to compute the subjective reputation.
	 * @param currentTime Time instant used during the computation of subjective reputation.
	 * @return Time adjustment factor. 
	 */
	private static double getFunctionTj(List<Impression> impressions, long currentTime)
	{
		// Computing sum of p(t, tj)
		double functionTj = 0;
		
		for(Impression imp : impressions)
		{
			functionTj += (double) imp.getTime()/currentTime;
		}
		return functionTj;
	}
}
