package hexlet.code.utils;

import hexlet.code.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class UserUtils {

    Logger log = LoggerFactory.getLogger("§§§HAS ACCESS: ");

    public boolean currentHasAccessTo(User owner) {
        SecurityContext seqCtx = SecurityContextHolder.getContext(); //связывает данный SeqCtx с текущим потоком выполнения
        Authentication auth = seqCtx.getAuthentication();
        // auth.isAuthenticated() true, если токен был аутентифицирован
        // и контроллеру - AbstractSecurityInterceptor
        // не нужно снова предоставлять токен Ауф-менеджеру для повторной аутентификации.
        if (auth == null || !auth.isAuthenticated()) { //если не ауф, не можем определить что за юзер
            return false;
        }
        String currentUserLogin = auth.getName();
        log.info("Matching " + currentUserLogin + " and owner login " + owner.getEmail());
        if (currentUserLogin.equals(owner.getEmail())) { //сравнение двух сущностей по логину
            return true;
        }
        return false;
    }

    public String getCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            return new String("<authentication is null>");
        }
        return authentication.getName();
    }
}
