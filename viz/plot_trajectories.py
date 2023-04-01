import matplotlib.pyplot as plt
import pandas as pd

cfg = pd.read_csv("../simout/cfg.txt")
numRuns = cfg.shape[0]

runId = 2
num_timesteps = cfg.loc[runId, 'num_timesteps']
numWolves = cfg.loc[runId, 'numWolves']
numRabbits = cfg.loc[runId, 'numRabbits']

sim = pd.read_csv(f"../simout/{runId}.txt")
animal_ids = sim['id'].unique()

plt.figure()
for id in animal_ids:

    mask = sim['id'] == id

    iswolf = sim.loc[mask, 'iswolf'].iloc[0]

    timestep = sim.loc[mask, 'timestep']
    posx = sim.loc[mask, 'posx']
    posy = sim.loc[mask, 'posy']
    age = sim.loc[mask, 'age']
    color = sim.loc[mask, 'color']
    hunger = sim.loc[mask, 'hunger']
    vision = sim.loc[mask, 'vision']

    print(iswolf, timestep.shape)

    plt.plot(posx,posy)

plt.show()


