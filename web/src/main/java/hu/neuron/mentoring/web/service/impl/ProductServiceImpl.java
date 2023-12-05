package hu.neuron.mentoring.web.service.impl;


import hu.neuron.mentoring.clientapi.dao.CategoryDAO;
import hu.neuron.mentoring.clientapi.dao.ProductDAO;
import hu.neuron.mentoring.clientapi.dao.UnitDAO;
import hu.neuron.mentoring.clientapi.datasource.DatasourceConfig;
import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.entity.Unit;
import hu.neuron.mentoring.web.service.ProductService;

import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/ProductService")
public class ProductServiceImpl implements ProductService {

    @Path("/getProducts/{page}/{length}/{category}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})

    public List<Product> getProducts(@PathParam("page") int page, @PathParam("length") int length, @PathParam("category") int categoryId) {
        List<Product> mockedData;
        DatasourceConfig.getInstance();

        ProductDAO productDAO = ProductDAO.getInstance();


        mockedData = productDAO.getByCategoryPageinated(page,length,categoryId);

        return mockedData;
    }

    @Path("/getProducts///")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Product> getProducts() {
        List<Product> mockedData;


        DatasourceConfig.getInstance();

        ProductDAO productDAO = ProductDAO.getInstance();

        mockedData = productDAO.getAll();

        return mockedData;
    }
    @Path("/getProducts/{page}/{length}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Override
    public List<Product> getProducts(@PathParam("page") int page,@PathParam("length") int length) {
        List<Product> mockedData;


        DatasourceConfig.getInstance();

        ProductDAO productDAO = ProductDAO.getInstance();


            mockedData = productDAO.getAllPageinated(page,length);

        return mockedData;
    }
    @Path("/addProduct")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public void addProduct( Product product){

        DatasourceConfig.getInstance();
        ProductDAO productDAO = ProductDAO.getInstance();
        productDAO.save(product);
    }

    @Path("/getCategories")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Override
    public List<Category> getCategories() {

        CategoryDAO categoryDAO = CategoryDAO.getInstance();

        return categoryDAO.getAll();
    }

    @Path("/getUnits")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Override
    public List<Unit> getUnits() {

        UnitDAO unitDao = UnitDAO.getInstance();

        return unitDao.getAll();
    }
}
