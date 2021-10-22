import matplotlib.pyplot as plt 
import numpy as np 

# plot takes two parameters
# parameter 1 - points on the x axis
# parameter 2 - points on the y axis
xpoints = np.array([])
ypoints = np.array([])

# first we will open the pulsedata.txt file
f = open('pulsedata1.txt', 'r')
read_data = f.read()
counter = 0
print(f.readline())


#print(xpoints.size)
#print(ypoints.size)
#plt.plot(xpoints, ypoints)
#plt.show()