package edu.eci.dosw.tdd.persistence.nonrelational.mapper;

import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.nonrelational.document.UserDocument;
import org.springframework.stereotype.Component;

@Component
public class UserDocumentMapper {

    public UserDocument toDocument(User user) {
        UserDocument doc = new UserDocument();
        doc.setId(user.getId());
        doc.setUsername(user.getUsername());
        doc.setPasswordHash(user.getPasswordHash());
        doc.setName(user.getName());
        doc.setRole(user.getRole());
        doc.setEmail(user.getEmail());
        doc.setMembershipType(user.getMembershipType());
        doc.setAddedAt(user.getAddedAt());
        return doc;
    }

    public User toDomain(UserDocument doc) {
        User user = new User();
        user.setId(doc.getId());
        user.setUsername(doc.getUsername());
        user.setPasswordHash(doc.getPasswordHash());
        user.setName(doc.getName());
        user.setRole(doc.getRole());
        user.setEmail(doc.getEmail());
        user.setMembershipType(doc.getMembershipType());
        user.setAddedAt(doc.getAddedAt());
        return user;
    }
}