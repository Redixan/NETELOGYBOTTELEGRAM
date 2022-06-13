package Questionnaire;

import java.util.ArrayList;

public class AttitudeToTheTask {
    ArrayList<String> attitudeToTheTask = new ArrayList<>();
    public AttitudeToTheTask() {
        attitudeToTheTask.add("Я считаю, что задание было\n"+ "скучным  1 2 3 4 5 6 7 8 9 10  интересным");
        attitudeToTheTask.add("Я научился\n"+ "многому  1 2 3 4 5 6 7 8 9 10  малому");
        attitudeToTheTask.add("Выполнить задание было\n"+ "легко 1 2 3 4 5 6 7 8 9 10  сложно");
        attitudeToTheTask.add("Результатами своей работы я\n"+ "не доволен  1 2 3 4 5 6 7 8 9 10  доволен");

    }
    public String sendQuestionsToStudent(int numberQuestion){
        return attitudeToTheTask.get(numberQuestion);
    }
}
