package auth.server.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Aspect
@Component
public class RequireRoleAspect {

    @Around("@annotation(requireRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RequireRole requireRole) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            throw new AccessDeniedException("Access denied: no authentication");
        }

        List<String> userRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        for (String requiredRole : requireRole.value()) {
            if (userRoles.contains(requiredRole)) {
                return joinPoint.proceed();
            }
        }

        throw new AccessDeniedException("Access denied: insufficient permissions");
    }
}
