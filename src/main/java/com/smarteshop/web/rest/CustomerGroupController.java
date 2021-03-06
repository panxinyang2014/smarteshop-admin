package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.smarteshop.domain.CustomerGroup;
import com.smarteshop.service.CustomerGroupService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing CustomerGroup.
 */
@RestController
@RequestMapping("/api/customer-groups")
public class CustomerGroupController extends AbstractController<CustomerGroup>{

    private final Logger log = LoggerFactory.getLogger(CustomerGroupController.class);

    @Inject
    private CustomerGroupService customerGroupService;

    /**
     * POST  /customer-groups : Create a new customerGroup.
     *
     * @param customerGroup the customerGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerGroup, or with status 400 (Bad Request) if the customerGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping()
    @Timed
    public ResponseEntity<CustomerGroup> createCustomerGroup(@RequestBody CustomerGroup customerGroup) throws URISyntaxException {
        log.debug("REST request to save CustomerGroup : {}", customerGroup);
        if (customerGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("customerGroup", "idexists", "A new customerGroup cannot already have an ID")).body(null);
        }
        CustomerGroup result = customerGroupService.save(customerGroup);
        return ResponseEntity.created(new URI("/api/customer-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("customerGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-groups : Updates an existing customerGroup.
     *
     * @param customerGroup the customerGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerGroup,
     * or with status 400 (Bad Request) if the customerGroup is not valid,
     * or with status 500 (Internal Server Error) if the customerGroup couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping()
    @Timed
    public ResponseEntity<CustomerGroup> updateCustomerGroup(@RequestBody CustomerGroup customerGroup) throws URISyntaxException {
        log.debug("REST request to update CustomerGroup : {}", customerGroup);
        if (customerGroup.getId() == null) {
            return createCustomerGroup(customerGroup);
        }
        CustomerGroup result = customerGroupService.save(customerGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("customerGroup", customerGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-groups : get all the customerGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customerGroups in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping()
    @Timed
    public ResponseEntity<List<CustomerGroup>> getAllCustomerGroups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CustomerGroups");
        Page<CustomerGroup> page = customerGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-groups/:id : get the "id" customerGroup.
     *
     * @param id the id of the customerGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerGroup, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<CustomerGroup> getCustomerGroup(@PathVariable Long id) {
        log.debug("REST request to get CustomerGroup : {}", id);
        CustomerGroup customerGroup = customerGroupService.findOne(id);
        return Optional.ofNullable(customerGroup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customer-groups/:id : delete the "id" customerGroup.
     *
     * @param id the id of the customerGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerGroup(@PathVariable Long id) {
        log.debug("REST request to delete CustomerGroup : {}", id);
        customerGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customerGroup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/customer-groups?query=:query : search for the customerGroup corresponding
     * to the query.
     *
     * @param query the query of the customerGroup search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search")
    @Timed
    public ResponseEntity<List<CustomerGroup>> searchCustomerGroups(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of CustomerGroups for query {}", query);
        Page<CustomerGroup> page = customerGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/customer-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
