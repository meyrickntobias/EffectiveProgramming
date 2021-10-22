import matplotlib.pyplot as plt

x1 = []
y1 = []
i = 1
for line in open('sma1_5point.txt', 'r'):
    x1.append(i*0.1)
    y1.append(line)
    i += 1

      
plt.title("Pulse Data - pulsedata1.txt - 5 point average")
plt.xlabel('Time (s)')
plt.ylabel('Signal')
plt.plot(x1, y1, marker = 'o', c = 'g')
plt.show()
