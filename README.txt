CDI, Weld and JSF Extensions
============================

Build the .jar and bundle it with your app.

1. Stackable security interceptors

Defines the @Secure annotation, which takes an EL expression. The expression must evaluate to true, otherwise
access won't be granted. It can also be used as a meta-annotation together with @SecureBinding. In case several
@Secure annotations apply to a method, all of the must be true. E.g.:

@SecureBinding
@Secure("#{loggedInUser.administrator}")
public @interface AdministratorOnly {
}

public class SecureBean {
    @AdministratorOnly
    @Secure("#{additionalSecurityCheck}")
    public void doSecret() { ... }
}

The values of parameters may be used in the security-checking expression by mapping them with @SecureVar.

There's also the @SecureResult annotation for checking post-conditions. The result is available under the "result"
EL variable.

Security checks can be temporarily by-passed by setting flags. To allow access to a method when a flag is set, use
the @AllowWithFlag annotation. To run code in the privileged, "flagged" mode do:

@Inject
private SecurityFlags securityFlags;

securityFlags.doWithFlag("flagName", new Callable<Void>() {
   @Override
   public Void call() throws Exception {
      return someBean.doWork();
   }
});

To use, add to your beans.xml the following:

<interceptors>
   <class>pl.softwaremill.cdiext.security.SecurityInterceptor</class>
   <class>pl.softwaremill.cdiext.security.SecurityResultInterceptor</class>
</interceptors>

Blog links:
* http://www.warski.org/blog/?p=197
* http://www.warski.org/blog/?p=211

2. Injectable EL Evaluator

Usage:

@Inject
private ELEvaluator elEvaluator;

void someMethod() {
    // ...
    Integer result = elEvaluator.evaluate("#{testParam1 + 10 + testParam2}", Integer.class, params);
    // ...
}

The evaluator is a request-scoped bean. It can be injected and used both when a web request is active, and when
not (e.g. during an MDB invocation). The third parameter is optional and is a map of parameters, which will be put
in the EL context for the duration of the evaluation.

3. Static BeanInject

Use BeanInject.lookup to obtain the current instance of a bean of the given class. There may be only one bean with
the given class for this to work.

4. Config extension

By Gaving King, see http://in.relation.to/13053.lace.

5. Current locale holder

6. Writeable & read only entity managers

7. Transaction JSF phase listeners

8. Fields equal validator

<h:inputSecret id="password" value="#{password}" />

<h:inputSecret id="confirmPassword" value="#{confirmPassword}">
    <f:validator validatorId="fieldsEqual" />
    <f:attribute name="fieldsEqualCompareTo" value="password" />
    <f:attribute name="fieldsEqualMessageKey" value="passwords don't match" />
</h:inputSecret>

9. Faces messages

A component for enqueing faces messages, which will survive redirects. Use:

@Inject
private FacesMessages facesMessages;