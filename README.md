# spring-webflux-intro

Intro to WebFlux in microservices architecture

* product-service: reactive service connected to mongo-db
* user-service: reactive service using R2DBC drivers with postgresql
* order-service: reactive service with blocking JPA db
    * Order service place the order calling both product and user service, hence it contains the most complex pipeline,
      mixing reactive and blocking operations
