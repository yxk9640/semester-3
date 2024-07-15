import numpy as np
import pandas as pd
import random
import matplotlib.pyplot as plt
import sys


def run_kmeans(k):
    if len(sys.argv) <= 1:
        print('Please provide a file name')
        sys.exit(1)
    df = pd.read_csv(sys.argv[1], header=None, sep="\s+")
    df = df.drop(df.columns[-1], axis=1)
    columns = df.shape[1]
    df['cluster_no'] = random.choices([i for i in range(k)], k=len(df))
    centroids = df.groupby('cluster_no').mean().values
    for _ in range(20):
        for i in range(len(centroids)):
            diff_sum = ((df.iloc[:, :columns] - centroids[i]) ** 2).sum(axis=1)
            euc_dist = np.sqrt(diff_sum)

            df['c{}'.format(i)] = euc_dist

        df['nearest_no'] = df.loc[:, ['c{}'.format(i) for i in range(k)]].idxmin(axis=1)
        df['nearest_no'] = df['nearest_no'].map(lambda x: int(x.lstrip('c')))
        df['cluster_no'] = df['nearest_no']
        centroids = df.groupby('cluster_no').mean().iloc[:, :columns].values

    return df.loc[:, ['c{}'.format(i) for i in range(k)]].min(axis=1).sum()


def display():
    y = []
    for i in range(2, 11):
        y.append(run_kmeans(i))
        print('For k = {} After 20 iterations: SSE error = {:.4f}'.format(i, y[-1]))

    _, img = plt.subplots()
    img.plot([i for i in range(2, 11)], y)
    plt.show()


display()
