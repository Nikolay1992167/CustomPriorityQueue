package by.mnp.model;

import model.Person;
import model.PersonWithComparableById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static by.mnp.util.Constants.CAPACITY_ERROR_MESSAGE;
import static by.mnp.util.Constants.IMPLEMENTATION_ERROR;
import static by.mnp.util.Constants.NOTIFICATION_NOT_NULL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomPriorityQueueImplTest {

    private CustomPriorityQueue<Object> queue;
    private CustomPriorityQueue<Person> queueWithComparator;

    @BeforeEach
    void setUp() {
        queue = new CustomPriorityQueueImpl<>();
        queueWithComparator = new CustomPriorityQueueImpl<>(Comparator.comparing(Person::getAge));
    }

    @Nested
    class TestConstructorsWithCapacity {

        @Test
        void shouldThrowIllegalArgumentExceptionWithExpectedMessageWhenCapacityIsNegative() {
            // given, when
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> new CustomPriorityQueueImpl<>(-1));
            String actualMessage = exception.getMessage();

            // then
            assertThat(actualMessage).isEqualTo(CAPACITY_ERROR_MESSAGE);
        }

        @Test
        void shouldCreateExpectedQueue() {
            // given, when
            CustomPriorityQueue<String> queue = new CustomPriorityQueueImpl<>(10);

            // then
            assertThat(queue).isNotNull();
        }

        @Test
        void shouldThrowIllegalArgumentExceptionWithExpectedMessageWhenComparatorIsNull() {
            // given, when
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> new CustomPriorityQueueImpl<>(0, null));
            String actualMessage = exception.getMessage();

            // then
            assertThat(actualMessage).isEqualTo(CAPACITY_ERROR_MESSAGE);
        }

        @Test
        void shouldCreateExpectedQueueWhenComparatorIsValid() {
            // given, when
            CustomPriorityQueue<String> queue = new CustomPriorityQueueImpl<>(10, Comparator.reverseOrder());

            // then
            assertThat(queue).isNotNull();
        }

    }

    @Nested
    class TestAddMethod {

        @Test
        void shouldReturnTrue() {
            // given, when
            boolean actual = queue.add(56);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void shouldThrowNullPointerExceptionWithExpectedMessageWhenValueIsNull() {
            // given, then
            Exception exception = assertThrows(NullPointerException.class, () -> queue.add(null));
            String actualMessage = exception.getMessage();

            // then
            assertThat(actualMessage).isEqualTo(NOTIFICATION_NOT_NULL);
        }

        @Test
        void shouldExpectedArray() {
            // given, when
            IntStream.range(0, 9)
                    .forEach(queue::add);

            // then
            assertThat(queue.size()).isEqualTo(9);
        }

        @Test
        void shouldThrowClassCastExceptionWithExpectedMessageIfClassIsNotImplementsComparable() {
            // given
            Person person1 = new Person("Ivan", 19);
            Person person2 = new Person("Oleg", 30);
            String expectedMessage = IMPLEMENTATION_ERROR + person2;
            queue.add(person1);

            // when
            Exception exception = assertThrows(ClassCastException.class, () -> queue.add(person2));
            String actualMessage = exception.getMessage();

            // then
            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        void shouldNotThrowClassCastExceptionIfConstructorWitComparator() {
            // given
            Person person1 = new Person("Ivan", 19);
            Person person2 = new Person("Oleg", 30);

            queueWithComparator.add(person1);
            queueWithComparator.add(person2);

            // when, then
            assertDoesNotThrow(() -> new ClassCastException());
        }

    }

    @Nested
    class TestPollMethod {

        @Test
        void shouldReturnExpectedValueAndRemoveItFromQueuesHead() {
            // given
            Stream.of(3, 6, 4, 2, 1, 32, 24, 56)
                    .forEach(queue::add);

            // when, then
            assertAll(
                    () -> assertThat(queue.poll()).isEqualTo(1),
                    () -> assertThat(queue.poll()).isEqualTo(2),
                    () -> assertThat(queue.poll()).isEqualTo(3),
                    () -> assertThat(queue.poll()).isEqualTo(4),
                    () -> assertThat(queue.poll()).isEqualTo(6),
                    () -> assertThat(queue.poll()).isEqualTo(24),
                    () -> assertThat(queue.poll()).isEqualTo(32),
                    () -> assertThat(queue.poll()).isEqualTo(56),
                    () -> assertThat(queue.poll()).isNull()
            );
        }

        @Test
        void shouldReturnExpectedValueComparableById() {
            // given
            PersonWithComparableById person = new PersonWithComparableById("Oleg", 30);
            PersonWithComparableById expectedValue = new PersonWithComparableById("Ivan", 19);
            queue.add(person);
            queue.add(expectedValue);

            // when, then
            assertThat(queue.poll()).isEqualTo(expectedValue);
        }

        @Test
        void shouldReturnExpectedValueAndRemoveItFromQueuesHeadWithComparator() {
            // given
            Person person1 = new Person("Ivan", 19);
            Person person2 = new Person("Oleg", 30);
            Person person3 = new Person("Leonid", 35);
            Stream.of(person1, person2, person3)
                    .forEach(queueWithComparator::add);

            // when, then
            assertAll(
                    () -> assertThat(queueWithComparator.poll()).isEqualTo(person1),
                    () -> assertThat(queueWithComparator.poll()).isEqualTo(person2),
                    () -> assertThat(queueWithComparator.poll()).isEqualTo(person3),
                    () -> assertThat(queueWithComparator.poll()).isNull()
            );
        }

    }

    @Nested
    class TestPeekMethod {

        @Test
        void shouldReturnExpectedValueWithoutRemovingIt() {
            // given
            String expectedValue = "Result";
            queue.add(expectedValue);

            // when, then
            assertAll(
                    () -> assertThat(queue.peek()).isEqualTo(expectedValue),
                    () -> assertThat(queue.peek()).isEqualTo(expectedValue),
                    () -> assertThat(queue.peek()).isNotNull()
            );
        }

        @Test
        void shouldReturnNull() {
            assertThat(queue.peek()).isNull();
        }

    }

    @Nested
    class TestSizeMethod {

        @Test
        void shouldReturnZero() {
            assertThat(queue.size()).isZero();
        }

        @Test
        void shouldReturnThreeAfterAddingThreeElements() {
            // given
            int expectedSize = 3;
            Stream.of(4, 8, 11)
                    .forEach(queue::add);

            // when, then
            assertThat(queue.size()).isEqualTo(expectedSize);
        }
    }

    @Nested
    class TestIsEmptyMethod {

        @Test
        void shouldReturnTrue() {
            assertThat(queue.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnFalse() {
            // given
            queue.add(1);

            // when, then
            assertThat(queue.isEmpty()).isFalse();
        }

    }

    @Nested
    class TestToStringMethod {

        @Test
        void shouldReturnExpectedStringIfQueueIsEmpty() {
            // given
            String expectedString = "[]";

            // when, then
            assertThat(queue).hasToString(expectedString);
        }

        @Test
        void shouldReturnExpectedStringIfQueueContainsElements() {
            // given
            Stream.of("Out", "Auto", "Bucket")
                    .forEach(queue::add);
            String expectedString = "[Auto, Out, Bucket]";

            // when, then
            assertThat(queue).hasToString(expectedString);
        }
    }
}