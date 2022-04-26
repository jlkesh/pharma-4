package com.onlinepharma.onlinepharma.domain.hr;

import com.onlinepharma.onlinepharma.domain.Auditable;
import com.onlinepharma.onlinepharma.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends Auditable {

    @OneToOne
    @JoinColumn(referencedColumnName = "code")
    private Position position;

    private String fullName;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String phoneNumber;

    private String address;

    private String profileImage;

}
