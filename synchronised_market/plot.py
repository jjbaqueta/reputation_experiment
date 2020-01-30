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

for line in dataset:
    line = line.strip()
    t1, t2, t3, t4 = line.split(',')
    
    list1.append(t1)    #seller's name
    list2.append(t2)    #seller's type
    list3.append(t3)    #time of sale
    list4.append(t4)    #total of sales at time

idx   = np.argsort(list3)

list1 = np.array(list1)[idx]
list2 = np.array(list2)[idx]
list3 = np.array(list3)[idx]
list4 = np.array(list4)[idx]

sellers = defaultdict(list)
types = defaultdict(set)
times = set()

for i in range(0, len(list1)):
    sellers[list1[i]].append(list4[i])
    types[list1[i]].add(list2[i])
    times.add(list3[i])

x = []
i = 1

for t in times:
    x.append(i)
    i += 1

for key in sellers.keys():
    rand = lambda: random.randint(0,255)
    color = '#%02X%02X%02X' % (rand(), rand(), 200)
    plt.plot(x, sellers[key], color, label=types[key])    

plt.title('Sales report')
plt.xlabel('time')
plt.ylabel('sellers')
plt.legend()

plt.show()
