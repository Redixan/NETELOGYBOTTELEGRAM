package Questionnaire;

import java.util.ArrayList;

public class AttitudeToTheTask {
    ArrayList<String> attitudeToTheTask = new ArrayList<>();
    public AttitudeToTheTask() {
        attitudeToTheTask.add("Я считаю, что задание было");
        attitudeToTheTask.add("Я научился");
        attitudeToTheTask.add("Я думаю, что слушал");
        attitudeToTheTask.add("Выполнить задание было\n");
        attitudeToTheTask.add("Результатами своей работы я");

    }
    public String sendQuestionsToStudent(int numberQuestion){
        return attitudeToTheTask.get(numberQuestion);
    }
}
