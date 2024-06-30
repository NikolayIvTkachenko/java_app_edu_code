package rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//https://www.youtube.com/watch?v=IhH97tkAhs4&t=101s&ab_channel=SergeyArkhipovJavaTutorials
//https://www.youtube.com/watch?v=ApUUN55V2RE&list=PLZ3FH0lcV0117kiek3g-qiQDkO4ezy_Ro&index=2&ab_channel=Codiguard //rx
//https://www.youtube.com/watch?v=yWdl58IXuPQ&list=PLZ3FH0lcV0117kiek3g-qiQDkO4ezy_Ro&index=4&ab_channel=Codiguard
//https://www.youtube.com/watch?v=rkP-em-bBvg&t=1s&ab_channel=SergeyArkhipovJavaTutorials

//https://www.youtube.com/watch?v=Kx1S_wtwA2g&list=PLZ3FH0lcV0117kiek3g-qiQDkO4ezy_Ro&index=6&ab_channel=Codiguard

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

    public void sampleCode04() {
        System.out.println("sampleCode04()");

        //range
        Observable<Integer> observable = Observable.range(2, 5);
        observable.subscribe( item -> {
           System.out.println(item);
        });


        //interval
        Observable<Long> intervalObservable = Observable.interval(1, TimeUnit.SECONDS);

        intervalObservable.subscribe( tic -> {
            System.out.println(tic);
        });

        //timer
        Observable<Long> timerObservable = Observable.timer(3, TimeUnit.SECONDS);
        timerObservable.subscribe( times -> {
           System.out.println("3 second passed!");
        });

        new Scanner(System.in).nextLine();

    }

    public void sampleCode05() {
        System.out.println("sampleCode05()");

        Action action = () -> System.out.println("System testing");
        Completable completable = Completable.fromAction(action);

        completable.subscribe(() ->{
           System.out.println("Action ends");
        });

        //Single
        Single<String> single = createSingle();
        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribe");
            }

            @Override
            public void onSuccess(@NonNull String s) {
                System.out.println("Success = " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error = " + e);
            }
        });

        //Maybe
        Maybe<String> maybe = createMaybe();
        maybe.subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribe");
            }

            @Override
            public void onSuccess(@NonNull String s) {
                System.out.println("Success s = " +s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error = " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("Complete");
            }
        });

    }

    public void sampleCode06() {

        Completable completable = createCompletable();
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribe");
            }

            @Override
            public void onComplete() {
                System.out.println("Complete");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error");
            }
        });


    }

    private Completable createCompletable() {
        return Completable.fromAction(actionPerformSomething());
    }

    private Action actionPerformSomething() {
        return new Action() {
            @Override
            public void run() throws Throwable {
                System.out.println("actionPerformSomething");
                System.out.println("Thread Name: " + Thread.currentThread().getName());
            }
        };
    }

    private Maybe<String> createMaybe(){
        return Maybe.create(emitter ->{
           var newCntent = readFIle();
           if (newCntent.isEmpty()) {
               emitter.onComplete();
           } else {
               emitter.onSuccess(newCntent);
           }
        });
    }

    private String readFIle() {
        return "File with data";
    }

    private Single<String> createSingle() {
        return Single.create(emitter -> {
            var user = getUser();
            if(user != null) {
                emitter.onSuccess(user);
            } else {
                emitter.onError(new Exception("User not found"));
            }
        });
    }

    private String getUser() {
        return "Nikolas";
    }
}
