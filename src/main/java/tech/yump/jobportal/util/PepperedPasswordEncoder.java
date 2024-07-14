package tech.yump.jobportal.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PepperedPasswordEncoder implements PasswordEncoder {

    private final String pepper;
    private final PasswordEncoder delegate;

    @Autowired
    public PepperedPasswordEncoder(@Value("${password.pepper}") String pepper, PasswordEncoder delegate) {
        this.pepper = pepper;
        this.delegate = delegate;
    }

    @PostConstruct
    public void init() {
        if (pepper == null || pepper.isEmpty()) {
            throw new IllegalArgumentException("Pepper is required");
        }
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return delegate.encode(pepper + rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return delegate.matches(pepper + rawPassword, encodedPassword);
    }
}
