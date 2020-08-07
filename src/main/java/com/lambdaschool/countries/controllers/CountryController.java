package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{
    @Autowired
    CountryRepository countryrepos;

    private List<Country> findCountries(List<Country> myList, CheckCountry tester)
    {
        List<Country> tempList = new ArrayList<>();

        for (Country c : myList)
        {
            if(tester.test(c))
            {
                tempList.add(c);
            }
        }
        return tempList;
    }

    // http://localhost:2020/names/all
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll()
            .iterator()
            .forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // http://localhost:2020/names/start/u
    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listAllByName(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll()
            .iterator()
            .forEachRemaining(myList::add);
        List<Country> rtnList = findCountries(myList,
            c -> c.getName()
                .charAt(0) == letter); // c is parameter, get name, 1st character

        for (Country c : rtnList)
        {
            System.out.println(c);
        }
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // http://localhost:2020/population/total
    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> listAllPopTotals()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll()
            .iterator()
            .forEachRemaining(myList::add);

        double totalPopulation = 0;
        for (Country c : myList)
        {
            totalPopulation = totalPopulation + c.getPopulation();
        }
        System.out.println("The Total Population is " + totalPopulation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2020/population/min
     @GetMapping(value = "/population/min", produces = {"application/json"})
     public ResponseEntity<?> listPopMin()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll()
            .iterator()
            .forEachRemaining(myList::add);
        myList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // http://localhost:2020/population/max

    // STRETCH http://localhost:2020/population/median
}
