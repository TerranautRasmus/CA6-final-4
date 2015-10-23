package controller;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import model.Car;

public class MainController {
    
    EntityManagerFactory emf;
    Query query;
    
    public MainController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public Car addCar(Car car) {
        EntityManager em = emf.createEntityManager();

        if (car == null) {
            throw new UnsupportedOperationException();
        }

        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }

        return car;
    }
    
    public List<Car> getCars() {
        EntityManager em = emf.createEntityManager();
        
        return em.createNamedQuery("car.FindAll").getResultList();
    }
    
    public void deleteCar(long id) {
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(em.find(Car.class, id));
        em.getTransaction().commit();
        System.out.println("test");
    }
    
    public void editCar(Car car) {
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(car);
        em.getTransaction().commit();
    }
}
