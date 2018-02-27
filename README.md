# jbossfuse-metrics-demo

Red Hat JBoss Fuse 6.3.0 projects to demo the usage of the ```camel-metrics``` component in order to expose metrics on 
services running on Apache KARAF containers (Red Hat JBoss Fuse standalone or in a Fabric8 environment)
* [restful-service-swaggerv1-metrics](restful-service-swaggerv1-metrics) (```swagger v1.2```)
* [restful-service-swaggerv2-metrics](restful-service-swaggerv2-metrics) (```swagger v2.0```)

## :warning: WARNING:
- The *__Red Hat JBoss Fuse 6.3.0 Rollup 6 (v6.3.0.redhat-329) BOM__* is used in this project. So make sure you use the same patch version or
adapt to your current patch version of Red Hat JBoss Fuse 6.3.0
- This project is configured to use a repository manager. 
Thus, the [parent POM](pom.xml) points to my private [Sonatype Nexus Repository OSS](https://www.sonatype.com/download-oss-sonatype).
My [Sonatype Nexus Repository OSS](https://www.sonatype.com/download-oss-sonatype) is configured to proxy the following 
Red Hat maven repositories in addition to [Maven Central](https://repo1.maven.org/maven2):
  - https://maven.repository.redhat.com/ga 
  - https://maven.repository.redhat.com/earlyaccess/all
  - Make sure you configure the [parent POM](pom.xml) to either point to
your own maven repository manager or directly to [Maven Central](https://repo1.maven.org/maven2) and
the two Red Hat maven repositories above.
- Various PID properties need to be adjusted according to your environment:
  - either within the projects blueprint for local tests with ```camel:run``` maven goal
  - either the following PID files for container runtime (Apache Karaf standalone or managed by Fabric8)
tests according to the tested module:
    - [restful-service-swaggerv1-metrics PID](restful-service-swaggerv1-metrics/src/main/fabric8/org.jeannyil.fuse.restful-service-swaggerv1-metrics.properties)
    - [restful-service-swaggerv2-metrics PID](restful-service-swaggerv2-metrics/src/main/fabric8/org.jeannyil.fuse.restful-service-swaggerv2-metrics.properties)

## :construction: TODO:
- *[restful-service-swaggerv1-metrics README](restful-service-swaggerv1-metrics)  with build/deployment instructions*
- *[restful-service-swaggerv2-metrics README](restful-service-swaggerv2-metrics) with build/deployment instructions*
