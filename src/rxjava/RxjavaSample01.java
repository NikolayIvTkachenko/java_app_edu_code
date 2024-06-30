package rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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


    public void sampleCode07() {
        System.out.println("sampleCode07()");
        //some source emits items at the very fast pace
        //items are emitted much faster than downstream can process it
        //----------------------------------
        //items are processed much faster when asynchronicity is introduced
        //we can use observeOn() method to introduce asynchronicity
        //The problem
        //producer no longer waits for observer to complete its processing
        //Solution - Flowable and backpressure
        //Backpressure
        //Process of slowing down producer using one of the available stragies
        //Flowable - one of flavor of Observable which support backpressure mechanism

        //synchronousObservableExample();
        //asyncObservableExample();
        asyncFlowableExample();


        new Scanner(System.in).nextLine();

        //Backpresure strategies
        //MISSING - no backpressure strategy at all
        //ERROR - downstream throws an exception right away when it cannot keep up with the source
        //BUFFER - items are buffered until downstream is able to handle it
        //LATEST - last emission is kept until downstream as able to handle it
        //DROP - when downstream cannot handle new emissions, all of these emissions are discarded
    }


    private void synchronousObservableExample() {
        System.out.println("synchronousObservableExample()");
        Observable.range(1, 1000000)
                .map(id -> new ItemValue(id))
                .subscribe( item -> {
                   Thread.sleep(1000);
                   System.out.println("Observable => Received Items = " +item.value + "\n");
                });

    }

    private void asyncObservableExample() {
        System.out.println("asyncObservableExample()");
        Observable.range(1, 100000)
                .map(ItemValue::new)
                .observeOn(Schedulers.io())
                .subscribe( item -> {
                   Thread.sleep(1000);
                   System.out.println("Observable Schedulers.io() => Received Item = " +item.value + "\n");
                });

//        try{
//            Thread.sleep(Long.MAX_VALUE);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void asyncFlowableExample() {
        System.out.println("asyncFlowableExample()");
        Flowable.range(1, 100000)
                .map(ItemValue::new)
                .observeOn(Schedulers.io())
                .subscribe( item -> {
                   Thread.sleep(20);
                    System.out.println(" Flowable Received Item = " +item.value + "\n");
                });

//        try{
//            Thread.sleep(Long.MAX_VALUE);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //Flowable.create(source, strategy)
    }

    private class ItemValue {
        private Integer value;

        public ItemValue(Integer value) {
            this.value = value;
            System.out.println("ItemValue is created " + value);
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
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
