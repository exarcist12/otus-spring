package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestServiceImplTest {
    @Test
    void executeTest_shouldPrintQuestions() {
        FakeIOService io = new FakeIOService();
        FakeQuestionDao dao = new FakeQuestionDao();

        TestServiceImpl service = new TestServiceImpl(io, dao);

        service.executeTest();

        String output = io.getOutput();
        assertTrue(output.contains("What is 2+2?"), "Вопрос не выведен");
        assertTrue(output.contains("4"), "Вариант ответа не выведен");
    }

    private static class FakeIOService implements IOService {
        private final List<String> lines = new ArrayList<>();

        @Override
        public void printLine(String s) {
            lines.add(s);
        }

        @Override
        public void printFormattedLine(String s, Object... args) {
            lines.add(String.format(s, args));
        }

        public String getOutput() {
            return String.join("\n", lines);
        }
    }

    private static class FakeQuestionDao implements QuestionDao {
        @Override
        public List<Question> findAll() {
            return List.of(
                    new Question("What is 2+2?", List.of(
                            new Answer("3", false),
                            new Answer("4", true)
                    ))
            );
        }
    }
}