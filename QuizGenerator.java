import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class QuizQuestion {
    String question;
    ArrayList<String> options;
    int correctOptionIndex;

    QuizQuestion(String question, ArrayList<String> options, int correctOptionIndex) {
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    boolean isCorrect(int userAnswer) {
        return userAnswer == correctOptionIndex;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(question).append("\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append((i + 1)).append(". ").append(options.get(i)).append("\n");
        }
        return sb.toString();
    }
}

public class QuizGenerator {
    private static final Scanner scanner = new Scanner(System.in);
    private static final HashMap<String, ArrayList<QuizQuestion>> quizzes = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Create a new quiz");
            System.out.println("2. Take a quiz");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> createQuiz();
                case 2 -> takeQuiz();
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createQuiz() {
        System.out.print("Enter quiz name: ");
        String quizName = scanner.nextLine();

        ArrayList<QuizQuestion> questions = new ArrayList<>();
        while (true) {
            System.out.print("Enter question (or type 'done' to finish): ");
            String questionText = scanner.nextLine();
            if (questionText.equalsIgnoreCase("done")) {
                break;
            }

            ArrayList<String> options = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                System.out.print("Enter option " + i + ": ");
                options.add(scanner.nextLine());
            }

            int correctOptionIndex;
            while (true) {
                System.out.print("Enter the number of the correct option (1-4): ");
                correctOptionIndex = scanner.nextInt() - 1;
                scanner.nextLine();
                if (correctOptionIndex >= 0 && correctOptionIndex < 4) {
                    break;
                }
                System.out.println("Invalid option. Please enter a number between 1 and 4.");
            }

            questions.add(new QuizQuestion(questionText, options, correctOptionIndex));
        }

        quizzes.put(quizName, questions);
        System.out.println("Quiz '" + quizName + "' created successfully.");
    }

    private static void takeQuiz() {
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available. Please create a quiz first.");
            return;
        }

        System.out.println("Available quizzes:");
        int index = 1;
        for (String quizName : quizzes.keySet()) {
            System.out.println(index++ + ". " + quizName);
        }

        System.out.print("Choose a quiz to take (1-" + (index - 1) + "): ");
        int quizChoice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (quizChoice < 0 || quizChoice >= quizzes.size()) {
            System.out.println("Invalid choice. Returning to main menu.");
            return;
        }

        String chosenQuizName = (String) quizzes.keySet().toArray()[quizChoice];
        ArrayList<QuizQuestion> questions = quizzes.get(chosenQuizName);

        int score = 0;
        for (QuizQuestion question : questions) {
            System.out.println(question);
            int userAnswer;
            while (true) {
                System.out.print("Your answer (1-4): ");
                userAnswer = scanner.nextInt() - 1;
                scanner.nextLine();
                if (userAnswer >= 0 && userAnswer < 4) {
                    break;
                }
                System.out.println("Invalid answer. Please enter a number between 1 and 4.");
            }
            if (question.isCorrect(userAnswer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong. The correct answer was option " + (question.correctOptionIndex + 1));
            }
        }

        System.out.println("Quiz finished. Your score: " + score + "/" + questions.size());
    }
}
