#!/usr/bin/env python
# coding: utf-8

# In[46]:


import json
import sys
import time


# In[50]:
def parse(numLevels, carpeta):

    for j in range(numLevels):
        newText = ''
        fileName = carpeta + "/" + str(j + 1) + ".json"
        f = open(fileName, 'r')
        file = f.readline() 
        n = 0
        max = len(file)
        while (n < max):
            i = file[n]
            if i == "m":
                if file[n+1] == 'x':
                    newText += "\"mx\":true, "
                else:
                    newText += "\"my\":true, "
                n += 7
            elif i >= 'a' and i <= 'z':
                newText += str("\"" + i + "\"")
            else:
                newText += str(i)
            n += 1
        f.close()

        f = open(fileName, 'w')    
        f.write(newText)
        f.close()

if __name__ == "__main__":
    start = time.time()
    parse(int(sys.argv[1]), sys.argv[2])
    end = time.time()
    print("Tiempo de ejecucion: " + str(end - start) + " segundos")


# In[ ]:





# In[ ]:




