package ua.javarush;

import lombok.*;

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
