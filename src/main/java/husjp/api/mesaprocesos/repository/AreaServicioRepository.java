package husjp.api.mesaprocesos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import husjp.api.mesaprocesos.entity.Servicio;

public  interface AreaServicioRepository extends JpaRepository<Servicio,Integer> {

}

