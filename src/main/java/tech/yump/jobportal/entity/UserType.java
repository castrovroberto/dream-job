package tech.yump.jobportal.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_type_t")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(targetEntity = User.class, mappedBy = "userType", cascade = CascadeType.ALL)
    private List<User> users;

    public UserType() {
        // Default constructor
    }

    public UserType(Long id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserTypeName() {
        return name;
    }

    public void setUserTypeName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
