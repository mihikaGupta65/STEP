
import java.util.*;

interface Question {
    String getQuestionText();
    boolean isCorrect(String answer);
}

class MultipleChoiceQuestion implements Question {
    private String questionText;
    private String correctAnswer;

    public MultipleChoiceQuestion(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion implements Question {
    private String questionText;
    private String correctAnswer;

    public TrueFalseQuestion(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Examination {
    private String examName;
    private List<Question> questions;

    public Examination(String examName) {
        this.examName = examName;
        questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getExamName() {
        return examName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Attempt startAttempt(Student student) {
        Attempt existing = student.getAttempt(this);

        if (existing != null) {
            if (existing.isSubmitted()) {
                throw new IllegalStateException(
                        "An attempt has already been submitted for this examination");
            }
            System.out.println("Examination '" + examName
                    + "' started by " + student.getName() + ".");
            return existing;
        }

        Attempt attempt = new Attempt(student, this);
        student.addAttempt(this, attempt);

        System.out.println("Examination '" + examName
                + "' started by " + student.getName() + ".");

        return attempt;
    }
}

class Student {
    private String name;
    private Map<Examination, Attempt> attempts;

    public Student(String name) {
        this.name = name;
        attempts = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addAttempt(Examination exam, Attempt attempt) {
        attempts.put(exam, attempt);
    }

    public Attempt getAttempt(Examination exam) {
        return attempts.get(exam);
    }
}

class Attempt {
    private Student student;
    private Examination examination;
    private Map<Integer, String> answers;
    private boolean submitted;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
        answers = new HashMap<>();
        submitted = false;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void answerQuestion(int questionNumber, String answer) {
        if (submitted) {
            System.out.println("Cannot change answers after submission.");
            return;
        }

        if (questionNumber < 1
                || questionNumber > examination.getQuestions().size()) {
            System.out.println("Invalid question number.");
            return;
        }

        answers.put(questionNumber, answer);

        System.out.println("Question " + questionNumber
                + " answered with '" + answer + "'.");
    }

    public void submit() {
        if (submitted) {
            System.out.println("This attempt has already been submitted.");
            return;
        }

        submitted = true;

        int score = 0;
        int total = examination.getQuestions().size();

        for (int i = 0; i < total; i++) {
            Question question = examination.getQuestions().get(i);
            String answer = answers.get(i + 1);

            if (answer != null && question.isCorrect(answer)) {
                score++;
            }
        }

        System.out.println("Examination '" + examination.getExamName()
                + "' submitted successfully.");

        System.out.println("Result for '" + examination.getExamName()
                + "' attempt: " + score + "/" + total + " correct");
    }
}

public class Pb1 {
    public static void main(String[] args) {

        Student student = new Student("Student");

        Examination exam = new Examination("Math Quiz");

        exam.addQuestion(new MultipleChoiceQuestion(
                "What is 2 + 2?", "A"));

        exam.addQuestion(new MultipleChoiceQuestion(
                "What is 5 x 3?", "B"));

        Attempt attempt = exam.startAttempt(student);

        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");

        attempt.submit();
    }
}
