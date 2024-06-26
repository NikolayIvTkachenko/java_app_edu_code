import monade.Student;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start Programming");

        Student student01 = new Student("Dart Vader", 15, true);
        Monad.from(student01)
                .map(Student::getNameStudent)
                .map(s -> s.split(" "))
                .map(c -> {
                            System.out.println(c[0]);
                            return c[0];
                });

        Student student02 = new Student(null, 15, true);
        Optional.from(student02)
                .map(Student::getNameStudent)
                .map(s -> s.split(" "))
                .map(c -> {
                    System.out.println(c[0]);
                    return c[0];
                });

    }

    //Монада - это возможность записать последоватеьность действий
    //Монада - это контейнер, какого-то типа, позволяющего применять
    //к своему содержимому функцию, и вернуть его же. (конструирование вычислительного процесса)
    //Монада - это композиция функции
    public static class Monad<T> {
        final T value;

        private Monad(T value) {
            this.value = value;
        }

        public static <T> Monad<T> from(T value) {
            return new Monad<>(value);
        }

        public <U> Monad<U> flatMap(Function<T, Monad<U>> mapFunc) {
            return mapFunc.apply(value);
        }

        public <U> Monad<U> map(Function<T, U> mapFunc) {
            return flatMap(val -> new Monad<>(mapFunc.apply(val)));
        }

    }

    public static class Optional<T> {

        private static final Optional<?> empty = new Optional<>(null);
        final T value;

        private Optional(T value) {
            this.value = value;
        }

        public static <T> Optional<T> from(T value) {
            if(value != null) {
                return new Optional<>(value);
            } else {
                return (Optional<T>)empty;
            }

        }

        public <U> Optional<U> flatMap(Function<T, Optional<U>> mapFunc) {
            return mapFunc.apply(value);
        }

        public <U> Optional<U> map(Function<T, U> mapFunc) {
            if(value != null) {
                return flatMap(val -> new Optional<>(mapFunc.apply(val)));
            } else {
                return (Optional<U>)empty;
            }
        }
    }
}