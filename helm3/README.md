Serverless Streams demo Helm3 chart

1. Create project

```
$ oc new-project streams-serverless-demo
```

2. Make sure all required Operators are installed before installing helm chart

```
$ oc get csv
NAME                                DISPLAY                             VERSION   REPLACES                          
amqstreams.v1.4.0                   Red Hat Integration - AMQ Streams   1.4.0     amqstreams.v1.3.0                   
knative-eventing-operator.v0.12.0   Knative Eventing Operator           0.12.0    knative-eventing-operator.v0.11.0   
knative-kafka-operator.v0.12.1      Knative Apache Kafka Operator       0.12.1    knative-kafka-operator.v0.11.2      
serverless-operator.v1.5.0          OpenShift Serverless Operator       1.5.0     serverless-operator.v1.4.1          
```
3. Clone this repo 

```
git clone https://github.com/jstakun/knative-kafka.git
```

4. Modify ingress host name to match your cluster routing configuration in ./helm3/serverless-streams/values.yaml

5. Install this chart

```
$ helm install --generate-name ./helm3/serverless-streams/
```
6. Check if all CRD objects has been created 

```
$ oc get Kafka
$ oc get KafkaTopic
$ oc get KafkaBridge
$ oc get KafkaSource
$ oc get KnativeEventingKafka
$ oc get Service.serving.knative.dev
```
7. Generate stream of events published to Kafka topic and consumed by Serverless service

```
$ ROUTE=$(oc get ingress | grep my-bridge | awk '{print $2}')

$ echo $ROUTE 

$ while true;
curl -X POST $ROUTE/topics/my-topic -H 'content-type: application/vnd.kafka.json.v2+json' -d '{"records": [{"value": "hello from shadowman"}]}'
echo;   
do sleep 0.5;
done;
```

