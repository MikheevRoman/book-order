package ru.book.order.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Client entity class
 * <p></p>
 * Comment: аннотации @Getter и после нее можно заменить @Data в конкретном ТЗ,
 * но если у энтити появятся вложенные сущности, то это приведет
 * к дальнейшим проблемам при вызовах equals(), hash() и toString()
 */
@Entity
@Table(name = "clients")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 255)
    private String fullName;

    /*
     * Здесь может быть пользовательская валидация (например, автору больше 18 лет)
     */
    @Column(nullable = false)
    @Past
    private LocalDate dateOfBirth;

}
