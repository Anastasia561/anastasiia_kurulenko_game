package ua.javarush;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Question {
    private Integer id;
    private String text;
    private List<Answer> answers;
}
