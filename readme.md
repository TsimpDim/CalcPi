# CalcPi
An attempt to calculate Pi accurately and efficiently.

## Branches:

GlobalSharedSum => Every time a sum is calculated, it is immediately added to the SharedSum (which is synchronized). *Not very efficient*

DelayedSharedSum => The local sums of each thread group are summed up locally and only when the group is finished does its sum get added to the SharedSum. *More efficient than the above*



## Outputs

### GlobalSharedSum

```
Starting computations
Starting group 0
Starting group 1
Starting group 2
Starting group 3
Starting group 4
Starting group 5
----------------------------------------------------------------------
Sequential program results with 900000 steps
computed pi = 3.14159265359002700000
difference between estimated pi and Math.PI = 0.00000000000023403501
time to compute = 0.038000 seconds

```

```
Starting computations
Starting group 0
Starting group 1
Starting group 2
Starting group 3
Starting group 4
Starting group 5
----------------------------------------------------------------------
Sequential program results with 900000 steps
computed pi = 3.14159265358988330000
difference between estimated pi and Math.PI = 0.00000000000009015011
time to compute = 0.062000 seconds

```
