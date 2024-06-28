package rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;

//https://www.youtube.com/watch?v=IhH97tkAhs4&t=101s&ab_channel=SergeyArkhipovJavaTutorials
//https://www.youtube.com/watch?v=ApUUN55V2RE&list=PLZ3FH0lcV0117kiek3g-qiQDkO4ezy_Ro&index=2&ab_channel=Codiguard //rx
//https://www.youtube.com/watch?v=yWdl58IXuPQ&list=PLZ3FH0lcV0117kiek3g-qiQDkO4ezy_Ro&index=4&ab_channel=Codiguard
//https://www.youtube.com/watch?v=rkP-em-bBvg&t=1s&ab_channel=SergeyArkhipovJavaTutorials

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
        Observable<String> observable = Observable.just("Position 01", "Position 02", "Position 03");

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }

    public void sampleCode03() {
        List<Integer> events = new ArrayList<>();
        events.add(1);
        events.add(2);
        events.add(3);
        events.add(4);

        Observable<Integer> observable = Observable.fromIterable(events);

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Item = " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);

    }

}
