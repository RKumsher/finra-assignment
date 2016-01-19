package kumsher.ryan.finra.service;

import static com.google.common.base.Preconditions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Service class for phone numbers.
 */
@Service
@Transactional
public class PhoneNumberService {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberService.class);
    private final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("^(\\d{7}|\\d{10})$");
    private static final Map<Character, Set<Character>> DIGIT_TO_CHARACTERS = ImmutableMap
        .<Character, Set<Character>>builder()
        .put('0', ImmutableSet.of('0'))
        .put('1', ImmutableSet.of('1'))
        .put('2', ImmutableSet.of('2', 'a', 'b', 'c'))
        .put('3', ImmutableSet.of('3', 'd', 'e', 'f'))
        .put('4', ImmutableSet.of('4', 'g', 'h', 'i'))
        .put('5', ImmutableSet.of('5', 'j', 'k', 'l'))
        .put('6', ImmutableSet.of('6', 'm', 'n', 'o'))
        .put('7', ImmutableSet.of('7', 'p', 'q', 'r', 's'))
        .put('8', ImmutableSet.of('8', 't', 'u', 'v'))
        .put('9', ImmutableSet.of('9', 'w', 'x', 'y', 'z'))
        .build();

    /**
     * Generates all possible alphanumeric representations for the given phone number.
     *
     * @param phoneNumber phone number, must be 7 or 10 digits only
     * @param pageable    pagination options
     * @return page of the all possible alphanumeric representations for the given phone number
     */
    public Page<String> generateAllPossibleAlphanumericRepresentations(String phoneNumber, Pageable pageable) {
        checkArgument(isValidPhoneNumber(phoneNumber), "%s is not a valid 7 or 10 digit phone number", phoneNumber);
        List<String> representations = generateAllPossibleAlphanumericRepresentations(phoneNumber);
        List<String> subList = Lists.partition(representations, pageable.getPageSize()).get(pageable.getPageNumber());
        return new PageImpl(subList, pageable, representations.size());
    }

    @VisibleForTesting
    boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && VALID_PHONE_NUMBER_REGEX.matcher(phoneNumber).matches();
    }

    private List<String> generateAllPossibleAlphanumericRepresentations(String phoneNumber) {
        Set<List<Character>> representations = Sets.cartesianProduct(getCharacterOptionsForEachDigit(phoneNumber));
        return representations.stream().map(characters -> Joiner.on("").join(characters)).collect(Collectors.toList());
    }

    private List<Set<Character>> getCharacterOptionsForEachDigit(String phoneNumber) {
        List<Set<Character>> digitOptions = Lists.newArrayListWithExpectedSize(phoneNumber.length());
        for (Character digit : phoneNumber.toCharArray()) {
            digitOptions.add(DIGIT_TO_CHARACTERS.get(digit));
        }
        return digitOptions;
    }

}
