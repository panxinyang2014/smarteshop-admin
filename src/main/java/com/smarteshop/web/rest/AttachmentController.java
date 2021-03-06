package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
import com.smarteshop.domain.Attachment;
import com.smarteshop.service.AttachmentService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Attachment.
 */
@RestController
@RequestMapping("/api/attachments")
public class AttachmentController extends AbstractController<Attachment>{

    private final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    @Inject
    private AttachmentService attachmentService;

    /**
     * POST  /attachments : Create a new attachment.
     *
     * @param attachment the attachment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attachment, or with status 400 (Bad Request) if the attachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PostMapping()
    public ResponseEntity<Attachment> createAttachment(@Valid @RequestBody Attachment attachment) throws URISyntaxException {
        log.debug("REST request to save Attachment : {}", attachment);
        if (attachment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("attachment", "idexists", "A new attachment cannot already have an ID")).body(null);
        }
        Attachment result = attachmentService.save(attachment);
        return ResponseEntity.created(new URI("/api/attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("attachment", result.getId().toString()))
            .body(result);
    }

    
    /**
     * GET  /attachments : get all the attachments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of attachments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @Timed
    @GetMapping()
    public ResponseEntity<List<Attachment>> getAllAttachments(Pageable pageable, String entityName, Long entityId)
        throws URISyntaxException {
        log.debug("REST request to get a page of Attachments");
        List<Attachment> list = attachmentService.getAttachmentsByEntityInfo(entityName, entityId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    /**
     * GET  /attachments/:id : get the "id" attachment.
     *
     * @param id the id of the attachment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attachment, or with status 404 (Not Found)
     */
    @Timed
    @GetMapping("/{id}")
    public ResponseEntity<Attachment> getAttachment(@PathVariable Long id) {
        log.debug("REST request to get Attachment : {}", id);
        Attachment attachment = attachmentService.findOne(id);
        return Optional.ofNullable(attachment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /attachments/:id : delete the "id" attachment.
     *
     * @param id the id of the attachment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Timed
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        log.debug("REST request to delete Attachment : {}", id);
        attachmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("attachment", id.toString())).build();
    }


    @Timed
    @GetMapping("/{entityName}/{entityId}/thumbnail")
    public ResponseEntity<byte[]> getThumbnail(@PathVariable  String entityName, @PathVariable  Long entityId)
        throws URISyntaxException {
        log.debug("REST request to get a thumbnail {} {}", entityName, entityId);
        byte[] thumbnail = null;
        List<Attachment> list = attachmentService.getAttachmentsByEntityInfo(entityName, entityId);
        if(!CollectionUtils.isEmpty(list)){
        	thumbnail = list.get(0).getContent();
        }
        return new ResponseEntity<>(thumbnail, HttpStatus.OK);
    }

    /**
     * SEARCH  /_search/attachments?query=:query : search for the attachment corresponding
     * to the query.
     *
     * @param query the query of the attachment search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @Timed
    @GetMapping("/_search")
    public ResponseEntity<List<Attachment>> searchAttachments(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Attachments for query {}", query);
        Page<Attachment> page = attachmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/attachments/_search");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
