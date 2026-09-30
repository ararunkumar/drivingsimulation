package com.simulator.carsimulation.controller;

import com.simulator.carsimulation.model.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/simulation")
@CrossOrigin(origins = "*")
public class SimulationController {

    @PostMapping("/run")
    public List<Car> runSimulation(@RequestBody SimulationResult request) {
        int width = request.width;
        int height = request.height;
        List<Car> cars = request.cars;

        int maxSteps = 0;
        for (Car car : cars) {
            car.currentX = car.initialX;
            car.currentY = car.initialY;
            car.currentOrientation = car.initialOrientation;
            car.collided = false;
            car.collisionStep = -1;
            car.collidedWith = new ArrayList<>();
            if (car.commands.length() > maxSteps) {
                maxSteps = car.commands.length();
            }
        }

        for (int step = 1; step <= maxSteps; step++) {
            for (Car car : cars) {
                if (car.collided || step > car.commands.length()) continue;

                char cmd = car.commands.charAt(step - 1);
                if (cmd == 'L') {
                    car.currentOrientation = car.currentOrientation.turnLeft();
                } else if (cmd == 'R') {
                    car.currentOrientation = car.currentOrientation.turnRight();
                } else if (cmd == 'F') {
                    int nextX = car.currentX + car.currentOrientation.deltaX;
                    int nextY = car.currentY + car.currentOrientation.deltaY;

                    if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height) {
                        car.currentX = nextX;
                        car.currentY = nextY;
                    }
                }
            }

            Map<String, List<Car>> positionMap = new HashMap<>();
            for (Car car : cars) {
                if (car.collided) continue;
                String posKey = car.currentX + "," + car.currentY;
                positionMap.computeIfAbsent(posKey, k -> new ArrayList<>()).add(car);
            }

            for (List<Car> carsAtPos : positionMap.values()) {
                if (carsAtPos.size() > 1) {
                    for (Car c1 : carsAtPos) {
                        c1.collided = true;
                        c1.collisionStep = step;
                        for (Car c2 : carsAtPos) {
                            if (!c1.name.equals(c2.name)) {
                                c1.collidedWith.add(c2.name);
                            }
                        }
                    }
                }
            }
        }
        return cars;
    }
}
