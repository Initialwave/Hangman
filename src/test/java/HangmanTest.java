import com.sun.jdi.ArrayReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HangmanTest {
public Hangman ref;

    @BeforeEach
    void setup() {
        ref = new Hangman();
    }

    @Test
    void containsTest() {
        ArrayList<String> t1 = new ArrayList<>(List.of("l", "o", "l", "e", "H"));
        for(String str: t1) {
            assertTrue(ref.contains("hello", str), "this value is not contained and should be");
        }
        ArrayList<String> t2 = new ArrayList<>(List.of("a", "w", "r", " ", "j", "y"));
        for(String str: t2) {
            assertFalse(ref.contains("hello", str), "this value is contained and should not be");
        }
    }

    @Test
    void updateSateTest() {
        ArrayList<String> bleh = new ArrayList<>(List.of("b", "o", "d", "i", "e", "s", "c", "h", "a", "n", "u", "r", "a"));
        for (String str: bleh) {
            ref.updateSate("BOdies", str);
        }
        assertEquals(ref.correct, "bodies", "you shall not pass!");
        assertEquals(ref.incorrect, "chanura", "you shall not pass yet again! muahahahaha");
    }

    @Test
    void isRunningTest() {
        ArrayList<String> a = new ArrayList<>(List.of("hellod", "Xylophone", "promiscuous", "hitherto", "peninsula"));
        for (String str: a) {
            assertTrue(ref.isRunning(str), "this failed because you are bad");
        }
        for (String str: a) {
            ref.incorrect = str;
            assertFalse(ref.isRunning(""), "this failed because you are bad");
        }
        for (String str: a) {
            ref.correct = str;
            assertFalse(ref.isRunning(str), "this failed because you are not only bad, but ugly too");
        }
    }

    @Test
    void wordbuilderTest() {
        ArrayList<String> a = new ArrayList<>(List.of("hello", "Xylophone", "promiscuous", "hitherto", "peninsula"));
        ArrayList<String> b = new ArrayList<>(List.of("eOLlhlLLo", "Xxyloenohpx", "MpuIuoioMsprc", "RhoirhtHte", "LLlUsnEpian"));
        for (int i = 0; i < a.size(); i++) {
            assertTrue(ref.wordbuilder(a.get(i), b.get(i)), "we are failing to compare words");
        }
        ArrayList<String> c = new ArrayList<>(List.of("death", "to", "Brian", "for", "ArrayLists"));
        ArrayList<String> d = new ArrayList<>(List.of("life", "of", "demons", "retort", "illusions"));
        for (int i = 0; i < c.size(); i++) {
            assertFalse(ref.wordbuilder(c.get(i), d.get(i)), "why you passing?!");
        }
    }
}