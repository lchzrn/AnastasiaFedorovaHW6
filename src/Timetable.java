import java.util.*;

public class Timetable {
        private final Map<DayOfWeek, NavigableMap<TimeOfDay, List<TrainingSession>>> timetable;
        public Timetable() {

            timetable = new HashMap<>();
            for (DayOfWeek day : DayOfWeek.values()) {
                timetable.put(day, new TreeMap<>());
            }
        }
        public void addNewTrainingSession(TrainingSession trainingSession) {

            DayOfWeek day = trainingSession.getDayOfWeek();

            TimeOfDay time = trainingSession.getTimeOfDay();

            timetable.get(day)
                    .computeIfAbsent(time, key -> new ArrayList<>())
                    .add(trainingSession);
        }

        public NavigableMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(
                DayOfWeek dayOfWeek
        ) {
            return timetable.get(dayOfWeek);
        }

        public List<TrainingSession> getTrainingSessionsForDayAndTime(
                DayOfWeek dayOfWeek,
                TimeOfDay timeOfDay
        ) {

            return timetable.get(dayOfWeek)
                    .getOrDefault(timeOfDay, Collections.emptyList());
        }

        public List<CounterOfTrainings> getCountByCoaches() {

            Map<Coach, Integer> counters = new HashMap<>();

            for (NavigableMap<TimeOfDay, List<TrainingSession>> sessionsByTime
                    : timetable.values()) {

                for (List<TrainingSession> sessions : sessionsByTime.values()) {

                    for (TrainingSession session : sessions) {

                        Coach coach = session.getCoach();

                        counters.put(
                                coach,
                                counters.getOrDefault(coach, 0) + 1
                        );
                    }
                }
            }

            List<CounterOfTrainings> result = new ArrayList<>();

            for (Map.Entry<Coach, Integer> entry : counters.entrySet()) {

                result.add(
                        new CounterOfTrainings(
                                entry.getKey(),
                                entry.getValue()
                        )
                );
            }

            result.sort(
                    (first, second) ->
                            Integer.compare(second.getCount(), first.getCount())
            );

            return result;
        }
}