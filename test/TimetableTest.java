import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import static org.junit.jupiter.api.Assertions.*;

class TimetableTest {
        private int countSessions(
                NavigableMap<TimeOfDay, List<TrainingSession>> sessions
        ) {
            return sessions.values()
                    .stream()
                    .mapToInt(List::size)
                    .sum();
        }

        @Test
        void testGetTrainingSessionsForDaySingleSession() {

            Timetable timetable = new Timetable();

            Group group = new Group(
                    "Акробатика для детей",
                    Age.CHILD,
                    60
            );

            Coach coach = new Coach(
                    "Иванов",
                    "Иван",
                    "Иванович"
            );

            TrainingSession session = new TrainingSession(
                    group,
                    coach,
                    DayOfWeek.MONDAY,
                    new TimeOfDay(13, 0)
            );

            timetable.addNewTrainingSession(session);

            assertEquals(
                    1,
                    countSessions(
                            timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)
                    )
            );

            assertTrue(
                    timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty()
            );
        }

        @Test
        void testGetTrainingSessionsForDayMultipleSessions() {

            Timetable timetable = new Timetable();

            Coach coach = new Coach(
                    "Петров",
                    "Петр",
                    "Петрович"
            );

            Group groupAdult = new Group(
                    "Акробатика для взрослых",
                    Age.ADULT,
                    90
            );

            Group groupChild = new Group(
                    "Акробатика для детей",
                    Age.CHILD,
                    60
            );

            timetable.addNewTrainingSession(
                    new TrainingSession(
                            groupAdult,
                            coach,
                            DayOfWeek.THURSDAY,
                            new TimeOfDay(20, 0)
                    )
            );

            timetable.addNewTrainingSession(
                    new TrainingSession(
                            groupChild,
                            coach,
                            DayOfWeek.MONDAY,
                            new TimeOfDay(13, 0)
                    )
            );

            timetable.addNewTrainingSession(
                    new TrainingSession(
                            groupChild,
                            coach,
                            DayOfWeek.THURSDAY,
                            new TimeOfDay(13, 0)
                    )
            );

            NavigableMap<TimeOfDay, List<TrainingSession>> thursday =
                    timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

            assertEquals(2, countSessions(thursday));

            List<TimeOfDay> times = new ArrayList<>(
                    thursday.navigableKeySet()
            );

            assertEquals(new TimeOfDay(13, 0), times.get(0));
            assertEquals(new TimeOfDay(20, 0), times.get(1));

            assertTrue(
                    timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty()
            );
        }

        @Test
        void testGetTrainingSessionsForDayAndTime() {

            Timetable timetable = new Timetable();

            Group group = new Group(
                    "Акробатика для детей",
                    Age.CHILD,
                    60
            );

            Coach coach = new Coach(
                    "Петров",
                    "Петр",
                    "Петрович"
            );

            TrainingSession session = new TrainingSession(
                    group,
                    coach,
                    DayOfWeek.MONDAY,
                    new TimeOfDay(13, 0)
            );

            timetable.addNewTrainingSession(session);

            assertEquals(
                    1,
                    timetable.getTrainingSessionsForDayAndTime(
                            DayOfWeek.MONDAY,
                            new TimeOfDay(13, 0)
                    ).size()
            );

            assertTrue(
                    timetable.getTrainingSessionsForDayAndTime(
                            DayOfWeek.MONDAY,
                            new TimeOfDay(14, 0)
                    ).isEmpty()
            );
        }

        @Test
        void testSeveralSessionsAtSameTime() {

            Timetable timetable = new Timetable();

            Coach coach = new Coach(
                    "Иванов",
                    "Иван",
                    "Иванович"
            );

            Group group1 = new Group(
                    "Акробатика",
                    Age.CHILD,
                    60
            );

            Group group2 = new Group(
                    "Гимнастика",
                    Age.CHILD,
                    60
            );

            timetable.addNewTrainingSession(
                    new TrainingSession(
                            group1,
                            coach,
                            DayOfWeek.MONDAY,
                            new TimeOfDay(13, 0)
                    )
            );

            timetable.addNewTrainingSession(
                    new TrainingSession(
                            group2,
                            coach,
                            DayOfWeek.MONDAY,
                            new TimeOfDay(13, 0)
                    )
            );

            List<TrainingSession> sessions =
                    timetable.getTrainingSessionsForDayAndTime(
                            DayOfWeek.MONDAY,
                            new TimeOfDay(13, 0)
                    );

            assertEquals(2, sessions.size());
        }

        @Test
        void testEmptyTimetable() {

            Timetable timetable = new Timetable();

            assertTrue(
                    timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).isEmpty()
            );

            assertTrue(
                    timetable.getTrainingSessionsForDayAndTime(
                            DayOfWeek.MONDAY,
                            new TimeOfDay(13, 0)
                    ).isEmpty()
            );
        }

        @Test
        void testSameTimeOnDifferentDays() {

            Timetable timetable = new Timetable();

            Coach coach = new Coach(
                    "Иванов",
                    "Иван",
                    "Иванович"
            );

            Group group = new Group(
                    "Акробатика",
                    Age.CHILD,
                    60
            );

            timetable.addNewTrainingSession(
                    new TrainingSession(
                            group,
                            coach,
                            DayOfWeek.MONDAY,
                            new TimeOfDay(13, 0)
                    )
            );

            timetable.addNewTrainingSession(
                    new TrainingSession(
                            group,
                            coach,
                            DayOfWeek.TUESDAY,
                            new TimeOfDay(13, 0)
                    )
            );

            assertEquals(
                    1,
                    timetable.getTrainingSessionsForDayAndTime(
                            DayOfWeek.MONDAY,
                            new TimeOfDay(13, 0)
                    ).size()
            );

            assertEquals(
                    1,
                    timetable.getTrainingSessionsForDayAndTime(
                            DayOfWeek.TUESDAY,
                            new TimeOfDay(13, 0)
                    ).size()
            );
        }
}