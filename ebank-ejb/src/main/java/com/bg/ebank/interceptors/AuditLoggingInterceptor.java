/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bg.ebank.interceptors;

import java.io.Serializable;
import java.util.Arrays;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author j2ee
 */
@Interceptor
public class AuditLoggingInterceptor implements Serializable {

    public AuditLoggingInterceptor() {
    }

    @AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception{
        System.out.println(ctx.getMethod().getName() + " called");
        System.out.println(ctx.getTarget() + " is target object");
        System.out.println(Arrays.toString(ctx.getParameters()) + " parameters");

        return ctx.proceed();
    }


}
