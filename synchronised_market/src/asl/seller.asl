// Agent seller in project synchronised_market

/* Initial beliefs */
//NONE

/* Initial goals */
//NONE

/* Initial beliefs and rules */
//check buying power

/* Plans */

+?request(Product)[source(buyer)]
	:	sell(A, B, C) & (A == Product | B == Product | C == Product) 
	<-	entities.services.sellerDefineOfferPrice(Product, Price);
		.print("Price defined for: ", Product, " is:", Price);
		.send(buyer, tell, offer(Product, Price));
		.print("Offer sent to: ", buyer);
		!hangOn.

+deal(R)[source(buyer)]
	: 	R == "accept"
	<-	.print("The product will be sent to buyer as soon as possible!");
		!hangOn.
	
+deal(R)[source(buyer)]
	: 	R == "reject"
	<-	!hangOn.

+!hangOn
	:	true
	<-	true.