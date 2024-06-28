package rxjava;

import io.reactivex.rxjava3.core.Observable;

public class RxjavaSample01 {

    public RxjavaSample01() {
    }

    public void sampleCode01() {
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("Step 01");
            emitter.onNext("Step 02");
            emitter.onNext("Step 03");
            emitter.onNext("Step 04");
        });

        observable.subscribe(
                item -> {
                    System.out.println(item);
                },
                throwable -> {
                    System.out.println(throwable.getMessage());
                },
                () -> {
                    System.out.println("On complete");
                });

    }

    public void sampleCode02() {

    }

}
