package Questionnaire;

import java.util.ArrayList;

public class AttitudeToThOccupation {

    ArrayList<String> attitudeToThOccupation = new ArrayList<>();

    public  AttitudeToThOccupation() {
        attitudeToThOccupation.add("Я считаю, что занятие было");
        attitudeToThOccupation.add("Я научился");
        attitudeToThOccupation.add("Я думаю, что слушал");
        attitudeToThOccupation.add("Я принимал участие в дискуссии");
        attitudeToThOccupation.add("Мне нравится учиться, потому что это интересно");
        attitudeToThOccupation.add("Материал был мне");
        attitudeToThOccupation.add("Результатами своей работы на уроке я");
    }

    public String sendQuestionsToStudent(int numberQuestion){
        return attitudeToThOccupation.get(numberQuestion);
    }
}


