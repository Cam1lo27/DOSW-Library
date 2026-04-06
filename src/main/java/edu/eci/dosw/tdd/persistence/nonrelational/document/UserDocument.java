package edu.eci.dosw.tdd.persistence.nonrelational.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserDocument {

    @Id
    private String id;
    private String username;
    private String passwordHash;
    private String name;
    private String role;
    private String email;
    private String membershipType;
    private LocalDate addedAt;
}