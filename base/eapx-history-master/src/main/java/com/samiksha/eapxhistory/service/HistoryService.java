package com.samiksha.eapxhistory.service;

import com.samiksha.eapxhistory.config.Config;
import com.samiksha.eapxhistory.entity.History;
import com.samiksha.eapxhistory.entity.Pagination;
import com.samiksha.eapxhistory.entity.Reservation;
import com.samiksha.eapxhistory.repository.ReservationRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {
    @Autowired
    private ReservationRepository repository;

    @Autowired
    private Config config;

    private int getPage(long pages, int page) {
        if (page < 1) {
            return 1;
        }

        if (page > pages) {
            return (int) pages;
        }

        return page;
    }

    private History paginate(Page<Reservation> reservations, int page, int perPage, long pages) {
        //prepare pagination object
        Pagination pagination = new Pagination();
        pagination.setCurrent(page);
        pagination.setPages(pages);
        pagination.setPerPage(perPage);

        //prepare history response
        History history = new History();
        history.setStatus(200);
        history.setMessage("History load success!");
        history.setPagination(pagination);
        history.setReservations(reservations.getContent());

        //return response
        return history;
    }

    public History getAllReservations(int page) {
        //get total number of documents in the collection
        long pages = repository.count();

        //format current page
        int currentPage = getPage(pages, page);

        //get pagination size
        int dataPerPage = (int) config.get("pagination.size");

        //create a pagination object
        PageRequest pageable = PageRequest.of(currentPage, dataPerPage);

        //fetch paginated documents
        Page<Reservation> reservations = repository.findAll(pageable);

        //return paginated request
        return paginate(reservations, currentPage, dataPerPage, pages);
    }

    public History getReservationsByUser(ObjectId userId, int page) {
        //get total number of documents in the collection
        long pages = repository.countAllByUserId(userId);

        //format current page
        int currentPage = getPage(pages, page);

        //get pagination size
        int dataPerPage = (int) config.get("pagination.size");

        //create a pagination object
        PageRequest pageable = PageRequest.of(currentPage, dataPerPage);

        //fetch paginated documents
        Page<Reservation> reservations = repository.findAllByUserId(userId, pageable);

        //return paginated request
        return paginate(reservations, currentPage, dataPerPage, pages);
    }
}
