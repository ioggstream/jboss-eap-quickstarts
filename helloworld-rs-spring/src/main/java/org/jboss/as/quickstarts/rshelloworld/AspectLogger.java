package org.jboss.as.quickstarts.rshelloworld;
/**
 * AspectJ class for measuring execution time in millisec.
 * 
 * Requires:
 * 1- spring-aspects artifact, that should be in the jboss spring module;
 * 2- 
 * 
 */
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/* To load the as
 *
 */
@Aspect
public class AspectLogger {
	
	@Around("methodsToBeProfiled()")
	public Object logTimeSpent(ProceedingJoinPoint joinpoint) throws Throwable {
		long start = System.nanoTime();
		Object returnValue = joinpoint.proceed();
		long end = System.nanoTime();
		System.out.printf("Execution of %s took %.3f ms%n", joinpoint.getSignature(), (end - start) / 1e+6 );
		if (returnValue instanceof String) {
			returnValue = returnValue + "Aspected";
		}
		return returnValue;
	}
	
	@Pointcut("execution(public * org.jboss.as.quickstarts..*.*(..))")
    public void methodsToBeProfiled(){}

}