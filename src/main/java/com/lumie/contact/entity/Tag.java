package com.lumie.contact.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    private Long id;
    @Column(name = "tag_name")
    private String tagName;


    @ManyToMany(fetch=FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "contact_tag",
            joinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id_tag") },
            inverseJoinColumns = { @JoinColumn(name = "contact_id", referencedColumnName = "id_contact") })
    private List<Contact> contacts = new ArrayList<>();


//    @ManyToMany(mappedBy = "tags",fetch=FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    private List<Contact> contacts = new ArrayList<>();

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
        contact.getTags().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) &&
                Objects.equals(tagName, tag.tagName) &&
                Objects.equals(contacts, tag.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tagName, contacts);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
