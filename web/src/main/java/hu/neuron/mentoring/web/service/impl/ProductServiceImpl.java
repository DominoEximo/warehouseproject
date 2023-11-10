package hu.neuron.mentoring.web.service.impl;

import hu.neuron.mentoring.client_api.Product;
import hu.neuron.mentoring.web.service.ProductService;

import jakarta.ws.rs.GET;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Path("/ProductService")
public class ProductServiceImpl implements ProductService {

    @Path("/getProducts")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Override
    public List<Product> getProducts() {
        List<Product> mockedData = new ArrayList<>();

        mockedData.add(new Product("Alma","Gyümölcs",3,"kg",new BigDecimal(100),new BigDecimal(200),"Magyar alma"));
        mockedData.add(new Product("Körte","Gyümölcs",5,"kg",new BigDecimal(40),new BigDecimal(300),"Lengyel körte"));
        mockedData.add(new Product("Barack","Gyümölcs",2,"kg",new BigDecimal(50),new BigDecimal(120),"Holland barack"));
        mockedData.add(new Product("Csirke","Hús",10,"kg",new BigDecimal(25),new BigDecimal(30),"Magyar csirke"));
        mockedData.add(new Product("Sertés","Hús",15,"kg",new BigDecimal(30),new BigDecimal(110),"Magyar sertés"));

        return mockedData;
    }
}
