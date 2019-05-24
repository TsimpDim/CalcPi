# CalcPi
An attempt to calculate Pi accurately and efficiently.

## Branches:

#### Solution 1

GlobalSharedSum => Every time a sum is calculated, it is immediately added to the SharedSum (which is synchronized). *Not very efficient*

DelayedSharedSum => The local sums of each thread group are summed up locally and only when the group is finished does its sum get added to the SharedSum. *More efficient than the above*

ReductionSum => Local sums are stored in an array on which a reduction algorithm is ran (no tree).


#### Solution 2

DitrSum => Distributed model in which the clients are served one after another.

DistrSumMultConns => Distributed model in which the clients are served "at the same time" (Multi-threaded).
