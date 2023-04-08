package org.medical.hub.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.medical.hub.models.StringListConverter;
import org.medical.hub.workflow.Workflow;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

//    @Convert(converter = StringListConverter.class)
//    private List<String> phone;
//
@JsonIgnore
@ManyToMany(mappedBy = "customer", fetch = FetchType.EAGER)
private Set<Workflow> workflow = new HashSet<>();
    @Column(name = "email", unique = true)
    private String email;
//
//
//    private Long createdAt;
//    private Long updatedAt;
}
