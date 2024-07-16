package tech.yump.jobportal.enums;

import java.util.Objects;

public enum UserTypeEnum {
    JOB_SEEKER(2L),
    RECRUITER(1L);

    private final Long id;

    UserTypeEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static UserTypeEnum fromId(Long id) {
        for (UserTypeEnum type : values()) {
            if (Objects.equals(type.id, id)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid UserType id: " + id);
    }

    public boolean matchesAuthority(String authority) {
        return authority.equals(this.getName());
    }

    public String getName() {
        if (this == JOB_SEEKER) {
            return "Job Seeker";
        } else if (this == RECRUITER) {
            return "Recruiter";
        }
        return null;
    }
}
