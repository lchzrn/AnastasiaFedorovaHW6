import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {

        private int hours; // от 00 до 23
        private int minutes;

        public TimeOfDay(int hours, int minutes) {
            if (hours < 0 || hours > 23) {
                throw new IllegalArgumentException("Некорректно указан час");
            }

            if (minutes < 0 || minutes > 59) {
                throw new IllegalArgumentException("Некорректно указаны минуты");
            }

            this.hours = hours;
            this.minutes = minutes;
        }

        public int getHours() {
            return hours;
        }

        public int getMinutes() {
            return minutes;
        }

        @Override
        public int compareTo(TimeOfDay other) {

            int hoursComparison = Integer.compare(this.hours, other.hours);

            if (hoursComparison != 0) {
                return hoursComparison;
            }

            return Integer.compare(this.minutes, other.minutes);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof TimeOfDay)) {
                return false;
            }

            TimeOfDay that = (TimeOfDay) o;

            return hours == that.hours && minutes == that.minutes;
        }

        @Override
        public int hashCode() {
            return Objects.hash(hours, minutes);
        }

        @Override
        public String toString() {
            return String.format("%02d:%02d", hours, minutes);
        }
}