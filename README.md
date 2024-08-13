# Load testing and fault tolerance with Kubernetes

[Video](https://drive.google.com/file/d/1TRXuvGNSHK4naz1bBQO1xYj82KYF2GHt/view)

## Setup

### Start minikube

``` bash
minikube start
```

## Load testing with JMeter

### Delete all custer resources

``` bash
kubectl delete all --all
```

#### Scenario 1: 100% success rate - no chaos

Deploy database and application with single pod

``` bash
kubectl apply -f webapp-stateless/k8s/db.yaml
kubectl apply -f webapp-stateless/k8s/webapp-single-pod.yaml
```

Get the service URL to run load tests

``` bash
minikube service webapp --url
```

Run load test

``` bash
jmeter -n -t webapp-stateless/jmeter/test.jmx -l webapp-stateless/jmeter/scenario1.jtl
```

#### Scenario 2: Single pod with errors because of chaos

Apply chaos to remove one pod every 10 seconds

``` bash
webapp-stateless/chaos-pod-monkey.sh
```

Run load test

``` bash
jmeter -n -t webapp-stateless/jmeter/test.jmx -l webapp-stateless/jmeter/scenario2.jtl
```

#### Scenario 3: Two pods to reduce the error rate

Apply redundancy to the deployment scaling to two pods

``` bash
kubectl scale deployments/webapp --replicas=2
```

Run load test

``` bash
jmeter -n -t webapp-stateless/jmeter/test.jmx -l webapp-stateless/jmeter/scenario3.jtl
```

#### Scenario 4: Apply Istio to the deployment to reduce errors

Create Istio resources if not already created

``` bash
kubectl create namespace istio-system
istioctl install --set profile=default
```

Apply Istio to the deployment

``` bash
kubectl apply -f webapp-stateless/k8s/istio.yaml
```

Run load test

``` bash
jmeter -n -t webapp-stateless/jmeter/test.jmx -l webapp-stateless/jmeter/scenario4.jtl
```

## Native development in Kubernetes with Okteto

### Deploying to Kubernetes  

``` bash
kubectl apply -f server/k8s
```

### Exporting the service

``` bash
minikube service serverservice
```

### Developing server service in Kubernetes with Okteto

``` bash
okteto up -f server/okteto.yml
```
