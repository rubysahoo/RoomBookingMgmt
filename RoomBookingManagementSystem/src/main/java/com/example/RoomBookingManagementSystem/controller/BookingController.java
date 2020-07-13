package com.example.RoomBookingManagementSystem.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.RoomBookingManagementSystem.dao.BookingDao;
import com.example.RoomBookingManagementSystem.entity.Customer;

@RestController
public class BookingController {

	int totalRoom = 20;

	@Autowired
	private BookingDao dao;

	@PostMapping(value = "/bookroom/{username}", consumes = "application/json")
	public void bookRoom(@RequestBody List<Customer> customers, @PathVariable String username,
			HttpServletResponse response) {

		Iterable<Customer> persistedCustomers = dao.findAll();
		for (Customer customer : persistedCustomers) {

			System.out.println("user name" + customer.getUsername());

			if (username.equals(customer.getUsername())) {
				System.out.println("user exist.");
				response.setStatus(400);
			} else {
				dao.saveAll(customers);

				totalRoom = totalRoom - customers.size();
				System.out.println("totalRoom booked" + totalRoom);
				response.setStatus(201);

			}

		}

	}

	@GetMapping(value = "/customer/{id}")
	public Optional<Customer> getCustomer(@PathVariable int id, HttpServletResponse response) {

		Optional<Customer> cust = dao.findById(id);
		if (cust == null) {
			response.setStatus(404);
			return cust;
		} else {
			response.setStatus(200);
			return cust;
		}

	}

}
