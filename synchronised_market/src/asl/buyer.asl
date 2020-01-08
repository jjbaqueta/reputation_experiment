/* Initial rules */
 
update_amount_offers(CNPId)
	:- 	amount_offers(CNPId, X) &				// Retrieves the number of offers for the request CNPId
		.count(offer(CNPId,_)[source(_)], N) &	// Counts the number of proposals received at the moment for request CNPId 
		X = N.									// Updates the value of amount offers received

/* Initial goals */

/* Plans */

// defining the initiator
+!register 
	<- .df_register(initiator).

// start the CNP
+!buy(Id, Product)
	<-	
		// wait participants introduction (register of participants)
		.print("Waiting seller for Product ", Product, " ...");
      	.wait(2000); 
      
	    // seeking possible sellers (participants)
      	+cnp_state(Id, propose);   // remember the state of the CNP
      	.df_search("participant", LP);
      	.print("Sending CFP to ", LP);
      	+nb_participants(Id, .length(LP));
      	
      	// sending buying request for sellers found
      	.send(LP, tell, cfp(Id, Product));
      	
		// the deadline of the CNP is now + 4 seconds (or all offers were received)
      	.wait(update_amount_offers(Id), 4000, _);
      	!contract(Id).

// this plan needs to be atomic so as not to accept
// proposals or refusals while contracting
@lc1[atomic]
+!contract(CNPId)
   :  cnp_state(CNPId,propose)
   <- -cnp_state(CNPId,_);
      +cnp_state(CNPId,contract);
      .findall(offer(O,A),propose(CNPId,O)[source(A)],L);
      .print("Offers are ",L);
      L \== []; // constraint the plan execution to at least one offer
      .min(L,offer(WOf,WAg)); // sort offers, the first is the best
      .print("Winner is ",WAg," with ",WOf);
      !announce_result(CNPId,L,WAg);
      -+cnp_state(CNPId,finished).

// nothing todo, the current phase is not 'propose'
@lc2 +!contract(_).

-!contract(CNPId)
   <- .print("CNP ",CNPId," has failed!").

+!announce_result(_,[],_).
// announce to the winner
+!announce_result(CNPId,[offer(_,WAg)|T],WAg)
   <- .send(WAg,tell,accept_proposal(CNPId));
      !announce_result(CNPId,T,WAg).
// announce to others
+!announce_result(CNPId,[offer(_,LAg)|T],WAg)
   <- .send(LAg,tell,reject_proposal(CNPId));
      !announce_result(CNPId,T,WAg).







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

@atomic
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