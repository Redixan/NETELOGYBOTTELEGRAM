package Questionnaire;

import java.util.ArrayList;

public class AttitudeToThOccupation {

    ArrayList<String> attitudeToThOccupation = new ArrayList<>();

    public  AttitudeToThOccupation() {
        attitudeToThOccupation.add("Я считаю, что занятие было\n"+ "скучным  1 2 3 4 5 6 7 8 9 10  интересным");
        attitudeToThOccupation.add("Я научился\n"+ "малому  1 2 3 4 5 6 7 8 9 10  многому");
        attitudeToThOccupation.add("Я думаю, что слушал\n"+ "невнимательно  1 2 3 4 5 6 7 8 9 10  внимательно");
        attitudeToThOccupation.add("Я принимал участие в дискуссии\n"+ "редко  1 2 3 4 5 6 7 8 9 10  часто");
        attitudeToThOccupation.add("Материал был мне\n"+ "непонятен  1 2 3 4 5 6 7 8 9 10  понятен");
        attitudeToThOccupation.add("Результатами своей работы на уроке я\n"+ "не доволен  1 2 3 4 5 6 7 8 9 10  доволен");
    }

    public String sendQuestionsToStudentATO(int numberQuestion){
        return attitudeToThOccupation.get(numberQuestion);
    }
}


