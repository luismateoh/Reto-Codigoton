package com.codigoton.dinnerforclients.service;

import com.codigoton.dinnerforclients.dto.Filter;
import com.codigoton.dinnerforclients.entity.Account;
import com.codigoton.dinnerforclients.entity.Client;
import com.codigoton.dinnerforclients.repository.ClientRepository;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TablesService {

    private static Session em;
    private final Path root = Paths.get("files");
    private final ModelMapper modelMapper;
    @Autowired
    private ClientRepository clientRepository;


    public TablesService(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    private static List<Object[]> queryWithFilters(Filter filter) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<Client> c = q.from(Client.class);
        Root<Account> a = q.from(Account.class);
        q.multiselect(c.get("id"),
                c.get("code"),
                c.get("male"),
                c.get("type"),
                c.get("location"),
                c.get("company"),
                c.get("encrypt"),
                cb.sum(a.get("balance")).as("total_balance".getClass())
        );

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(c.get("id"), a.get("client_id")));
        if (filter.getTC() != null) {
            predicates.add(cb.equal(c.get("type"), filter.getTC()));
        }
        if (filter.getUG() != null) {
            predicates.add(cb.equal(c.get("location"), filter.getUG()));
        }
        if (filter.getRI() != null) {
            predicates.add(cb.greaterThanOrEqualTo(a.get("total_balance"), filter.getRI()));
        }
        if (filter.getRF() != null) {
            predicates.add(cb.lessThanOrEqualTo(a.get("total_balance"), filter.getRF()));
        }

        q.where(
                cb.and(predicates.toArray(new Predicate[0]))
        );
        q.groupBy(c.get("id"));
        TypedQuery<Object[]> query = em.createQuery(q);
        List<Object[]> results = query.getResultList();
        for (Object[] result : results) {

            System.out.println(result.length);
            System.out.println(
                    result[0] + ", " + result[1] + ", " + result[2] + ", " + result[3] + ", " + result[4] + ", " + result[5] + ", " + result[6] + ", " + result[7]);
        }

        return results;

    }

    private static List<Filter> readFiltersFile(File file) {
        List<Filter> result = new ArrayList<>();
        Filter filter = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Pattern name = Pattern.compile("^<.>*");//. represents single character
                Matcher m = name.matcher(line);
                if (m.matches()) {
                    filter = new Filter();
                    filter.setName(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;


    }

    public Resource tablesAssignment(File file) {

        List<Filter> filters = readFiltersFile(file);

        List<Object[]> result = queryWithFilters(filters.get(0));


        try {
            FileWriter myWriter = new FileWriter(root + "/salida.txt");
            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            Path filePath = root.resolve("salida.txt");
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }

    }

}
