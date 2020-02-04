# Experiment Description
This project implements a simple version of reputation model ReGret in a buying and selling scenario

In this experiment there are two type of agents:
 - Sellers: They sell specific items and are evaluated by buyers.
 - Buyers: They buy items, searching for the item that better meet his shopping preferences.

Types of Buyers:
 - Price oriented: This buyer wants buying the lowest price items.
 - Quality oriented: This buyer wants buying the best quality items.
 - Delivery oriented: This buyer prioritizes delivery time, the smaller the better.
 - General oriented: The preferences of this buyer can be customized according to experiment.

Types of Seller:
 - Bad: He changes the sale contract at 90% of the time. He changes the price, quality and delivery conditions.
 - Neutral: He may change only one contract conditions (price, quality or delivery). It may happen at 50% of the time.
 - Good: He never changes the contract conditions.

General results:
 - The sellers are evaluated during the experiment receiving a score according to their behavior.
 - The sellers receive a score for each criteria (price, quality and delivery time).
 - Such a score is a value between -1 (the lowest score) and 1 (the highest score).
 - As expected, when the simulation starts, all sellers have the same chance to make sales. 
 However, as time goes by, the Good sellers end up controlling the market, due to their good reputation.
 
=================================== DEMO =========================================<br> 
- <b>input</b>: total of sellers: Bad seller {1}; Neutral Seller {0}; Good seller {1}
- <b>input</b>: total of buyers: Price oriented {1}; Quality oriented {1}; Delivery oriented {1}; General {1}
- <b>input</b>: items sold per seller {3}
- <b>input</b>: number of purchase request per buyer {3}
- <b>input</b>: total purchase requests: 20

Informations about sellers:
 - <b>output</b>: seller1(BadSeller) {sales made: 1; sales lost: 19}
 - <b>output</b>: seller2(GoodSeller) {sales made: 19; sales lost: 1}

Informations about buyers:
- <b>output</b>: buyer1(PriceOrientedBuyer) {purchases completed: 10; purchases aborted: 0}
- <b>output</b>: buyer2(QualityOrientedBuyer) {purchases completed: 10; purchases aborted: 0}
- <b>output</b>: buyer3(DeliveryOrientedBuyer) {purchases completed: 10; purchases aborted: 0}
- <b>output</b>: buyer4(GeneralOrientedBuyer) {purchases completed: 10; purchases aborted: 0}

![Screenshot](https://user-images.githubusercontent.com/42413563/73758981-0bd6cd00-474a-11ea-8712-ed395bae84d8.png)
