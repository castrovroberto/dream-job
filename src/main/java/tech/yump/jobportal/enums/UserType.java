package tech.yump.jobportal.enums;

import java.util.Objects;

public enum UserType {
    JOB_SEEKER(2L),
    RECRUITER(1L);

    private final Long id;

    UserType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static UserType fromId(Long id) {
        for (UserType type : values()) {
            if (Objects.equals(type.id, id)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid UserType id: " + id);
    }
}
