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
Log in to the OpenShift cluster using the OpenShift CLI.
> oc login -u kubeadmin https://api.cluster-5pltp.5pltp.sandbox1298.opentlc.com:6443/

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

## References
> https://infinispan.org/get-started/

> https://infinispan.org/tutorials/simple/simple_tutorials.html

> https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.1/html-single/running_data_grid_on_openshift/index#exposing_routes-access

> https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.4

> https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.4/html/data_grid_operator_guide/configuring-authentication#default-credentials_authn

> https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.1/html-single/running_data_grid_on_openshift/index#exposing_routes-access

> https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.4/html/hot_rod_java_client_guide/index

> https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.4/html-single/data_grid_server_guide/index#opentelemetry-tracing

> https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.1/html/running_data_grid_on_openshift/connecting_clients 
