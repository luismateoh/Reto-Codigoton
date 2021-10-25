package com.codigoton.dinnerforclients.repository;


import com.codigoton.dinnerforclients.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {


    @Query(value = "SELECT Client.ID, CODE, MALE, TYPE, LOCATION,COMPANY, ENCRYPT, SUM(balance) AS totalBalance\n" +
            "from Client left JOIN account ON client.id = client_id\n" +
            "GROUP BY client.id" +
            "ORDER BY totalBalance DESC :TC :UG :RI :RF", nativeQuery = true)
    Iterable<Client> findClientsSumBalance(@Param("TC") String TC,
                                           @Param("UG") String UG,
                                           @Param("RI") String RI,
                                           @Param("RF") String RF);


}
