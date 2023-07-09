package com.polytech.moodleparser.docxConfig;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word {

    public static void printTextAndType(XWPFDocument document, Integer number, String questionText, String questionType){
        XWPFParagraph newLine = document.createParagraph();
        XWPFRun run = newLine.createRun();
        run.setText(" ");

        XWPFParagraph question = document.createParagraph();
        XWPFRun questionSettings = question.createRun(); //для текста вопроса
        questionSettings.setFontFamily("Times New Roman");
        questionSettings.setFontSize(14);
        questionSettings.setBold(true);
        questionSettings.setText("Вопрос №" + number.toString() + ": " + questionText);

        XWPFParagraph type = document.createParagraph();
        XWPFRun typeSettings = type.createRun();
        typeSettings.setFontFamily("Times New Roman");
        typeSettings.setFontSize(14);
        typeSettings.setItalic(true);
        typeSettings.setText("Тип вопроса: " + questionType);
    }

    public static void multichoicesAndGap(XWPFDocument document, Integer number, String questionText, List<String> answersList, String questionType){
        printTextAndType(document, number, questionText, questionType);
        int count = 1;

        for (String s : answersList) {
            XWPFParagraph answers = document.createParagraph();
            XWPFRun tab = answers.createRun();
            tab.setFontFamily("Times New Roman");
            tab.setFontSize(14);
            tab.setText("   ");

            XWPFRun answersSettings = answers.createRun();
            answersSettings.setFontFamily("Times New Roman");
            answersSettings.setFontSize(14);

            if (s.contains(" - Правильный ответ")) {
                answersSettings.setTextHighlightColor("yellow");
            }
            answersSettings.setText(count + ". " + s.replaceAll(" - Правильный ответ", ""));
            count++;
        }
    }

    public static void essay(XWPFDocument document, Integer number, String questionText, String questionType){
        printTextAndType(document, number, questionText, questionType);
    }

    public static void ddwtos(XWPFDocument document, Integer number, String questionText, List<String> answersList, String questionType){ //3
        printTextAndType(document, number, questionText, questionType);

        XWPFParagraph answersTitle = document.createParagraph();
        XWPFRun answersPrint = answersTitle.createRun();
        answersPrint.setFontFamily("Times New Roman");
        answersPrint.setFontSize(14);
        answersPrint.setText("Варианты ответов:");

        XWPFParagraph answers = document.createParagraph();
        XWPFRun answersSettings = answers.createRun();
        answersSettings.setFontFamily("Times New Roman");
        answersSettings.setFontSize(14);

        int answersSize = answersList.size();
        for (int k = 0; k < answersSize; k++) {
            if (k == answersSize - 1) {
                answersSettings.setText(answersList.get(k));
                break;
            }
            answersSettings.setText(answersList.get(k) + ", ");
        }
    }

    public static void numericalAndShort(XWPFDocument document, Integer number, String questionText, List<String> answersList, String questionType){ // 2
        printTextAndType(document, number, questionText, questionType);

        XWPFParagraph answersTitle = document.createParagraph();
        XWPFRun answersPrint = answersTitle.createRun();
        answersPrint.setFontFamily("Times New Roman");
        answersPrint.setFontSize(14);
        answersPrint.setText("Правильные варианты ответов:");

        int count = 1;

        for (String s : answersList) {
            XWPFParagraph answers = document.createParagraph();
            XWPFRun answersSettings = answers.createRun();
            answersSettings.setFontFamily("Times New Roman");
            answersSettings.setFontSize(14);
            answersSettings.setText("   " + count + ". " + s);
            count++;
        }
    }


    public static void generateWord(List<Question> parsed) {
        //главный List

        Map<String, String> types = new HashMap<>();
        types.put("ddwtos", "Перетаскивание в текст");
        types.put("essay", "Эссе");
        types.put("gapselect", "Выбор пропущенных слов");
        types.put("multichoice", "Множественный выбор и выбор пропущенных слов");
        types.put("multichoiceset", "Все или ничего");
        types.put("numerical", "Числовой ответ");
        types.put("shortanswer", "Короткий ответ");

        try {
            XWPFDocument document = new XWPFDocument();
            FileOutputStream out = new FileOutputStream("src/main/resources/DOCX/Вопросы.docx"); //надо поменять

            //основа
            for (int i = 0; i < parsed.size(); i++) {
                Question question = parsed.get(i);
                String questionType = question.getType();
                String questionText = question.getText();
                List<String> answers = question.getAnswers();
                int questionNumber = i + 1; //надо поменять с учетом что первый элемент это категория
                //Для отладки
//                System.out.println("Вопрос № " + questionNumber + "\n" + "Type: " + questionType);
//                System.out.println("Text: " + questionText + "\nAnswers: " + answers +"\n\n");
                //запись в файл
                if (types.containsKey(questionType)){
                    switch (questionType) {
                        case ("ddwtos") -> ddwtos(document, questionNumber, questionText, answers, types.get(questionType));
                        case ("essay") -> essay(document, questionNumber, questionText, types.get(questionType));
                        case ("gapselect"), ("multichoice"), ("multichoiceset") -> multichoicesAndGap(document, questionNumber, questionText, answers, types.get(questionType));
                        case ("numerical"), ("shortanswer") -> numericalAndShort(document, questionNumber, questionText, answers, types.get(questionType));
                        default -> System.out.println("Такого типа вопроса нет");
                    }
                }
            }

            //записываем все в док и закрываем его
            document.write(out);
            out.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
