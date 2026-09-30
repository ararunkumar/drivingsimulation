# drivingsimulation
driving crash simulation program using Angular18 and SpringBoot micrservices,

 Functional Requirements

The simulation program is designed to work with a rectangular field, specified by its width and height. The bottom left coordinate of the field is at position (0, 0),
and the top right position is denoted (width, height). For example, a field with dimensions 10 x 10 would have its upper right coordinate at position (9, 9).

One or more cars can be added to the field, each with a unique name, starting position, and direction they are facing. For instance, a car named "A" may be placed at position (1, 2) and facing North.

A list of commands can be issued to each car, which can be one of three commands:

* L: rotates the car by 90 degrees to the left
* R: rotates the car by 90 degrees to the right
* F: moves forward by 1 grid point

If a car tries to move beyond the boundary of the field, the command is ignored, and the car stays in its current position. For example, if a car at position (0, 0) is facing South and receives an F command, the command will be ignored as it would take the car beyond the boundary of the field.

## Example Session

Using CLI as an example, your application output should contain at least the following depending on the scenario and commands. But feel free
to add extra output as you see fit.

### Scenario 1 - Running simulation with one car

```
Welcome to Car Crash Java!

Please enter the width and heigh of the simulation field in x y format:
10 10

You have created a field of 10 x 10.

Please choose from the following options:
\[1] Add a car to field
\[2] Run simulation

1

Please enter the name of the car:
A

Please enter initial position of car A in x y Direction format:
1 2 N

Please enter the commands for car A:
FFRFFFFRRL

Your current list of cars are:
- A, (1,2) N, FFRFFFFRRL

Please choose from the following options:
\[1] Add a car to field
\[2] Run simulation

2

Your current list of cars are:
- A, (1,2) N, FFRFFFFRRL

After simulation, the result is:
- A, (5,4) S

Please choose from the following options:
\[1] Start over
\[2] Exit

2

Thank you for running the simulation. Goodbye!
```

### Scenario 2 - Running simulation with multiple cars

```
Welcome to Car Crash Java!

Please enter the width and height of the simulation field in x y format:
10 10

You have created a field of 10 x 10.

Please choose from the following options:
\[1] Add a car to field
\[2] Run simulation

1

Please enter the name of the car:
A

Please enter initial position of car A in x y Direction format:
1 2 N

Please enter the commands for car A:
FFRFFFFRRL

Your current list of cars are:
- A, (1,2) N, FFRFFFFRRL

Please choose from the following options:
\[1] Add a car to field
\[2] Run simulation

1

Please enter the name of the car:
B

Please enter initial position of car A in x y Direction format:
7 8 W

Please enter the commands for car A:
FFLFFFFFFF

Your current list of cars are:
- A, (1,2) N, FFRFFFFRRL
- B, (7,8) W, FFLFFFFFFF

Please choose from the following options:
\[1] Add a car to field
\[2] Run simulation

2

Your current list of cars are:
- A, (1,2) N, FFRFFFFRRL
- B, (7,8) W, FFLFFFFFFF

After simulation, the result is:
- A, collides with B at (5,4) at step 7
- B, collides with A at (5,4) at step 7

Please choose from the following options:
\[1] Start over
\[2] Exit

2

Thank you for running the simulation. Goodbye!
