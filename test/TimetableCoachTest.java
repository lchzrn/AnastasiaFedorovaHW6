import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimetableCoachTest {

    private void addSession(
            Timetable timetable,
            Coach coach,
            DayOfWeek day,
            int hour
    ) {

        Group group = new Group(
                "Акробатика",
                Age.ADULT,
                60
        );

        TrainingSession session = new TrainingSession(
                group,
                coach,
                day,
                new TimeOfDay(hour, 0)
        );

        timetable.addNewTrainingSession(session);
    }

    @Test
    void testGetCountByCoachesSortedDescending() {

        Timetable timetable = new Timetable();

        Coach coach1 = new Coach(
                "Иванов",
                "Иван",
                "Иванович"
        );

        Coach coach2 = new Coach(
                "Петров",
                "Петр",
                "Петрович"
        );

        Coach coach3 = new Coach(
                "Кириллов",
                "Кирилл",
                "Кириллович"
        );

        addSession(timetable, coach1, DayOfWeek.MONDAY, 10);
        addSession(timetable, coach1, DayOfWeek.TUESDAY, 12);
        addSession(timetable, coach1, DayOfWeek.WEDNESDAY, 14);

        addSession(timetable, coach2, DayOfWeek.THURSDAY, 10);
        addSession(timetable, coach2, DayOfWeek.FRIDAY, 12);

        addSession(timetable, coach3, DayOfWeek.SATURDAY, 10);

        List<CounterOfTrainings> result =
                timetable.getCountByCoaches();

        assertEquals(3, result.size());

        assertEquals(3, result.get(0).getCount());
        assertEquals(2, result.get(1).getCount());
        assertEquals(1, result.get(2).getCount());

        assertEquals(
                "Иванов",
                result.get(0).getCoach().getSurname()
        );

        assertEquals(
                "Петров",
                result.get(1).getCoach().getSurname()
        );

        assertEquals(
                "Кириллов",
                result.get(2).getCoach().getSurname()
        );
    }

    // 2. Проверяем тренеров с одинаковыми ФИО
    @Test
    void testCoachesWithSameNameAreCountedTogether() {

        Timetable timetable = new Timetable();

        Coach coach1 = new Coach(
                "Иванов",
                "Иван",
                "Иванович"
        );

        Coach coach2 = new Coach(
                "Иванов",
                "Иван",
                "Иванович"
        );

        addSession(timetable, coach1, DayOfWeek.MONDAY, 10);
        addSession(timetable, coach2, DayOfWeek.TUESDAY, 12);

        List<CounterOfTrainings> result =
                timetable.getCountByCoaches();

        assertEquals(1, result.size());

        assertEquals(2, result.get(0).getCount());
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {

        Timetable timetable = new Timetable();

        List<CounterOfTrainings> result =
                timetable.getCountByCoaches();

        assertTrue(result.isEmpty());
    }
}