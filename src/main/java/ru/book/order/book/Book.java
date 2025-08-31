package ru.book.order.book;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * Book entity class
 * <p></p>
 * Comment: аннотации @Getter и после нее можно заменить @Data в конкретном ТЗ,
 * но если у энтити появятся вложенные сущности, то это приведет
 * к дальнейшим проблемам при вызовах equals(), hash() и toString()
 */
@Entity
@Table(name = "books")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 255)
    private String name;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 255)
    private String author;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 32)
    private String isbn;

}
