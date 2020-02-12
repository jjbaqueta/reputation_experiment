from numpy import *
from collections import defaultdict

import numpy as np 
import math
import matplotlib.pyplot as plt
import random

dataset = open("img.txt", 'r')

buyersPI = defaultdict(lambda : defaultdict(list))
buyersQI = defaultdict(lambda : defaultdict(list))
buyersDI = defaultdict(lambda : defaultdict(list))

times = defaultdict(lambda : defaultdict(list))

for line in dataset:
    line = line.strip()
    b, s, t, pR, qR, dR = line.split(';')    #seller3;time;priceR;qualityR;deliveryR;PriceL;QualityL;deliveryL
    
    buyersPI[b][s].append(float(pR))
    buyersQI[b][s].append(float(qR))
    buyersDI[b][s].append(float(dR))

    times[b][s].append(int(t))

for i in buyersPI.keys():    
    for j in buyersPI[i].keys():

        idx = np.argsort(times[i][j])
        
        buyersPI[i][j] = np.array(buyersPI[i][j])[idx]
        buyersQI[i][j] = np.array(buyersQI[i][j])[idx]
        buyersDI[i][j] = np.array(buyersDI[i][j])[idx]
        times[i][j] = np.array(times[i][j])[idx]

for i in times.keys():    

    k = 0;

    for j in times[i].keys():
        times[i][j] = k
        k += 1

for i in buyersPI.keys():    
    for j in buyersPI[i].keys():

        rand = lambda: random.randint(0,255)
        color = '#%02X%02X%02X' % (rand(), rand(), 200)
        plt.plot(buyersPI[i][j], color, label= 'image: ' + i + ' -> ' + j)

plt.yticks(np.arange(-1, 1.1, 0.1))
#plt.xticks(np.arange(min(x), max(x)+1, 1.0))
plt.legend()
plt.show()

