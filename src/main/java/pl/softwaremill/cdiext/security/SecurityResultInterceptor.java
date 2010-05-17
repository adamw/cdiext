package pl.softwaremill.cdiext.security;

import pl.softwaremill.cdiext.el.ELEvaluator;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author Adam Warski (adam at warski dot org)
 */
@Interceptor
@SecureResult("")
public class SecurityResultInterceptor {
    @Inject
    private ELEvaluator elEvaluator;

    @AroundInvoke
    public Object invoke(InvocationContext ctx) throws Exception {

        // Getting the result
        Object result = ctx.proceed();

        // And checking the condition
        SecureResult sr = ctx.getMethod().getAnnotation(SecureResult.class);
        Boolean expressionValue = evaluateSecureResultExp(result, sr);

        if (expressionValue == null || !expressionValue) {
            // TODO: message
            throw new SecurityConditionException();
        }

        return result;
    }

    public Boolean evaluateSecureResultExp(Object base, SecureResult secureResult) {
        elEvaluator.setParameter("result", base);
        try {
            return elEvaluator.evaluate(secureResult.value(), Boolean.class);
        } finally {
            elEvaluator.clearParameter("result");
        }
    }
}