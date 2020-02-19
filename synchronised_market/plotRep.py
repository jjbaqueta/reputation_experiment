from numpy import *
from collections import defaultdict

import numpy as np 
import math
import matplotlib.pyplot as plt
import random

dataset = open("rep.txt", 'r')

sellersPR = defaultdict(list)
sellersQR = defaultdict(list)
sellersDR = defaultdict(list)
sellersPL = defaultdict(list)
sellersQL = defaultdict(list)
sellersDL = defaultdict(list)
times = defaultdict(list)

for line in dataset:
    line = line.strip()
    s, t, pR, qR, dR, pL, qL, dL = line.split(';')    #seller3;time;priceR;qualityR;deliveryR;PriceL;QualityL;deliveryL
    
    sellersPR[s].append(float(pR))
    sellersQR[s].append(float(qR))
    sellersDR[s].append(float(dR))
    sellersPL[s].append(float(pL))
    sellersQL[s].append(float(qL))
    sellersDL[s].append(float(dL))

    times[s].append(int(t))

for key in sellersPR.keys():    
    
    idx = np.argsort(times[key])
    
    sellersPR[key] = np.array(sellersPR[key])[idx]
    sellersQR[key] = np.array(sellersQR[key])[idx]
    sellersDR[key] = np.array(sellersDR[key])[idx]
    sellersPL[key] = np.array(sellersPL[key])[idx]
    sellersQL[key] = np.array(sellersQL[key])[idx]
    sellersDL[key] = np.array(sellersDL[key])[idx]

    times[key] = np.array(times[key])[idx]
 
for key in times.keys():
    for j in range(0, len(times[key])):
        times[key][j] = j

for key in sellersPR.keys():
    rand = lambda: random.randint(0,255)
    color = '#%02X%02X%02X' % (rand(), rand(), 200)
    plt.plot(times[key], sellersPR[key], color='tab:blue', label=key)

plt.yticks(np.arange(-1, 1.1, 0.1))
#plt.xticks(np.arange(min(x), max(x)+1, 1.0))
plt.legend()
plt.show()

#for key in sellersPR.keys():
#    rand = lambda: random.randint(0,255)
#    color = '#%02X%02X%02X' % (rand(), rand(), 200)
#    plt.plot(times[key], sellersPR[key], color, label=key)    
#
#plt.title('Reputations')
#plt.xlabel('time')
#plt.ylabel('Reputations')
#plt.legend()
#
#plt.show()
