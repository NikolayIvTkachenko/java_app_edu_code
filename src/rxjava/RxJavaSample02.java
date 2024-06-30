package rxjava;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class RxJavaSample02 {

    public RxJavaSample02(){}

    public void sampleCode01_cold_hot_observable() {
        try{
            //coldObservableExample();
            hotObservableExample();
        } catch (Exception e) {
            System.out.println("error = " + e.getMessage());
        }

        new Scanner(System.in).nextLine();
    }

    private void coldObservableExample() {
        Observable observable = Observable.just("a", "b", "c");

        observable.subscribe((item) -> System.out.println("Observer 1 - " + item));
        observable.subscribe((item) -> System.out.println("Observer 2 - " + item));
        observable.subscribe((item) -> System.out.println("Observer 3 - " + item));
    }

    private void hotObservableExample() throws InterruptedException{
        ConnectableObservable observable = Observable.interval(1, TimeUnit.SECONDS).publish();

        observable.connect(); //старт observable

        observable.subscribe((item) -> {
            System.out.println("Observer 1, sec: - " + item);
        });
        Thread.sleep(5000);

        observable.subscribe((item) -> {
            System.out.println("Observer 2, sec: - " + item);
        });
        Thread.sleep(20000);
        observable.subscribe((item) -> {
            System.out.println("Observer 3, sec: - " + item);
        });


    }

}
