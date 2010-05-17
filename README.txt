CDI & Weld Extensions
=====================

1. Stackable security interceptors

To use, add to your beans.xml the following:

<interceptors>
   <class>pl.softwaremill.cdiext.security.SecurityInterceptor</class>
   <class>pl.softwaremill.cdiext.security.SecurityResultInterceptor</class>
</interceptors>

Blog links:
* http://www.warski.org/blog/?p=197
* http://www.warski.org/blog/?p=211

2. Injectable EL Evaluator