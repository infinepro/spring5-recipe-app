package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Notes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    final Long NOTES_ID = 1L;
    final String RECIPE_NOTES = "some recipe notes";

    NotesCommandToNotes notesCommandToNotes;

    @BeforeEach
    void setUp() {
        notesCommandToNotes = new NotesCommandToNotes();
    }

    @Test
    void convertNullObject() {
        Assertions.assertNull(notesCommandToNotes.convert(null));
    }

    @Test
    void convertEmptyObject() {
        Assertions.assertNotNull(notesCommandToNotes.convert(new NotesCommand()));
    }

    @Test
    void testConvertNotesCommandToNotes() {
        //setup data
        NotesCommand testNotesCommand = new NotesCommand()
                .setId(NOTES_ID)
                .setRecipeNotes(RECIPE_NOTES);

        //convert
        Notes convertNotes = notesCommandToNotes.convert(testNotesCommand);

        //check
        Assertions.assertEquals(convertNotes.getId(), testNotesCommand.getId());
        Assertions.assertEquals(convertNotes.getRecipeNotes(), testNotesCommand.getRecipeNotes());
    }
}