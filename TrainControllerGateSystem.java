import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TrainControllerGateSystem {

    // States for Train, Controller, and Gate
    enum TrainState { FAR, NEAR, IN }
    enum ControllerState { ZERO, ONE, TWO, THREE }
    enum GateState { UP, COMING_DOWN, DOWN, COMING_UP }

    private TrainState trainState = TrainState.FAR;
    private ControllerState controllerState = ControllerState.ZERO;
    private GateState gateState = GateState.UP;

    private int trainClock = 0;
    private int controllerClock = 0;
    private int gateClock = 0;

    private final Lock lock = new ReentrantLock();

    public void simulate() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                // Print current states and clock values
                System.out.println("Train State: " + trainState + ", Train Clock: " + trainClock);
                System.out.println("Controller State: " + controllerState + ", Controller Clock: " + controllerClock);
                System.out.println("Gate State: " + gateState + ", Gate Clock: " + gateClock);

                // Train transitions
                if (trainState == TrainState.FAR && controllerState == ControllerState.ZERO) {
                    trainState = TrainState.NEAR;
                    trainClock = 0;
                    System.out.println("Train transitions to NEAR");
                } else if (trainState == TrainState.NEAR && trainClock >= 3) {
                    trainState = TrainState.IN;
                    trainClock = 0;
                    System.out.println("Train transitions to IN");
                } else if (trainState == TrainState.IN && gateState == GateState.UP && trainClock >= 5) {
                    trainState = TrainState.FAR;
                    trainClock = 0;
                    System.out.println("Train transitions to FAR");
                }

                // Controller transitions
                if (controllerState == ControllerState.ZERO && trainState == TrainState.NEAR) {
                    controllerState = ControllerState.ONE;
                    controllerClock = 0;
                    System.out.println("Controller transitions to ONE");
                } else if (controllerState == ControllerState.ONE && controllerClock >= 1) {
                    controllerState = ControllerState.TWO;
                    gateState = GateState.COMING_DOWN;
                    gateClock = 0;
                    controllerClock = 0;
                    System.out.println("Controller transitions to TWO and Gate transitions to COMING_DOWN");
                } else if (controllerState == ControllerState.TWO && gateState == GateState.DOWN) {
                    controllerState = ControllerState.THREE;
                    controllerClock = 0;
                    System.out.println("Controller transitions to THREE");
                } else if (controllerState == ControllerState.THREE && trainState == TrainState.IN) {
                    controllerState = ControllerState.ZERO;
                    controllerClock = 0;
                    gateState = GateState.COMING_UP;
                    gateClock = 0;
                    System.out.println("Controller transitions to ZERO and Gate transitions to COMING_UP");
                }

                // Gate transitions
                if (gateState == GateState.COMING_DOWN && gateClock >= 1) {
                    gateState = GateState.DOWN;
                    gateClock = 0;
                    System.out.println("Gate transitions to DOWN");
                } else if (gateState == GateState.COMING_UP && gateClock >= 1) {
                    gateState = GateState.UP;
                    gateClock = 0;
                    System.out.println("Gate transitions to UP");
                }

                // Check invariants
                if (trainState == TrainState.NEAR && trainClock > 5) {
                    throw new IllegalStateException("Invariant violation in Train.NEAR");
                }
                if (controllerState == ControllerState.ONE && controllerClock > 1) {
                    throw new IllegalStateException("Invariant violation in Controller.ONE");
                }
                if (gateState == GateState.COMING_DOWN && gateClock > 1) {
                    throw new IllegalStateException("Invariant violation in Gate.COMING_DOWN");
                }

                // Update clock values
                trainClock++;
                controllerClock++;
                gateClock++;

                // Sleep to simulate time passage
                Thread.sleep(1000);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TrainControllerGateSystem system = new TrainControllerGateSystem();
        try {
            system.simulate();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


//New Comment

//Hwlloe new code

//hidahsidahid
//OKAY SECOND COMMIT
//changes to be made same




//New Comment

//Hwlloe new code

//hidahsidahid
//OKAY SECOND COMMIT
//changes to be made same

//V-1.2



