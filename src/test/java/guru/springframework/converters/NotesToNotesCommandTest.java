package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotesToNotesCommandTest {
    final Long NOTES_ID = 1L;
    final String RECIPE_NOTES = "some recipe notes";

    NotesToNotesCommand notesToNotesCommand;

    @BeforeEach
    void setUp() {
        notesToNotesCommand = new NotesToNotesCommand();
    }

    @Test
    void testConvertNullObject() {
        Assertions.assertNull(notesToNotesCommand.convert(null));
    }

    @Test
    void testConvertEmptyObject() {
        Assertions.assertNotNull(notesToNotesCommand.convert(new Notes()));
    }

    @Test
    void testConvertNotesToNotesCommand() {
        //setup data
        Notes testNotes = new Notes()
                .setId(NOTES_ID)
                .setRecipeNotes(RECIPE_NOTES);

        //convert
        NotesCommand convertNotesCommand = notesToNotesCommand.convert(testNotes);

        //check
        Assertions.assertEquals(convertNotesCommand.getId(), testNotes.getId());
        Assertions.assertEquals(convertNotesCommand.getRecipeNotes(), testNotes.getRecipeNotes());
    }

}