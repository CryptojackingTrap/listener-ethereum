//package it.unitn;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import rx.Observable;
//import rx.Subscription;
//
//import org.web3j.protocol.Web3j;
//import org.web3j.protocol.core.methods.response.EthBlock;
//import org.web3j.protocol.core.methods.response.Transaction;
//import org.web3j.protocol.http.HttpService;
//import org.web3j.utils.Convert;
//
///**
// * Demonstrations of working with RxJava's Observables in web3j.
// */
//public class Main {
//
//    private static final int COUNT = 10;
//
//    private static Logger log = LoggerFactory.getLogger(Main.class);
//
//    private final Web3j web3j;
//    private static String mainNetUrl = "https://main-light.eth.linkpool.io";
//
//    public Main() {
//        web3j = Web3j.build(new HttpService(mainNetUrl));  // defaults to http://localhost:8545/
//    }
//
//    private void run() throws Exception {
//        simpleFilterExample();
//        blockInfoExample();
//        countingEtherExample();
//        clientVersionExample();
//        System.exit(0);  // we explicitly call the exit to clean up our ScheduledThreadPoolExecutor used by web3j
//    }
//
//    public static void main(String[] args) throws Exception {
//        new Main().run();
//    }
//
//    void simpleFilterExample() throws Exception {
//
//        Subscription subscription = web3j.blockObservable(false).subscribe(block -> {
//            logInfo("Sweet, block number " + block.getBlock().getNumber()
//                    + " has just been created");
//        }, Throwable::printStackTrace);
//
//        TimeUnit.MINUTES.sleep(2);
//        subscription.unsubscribe();
//    }
//
//    private static void logInfo(String s) {
////        log.info(s);
//        System.out.println(s);
//    }
//
//    void blockInfoExample() throws Exception {
//        CountDownLatch countDownLatch = new CountDownLatch(COUNT);
//
//        logInfo("Waiting for " + COUNT + " transactions...");
//        Subscription subscription = web3j.blockObservable(true)
//                .take(COUNT)
//                .subscribe(ethBlock -> {
//                    EthBlock.Block block = ethBlock.getBlock();
//                    LocalDateTime timestamp = Instant.ofEpochSecond(
//                            block.getTimestamp()
//                                    .longValueExact()).atZone(ZoneId.of("UTC")).toLocalDateTime();
//                    int transactionCount = block.getTransactions().size();
//                    String hash = block.getHash();
//                    String parentHash = block.getParentHash();
//
//                    logInfo(
//                            timestamp + " " +
//                                    "Tx count: " + transactionCount + ", " +
//                                    "Hash: " + hash + ", " +
//                                    "Parent hash: " + parentHash
//                    );
//                    countDownLatch.countDown();
//                }, Throwable::printStackTrace);
//
//        countDownLatch.await(10, TimeUnit.MINUTES);
//        subscription.unsubscribe();
//    }
//
//    void countingEtherExample() throws Exception {
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//
//        logInfo("Waiting for " + COUNT + " transactions...");
//        Observable<BigInteger> transactionValue = web3j.transactionObservable()
//                .take(COUNT)
//                .map(Transaction::getValue)
//                .reduce(BigInteger.ZERO, BigInteger::add);
//
//        Subscription subscription = transactionValue.subscribe(total -> {
//            BigDecimal value = new BigDecimal(total);
//            logInfo("Transaction value: " +
//                    Convert.fromWei(value, Convert.Unit.ETHER) + " Ether (" +  value + " Wei)");
//            countDownLatch.countDown();
//        }, Throwable::printStackTrace);
//
//        countDownLatch.await(10, TimeUnit.MINUTES);
//        subscription.unsubscribe();
//    }
//
//    void clientVersionExample() throws Exception {
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        Subscription subscription = web3j.web3ClientVersion().observable().subscribe(x -> {
//            logInfo("Client is running version: {" + x.getWeb3ClientVersion()+"}");
//            countDownLatch.countDown();
//        });
//
//        countDownLatch.await();
//        subscription.unsubscribe();
//    }
//}