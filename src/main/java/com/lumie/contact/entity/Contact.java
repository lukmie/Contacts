package com.lumie.contact.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contact")
    private Long id;
    @NotNull
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @Column(name = "last_name")
    private String lastName;
    @NotNull
    @Column(name = "email")
    private String email;
    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;
    private String tag;

    @ManyToMany(mappedBy = "contacts", fetch = FetchType.LAZY, cascade = { //CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Tag> tags = new ArrayList<>();

//    @ManyToMany(fetch=FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinTable(name = "contact_tag",
//            joinColumns = { @JoinColumn(name = "contact_id", referencedColumnName = "id_contact") },
//            inverseJoinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id_tag") })
//    private List<Tag> tags = new ArrayList<>();

    public Contact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void removeTag() {
        for (Tag tag : tags) {
            tag.getContacts().remove(this);
        }
    }

    public void addTag() {
//        System.out.println("1------------"+tags);
//        if (!tags.isEmpty()){
//            removeTag();
//        }
//        System.out.println("2------------"+tags);
        for (Tag tag : tags) {
            tag.getContacts().add(this);
        }

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) &&
                Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(email, contact.email) &&
                Objects.equals(phoneNumber, contact.phoneNumber) &&
                Objects.equals(tag, contact.tag) &&
                Objects.equals(tags, contact.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneNumber, tag, tags);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
