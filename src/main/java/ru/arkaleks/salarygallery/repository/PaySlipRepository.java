package ru.arkaleks.salarygallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.PaySlip;

import java.util.List;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface PaySlipRepository extends JpaRepository<PaySlip, Integer> {


    @Query("SELECT ps FROM PaySlip ps WHERE ps.employee = :employee")
    List<PaySlip> findBy(@Param("employee") Employee employee);

    @Modifying
    @Query("DELETE FROM PaySlip ps WHERE ps.id = :paySlipId")
    void deletePaySlipById(@Param("paySlipId")Integer paySlipId);
}
