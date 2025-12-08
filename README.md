# Optimizing-Hospital-Room-Utilization-using-Simulation
CS 4632 Modeling and Simulation Project aiming to simulate patient and provider scheduling hospital rooms based on specialties. using the Artificial Bee Colony Algorithm

# Project Overview and Introduction
With ever increasing demands on healthcare institutions and limited
exam rooms and beds in hospitals, clinical schedulers must manage
resources skillfully to reduce costs and provide quality care. One
of the biggest expenses of hospitals is the rooms that providers use
each day for outpatient care, and many hospitals still use inefficient
workflows to schedule their rooms leading to wasted space and longer
patient wait times. Over the past year, I have interned with provider
scheduling company QGenda working with software that monitors
the capacity of rooms as well as display that data in intuitive visuals
for hospital management.
<br/>
This project aims to create a simulation of appointment scheduling
across different departments to most efficiently use a limited set of
rooms. One of the challenges of room scheduling is matching differ-
ent types of rooms used by different departments to meet provider
needs while still using space efficiently. Hospital rooms will have dif-
ferent types of equipment constraining which providers can use the
space, but other rooms are available to be used by multiple special-
ties. This combined with the changing number of specialists available
from session to session adds complexity to creating a schedule.
<br/>
This project seeks to answer how we can efficiently assign rooms in
a schedule to maximize hospital profit and patient throughput. To
accomplish this, we will need to be able develop a stochastic model for
patient demand for appointments, model our active provider agents
looking for assignments that fit their specialty, and a set number
of room resources. We will assume that patient demand follows a
Poisson distribution, and that each room will hold one patient and one
provider creating an assignment. The model will treat assignments
as one block that fills a set duration.

# Installation Instructions

The project's current version uses a Test.java file which runs a hard coded simulation with randomly generated patients, providers, and rooms according to set parameters each run. The only packages used in the program are from the standard java libraries and the entities folder included in the repsotiory. To run the test simulation, you simply need to clone the Github repsoitory and run the Test.java file to output a schedule printed in the command line.

# Usage

When the simulation is run, thh user is prompted to input values for the number of patients, providers, rooms, speciaties, and days to schedule in the command line. In the simulation, each assignment in the schedule matches a patient, provider, and room with the same specialty to a date. That provider and room will be 'busy' that day, and no other assignments with them can be scheduled the same day. Entering a 0 for the first value will instead run the simulation with default parameters. For each run of the simulation, the program will generate patients, providers, and rooms with random specialties from the user's specified count. After that, both algorithms, ABC and first-fit are run to create a schedule, exported in CSV format to the project folder. These files show the results and statistics about the schedule generated. 

An example output for default settings shows the total runtime of 11.03 sec, values of parameters, the ratio of total patients scheduled of total patients, and the rate of providers scheduled days over their maximum available days. Under that is a table of each assignment showing patient, provider, room, and day as well as when the assignment was created. This makes up the generated schedule sorted by Patient ID.

# Architecture Overview

Each assignment needs an object from the each of the entities as well as a date, matching the design of the UML diagram. As of now, the architecture no longer needs a specialty parent class, as entities are only matched with their specialty to reduce complexity. The schedule class as well is not implemented and the generated schedule is stored in an array that can be printed in the console. 

In the FirstFit.java file, the First-Fit algorithm is implemented to take lists of patients, providers, and rooms over a set period of time to generate a schedule that attempts to schedule every patient. The algorithm works by looking through the possible patient/doctor/room pairings that fit the patients specialty need and then creating an assignment for the first possible time that has not already been scheduled. The algorithm runs with nested loops checking all of the potential combinations to create an assignment until a valid one is created and added to the schedule.

The Artificial Bee Colony algorithm follows the sequence diagram layed out in the initial proposal mostly closely, although the initial diagram could have better shown the phases by different types of bees in the algorithm. The most central parts of the algorithm involve the method to create the first random solution used for initalization, the method to evaluate the fitness of a given solution, and the method to copy a neighbor's solution. Using many different helper methods was useful in the implementation as the evaluation and copying of solutions is shared between the different types of bees with different roles. The solution class also is very important for comapring and keeping track of the fitness of different schedules throughout the iterations of bee searching. 

