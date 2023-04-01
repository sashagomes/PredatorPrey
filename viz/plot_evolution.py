import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

cfg = pd.read_csv("../simout/cfg.txt")
numruns = cfg.shape[0]

for runId in range(numruns):

    background_color = cfg.loc[runId,'backgroundColor']

    sim = pd.read_csv(f"../simout/{runId}.txt")

    time = np.sort(sim['timestep'].unique())
    numtimesteps = time.shape[0]

    avg_color = np.empty(numtimesteps)
    avg_vision = np.empty(numtimesteps)

    for t in range(numtimesteps):
        now  = sim.loc[sim['timestep']==t,['iswolf','color','vision']]
        avg_vision[t]= now.loc[now['iswolf']==1,'vision'].mean()
        avg_color[t] = now.loc[now['iswolf']==0,'color'].mean()

    plt.figure()
    plt.title(f"Run {runId}")
    plt.plot(time,avg_vision,label='average wolf vision')
    plt.plot(time,avg_color,label='average rabbit color')
    plt.axhline(y=background_color,color='k',linestyle='--')
    plt.legend()

plt.show()


