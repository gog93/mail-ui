package org.medical.hub.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="user_details")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", unique = true)
    private String username ;

    @Column(name = "activation_code", unique = true)
    private String activationCode ;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "user_id", unique = true)
    private String userId;

    private String password ;
    private String city ;
    private String country ;
    private String department ;
    private Long emailVerificationToken;
    private String firstName;
    private String surname;
    private String institution;
    private Boolean isActive;
    private Boolean isEmailVerified;

    private Boolean isNotifiedForCheckList;
    private Boolean isNotifiedForFileUpload;
    //private Long createdAt;
    private Long updatedAt;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<ExcelFile> excelFiles;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Rule> rules;
//
//    @OneToMany(mappedBy = "user")
//    private Set<GP2Ecrf1> eCRF1;
}
