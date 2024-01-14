package ua.javarush.game;

import lombok.*;
import ua.javarush.game.Answer;

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
