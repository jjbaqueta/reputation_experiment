// Agent buyer in project synchronised_market

/* Initial beliefs */
budget(10000.0).
count_offers(0).
has_offer(false).

/* Initial goals */
//NONE

/* Initial beliefs and rules */
//check buying power

/* Plans */

+my_wish(buy, Product)
	: true
	<-	.broadcast(askOne, request(Product));
		.print("finding sellers ... (Product: ", Product ,")");
		!hangOn.		

+offer(Product, Price)[source(seller)]
	:	true
	<-	.print("offer recevied from: ", seller, ", product: ", Product , ", price: ", Price);
		!set_pending(offer);
		?count_offers(Offers);
		-+count_offers(Offers + 1);
		!check_best_offer(Product, Price, seller).
		
-offer(Product, Price)
	:	true
	<- 	?count_offers(Offers);
		-+count_offers(Offers - 1).

+count_offers(C)
	:	has_offer(X) & X == false & C == 1
	<-	?best_offer(Product, Price, Seller);
		-offer(Product, Price)[source(Seller)];
		-+count_offers(0);
		-+has_offer(false);
		?budget(B);
		-+budget(B - Price);
		.send(Seller, tell, deal(accept));
		.print("offer accepted (seller: ", Seller ,")");
		!negotiation(continue).

+count_answers(Answers)
	:	count_offers(Offers) & Answers == (Offers + 1) & Offers == 0  
	<-	true.

+!negotiation(continue)
	:	budget(X) & X <= 0
	<-	.print("no money to continue buying, end of negotiations");
		finish(neg).

+!set_pending(offer)
	:	has_offer(X) & X == false
	<-	-+has_offer(true).

+!check_best_offer(Product, Price, seller)
	:	not best_offer(Product, P, S)
	<-	+best_offer(Product, P, S).

+!check_best_offer(Product, Price, seller)
	:	best_offer(Product, P, S) & P <= Price
	<-	-offer(Product, Price)[source(seller)];
		.send(seller, tell, deal(reject));
		.print("offer rejected (seller: ", seller ,")").
	
+!check_best_offer(Product, Price, seller)
	:	best_offer(Product, P, S) & P > Price
	<-	-+best_offer(Product, Price, seller);
		-offer(Product, P)[source(S)];
		.send(S, tell, deal(reject));
		.print("offer rejected (seller: ", S ,")").
	
+!hangOn : true <- true.