package com.lumie.contact.service;

import com.lumie.contact.entity.Contact;
import com.lumie.contact.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getTags();
    List<Contact> getTagsByTagName(String tagName);

}
