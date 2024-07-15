import sys
from matplotlib import pyplot as plt
import pandas as pd
from sklearn.cluster import KMeans
    
#preprocess
# dateset last col : class label
# no head dimensions are features.       
#read data(.txt) from CLI convert to dataframe format

filename = sys.argv[1] # only one argument is taken from CLI
data = pd.read_csv(filename,sep='\s+',header=None)
target = data.iloc[:,-1:] #select only one column
features = data.drop([target.columns[0]],axis=1) #axis parameter indicates the column

#do 20 iterations 
#change centroids 

#apply KNN 
SSE = []
for cluster in range(2,11): #K-Means cluster runs for K =(2,10)
    #init parameter initializes randomly
    #max_iter makes it run for 20 iterations
    k_means = KMeans(n_clusters=cluster,max_iter=20,init='random') 
    k_means.fit(features)
    SSE.append(k_means.inertia_)

#prints the SSE for each cluster from k=2 to k=10
for i in range(len(SSE)):    
    print(f'for k ={i+2} After 20 iterations: SSE error = {SSE[i]:.4f}% ')


#Plotting Graph 
frame = pd.DataFrame({'Cluster':range(2,11),'SSE':SSE})
plt.figure(figsize=(12,6))
plt.plot(frame['Cluster'], frame['SSE'],marker='o')
plt.xlabel('Number of Cluster')
plt.ylabel('SSE Error')
plt.show()
# print(frame['Cluster'],frame['SSE'])