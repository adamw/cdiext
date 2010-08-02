CDI, Weld and JSF Extensions
============================

Build the .jar and bundle it with your app.

Maven repository:

<repository>
    <id>softwaremill-snapshots</id>
    <name>SoftwareMill Snapshots</name>
    <url>http://tools.softwaremill.pl/nexus/content/repositories/snapshots</url>
</repository>

<dependency>
    <groupId>pl.softwaremill.cdiext</groupId>
    <artifactId>cdiext-common</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>pl.softwaremill.cdiext</groupId>
    <artifactId>cdiext-cdi</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>pl.softwaremill.cdiext</groupId>
    <artifactId>cdiext-faces</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>

1. Stackable security interceptors
----------------------------------

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
--------------------------

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

3. Object services
------------------

Object services can be used to "extend" a class hierarchy with methods. If there is a bean implementing some interface
for each class in a hierarchy, using the extension it is possible to obtain a bean corresponding to a given object; the
resolution is performed run-time.

For example, if we have the type hierarchy:
abstract class A
class B extends A
class C extends A

And corresponding services:
interface TestService<T extends A> extends OS<T> { void someMethod(); }
class TestServiceB implements TestService<B> { void someMethod() { ... } }
class TestServiceC implements TestService<C> { void someMethod() { ... } }

The extension registeres object service provider beans:

@Inject
OSP<A, TestService<A>> testService;

void test() {
    // Will invoke someMethod in TestServiceB
    testService.f(new B()).someMethod()

    // Will invoke someMethod in TestServiceC
    testService.f(new C()).someMethod()
}

The serviced object (B or C in the above example) is set on the services using the setServiced() method, when the
service is obtained. On each invocation of f(), a new service instance is created.

4. Static BeanInject
--------------------

Use BeanInject.lookup to obtain the current instance of a bean of the given class. There may be only one bean with
the given class for this to work.

5. Config extension
-------------------

You can configure a bean using a properties file. The properties file must be in the same package as the bean.

By Gaving King, see http://in.relation.to/13053.lace.

6. Current locale holder
------------------------

Stores the selected locale in a client cookie, for a month. To change the locale, use the
CurrentLocale.setCurrentLocale method.

To enable, add to faces-config.xml:

<application>
    <view-handler>pl.softwaremill.cdiext.i18n.CurrentLocaleViewHandler</view-handler>
</application>

7. Writeable & read only entity managers
----------------------------------------

You can inject a read-only and writeable entity managers, to control when entities are written. To read entities, use:

@Inject
private @ReadOnly EntityManager readOnlyEntityManager;

To write entities, use the EntityWriter bean:

@Inject
private EntityWriter entityWriter;

Any entities passed there must implement the Identifiable interface. The passed entity will be written, and properly
removed and reloaded in the read only EM.

For this to work properly, all relations in all writeable entities must have DETACH cascade enabled.

8. Transaction JSF phase listeners
----------------------------------

The phase listener starts two transactions: one for postbacks, which lasts until after the invoke application phase,
and a second one for rendering the response.

To enable, add to faces-config.xml:

<lifecycle>
    <phase-listener>pl.softwaremill.cdiext.transaction.TransactionPhaseListener</phase-listener>
</lifecycle>

9. Fields equal validator
-------------------------

A validator for checking that the content of two fields is equal.

<h:inputSecret id="password" value="#{password}" />

<h:inputSecret id="confirmPassword" value="#{confirmPassword}">
    <f:validator validatorId="fieldsEqual" />
    <f:attribute name="fieldsEqualCompareTo" value="password" />
    <f:attribute name="fieldsEqualMessageKey" value="passwords don't match" />
</h:inputSecret>

To enable, add to faces-config.xml:

<validator>
    <validator-id>fieldsEqual</validator-id>
    <validator-class>pl.softwaremill.cdiext.validator.FieldsEqualValidator</validator-class>
</validator>

10. Faces messages
-----------------

A component for enqueing faces messages, which will survive redirects. Use:

@Inject
private FacesMessages facesMessages;

To enable, add to faces-config.xml:

<application>
    <system-event-listener>
        <system-event-class>javax.faces.event.PreRenderViewEvent</system-event-class>
        <system-event-listener-class>pl.softwaremill.cdiext.messages.FacesMessagesListener</system-event-listener-class>
    </system-event-listener>
</application>

11. Navigation
--------------

Extend the NavBase to create a "nav" component and define any pages that you use the following way, using the PageBuilder:

private final Page page1 = new ViewIdPageBuilder("/page1.xhtml").setRequiresLogin(true).b();
private final Page page1 = new ViewIdPageBuilder("/admin.xhtml").setRequiresLogin(true).setSecurityEL("#{currentUser.isAdmin)").b();
private final Page login = new ViewIdPageBuilder("/login.xhtml").b();
...

And define a getter for each page.

You can then use the component either to return results of action methods or to create links:

<h:link outcome="#{nav.page1.s}">Page 1</h:link>

public String someAction() {
    ...
    return nav.getPage1().includeViewParams().redirect().s();
}

You must define a login page. This works in conjuction with restricting pages to logged in users only.

If you want to add extra security on the page, set the security EL using the page builder. It has to resolve to Boolean.class.
If the expression returns false user will get 403 Forbidden.

12. Restricting pages to logged in users only
---------------------------------------------

There must be a bean implementing the LoginBean interface; the bean controls if there's a logged in user.
Any pages, defined in the navigation (see above) by the page builder to require login, will be redirected to the
login page if a user isn't logged in.

To enable, add to faces-config.xml:

<lifecycle>
    <phase-listener>pl.softwaremill.util.SecurityPhaseListener</phase-listener>
</lifecycle>

13. Transaction interceptor
---------------------------

Use the @Transactional annotation to surround a method call with a transaction. Useful if the call is not in the
scope of a JSF request, and the TX isn't managed by the container (e.g. remoting call).

To enable, add to beans.xml:

<interceptors>
    <class>pl.softwaremill.cdiext.transaction.TransactionalInterceptor</class>
</interceptors>

14. TransactionTimeout interceptor
----------------------------------
             
Use the @TransactionTimeout(timeout = SECONDS) on a method or type to prolong a transaction
 timeout that is used on an annotated method.

To enable, add to beans.xml:

<interceptors>
    <class>pl.softwaremill.cdiext.transaction.TransactionTimeoutInterceptor</class>
</interceptors>

15. Redirecting to an error page in case of a missing required view parameter
-----------------------------------------------------------------------------

If there's a required view parameter, which is missing, JSF only gives the possibility to add a faces message. With the
listener, the user will be redirected to an error page, if that's the case, and the message will also be enqueued.
The error page is specified by the navigation component (see 11).

Important! To mark a view parameter as required, specify the requiredMessage attribute. Leave out the required
attribute, as it interferes with <f:ajax> (don't ask me why ... ;) ).

To enable, add to faces-config.xml:

<lifecycle>
    <phase-listener>pl.softwaremill.cdiext.navigation.RequiredViewParameterPhaseListener</phase-listener>
</lifecycle>
