# -*- coding: utf-8 -*-
"""
Created on Thu Mar 15 15:06:54 2018

@author: camusj
"""

# Imports
try:
    import numpy as np
    np.random.seed(0)
except ImportError:
    raise ImportError('numpy module needs to be imported.')
try:
    import matplotlib.pyplot as plt
except ImportError:
    raise ImportError('matplotlib.pyplot module needs to be imported.')
    

def f(lmbda, N, E):
    return np.linalg.inv(np.dot(np.linalg.inv(N), np.dot(E, np.linalg.inv(N))) 
    + lmbda*np.linalg.inv(N)) + np.linalg.inv(np.linalg.inv(N) + 
    lmbda*np.linalg.inv(E))
    
    
def frobenius(A):
    return np.linalg.norm(A, 'fro')
    

def plot_cov_curve(x_array, y_array):  
    plt.plot(x_array, y_array, linewidth=5)

    plt.xticks(fontsize=10);
    plt.yticks(fontsize=10);
    
    plt.xlabel('Fro(N)', fontsize=15);
    plt.ylabel('Fro(Cov)', fontsize=15);
    
    plt.legend(loc='best', fontsize=5);
    
    
# Main function
if __name__ == '__main__':
    
    # Initialization
    a = 0.01
    rf = 0.0    # R = 1
    lmbda = 0.3
    E = np.array([[120, 30],[30, 8]])
    Z = np.array([[50, 0],[0, 5]])
    x_array = []
    y_array = []
    
    # Loop over x
    for x in range(150):
        T = np.array([[90 + x, 10],[10, 30 + x]])
        N = T - np.dot(T, np.dot(T + (a/lmbda)**2 * np.dot(E, np.dot(Z, E)), 
                                 T))
        Cov = -lmbda*f(lmbda, N, E) - lmbda**2*np.dot(f(lmbda, N, E), 
                       np.dot(np.linalg.inv(T - N), f(lmbda, N, E)))
        
        # Append to arrays
        x_array.append(frobenius(N))
        y_array.append(frobenius(Cov))
        #x_array.append(N[0][0])
        #y_array.append(Cov[0][0])
    
    # Plot
    plot_cov_curve(x_array, y_array)