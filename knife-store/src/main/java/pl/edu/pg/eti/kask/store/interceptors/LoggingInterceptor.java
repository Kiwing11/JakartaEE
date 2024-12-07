package pl.edu.pg.eti.kask.store.interceptors;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;

import java.io.Serializable;

@Interceptor
@LoggingBinding
@Priority(1000)
public class LoggingInterceptor implements Serializable {
    private final SecurityContext securityContext;

    @Inject
    public LoggingInterceptor(@SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        String log = "Operation: " + context.getMethod().getName() + " by " + securityContext.getCallerPrincipal().getName() + " with resource id: " + context.getParameters()[0];
        System.out.println(log);

        return context.proceed();
    }
}
