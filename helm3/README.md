Serverless Streams demo Helm3 chart

1. Create project

```
$ oc new-project streams-serverless-demo
```

2. Make sure all required Operators are installed before installing helm chart

```
$ oc get csv
NAME                             DISPLAY                             VERSION   REPLACES   PHASE
amqstreams.v1.4.1                Red Hat Integration - AMQ Streams   1.4.1                Succeeded
knative-kafka-operator.v0.15.0   Knative Apache Kafka Operator       0.15.0               Succeeded
serverless-operator.v1.7.1       OpenShift Serverless Operator       1.7.1                Succeeded
```
3. Clone this repo 

```
git clone https://github.com/jstakun/knative-kafka.git
```

4. Modify ingress host name to match your cluster routing configuration in ./helm3/serverless-streams/values.yaml

5. There are 3 kafka-cluster crd files in the ./helm3/serverless-streams/crds directory which will create the same Kafka object with different configurations. Choose one you want to use and delete other two kafka-cluster files.  

6. Install this chart. If chart installation fails retry. You can also install crds manually in order of crd files names number prefixes starting from 0-.

```
$ helm install --generate-name ./helm3/serverless-streams/
```
7. Check if all CRD objects has been created 

```
$ oc get Kafka
$ oc get KafkaTopic
$ oc get KafkaBridge
$ oc get KnativeEventingKafka
$ oc get KafkaSource
$ oc get Service.serving.knative.dev
```
8. Generate stream of events published to Kafka topic and consumed by Serverless service

```
$ ROUTE=$(oc get ingress -n streams-serverless-demo | grep my-bridge | awk '{print $2}') && echo $ROUTE 

$ while :; 
curl -X POST $ROUTE/topics/my-topic -H 'content-type: application/vnd.kafka.json.v2+json' -d '{"records": [{"value": "'"$i"' hello from shadowman"}]}'; 
echo $i;
do sleep 0.5;
((i=i+1)); 
done;
```
