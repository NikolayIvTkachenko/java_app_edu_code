package rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.observers.ResourceObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class RxJavaSample02 {

    public RxJavaSample02() {
    }

    public void sampleCode01_cold_hot_observable() {
        try {
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

    private void hotObservableExample() throws InterruptedException {
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

    public void sampleCode02_disposable() {
        //disposableExample1();
        //disposableExample2();
        disposableExample3();
    }

    private void disposableExample1() {
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS);

        Disposable disposable = seconds.subscribe(ss -> System.out.println("Item: " + ss));

        if (disposable.isDisposed()) {
            disposable.dispose();
        }

    }

    private void disposableExample2() {
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS);

        CompositeDisposable compositeDisposable = new CompositeDisposable();

        seconds.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull Long aLong) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        compositeDisposable.dispose();

    }

    private void disposableExample3() {
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS);

        ResourceObserver<Long> resourceObserver = new ResourceObserver<Long>() {
            @Override
            public void onNext(@NonNull Long value) {
                System.out.println("Item: " + value);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        seconds.subscribe(resourceObserver);
        resourceObserver.dispose();
    }

    public void sampleCode03_filtering_conditional() {
        Observable.just("Hello", "my", "World")
                .filter(item -> item.length() != 2)
                .subscribe(item -> System.out.println(item));

        Observable.just("Hello", "my", "World")
                .take(2)
                .subscribe(item -> System.out.println(item));

        Observable.just("Hello", "my", "World")
                .skip(2)
                .subscribe(item -> System.out.println(item));

        Observable.just("Hello", "my", "World", "Hello")
                .distinct()
                .subscribe(item -> System.out.println(item));


        Observable.just("Hell", "Hello", "my", "World", "Hello")
                .first("xxx")
                .subscribe(item -> System.out.println(item));

        Observable.just("Hell", "Hello", "my", "World", "Hello")
                .last("xxx")
                .subscribe(item -> System.out.println(item));

        Observable.just(3, 4, 5, 6, 7, 8, 9)
                .takeWhile(item -> item <= 5)
                .subscribe(item -> System.out.println(item));

        Observable.just(3, 4, 5, 6, 7, 8, 9)
                .skipWhile(item -> item <= 5)
                .subscribe(item -> System.out.println(item));

        Observable.just("Hell", "Hello", "my", "World", "Hello", "Home")
                .all(item -> item.length() == 4)
                .subscribe(item -> System.out.println(item));

        Observable.just("Hell", "Home")
                .all(item -> item.length() == 4)
                .subscribe(item -> System.out.println(item));

        Observable.just("Hell", "Hello", "my", "World", "Hello", "Home")
                .any(item -> item.length() == 4)
                .subscribe(item -> System.out.println(item));

        Observable.just("Hell", "Hello", "my", "World", "Hello", "Home")
                .filter(item -> item.length() == 2)
                .defaultIfEmpty("AD")
                .subscribe(item -> System.out.println(item));

        Observable.just("Hell", "Hello", "World", "Hello", "Home")
                .filter(item -> item.length() == 2)
                .defaultIfEmpty("AD")
                .subscribe(item -> System.out.println(item));

        Observable.just("Hell", "Hello", "World", "Hello", "Home")
                .filter(item -> item.length() == 2)
                .switchIfEmpty(Observable.just("Ad3", "Ad6"))
                .subscribe(item -> System.out.println(item));

    }

    public void sampleCode04_transforming_combining() {

        Observable.just(1, 2, 3, 4)
                .map(item -> String.valueOf(item))
                .subscribe(item -> System.out.println(item));

        Observable.just(7, 3, 2, 4, 5)
                .sorted()
                .subscribe(item -> System.out.println(item));

        Observable.just(1, 2, 3, 4)
                .scan((accumulator, item) -> accumulator + item)
                .subscribe(item -> System.out.println(item));


        Observable.range(0, 20)
                .buffer(3)
                .subscribe(item -> System.out.println(item));

        Observable.just("a", "a", "bb", "bb", "ccc", "ccc", "d", "ff", "eee")
                .groupBy(item -> item.length())
                .flatMapSingle(group -> group.toList())
                .subscribe(item -> System.out.println(item));

        Observable.just(1, 2, 3, 4)
                .flatMap(item -> Observable.just(item * 2))
                .subscribe(item -> System.out.println(item));

        Observable.just(1, 2, 3, 4)
                .toList()
                .subscribe(item -> System.out.println(item));

        //Combining observables

        Observable.just(1, 2, 3, 4)
                .mergeWith(Observable.just(5, 6, 7, 8))
                .mergeWith(Observable.just(9, 10, 11, 12))
                .subscribe(item -> System.out.println(item));


        var obs1 = Observable.just("A", "B");
        var obs2 = Observable.just("C", "D");

        obs1.zipWith(obs2, (item1, item2) -> {
            return String.format("%s%s", item1, item2);
        }).subscribe(result -> System.out.println("Item: " + result));

    }

    public void sampleCode05_error_utilities() {
        Observable.just(1, 2, 3, 4)
                .delay(2, TimeUnit.SECONDS)
                .subscribe(item -> System.out.println(item));

        //new Scanner(System.in).nextLine();

        Observable.just(5, 6, 7, 8)
                .timeout(5, TimeUnit.SECONDS)
                .subscribe(item -> System.out.println(item));

        //new Scanner(System.in).nextLine();

        System.out.println(Thread.currentThread().getName());
        Observable.just("Test message!")
                .observeOn(Schedulers.io())
                .subscribe(item -> {
                            System.out.println(Thread.currentThread().getName());
                            System.out.println(item);
                        }
                );

        System.out.println(Thread.currentThread().getName());
        Observable.just("Test message 2!")
                .subscribeOn(Schedulers.newThread())
                .subscribe(item -> {
                            System.out.println(Thread.currentThread().getName());
                            System.out.println(item);
                        }
                );

        Observable.just(1, 2, 3, 4)
                .doOnNext(item -> System.out.println("Log in: ->"))
                .filter(item -> item >= 3)
                .subscribe(item -> System.out.println(item));


        Disposable disposable = Observable.timer(1, TimeUnit.SECONDS)
                .doOnDispose(() -> System.out.println("Disposable called!"))
                .subscribe( item -> {
                    System.out.println(item);
                });
        disposable.dispose();


        Observable.just(2, 1, 0)
                .map(item -> 2 / item)
                .retry(1)
                .subscribe(item -> {;
                    System.out.println("item = " + item);
                }, throwable -> {
                    System.out.println("Error = " + throwable.getMessage());
                });

        Observable.just(2, 1, 0)
                .map(item -> 2 / item)
                .onErrorReturnItem(-1)
                .subscribe(item -> {;
                    System.out.println("item = " + item);
                }, throwable -> {
                    System.out.println("Error = " + throwable.getMessage());
                });

        Observable.just(2, 1, 0)
                .map(item -> 2 / item)
                .onErrorResumeWith(Observable.just(5, 6, 7))
                .subscribe(item -> {;
                    System.out.println("item = " + item);
                }, throwable -> {
                    System.out.println("Error = " + throwable.getMessage());
                });

    }

    public void sampleCode06_subject() {



    }

}
