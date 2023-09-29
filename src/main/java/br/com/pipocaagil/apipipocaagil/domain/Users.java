package br.com.pipocaagil.apipipocaagil.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createDate = LocalDate.now();
}
