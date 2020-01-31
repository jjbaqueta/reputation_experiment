from numpy import *
from collections import defaultdict

import numpy as np 
import math
import matplotlib.pyplot as plt
import random

dataset = open("sale.txt", 'r')

list1 = []
list2 = []
list3 = []
list4 = []

sellers = defaultdict(list)
types = defaultdict(set)
times = defaultdict(list)

for line in dataset:
    line = line.strip()
    t1, t2, t3, t4 = line.split(',')    #t1: seller's name; t2: seller's type; t3: time of sale; t4: total of sales at time
    
    sellers[t1].append(t4)
    types[t1].add(t2)
    times[t1].append(t3)

for key in sellers.keys():    
    
    idx = np.argsort(times[key])
    
    sellers[key] = np.array(sellers[key])[idx]
    times[key] = np.array(times[key])[idx]
 
for key in times.keys():
    for j in range(0, len(times[key])):
        times[key][j] = j

for key in sellers.keys():
    rand = lambda: random.randint(0,255)
    color = '#%02X%02X%02X' % (rand(), rand(), 200)
    plt.plot(times[key], sellers[key], color, label=types[key])    

plt.title('Sales report')
plt.xlabel('time')
plt.ylabel('sellers')
plt.legend()

plt.show()
