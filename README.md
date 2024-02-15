# Infinispan Simple Tutorials

This is a collection of simple tutorials that explain how to use certain
features of Infinispan in the most straightforward way possible.

Check the documentation in the [Infinispan website](https://infinispan.org/tutorials/simple/simple_tutorials.html)

## To go further
Check [Infinispan Demos](https://github.com/infinispan-demos/links) repository

## Start the Infinispan server
> podman run -it -p 11222:11222 -e USER="admin" -e PASS="password" --net=host quay.io/infinispan/server:14.0

## Open the console
In a browser, navigate to http://localhost:11222/console

## Run the remote cache example
> cd infinispan-remote/cache

## Install the Data Grid operator on OpenShift
Use the OpenShift web console Administrator view to install the Data Grid operator.

## Create a Data Grid cluster on OpenShift
Use the OpenShift web console, Installed Operators -> Data Grid -> Infinispan Clusters -> Create Infinispan to create the cluster.
> mvn exec:exec
