package com.lumie.contact.service;

import com.lumie.contact.entity.Contact;
import com.lumie.contact.entity.Tag;
import com.lumie.contact.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService{

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Contact> getTagsByTagName(String tagName) {
        return tagRepository.findAllByTagName(tagName)
                .stream()
                .map(Tag::getContacts)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
