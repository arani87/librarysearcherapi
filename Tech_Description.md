## Solution Overview

* Framework chosen is SpringBoot so that this can be built as a micro-service.  
* The app has been Dockerized so that it can be easily deployed/scaled on demand on an Elastic Container Service.  
* Health Metrics is exposed using actuator. Spring AOP has been used for monitoring upstream API response times
* In order to make the downstream service insulated from upstream API outage, circuit-breaker pattern has been used. (Netflix Hysterix)
* Response time achieved is in milliseconds
