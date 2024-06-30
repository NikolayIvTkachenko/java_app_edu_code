import monade.Student;
import rxjava.RxJavaSample02;
import rxjava.RxjavaSample01;

import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args){
        System.out.println("Start Programming");

        try {
            mainProgram02();
        } catch (Exception e) {
            System.out.println("Exception = " + e);
        }


    }

    public static void mainProgram02() throws InterruptedException {
        RxjavaSample01 rxjavaSample01 = new RxjavaSample01();
        RxJavaSample02 rxJavaSample02 = new RxJavaSample02();
        //rxjavaSample01.sampleCode01();
        //rxjavaSample01.sampleCode02();
        //rxjavaSample01.sampleCode03();
        //rxjavaSample01.sampleCode04();
        //Thread.sleep(5000);
        //rxjavaSample01.sampleCode05();
        //rxjavaSample01.sampleCode06();
        //rxjavaSample01.sampleCode07();

        //rxJavaSample02.sampleCode01_cold_hot_observable();
        //rxJavaSample02.sampleCode02_disposable();
        //rxJavaSample02.sampleCode03_filtering_conditional();
        //rxJavaSample02.sampleCode04_transforming_combining();
        //rxJavaSample02.sampleCode05_error_utilities();
        rxJavaSample02.sampleCode06_subject();



    }

    public static void mainProgram01(){
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

        Student student03 = new Student("111 333", 15, true);
        Lazy<String> composed = Lazy.from(student03)
                .map(Student::getNameStudent)
                .map(s -> s.split(" "))
                .map(c -> {
                    System.out.println(c[0]);
                    return c[0];
                });
        composed.get();
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

    public static class Lazy<T> {
        T value;

        Supplier<T> supplier;

        private Lazy(T value) {
            this.value = value;
        }

        private Lazy(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        public static <T> Lazy<T> from(T value) {
            return new Lazy<>(value);
        }

        public T get() {
            if(value == null) {
                value = supplier.get();
            }
            return value;
        }

        public <U> Lazy<U> flatMap(Function<T, Lazy<U>> mapFunc) {
            return mapFunc.apply(value);
        }

        public <U> Lazy<U> map(Function<T, U> mapFunc) {
            return new Lazy<>(() -> mapFunc.apply(get()));
        }
    }
}
