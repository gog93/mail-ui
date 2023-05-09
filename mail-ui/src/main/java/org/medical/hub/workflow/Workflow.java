package org.medical.hub.workflow;

import lombok.Data;
import org.hibernate.annotations.Columns;
import org.medical.hub.customer.Customer;
import org.medical.hub.provider.entities.MailProfile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Table(name = "workflows")
@Entity
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "workflow_costumer_mapping",
            joinColumns = @JoinColumn(name = "workflow_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customer;

    //    @Column(name = "created_at", nullable = false, updatable = false)
//    private Long createdAt;
//
//    @Column(name = "updated_at", nullable = false)
//    private Long updatedAt;
//
//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "created_by", nullable = false)
//    private User user;
//
//    @OneToMany(mappedBy = "workflow")
//    private List<UserEmails> mails;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mailProfile_id")
    private MailProfile mailProfile;
}
