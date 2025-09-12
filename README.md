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
