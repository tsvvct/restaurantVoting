package com.github.tsvvct.restaurantvoting.web.user;

import com.github.tsvvct.restaurantvoting.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.github.tsvvct.restaurantvoting.HasIdAndEmail;
import com.github.tsvvct.restaurantvoting.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UniqueMailValidator implements Validator {
    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";

    private final UserRepository repository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HasIdAndEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        HasIdAndEmail user = ((HasIdAndEmail) target);
        if (StringUtils.hasText(user.getEmail())) {
            repository.findByEmailIgnoreCase(user.getEmail())
                    .ifPresent(dbUser -> {
                        if (request.getMethod().equals("PUT")) {
                            int dbId = dbUser.id();

                            if (user.getId() != null && dbId == user.id()) return;

                            String requestURI = request.getRequestURI();
                            if (requestURI.endsWith("/" + dbId) || (dbId == SecurityUtil.authId() && requestURI.contains("/profile")))
                                return;
                        }
                        errors.rejectValue("email", "", EXCEPTION_DUPLICATE_EMAIL);
                    });
        }
    }
}
