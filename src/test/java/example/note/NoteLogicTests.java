package example.note;

import example.note.NoteLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты класса NoteLogic
 */
public class NoteLogicTests {
    private NoteLogic noteLogic;

    /**
     * Создает экземпляр NoteLogic()
     */
    @BeforeEach
    public void setUp() {
        noteLogic = new NoteLogic();
    }

    /**
     * Тестирует добавление и получение заметки
     */
    @Test
    public void addAndGetNoteTest() {
        noteLogic.handleMessage("/add test note");
        String expected = "Your notes: test note";
        Assertions.assertEquals(expected, noteLogic.handleMessage("/notes"));
    }

    /**
     * Тестирует редактирование заметки
     */
    @Test
    public void editNoteTest() {
        noteLogic.handleMessage("/add test note");
        noteLogic.handleMessage("/edit edited note");
        String expected = "Your notes: edited note";
        Assertions.assertEquals(expected, noteLogic.handleMessage("/notes"));
    }

    /**
     * Тестирует удаление заметки
     */
    @Test
    public void deleteNoteTest() {
        noteLogic.handleMessage("/add test note");
        noteLogic.handleMessage("/add deleted note");
        noteLogic.handleMessage("/del deleted note");
        String expected = "Your notes: test note";
        Assertions.assertEquals(expected, noteLogic.handleMessage("/notes"));
    }
}

