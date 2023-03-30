import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

cfg = pd.read_csv("../simout/cfg.txt")

runId = 0
sim = pd.read_csv(f"../simout/{runId}.txt")

time = np.sort(sim['timestep'].unique())
numtimesteps = time.shape[0]

numwolves = np.empty(numtimesteps)
numrabbits = np.empty(numtimesteps)

for t in range(numtimesteps):
    now  = sim.loc[sim['timestep']==t,'iswolf']
    numwolves[t] = sum( now==1 )
    numrabbits[t]=  sum( now==0 )

plt.figure()
plt.plot(time,numwolves,label='wolves')
plt.plot(time,numrabbits,label='rabbits')
plt.legend()
plt.show()



