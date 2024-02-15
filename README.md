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

> mvn exec:exec

## Install the Data Grid operator on OpenShift
Use the OpenShift web console Administrator view to install the Data Grid operator in the openshift-operators namespace.

## Create a Data Grid cluster on OpenShift
Use the OpenShift web console, Installed Operators -> Data Grid -> Infinispan Clusters -> Create Infinispan to create the cluster.
> Specify "route" as the method of access

## Obtain the password for the "developer" user
Data Grid creates a secret to store the "developer" user credential. 
> oc get secret example-infinispan-generated-secret -n openshift-operators -o jsonpath="{.data.identities\.yaml}" | base64 --decode

Example response:
- username: developer
  password: eQWIAItdhDGHWgqG
  roles:
  - admin
  - controlRole

## Access the Data Grid console
Use the OpenShift web console, "Networking -> Routes" to obtain the route. For example, https://example-infinispan-external-openshift-operators.apps.cluster-5pltp.5pltp.sandbox1298.opentlc.com/ 

Click on the "Open the console" button.

Authenticate using the "developer" user and password obtained earlier.
