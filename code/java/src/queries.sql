-- Option 6
select date, comment, bill from closed_requests where bill < 100;
-- Option 7
select fname, lname from customer, (select customer_id, count(*) from owns group by customer_id having count(*) > 20) as o  where o.customer_id = id;
-- Option 8
select make, model, year, cur_miles from car C, (select car_vin, max(odometer) as cur_miles  from service_request group by car_vin) as cm where C.year < 1995 and C.vin = cm.car_vin and cur_miles < 50000;
-- Option 9
select distinct make, model, noService from car, (select car_vin, count(*) as noService from service_request group by car_vin) as cm where car_vin = vin order by noService desc limit 10(k);
-- Option 10
select fname, lname, totalBills from customer,(select sum(bill) as totalBills, customer_id from closed_request C, service_request S where C.rid = S.rid group by customer_id) as bills where customer_id = id order by totalBills desc;
