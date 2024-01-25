package ua.javarush.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Answer {
    private Integer id;
    private String text;
    private String message;
    private Integer nextQuestionId;
}
