package Questionnaire;

import java.util.ArrayList;

public class ScalesOfAcademicMotivation {

    ArrayList<String> scalesOfAcademicMotivation = new ArrayList<>();

    public ScalesOfAcademicMotivation() {

        scalesOfAcademicMotivation.add("Мне интересно учиться");
        scalesOfAcademicMotivation.add("Учёба доставляет мне удовольствие, я люблю решать трудные задачи");
        scalesOfAcademicMotivation.add("Потому что я заплатил за курс и должен закончить его");
        scalesOfAcademicMotivation.add("Честно говоря, не знаю, мне кажется, что я здесь просто теряю время");
        scalesOfAcademicMotivation.add("Мне нравится учиться, потому что это интересно");
        scalesOfAcademicMotivation.add("Я чувствую удовлетворение, когда нахожусь в процессе решениях сложных учебных задач");
        scalesOfAcademicMotivation.add( "Потому что совесть заставляет меня учиться");
        scalesOfAcademicMotivation.add( "Раньше я понимал(а), зачем учусь, а теперь не уверен(а), стоит ли продолжать");
        scalesOfAcademicMotivation.add( "Мне просто нравится учиться и узнавать новое");
        scalesOfAcademicMotivation.add( "Мне нравится решать трудные задачи и прикладывать интеллектуальные усилия");
        scalesOfAcademicMotivation.add( "Потому что, решив для себя пройти курс, я должен посещать занятия и учиться");
        scalesOfAcademicMotivation.add( "Не знаю, не уверен(а), что мне это действительно надо");
        scalesOfAcademicMotivation.add( "Я действительно получаю удовольствие от изучения нового материала на занятиях");
        scalesOfAcademicMotivation.add( "Я просто люблю учиться, решать сложные задачи и чувствовать себя компетентным(ой)");
        scalesOfAcademicMotivation.add("Потому что иначе в будущем не смогу иметь достаточно обеспеченную жизнь");
        scalesOfAcademicMotivation.add("Хожу по привычке, зачем, откровенно говоря, точно не знаю");
    }

    public String sendQuestionsToStudent(int numberQuestion){
            return scalesOfAcademicMotivation.get(numberQuestion);
    }
}
