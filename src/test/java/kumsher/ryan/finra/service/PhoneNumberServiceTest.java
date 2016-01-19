package kumsher.ryan.finra.service;

import static org.junit.Assert.*;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.google.common.collect.ImmutableList;

/**
 * Unit tests for @{link PhoneNumberService}.
 */
public class PhoneNumberServiceTest {

    private static final String TEST_PHONE_NUMBER = "1234567890";
    private static final int EXPECTED_REPRESENTATIONS_COUNT = 1 * 1 * 4 * 4 * 4 * 4 * 4 * 5 * 4 * 5; // options per digit
    private static final Pageable PER_PAGE_20 = new PageRequest(0, 20);
    private PhoneNumberService service = new PhoneNumberService() {
        @Override
        boolean isValidPhoneNumber(String phoneNumber) {
            return true; // So we can test 1 and 2 digit numbers
        }
    };

    @Test
    public void generateAllPossibleAlphaNumericRepresentationsForTheZeroDigit() {
        List<String> expected = ImmutableList.of("0");
        List<String> actual = service.generateAllPossibleAlphanumericRepresentations("0", PER_PAGE_20).getContent();
        assertEquals(expected, actual);
    }

    @Test
    public void generateAllPossibleAlphaNumericRepresentationsWithOneDigit() {
        List<String> expected = ImmutableList.of("2", "a", "b", "c");
        List<String> actual = service.generateAllPossibleAlphanumericRepresentations("2", PER_PAGE_20).getContent();
        assertEquals(expected, actual);
    }

    @Test
    public void generateAllPossibleAlphaNumericRepresentationsWithTwoDigits() {
        List<String> expected = ImmutableList
            .of("23", "2d", "2e", "2f", "a3", "ad", "ae", "af", "b3", "bd", "be", "bf", "c3", "cd", "ce", "cf");
        List<String> actual = service.generateAllPossibleAlphanumericRepresentations("23", PER_PAGE_20).getContent();
        assertEquals(expected, actual);
    }

    @Test
    public void generateAllPossibleAlphaNumericRepresentationsWithTwoDigitsWithPaging() {
        List<String> expectedPage1 = ImmutableList.of("23", "2d", "2e", "2f", "a3", "ad", "ae", "af", "b3", "bd");
        List<String> expectedPage2 = ImmutableList.of("be", "bf", "c3", "cd", "ce", "cf");
        Page<String> actualPage1 = service.generateAllPossibleAlphanumericRepresentations("23", new PageRequest(0, 10));
        Page<String> actualPage2 = service.generateAllPossibleAlphanumericRepresentations("23", new PageRequest(1, 10));
        assertEquals(expectedPage1, actualPage1.getContent());
        assertEquals(16, actualPage1.getTotalElements());
        assertEquals(2, actualPage1.getTotalPages());
        assertEquals(16, actualPage2.getTotalElements());
        assertEquals(expectedPage2, actualPage2.getContent());
    }

    @Test
    public void generateAllPossibleAlphaNumericRepresentationsWithTenDigits() {
        Page<String> page = service.generateAllPossibleAlphanumericRepresentations(TEST_PHONE_NUMBER, PER_PAGE_20);
        assertThat(page.getContent(), Matchers.hasSize(20));
        assertEquals(EXPECTED_REPRESENTATIONS_COUNT, page.getTotalElements());
    }

}
