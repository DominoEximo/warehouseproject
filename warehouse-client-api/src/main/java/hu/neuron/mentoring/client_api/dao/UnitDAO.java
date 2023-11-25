package hu.neuron.mentoring.client_api.dao;

import hu.neuron.mentoring.client_api.Category;
import hu.neuron.mentoring.client_api.Unit;
import hu.neuron.mentoring.client_api.datasource.DatasourceConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UnitDAO implements DAO<Unit>{

    static EntityManagerFactory emf;
    static EntityManager em;

    private static UnitDAO instance = null;

    public static synchronized UnitDAO getInstance() {
        if (instance == null){
            emf = Persistence
                    .createEntityManagerFactory("ProductPU");;
            em = emf.createEntityManager();
            instance = new UnitDAO();
            DatasourceConfig.getInstance();
        }

        return  instance;
    }
    @Override
    public Unit findById(int id) {
        return em.find(Unit.class,id);
    }


    public Unit findByName(String name){
        TypedQuery<Unit> query = em.createQuery(
                "SELECT u FROM Unit u WHERE u.unitName = :name" , Unit.class);

        Unit unit = query.setParameter("name", name).getSingleResult();
        return unit;
    }
    @Override
    public List<Unit> getAll() {
        return em.createQuery("SELECT u FROM Unit u", Unit.class).getResultList();
    }

    @Override
    public void save(Unit unit) {
        em.getTransaction().begin();
        em.persist(unit);
        em.getTransaction().commit();
    }

    @Override
    public Unit update(Unit unit) {
        return em.merge(unit);
    }

    @Override
    public void delete(Unit unit) {
        em.remove(unit);
    }
}
