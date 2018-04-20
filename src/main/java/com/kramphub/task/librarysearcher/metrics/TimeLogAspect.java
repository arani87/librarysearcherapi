package com.kramphub.task.librarysearcher.metrics;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeLogAspect {

    private final InfoMetrics infoMetrics;

    @Autowired
    public TimeLogAspect(InfoMetrics infoMetrics) {
        this.infoMetrics = infoMetrics;
    }

    @Around("@annotation(com.kramphub.task.librarysearcher.invoker.Timed) && execution(public * *(..))")
    public Object time(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object value = null;

        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Error", throwable);
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info(
                    "{}.{} took {} ms",
                    proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName(),
                    proceedingJoinPoint.getSignature().getName(),
                    duration);
            if ("searchMovieLibrary".equalsIgnoreCase(proceedingJoinPoint.getSignature().getName())) {
                infoMetrics.setAverageResponseTimeMovie(((infoMetrics.getAverageResponseTimeMovie() * (infoMetrics.getMoviesApiHitCounter() - 1) + duration)) / infoMetrics.getMoviesApiHitCounter() == 0 ? 1 : infoMetrics.getMoviesApiHitCounter());
            } else if ("searchBooksLibrary".equalsIgnoreCase(proceedingJoinPoint.getSignature().getName())) {
                infoMetrics.setAverageResponseTimeBook(((infoMetrics.getAverageResponseTimeBook() * (infoMetrics.getBooksApiHitCounter() - 1) + duration)) / infoMetrics.getBooksApiHitCounter() == 0 ? 1 : infoMetrics.getBooksApiHitCounter());
            }
        }

        return value;
    }
}
