package environments;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.model.Product;
import entities.model.buyers.Buyer;
import entities.model.buyers.GeneralOrientedBuyer;
import entities.model.sellers.Seller;
import entities.services.BehaviorFactory;
import entities.services.BuyerFactory;
import entities.services.ProductBuilder;
import entities.services.ProductsFacade;
import entities.services.SellerFactory;
import enums.BehaviorPattern;
import enums.BuyerType;
import enums.CriteriaType;
import enums.ProductClassType;
import enums.ProductDefault;
import enums.SellerType;
import jason.asSyntax.Literal;
import reputationModels.ReputationModel;

public class Experiment 
{	
	public static File fileSales = new File("sale.txt");
	public static File fileRep = new File("rep.txt");
	public static File fileImg = new File("img.txt");
	
	public static boolean ReputationFilter;
	public static boolean ImageFilter;
	
	private int numberBadSellers;
	private int numberGoodSellers;
	private int numberGeneralSellers;
	private int numberProductsBySeller;

	private int numberPriceBuyers;
	private int numberQualityBuyers;
	private int numberDeliveryBuyers;
	private int numberGeneralBuyers;
	private int numberProductsByBuyer;
	
	public Experiment() 
	{	
		if(fileSales.exists())
			fileSales.delete();
		
		if(fileRep.exists())
			fileRep.delete();
		
		if(fileImg.exists())
			fileImg.delete();
		
		// Defining the criteria used by model of reputation
		ReputationModel.insertNewCriteria(CriteriaType.PRICE.getValue(), Double.class);
		ReputationModel.insertNewCriteria(CriteriaType.QUALITY.getValue(), Double.class);
		ReputationModel.insertNewCriteria(CriteriaType.DELIVERY.getValue(), Integer.class);
	}
	
	public void startExperiment1() throws Exception
	{
		ReputationFilter = true;
		ImageFilter = true;
		
		numberBadSellers = 0;
		numberGoodSellers= 0;
		numberGeneralSellers = 1;

		numberPriceBuyers = 1;
		numberQualityBuyers = 0;
		numberDeliveryBuyers = 0;
		numberGeneralBuyers = 0;
		
		numberProductsBySeller = 3;
		numberProductsByBuyer = 10;
		
		Market.sellers = new Seller[numberBadSellers + numberGoodSellers + numberGeneralSellers];
		Market.buyers = new Buyer[numberPriceBuyers + numberQualityBuyers + numberDeliveryBuyers + numberGeneralBuyers];
		Market.numberBuyingRequest = (numberPriceBuyers + numberQualityBuyers + numberDeliveryBuyers + numberGeneralBuyers) * numberProductsByBuyer;
		
		// Setting products for buyer
		
		List<Product> buyerProducts = new ArrayList<Product>();
		
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		
		Market.buyers[0] = BuyerFactory.getBuyer(BuyerType.PRICE_ORIENTED, "buyer", buyerProducts);
		
		// Setting products for seller
		
		List<Product> sellerProducts = new ArrayList<Product>();
		
		sellerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		sellerProducts.add(new ProductBuilder(ProductDefault.TABLET.name(), ProductClassType.ECONOMY).build());
		sellerProducts.add(new ProductBuilder(ProductDefault.DESKTOP.name(), ProductClassType.ECONOMY).build());
	
		sellerProducts.get(0).setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.numberBuyingRequest));
		sellerProducts.get(0).setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.numberBuyingRequest));
		sellerProducts.get(0).setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.numberBuyingRequest));
		
		sellerProducts.get(1).setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.numberBuyingRequest));
		sellerProducts.get(1).setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.numberBuyingRequest));
		sellerProducts.get(1).setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.numberBuyingRequest));
		
		sellerProducts.get(2).setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_DECREASING, Market.numberBuyingRequest));
		sellerProducts.get(2).setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_DECREASING, Market.numberBuyingRequest));
		sellerProducts.get(2).setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_DECREASING, Market.numberBuyingRequest));
		
		Market.sellers[0] = SellerFactory.getSeller(SellerType.GENERAL, "seller", 0, 0, 0, sellerProducts);
				
		adjustingAgentsNames();
	}
	
	public void startExperiment2() throws Exception
	{
		ReputationFilter = true;
		ImageFilter = false;
		
		numberBadSellers = 0;
		numberGoodSellers= 0;
		numberGeneralSellers = 2;

		numberPriceBuyers = 0;
		numberQualityBuyers = 0;
		numberDeliveryBuyers = 1;
		numberGeneralBuyers = 0;
		
		numberProductsBySeller = 1;
		numberProductsByBuyer = 20;
		
		Market.sellers = new Seller[numberBadSellers + numberGoodSellers + numberGeneralSellers];
		Market.buyers = new Buyer[numberPriceBuyers + numberQualityBuyers + numberDeliveryBuyers + numberGeneralBuyers];
		Market.numberBuyingRequest = (numberPriceBuyers + numberQualityBuyers + numberDeliveryBuyers + numberGeneralBuyers) * numberProductsByBuyer;
		
		// Setting products for buyer
		
		List<Product> buyerProducts = new ArrayList<Product>();
		
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		buyerProducts.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
		
		Market.buyers[0] = BuyerFactory.getBuyer(BuyerType.DELIVERY_ORIENTED, "buyer", buyerProducts);
		
		// Setting products for seller
		
		List<Product> sellerProducts1 = new ArrayList<Product>();
		
		sellerProducts1.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
	
		sellerProducts1.get(0).setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.numberBuyingRequest));
		sellerProducts1.get(0).setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.numberBuyingRequest));
		sellerProducts1.get(0).setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.numberBuyingRequest));
		
		Market.sellers[0] = SellerFactory.getSeller(SellerType.GENERAL, "seller1", 0.5, 0.5, 0.5, sellerProducts1);
		
		List<Product> sellerProducts2 = new ArrayList<Product>();
		
		sellerProducts2.add(new ProductBuilder(ProductDefault.TV.name(), ProductClassType.ECONOMY).build());
	
		sellerProducts2.get(0).setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_INCREASING, Market.numberBuyingRequest));
		sellerProducts2.get(0).setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_INCREASING, Market.numberBuyingRequest));
		sellerProducts2.get(0).setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_INCREASING, Market.numberBuyingRequest));
		
		Market.sellers[1] = SellerFactory.getSeller(SellerType.GENERAL, "seller2", 0, 0, 0, sellerProducts2);
				
		adjustingAgentsNames();
	}
	
	public void startExperiment3()
	{
		ReputationFilter = false;
		ImageFilter = false;
		
		numberBadSellers = 1;
		numberGoodSellers= 1;
		numberGeneralSellers = 1;

		numberPriceBuyers = 1;
		numberQualityBuyers = 1;
		numberDeliveryBuyers = 1;
		numberGeneralBuyers = 1;
		
		numberProductsBySeller = 5;
		numberProductsByBuyer = 3;
		
		Market.sellers = new Seller[numberBadSellers + numberGoodSellers + numberGeneralSellers];
		Market.buyers = new Buyer[numberPriceBuyers + numberQualityBuyers + numberDeliveryBuyers + numberGeneralBuyers];
		Market.numberBuyingRequest = (numberPriceBuyers + numberQualityBuyers + numberDeliveryBuyers + numberGeneralBuyers) * numberProductsByBuyer;
		
		// Implements experiment
		
		adjustingAgentsNames();
	}
	
	public void startAutomaticallyExperiment() throws Exception
	{
		ReputationFilter = false;
		ImageFilter = false;
		
		numberBadSellers = 1;
		numberGoodSellers= 1;
		numberGeneralSellers = 1;

		numberPriceBuyers = 1;
		numberQualityBuyers = 1;
		numberDeliveryBuyers = 1;
		numberGeneralBuyers = 1;
		
		numberProductsBySeller = 5;
		numberProductsByBuyer = 3;
		
		Market.sellers = new Seller[numberBadSellers + numberGoodSellers + numberGeneralSellers];
		Market.buyers = new Buyer[numberPriceBuyers + numberQualityBuyers + numberDeliveryBuyers + numberGeneralBuyers];
		Market.numberBuyingRequest = (numberPriceBuyers + numberQualityBuyers + numberDeliveryBuyers + numberGeneralBuyers) * numberProductsByBuyer;
		
		initBuyersAutomatically(numberPriceBuyers, numberQualityBuyers, numberDeliveryBuyers,numberGeneralBuyers, numberProductsByBuyer);
		initSellersAutomatically(numberBadSellers, numberGoodSellers, numberGeneralSellers, numberProductsBySeller);
		
		adjustingAgentsNames();
	}
	
	private void initBuyersAutomatically(int numberPriceBuyers, int numberQualityBuyers, int numberDeliveryBuyers, int numberGeneralBuyers, int numberProductsByBuyer) throws Exception
	{
		Random rand = new Random();
		int j = 0;

		for (int i = 0; i < numberPriceBuyers; i++)
			Market.buyers[j++] = BuyerFactory.getBuyer(
					BuyerType.PRICE_ORIENTED,
					"buyer" + j,
					ProductsFacade.generateRamdomListOfProducts(numberProductsByBuyer));

		for (int i = 0; i < numberQualityBuyers; i++)
			Market.buyers[j++] = BuyerFactory.getBuyer(
					BuyerType.QUALITY_ORIENTED, 
					"buyer" + j, 
					ProductsFacade.generateRamdomListOfProducts(numberProductsByBuyer));

		for (int i = 0; i < numberDeliveryBuyers; i++)
			Market.buyers[j++] = BuyerFactory.getBuyer(
					BuyerType.DELIVERY_ORIENTED, 
					"buyer" + j, 
					ProductsFacade.generateRamdomListOfProducts(numberProductsByBuyer));

		for (int i = 0; i < numberGeneralBuyers; i++)
			Market.buyers[j++] = new GeneralOrientedBuyer(
					"buyer" + j, 
					rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 
					ProductsFacade.generateRamdomListOfProducts(numberProductsByBuyer));
	}
	
	private void initSellersAutomatically(int numberBadSellers, int numberGoodSellers, int numberGeneralSellers, int numberProductsBySeller) throws Exception
	{
		Random rand = new Random();
		int j = 0;

		for (int i = 0; i < numberBadSellers; i++)
			Market.sellers[j++] = SellerFactory.getSeller(
					SellerType.BAD, "seller" + j, 0.5, 0.5, 0.5,
					ProductsFacade.generateRamdomListOfProducts(numberProductsBySeller));
			

		for (int i = 0; i < numberGoodSellers; i++)
			Market.sellers[j++] = SellerFactory.getSeller(
					SellerType.GOOD, "seller" + j, 0.0, 0.0, 0.0,
					ProductsFacade.generateRamdomListOfProducts(numberProductsBySeller));
		
		for (int i = 0; i < numberGeneralSellers; i++)
		{	
			List<Product> products = ProductsFacade.generateRamdomListOfProducts(numberProductsBySeller);
			
			for (Product product : products)
			{
				product.setSalesBehaviorPrice(BehaviorFactory.getBehavior(BehaviorPattern.LINEAR_DECREASING, Market.numberBuyingRequest));
				product.setSalesBehaviorQuality(BehaviorFactory.getBehavior(BehaviorPattern.SEMICONSTANT, Market.numberBuyingRequest));
				product.setSalesBehaviorDelivery(BehaviorFactory.getBehavior(BehaviorPattern.CONSTANT, Market.numberBuyingRequest));
			}
			
			Market.sellers[j++] = SellerFactory.getSeller(
					SellerType.GENERAL, "seller" + j, 
					rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), products);
		}
	}

	public int getNumberBadSellers() {return numberBadSellers;}

	public int getNumberGoodSellers() {return numberGoodSellers;}

	public int getNumberGeneralSellers() {return numberGeneralSellers;}
	

	public int getNumberPriceBuyers() {return numberPriceBuyers;}

	public int getNumberQualityBuyers() {return numberQualityBuyers;}

	public int getNumberDeliveryBuyers() {return numberDeliveryBuyers;}

	public int getNumberGeneralBuyers() {return numberGeneralBuyers;}
	

	public int getNumberProductsByBuyer() {return numberProductsByBuyer;}
	
	public int getNumberProductsBySeller() {return numberProductsBySeller;}
	
	
	private void adjustingAgentsNames()
	{
		if (Market.sellers.length == 1)
			Market.sellers[0].setName("seller");
		
		if (Market.buyers.length == 1)
			Market.buyers[0].setName("buyer");
		
		long time = System.currentTimeMillis();
		
		for(Seller seller : Market.sellers)
			Files.writeSaleStatus(seller, time);
	}
	
	
	/*
	 * This method shows all information about sellers and buyers including their products for sale and to buying, respectively
	 */
	public void showBuyersAndSellersInformations()
	{
		System.out.println("\n--------------------- BUYERS --------------------\n");
		
		for(Buyer buyer : Market.buyers)
		{
			System.out.println("Buyer's name: " + buyer.getName());
			System.out.println("Preferences: {" + "price: " + buyer.getPreferenceByPrice() + ", quality: " + buyer.getPreferenceByQuality() + ", delivery: " + buyer.getPreferenceByDelivery() + "}");
			System.out.println("Orders list {format: buy(CNPId, product)}: ");
			
			for(Literal order: buyer.getProductsToBuy())
				System.out.println("   -> " + order);

			System.out.println("");
		}
		
		System.out.println("\n--------------------- SELLERS --------------------\n");
		
		for(Seller seller : Market.sellers)
		{
			System.out.println("Seller's name: " + seller.getName());
			System.out.println("List of products for sale: ");
			
			for(Product product: seller.getProductsForSale())
				System.out.println("   -> " + product);

			System.out.println("");
		}
	}
}
