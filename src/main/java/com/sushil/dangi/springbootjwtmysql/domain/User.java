package com.sushil.dangi.springbootjwtmysql.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sushil.dangi.springbootjwtmysql.utils.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
@ToString
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "username is required!")
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50, message = "Minimum 1 and maximum 50 is allowed!")
    @Column(length = 50, unique = true, nullable = false, name = "username")
    private String username;

    @JsonIgnore
    @NotBlank(message = "Password is required!")
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50, nullable = false)
    @NotBlank(message = "First Name is required!")
    private String firstName;

    @Size(max = 50, message = "Maximum 254 is allowed!")
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(min = 5, max = 254, message = "Minimum 5 and maximum 254 is allowed!")
    @NotBlank(message = "Email is required!")
    @Email(message = "Please enter correct email!")
    @Column(name = "email", nullable = false, unique = true, length = 254)
    @NaturalId
    private String email;

    @JsonIgnore
    @Column(nullable = false, name = "account_non_expired")
    private boolean accountNonExpired = true;

    @JsonIgnore
    @Column(nullable = false, name = "account_non_locked")
    private boolean accountNonLocked = true;

    @JsonIgnore
    @Column(nullable = false, name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    @JsonIgnore
    @Column(nullable = false, name = "enabled")
    private boolean enabled = true;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();
}
