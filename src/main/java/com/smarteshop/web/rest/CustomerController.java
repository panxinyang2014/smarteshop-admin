package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.Customer;
import com.smarteshop.service.AddressService;
import com.smarteshop.service.CustomerService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Customer.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController extends AbstractController<Customer>{

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;

    /**
     * POST  /customers : Create a new customer.
     *
     * @param customer the customer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customer, or with status 400 (Bad Request) if the customer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping()
    @Timed
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws URISyntaxException {
        log.debug("REST request to save Customer : {}", customer);
        if (customer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("customer", "idexists", "A new customer cannot already have an ID")).body(null);
        }
        Customer result = customerService.save(customer);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("customer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customers : Updates an existing customer.
     *
     * @param customer the customer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customer,
     * or with status 400 (Bad Request) if the customer is not valid,
     * or with status 500 (Internal Server Error) if the customer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping()
    @Timed
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws URISyntaxException {
        log.debug("REST request to update Customer : {}", customer);
        if (customer.getId() == null) {
            return createCustomer(customer);
        }
        Customer result = customerService.save(customer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("customer", customer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customers : get all the customers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping()
    @Timed
    public ResponseEntity<List<Customer>> getAllCustomers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Customers");
        Page<Customer> page = customerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customers/:id : get the "id" customer.
     *
     * @param id the id of the customer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customer, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        log.debug("REST request to get Customer : {}", id);
        Customer customer = customerService.findOne(id);
        return Optional.ofNullable(customer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customers/:id : delete the "id" customer.
     *
     * @param id the id of the customer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.debug("REST request to delete Customer : {}", id);
        customerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customer", id.toString())).build();
    }

    /**
     * SEARCH  /_search/customers?query=:query : search for the customer corresponding
     * to the query.
     *
     * @param query the query of the customer search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search")
    @Timed
    public ResponseEntity<List<Customer>> searchCustomers(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Customers for query {}", query);
        Page<Customer> page = customerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/customers/_search");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
