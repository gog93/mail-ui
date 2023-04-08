package org.medical.hub.mail;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.medical.hub.customer.Customer;
import org.medical.hub.workflow.Workflow;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user_emails")
public class UserEmails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String from;
    private String subject;
    private String messageId;
    private String content;
    private Long createdAt;
    private Long updatedAt;
    private Long sentAt;

    private Boolean isFavorite;

    private Long deletedAt;

//    @Column(name = "mail_status")
//    @Convert(converter = MailStatusConverter.class)
//    private MailStatus status;
//
//    @OneToMany(mappedBy = "email", orphanRemoval = true, cascade = CascadeType.ALL)
//    private List<FavoriteEmail> user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private UserEmails parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", referencedColumnName = "id")
    private Workflow workflow;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<UserEmails> children = new HashSet<>();
    
//    @JsonIgnore
//    @OneToMany(mappedBy = "emailId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MailAttachment> mailAttachment;
}
