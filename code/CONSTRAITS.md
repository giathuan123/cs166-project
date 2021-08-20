constrait to implement in create.sql
---
1. closing request date must be after service request date
2. a new service request odometer must have less mileage than the maximum
>  odometer
3. customer-id of service request must be owner of the car ( check owns table
>  if cid of that car-vin = customer-id)

